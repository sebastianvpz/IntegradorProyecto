package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.ProductoSolicitadoDto;
import com.utp.integradorspringboot.models.ProductoSolicitado;
import com.utp.integradorspringboot.security.JwtUtil;
import com.utp.integradorspringboot.services.ProductoSolicitadoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solicitados")
@CrossOrigin
public class ProductoSolicitadoController {

    @Autowired
    private ProductoSolicitadoService service;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<ProductoSolicitadoDto>> listar(HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        return ResponseEntity.ok(service.listarPorRestaurante(restauranteId));
    }

    @GetMapping("/pendientes")
    public List<ProductoSolicitado> listarPendientes(HttpServletRequest request) {
        Long restauranteId = jwtUtil.extraerRestauranteId(jwtUtil.obtenerTokenDesdeRequest(request));
        return service.listarPendientes(restauranteId);
    }

    @PostMapping
    public ResponseEntity<ProductoSolicitado> crear(@RequestBody ProductoSolicitado solicitado) {
        return ResponseEntity.ok(service.guardar(solicitado));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ProductoSolicitado> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        String estado = body.get("estado");
        ProductoSolicitado actualizado = service.actualizarEstado(id, estado);
        return actualizado != null
                ? ResponseEntity.ok(actualizado)
                : ResponseEntity.notFound().build();
    }

}
