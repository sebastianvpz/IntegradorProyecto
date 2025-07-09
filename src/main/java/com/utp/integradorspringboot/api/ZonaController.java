package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Zona;
import com.utp.integradorspringboot.security.JwtUtil;
import com.utp.integradorspringboot.services.ZonaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las zonas (pisos) de un restaurante.
 */
@RestController
@RequestMapping("/api/zonas")
@CrossOrigin
public class ZonaController {

    @Autowired
    private ZonaService zonaService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Lista todas las zonas activas de un restaurante.
     *
     * @param request la petición HTTP para extraer el token.
     * @return lista de zonas.
     */
    @GetMapping
    public ResponseEntity<List<Zona>> listar(HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        return ResponseEntity.ok(zonaService.listarPorRestaurante(restauranteId));
    }

    /**
     * Crea una nueva zona para el restaurante autenticado.
     *
     * @param zona la nueva zona.
     * @param request la petición HTTP para extraer el token.
     * @return la zona creada.
     */
    @PostMapping
    public ResponseEntity<Zona> crear(@RequestBody Zona zona, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        zona.setRestauranteId(restauranteId);
        return ResponseEntity.ok(zonaService.guardar(zona));
    }

    /**
     * Actualiza una zona existente.
     *
     * @param id el ID de la zona.
     * @param zona los datos actualizados.
     * @return la zona actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Zona> actualizar(@PathVariable Long id, @RequestBody Zona zona) {
        return ResponseEntity.ok(zonaService.actualizar(id, zona));
    }

    /**
     * Desactiva (elimina lógicamente) una zona.
     *
     * @param id el ID de la zona.
     * @return la zona con estado inactivo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Zona> desactivar(@PathVariable Long id) {
        Zona zona = zonaService.desactivar(id);
        return ResponseEntity.ok(zona);
    }

}
