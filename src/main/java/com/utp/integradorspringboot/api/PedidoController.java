/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Pedido;
import com.utp.integradorspringboot.repositories.PedidoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author jcerv
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PedidoController {

    @Autowired
    PedidoRepository repository;

    @GetMapping("/pedidos")
    public ResponseEntity<List<Pedido>> getAll() {
        List<Pedido> lista = repository.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<Pedido> getById(@PathVariable("id") Long id) {
        Optional<Pedido> dto = repository.findById(id);
        if (dto.isPresent()) {
            return new ResponseEntity<>(dto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/pedidos")
    public ResponseEntity<Pedido> create(@RequestBody Pedido dto) {
        Pedido nuevo = new Pedido();
        nuevo.setIdRestaurante(dto.getIdRestaurante());
        nuevo.setIdMesa(dto.getIdMesa());
        nuevo.setIdUsuario(dto.getIdUsuario());
        nuevo.setFechaCreacion(LocalDate.now().toString());
        nuevo.setEstadoEntrega("en preparación");
        nuevo.setEstadoPago("no pagado");
        nuevo.setSubtotal(dto.getSubtotal());
        nuevo.setDescuento(dto.getDescuento());
        nuevo.setCostoFinal(dto.getCostoFinal());
        Pedido saved = repository.save(nuevo);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/pedidos/{id}")
    public ResponseEntity<Pedido> update(@PathVariable("id") Long id, @RequestBody Pedido dto) {
        Pedido nuevo = repository.findById(id).orElse(null);
        if (nuevo != null) {
            nuevo.setIdMesa(dto.getIdMesa());
            return new ResponseEntity<>(repository.save(nuevo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
