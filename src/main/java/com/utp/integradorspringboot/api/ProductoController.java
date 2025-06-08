package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Producto;
import com.utp.integradorspringboot.services.ProductoService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listar() {
        return productoService.listarTodos();
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        producto.setIdRestaurante(1L);
        producto.setFechaIngreso(LocalDate.now().toString());

        return productoService.guardar(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        Optional<Producto> existente = productoService.buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Producto actualizado = existente.get();
        actualizado.setNombre(producto.getNombre());
        actualizado.setCategoria(producto.getCategoria());
        actualizado.setCantidad(producto.getCantidad());
        actualizado.setCantidadMinima(producto.getCantidadMinima());
        actualizado.setFechaVencimiento(producto.getFechaVencimiento());
        actualizado.setEstado(producto.getEstado());

        return ResponseEntity.ok(productoService.guardar(actualizado));
    }
    @PutMapping("/inactivar/{id}")
    public ResponseEntity<Producto> inactivar(@PathVariable Long id) {
        Optional<Producto> existente = productoService.buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Producto producto = existente.get();
        producto.setEstado("inactivo");

        return ResponseEntity.ok(productoService.guardar(producto));
    }

    @GetMapping("/excel")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        List<Producto> productos = productoService.listarTodos();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inventario");

        Row header = sheet.createRow(0);
        String[] columnas = {"ID", "Nombre", "Categoría", "Cantidad", "Stock mínimo", "Vencimiento", "Estado"};
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        int fila = 1;
        for (Producto p : productos) {
            Row row = sheet.createRow(fila++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getNombre());
            row.createCell(2).setCellValue(p.getCategoria());
            row.createCell(3).setCellValue(p.getCantidad());
            row.createCell(4).setCellValue(p.getCantidadMinima());
            row.createCell(5).setCellValue(p.getFechaVencimiento() != null ? p.getFechaVencimiento() : "");
            row.createCell(6).setCellValue(p.getEstado());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Inventario.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
