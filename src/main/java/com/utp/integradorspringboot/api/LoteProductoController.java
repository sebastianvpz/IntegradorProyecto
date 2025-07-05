package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.LoteProducto;
import com.utp.integradorspringboot.security.JwtUtil;
import com.utp.integradorspringboot.services.LoteProductoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los lotes de productos.
 * Acceso restringido por restaurante del usuario autenticado.
 */
@RestController
@RequestMapping("/api/lotes")
@CrossOrigin
public class LoteProductoController {

    @Autowired
    private LoteProductoService loteProductoService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Lista todos los lotes activos del restaurante del usuario autenticado.
     */
    @GetMapping
    public List<LoteProducto> listar(
            @RequestParam Long productoId,
            HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        return loteProductoService.listarPorProductoYRestaurante(productoId, restauranteId);
    }

    /**
     * Crea un nuevo lote de producto asociado al restaurante autenticado.
     */
    @PostMapping
    public ResponseEntity<LoteProducto> crear(@RequestBody LoteProducto lote, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        LoteProducto guardado = loteProductoService.guardar(lote, restauranteId);
        return ResponseEntity.ok(guardado);
    }

    /**
     * Inactiva un lote de producto.
     */
    @PutMapping("/inactivar/{id}")
    public ResponseEntity<LoteProducto> inactivar(@PathVariable Long id) {
        LoteProducto inactivado = loteProductoService.inactivar(id);
        if (inactivado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inactivado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoteProducto> actualizar(
            @PathVariable Long id,
            @RequestBody LoteProducto lote,
            HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        LoteProducto actualizado = loteProductoService.actualizar(id, lote, restauranteId);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

}
