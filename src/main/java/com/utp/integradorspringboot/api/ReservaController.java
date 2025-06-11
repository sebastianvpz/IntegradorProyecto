package com.utp.integradorspringboot.controllers;

import com.utp.integradorspringboot.models.Reserva;
import com.utp.integradorspringboot.services.ReservaService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 1) Mostrar todas las reservas (lista) en la vista "reservas.html"
     *    URL: GET /reservas
     */
    @GetMapping
    public String mostrarReservas(Model model) {
        List<Reserva> todas = reservaService.listarTodos();
        model.addAttribute("reservas", todas);
        return "reservas";
    }

    /**
     * 1.b) Búsqueda (para que /reservas/buscar no devuelva 404)
     *     URL: GET /reservas/buscar?filtro=...
     *
     *     POR AHORA SOLO SE MUESTRAN TODAS (sin filtrar). 
     *     Si quieres implementar un filtro real, podrías añadir un método en el repositorio.
     */
    @GetMapping("/buscar")
    public String buscarReservas(@RequestParam(required = false) String filtro, Model model) {
        // Por el momento, ignoro el parámetro "filtro" y devuelvo todas
        List<Reserva> todas = reservaService.listarTodos();
        model.addAttribute("reservas", todas);
        return "reservas";
    }

    /**
     * 2) Mostrar el formulario de "NUEVA RESERVA"
     *    URL: GET /reservas/nuevo
     */
    @GetMapping("/nuevo")
    public String formularioNuevaReserva(Model model) {
        Reserva reserva = new Reserva();
        model.addAttribute("reserva", reserva);
        return "nueva_reserva";
    }

    /**
     * 2.b) Mostrar el formulario para EDITAR una reserva existente
     *     URL: GET /reservas/editar/{id}
     */
    @GetMapping("/editar/{id}")
    public String formularioEditarReserva(@PathVariable Long id, Model model) {
        Optional<Reserva> opt = reservaService.buscarPorId(id);
        if (opt.isPresent()) {
            model.addAttribute("reserva", opt.get());
            return "nueva_reserva";
        } else {
            // Si no existe, redirigimos a la lista
            return "redirect:/reservas";
        }
    }

    /**
     * 3) Procesar el formulario y GUARDAR NUEVA reserva
     *    URL: POST /reservas/guardar
     */
    @PostMapping("/guardar")
    public String guardarReserva(@ModelAttribute("reserva") Reserva reserva) {
        // Asignamos fechaCreacion automáticamente
        reserva.setFechaCreacion(LocalDate.now().toString());
        // El campo "estado" ya viene por defecto como "reservado" en el modelo
        reservaService.guardar(reserva);
        return "redirect:/reservas";
    }

    /**
     * 4) Procesar el formulario y ACTUALIZAR reserva existente
     *    URL: POST /reservas/actualizar
     */
    @PostMapping("/actualizar")
    public String actualizarReserva(@ModelAttribute("reserva") Reserva reserva) {
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
            // Si quieres permitir cambiar el estado manualmente, déjalo; 
            // de lo contrario, podrías impedir modificar "estado" aquí.
            actual.setEstado(reserva.getEstado());
            reservaService.guardar(actual);
        }
        return "redirect:/reservas";
    }

    /**
     * 5) Inactivar (borrado lógico) una reserva por su ID
     *    URL: GET /reservas/inactivar/{id}
     */
    @GetMapping("/inactivar/{id}")
    public String inactivarReserva(@PathVariable Long id) {
        reservaService.inactivar(id);
        return "redirect:/reservas";
    }

    /**
     * 6) Exportar todas las reservas a Excel
     *    URL: GET /reservas/exportar/excel
     */
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
