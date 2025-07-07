package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Mesa;
import com.utp.integradorspringboot.models.Zona;
import com.utp.integradorspringboot.services.MesaService;
import com.utp.integradorspringboot.services.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar las mesas de una zona.
 */
@RestController
@RequestMapping("/api/mesas")
@CrossOrigin
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @Autowired
    private ZonaService zonaService;

    /**
     * Lista todas las mesas de una zona.
     *
     * @param zonaId el ID de la zona.
     * @return lista de mesas.
     */
    @GetMapping("/por-zona/{zonaId}")
    public ResponseEntity<List<Mesa>> listarPorZona(@PathVariable Long zonaId) {
        Zona zona = zonaService.buscarPorId(zonaId);
        return ResponseEntity.ok(mesaService.listarPorZona(zona));
    }

    /**
     * Crea una nueva mesa en una zona.
     *
     * @param mesa la mesa a crear.
     * @return la mesa creada.
     */
    @PostMapping
    public ResponseEntity<Mesa> crear(@RequestBody Mesa mesa) {
        return ResponseEntity.ok(mesaService.guardar(mesa));
    }

    /**
     * Actualiza una mesa existente.
     *
     * @param id el ID de la mesa.
     * @param mesa los datos actualizados.
     * @return la mesa actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Mesa> actualizar(@PathVariable Long id, @RequestBody Mesa mesa) {
        return ResponseEntity.ok(mesaService.actualizar(id, mesa));
    }

    /**
     * Desactiva (elimina lógicamente) una mesa.
     *
     * @param id el ID de la mesa.
     * @return la mesa con estado inactivo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Mesa> desactivar(@PathVariable Long id) {
        Mesa mesa = mesaService.desactivar(id);
        return ResponseEntity.ok(mesa);
    }
}
