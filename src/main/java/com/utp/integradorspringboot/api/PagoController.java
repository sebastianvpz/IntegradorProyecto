package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Pago;
import com.utp.integradorspringboot.repositories.PagoRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PagoController {

    @Autowired
    private PagoRepository repository;

    // Obtener todos los pagos
    @GetMapping("/pagos")
    public ResponseEntity<List<Pago>> getAll() {
        try {
            List<Pago> lista = repository.findAll();
            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener pago por ID
    @GetMapping("/pagos/{id}")
    public ResponseEntity<Pago> getById(@PathVariable("id") Long id) {
        Optional<Pago> entidad = repository.findById(id);
        return entidad.map(pago -> new ResponseEntity<>(pago, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un nuevo pago
    @PostMapping("/pagos")
    public ResponseEntity<Pago> create(@RequestBody Pago entidad) {
        try {
            entidad.setFechaPago(LocalDateTime.now()); // ← asigna fecha actual
            Pago nuevoPago = repository.save(entidad);
            return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar un pago
    @PutMapping("/pagos/{id}")
    public ResponseEntity<Pago> update(@PathVariable("id") Long id, @RequestBody Pago entidad) {
        Optional<Pago> optionalPago = repository.findById(id);
        if (optionalPago.isPresent()) {
            Pago existente = optionalPago.get();
            existente.setIdPedido(entidad.getIdPedido());
            existente.setMonto(entidad.getMonto());
            existente.setMedioPago(entidad.getMedioPago());
            existente.setObservacion(entidad.getObservacion());
            existente.setFechaPago(entidad.getFechaPago());
            existente.setIdUsuario(entidad.getIdUsuario());

            return new ResponseEntity<>(repository.save(existente), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un pago
    @DeleteMapping("/pagos/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
