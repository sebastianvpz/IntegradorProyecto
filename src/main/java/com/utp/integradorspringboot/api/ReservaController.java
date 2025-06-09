package com.utp.integradorspringboot.controllers;

import com.utp.integradorspringboot.models.Reserva;
import com.utp.integradorspringboot.services.ReservaService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Mostrar página de reservas (vista HTML)
    @GetMapping
    public String mostrarReservas(Model model) {
        List<Reserva> reservas = reservaService.listarTodos();
        model.addAttribute("reservas", reservas);
        return "reservas";
    }

    // Crear reserva desde formulario web
    @PostMapping("/guardar")
    public String guardarReserva(@ModelAttribute Reserva reserva) {
        reserva.setFechaCreacion(LocalDate.now().toString());
        reserva.setIdRestaurante(1); // temporal
        reservaService.guardar(reserva);
        return "redirect:/reservas";
    }

    // Actualizar reserva desde formulario web
    @PostMapping("/actualizar")
    public String actualizarReserva(@ModelAttribute Reserva reserva) {
        Optional<Reserva> existente = reservaService.buscarPorId(reserva.getId());
        if (existente.isPresent()) {
            Reserva actual = existente.get();
            actual.setNombresComensal(reserva.getNombresComensal());
            actual.setApellidosComensal(reserva.getApellidosComensal());
            actual.setCorreoComensal(reserva.getCorreoComensal());
            actual.setTelefonoComensal(reserva.getTelefonoComensal());
            actual.setOcasion(reserva.getOcasion());
            actual.setFechaReserva(reserva.getFechaReserva());
            actual.setHoraReserva(reserva.getHoraReserva());
            actual.setNumeroPersonas(reserva.getNumeroPersonas());
            actual.setIdMesa(reserva.getIdMesa());
            actual.setEstado(reserva.getEstado());
            reservaService.guardar(actual);
        }
        return "redirect:/reservas";
    }

    // Inactivar reserva desde botón web
    @GetMapping("/inactivar/{id}")
    public String inactivarReserva(@PathVariable Long id) {
        Optional<Reserva> existente = reservaService.buscarPorId(id);
        existente.ifPresent(reserva -> {
            reserva.setEstado("cancelado");
            reservaService.guardar(reserva);
        });
        return "redirect:/reservas";
    }

    // Exportar reservas a Excel (desde botón en vista)
    @GetMapping("/exportar/excel")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        List<Reserva> reservas = reservaService.listarTodos();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reservas");

        String[] columnas = {"ID", "Nombre", "Apellido", "Correo", "Teléfono", "Fecha", "Hora", "Personas", "Mesa", "Estado"};
        Row header = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        int fila = 1;
        for (Reserva r : reservas) {
            Row row = sheet.createRow(fila++);
            row.createCell(0).setCellValue(r.getId());
            row.createCell(1).setCellValue(r.getNombresComensal());
            row.createCell(2).setCellValue(r.getApellidosComensal());
            row.createCell(3).setCellValue(r.getCorreoComensal());
            row.createCell(4).setCellValue(r.getTelefonoComensal());
            row.createCell(5).setCellValue(r.getFechaReserva());
            row.createCell(6).setCellValue(r.getHoraReserva());
            row.createCell(7).setCellValue(r.getNumeroPersonas());
            row.createCell(8).setCellValue(r.getIdMesa());
            row.createCell(9).setCellValue(r.getEstado());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Reservas.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
