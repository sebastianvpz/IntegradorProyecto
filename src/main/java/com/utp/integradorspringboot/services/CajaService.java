package com.utp.integradorspringboot.services;
// Archivo: CajaService.java

import com.utp.integradorspringboot.models.CierreCaja;
import com.utp.integradorspringboot.models.DetalleMovimiento;
import com.utp.integradorspringboot.repositories.CierreCajaRepository;
import com.utp.integradorspringboot.repositories.DetalleMovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CajaService {

    @Autowired
    private CierreCajaRepository cierreCajaRepo;

    @Autowired
    private DetalleMovimientoRepository movimientoRepo;

    public List<CierreCaja> listarCierres() {
        return cierreCajaRepo.findAll();
    }

    public Optional<CierreCaja> buscarCierrePorId(Long id) {
        return cierreCajaRepo.findById(id);
    }

    public CierreCaja guardarCierre(CierreCaja cierre) {
        return cierreCajaRepo.save(cierre);
    }

    public void eliminarCierre(Long id) {
        cierreCajaRepo.deleteById(id);
    }

    public List<DetalleMovimiento> listarMovimientosPorCaja(Long cajaId) {
        return movimientoRepo.findByCierreCajaId(cajaId);
    }

    public DetalleMovimiento guardarMovimiento(DetalleMovimiento mov) {
        return movimientoRepo.save(mov);
    }
}
