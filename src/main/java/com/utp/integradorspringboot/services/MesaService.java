package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Mesa;
import com.utp.integradorspringboot.models.Zona;
import com.utp.integradorspringboot.repositories.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    public List<Mesa> listarPorZona(Zona zona) {
        return mesaRepository.findByZona(zona);
    }

    public Mesa guardar(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    public Mesa actualizar(Long id, Mesa mesa) {
        Mesa existente = mesaRepository.findById(id).orElseThrow();
        existente.setNumeroMesa(mesa.getNumeroMesa());
        existente.setCapacidad(mesa.getCapacidad());
        existente.setEtiqueta(mesa.getEtiqueta());
        existente.setEstado(mesa.getEstado());
        existente.setPosX(mesa.getPosX());
        existente.setPosY(mesa.getPosY());
        return mesaRepository.save(existente);
    }

    /**
     * Marca la mesa como inactiva.
     *
     * @param id ID de la mesa.
     * @return la mesa actualizada.
     */
    public Mesa desactivar(Long id) {
        Mesa existente = mesaRepository.findById(id).orElseThrow();
        existente.setEstado("inactiva");
        return mesaRepository.save(existente);
    }
}
