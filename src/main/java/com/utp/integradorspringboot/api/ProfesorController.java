/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Profesor;
import com.utp.integradorspringboot.repositories.ProfesorRepository;
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
public class ProfesorController {

    @Autowired
    ProfesorRepository repository;

    @GetMapping("/profesor")
    public ResponseEntity<List<Profesor>> getAll(@RequestParam(required = false) String title) {
        try {
            List<Profesor> lista = new ArrayList<Profesor>();
            repository.findAll().forEach(lista::add);
            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profesor/{id}")
    public ResponseEntity<Profesor> getById(@PathVariable("id") Long id) {
        Optional<Profesor> entidad = repository.findById(id);
        if (entidad.isPresent()) {
            return new ResponseEntity<>(entidad.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/profesor")
    public ResponseEntity<Profesor> create(@RequestBody Profesor entidad) {
        try {
            Profesor _entidad = repository.save(new Profesor(null, entidad.getNombres(), entidad.getApellidos()));
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/profesor/{id}")
    public ResponseEntity<Profesor> update(@PathVariable("id") Long id, @RequestBody Profesor entidad) {
        Profesor _entidad = repository.findById(id).orElse(null);
        if (_entidad != null) {
            _entidad.setNombres(entidad.getNombres());
            _entidad.setApellidos(entidad.getApellidos());
            return new ResponseEntity<>(repository.save(_entidad), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/profesor/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
