package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Carta;
import com.utp.integradorspringboot.repositories.CartaRepository;
import com.utp.integradorspringboot.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CartaController {

    @Autowired
    private CartaRepository repository;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/platos")
    public ResponseEntity<List<Carta>> getAll(HttpServletRequest request) {
        try {
            String token = jwtUtil.obtenerTokenDesdeRequest(request);
            Long restauranteId = jwtUtil.extraerRestauranteId(token);

            List<Carta> lista = repository.findByIdRestauranteAndEstado(restauranteId, "activo");

            System.out.println(lista);
            if (lista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/platos/{id}")
    public ResponseEntity<Carta> getById(@PathVariable("id") Long id) {
        Optional<Carta> entidad = repository.findById(id);
        return entidad.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/platos")
    public ResponseEntity<Carta> create(@RequestBody Carta entidad, HttpServletRequest request) {
        try {
            String token = jwtUtil.obtenerTokenDesdeRequest(request);
            Long restauranteId = jwtUtil.extraerRestauranteId(token);

            entidad.setIdRestaurante(restauranteId);
            entidad.setEstado("activo");

            Carta _entidad = repository.save(entidad);
            return new ResponseEntity<>(_entidad, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/platos/{id}")
    public ResponseEntity<Carta> update(@PathVariable("id") Long id, @RequestBody Carta entidad) {
        Optional<Carta> opt = repository.findById(id);
        if (opt.isPresent()) {
            Carta _entidad = opt.get();
            _entidad.setNombre(entidad.getNombre());
            _entidad.setDescripcion(entidad.getDescripcion());
            _entidad.setPrecio(entidad.getPrecio());
            return new ResponseEntity<>(repository.save(_entidad), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/platos/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
