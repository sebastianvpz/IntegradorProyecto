package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Producto;
import com.utp.integradorspringboot.security.JwtUtil;
import com.utp.integradorspringboot.services.ProductoService;
import jakarta.servlet.http.HttpServletRequest;
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

/**
 * API REST que expone las operaciones del módulo de inventario.
 */
@RestController
@RequestMapping("/api/productos")
@CrossOrigin
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private JwtUtil jwtUtil;


    /**
     * Obtiene todos los productos activos y pertenecientes a su restaurante.
     *
     * @return Lista de productos.
     */
    @GetMapping
    public List<Producto> listar(HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        return productoService.listarPorRestaurante(restauranteId);
    }

    /**
     * Crea un nuevo producto.
     *
     * @param producto Producto recibido desde el frontend.
     * @return Producto creado.
     */
    @PostMapping
    public Producto crear(@RequestBody Producto producto, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        producto.setIdRestaurante(restauranteId);
        producto.setFechaIngreso(LocalDate.now().toString());
        return productoService.guardar(producto);
    }


    /**
     * Actualiza un producto existente.
     *
     * @param id        ID del producto a actualizar.
     * @param producto  Datos nuevos del producto.
     * @param request token del usuario logeado
     * @return Producto actualizado o error si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);

        Optional<Producto> existente = productoService.buscarPorId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Producto actual = existente.get();
        if (!actual.getIdRestaurante().equals(restauranteId)) {
            return ResponseEntity.status(403).build();
        }

        actual.setNombre(producto.getNombre());
        actual.setCategoria(producto.getCategoria());
        actual.setCantidad(producto.getCantidad());
        actual.setCantidadMinima(producto.getCantidadMinima());
        actual.setFechaVencimiento(producto.getFechaVencimiento());
        actual.setEstado(producto.getEstado());

        return ResponseEntity.ok(productoService.guardar(actual));
    }


    /**
     * Desactiva un producto cambiando su estado a "inactivo".
     *
     * @param id ID del producto a desactivar.
     * @return Producto actualizado con estado inactivo.
     */
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

    /**
     * Exporta todos los productos activos a un archivo Excel.
     *
     * @param response Objeto HTTP para escribir el archivo Excel.
     * @throws IOException si ocurre un error al generar el archivo.
     */
    @GetMapping("/excel")
    public void exportarExcel(HttpServletRequest request ,HttpServletResponse response) throws IOException {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        List<Producto> productos = productoService.listarPorRestaurante(restauranteId);


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
