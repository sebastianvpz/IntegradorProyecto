package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Pago;
import com.utp.integradorspringboot.models.Pedido;
import com.utp.integradorspringboot.repositories.PagoRepository;
import com.utp.integradorspringboot.repositories.PedidoRepository;
import com.utp.integradorspringboot.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PagoController {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/pagos")
    public ResponseEntity<List<Pago>> getAll(HttpServletRequest request) {
        Long restauranteId = jwtUtil.extraerRestauranteId(jwtUtil.obtenerTokenDesdeRequest(request));
        List<Pago> lista = pagoRepository.findAll()
                .stream()
                .filter(p -> {
                    Optional<Pedido> pedido = pedidoRepository.findById(p.getPedidoId());
                    return pedido.isPresent() && pedido.get().getRestauranteId().equals(restauranteId);
                })
                .toList();

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/pagos/{id}")
    public ResponseEntity<Pago> getById(@PathVariable("id") Long id, HttpServletRequest request) {
        Long restauranteId = jwtUtil.extraerRestauranteId(jwtUtil.obtenerTokenDesdeRequest(request));
        Optional<Pago> pagoOpt = pagoRepository.findById(id);

        if (pagoOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Pago pago = pagoOpt.get();
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pago.getPedidoId());
        if (pedidoOpt.isEmpty() || !pedidoOpt.get().getRestauranteId().equals(restauranteId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(pago, HttpStatus.OK);
    }

    @PostMapping("/pagos")
    public ResponseEntity<String> create(@RequestBody Pago entidad, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long usuarioId = jwtUtil.extraerUsuarioId(token);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);

        Optional<Pedido> pedidoOpt = pedidoRepository.findById(entidad.getPedidoId());
        if (pedidoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pedido no encontrado");
        }

        Pedido pedido = pedidoOpt.get();
        if (!pedido.getRestauranteId().equals(restauranteId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes pagar un pedido de otro restaurante");
        }

        entidad.setUsuarioId(usuarioId);
        entidad.setFechaPago(LocalDateTime.now());
        pagoRepository.save(entidad);

        return ResponseEntity.status(HttpStatus.CREATED).body("Pago registrado");
    }

    @PutMapping("/pagos/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody Pago entidad, HttpServletRequest request) {
        Long restauranteId = jwtUtil.extraerRestauranteId(jwtUtil.obtenerTokenDesdeRequest(request));
        Optional<Pago> pagoOpt = pagoRepository.findById(id);

        if (pagoOpt.isEmpty()) {
            return new ResponseEntity<>("Pago no encontrado", HttpStatus.NOT_FOUND);
        }

        Pago existente = pagoOpt.get();
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(existente.getPedidoId());
        if (pedidoOpt.isEmpty() || !pedidoOpt.get().getRestauranteId().equals(restauranteId)) {
            return new ResponseEntity<>("No puedes modificar pagos de otro restaurante", HttpStatus.FORBIDDEN);
        }

        existente.setMonto(entidad.getMonto());
        existente.setMedioPago(entidad.getMedioPago());
        existente.setObservacion(entidad.getObservacion());
        existente.setFechaPago(entidad.getFechaPago());
        pagoRepository.save(existente);

        return new ResponseEntity<>("Pago actualizado", HttpStatus.OK);
    }

    @DeleteMapping("/pagos/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        Long restauranteId = jwtUtil.extraerRestauranteId(jwtUtil.obtenerTokenDesdeRequest(request));
        Optional<Pago> pagoOpt = pagoRepository.findById(id);

        if (pagoOpt.isEmpty()) {
            return new ResponseEntity<>("Pago no encontrado", HttpStatus.NOT_FOUND);
        }

        Pago pago = pagoOpt.get();
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pago.getPedidoId());
        if (pedidoOpt.isEmpty() || !pedidoOpt.get().getRestauranteId().equals(restauranteId)) {
            return new ResponseEntity<>("No puedes eliminar pagos de otro restaurante", HttpStatus.FORBIDDEN);
        }

        pagoRepository.deleteById(id);
        return new ResponseEntity<>("Pago eliminado", HttpStatus.NO_CONTENT);
    }
}
