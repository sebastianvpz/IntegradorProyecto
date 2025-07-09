package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Mesa;
import com.utp.integradorspringboot.models.Zona;
import com.utp.integradorspringboot.repositories.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZonaService {

    @Autowired
    private ZonaRepository zonaRepository;

    @Autowired
    private MesaService mesaService;

    public List<Zona> listarPorRestaurante(Long restauranteId) {
        return zonaRepository.findByRestauranteId(restauranteId);
    }

    public Zona guardar(Zona zona) {
        return zonaRepository.save(zona);
    }

    public Zona actualizar(Long id, Zona zona) {
        Zona existente = zonaRepository.findById(id).orElseThrow();
        existente.setNombre(zona.getNombre());
        existente.setNota(zona.getNota());
        existente.setEstado(zona.getEstado());
        return zonaRepository.save(existente);
    }

    public Zona buscarPorId(Long id) {
        return zonaRepository.findById(id).orElseThrow();
    }

    /**
     * Marca la zona como inactiva.
     *
     * @param id ID de la zona.
     * @return la zona actualizada.
     */
    public Zona desactivar(Long id) {
        Zona existente = zonaRepository.findById(id).orElseThrow();
        existente.setEstado("inactivo");
        return zonaRepository.save(existente);
    }

    /**
     * Guarda una zona y crea automáticamente las mesas indicadas.
     *
     * @param zona la zona con cantidadMesas.
     * @return la zona creada.
     */
    public Zona guardarConMesas(Zona zona) {
        Zona nueva = zonaRepository.save(zona);
        for (int i = 1; i <= zona.getCantidadMesas(); i++) {
            Mesa mesa = Mesa.builder()
                    .restauranteId(zona.getRestauranteId())
                    .zona(nueva)
                    .numeroMesa(String.valueOf(i))
                    .capacidad(4) // valor por defecto
                    .estado("disponible")
                    .build();
            mesaService.guardar(mesa);
        }
        return nueva;
    }
}
