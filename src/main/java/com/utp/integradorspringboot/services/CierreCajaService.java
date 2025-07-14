package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.CierreCaja;
import com.utp.integradorspringboot.models.DetalleMovimiento;
import com.utp.integradorspringboot.repositories.CierreCajaRepository;
import com.utp.integradorspringboot.repositories.DetalleMovimientoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CierreCajaService {

    @Autowired
    private CierreCajaRepository repo;

    @Autowired
    private DetalleMovimientoRepository detalleMovimientoRepo;

    public CierreCaja abrirCaja(CierreCaja dto) {
        dto.setEstado("abierta");
        dto.setFechaInicio(LocalDate.now());
        dto.setFechaCreacion(LocalDate.now());
        CierreCaja cajaGuardada = repo.save(dto);

        DetalleMovimiento det = new DetalleMovimiento();
        det.setIdCierresDeCaja(cajaGuardada.getId());
        det.setTipo("apertura");
        det.setDescripcion("Apertura de caja");
        det.setMonto(dto.getTotalEfectivo() != null ? dto.getTotalEfectivo().toString() : "0");
        det.setFecha(LocalDate.now());
        detalleMovimientoRepo.save(det);

        return cajaGuardada;
    }

    public CierreCaja cerrarCaja(Long id, BigDecimal totalVentas, BigDecimal totalEfectivo,
                                 BigDecimal totalTarjetaCredito, BigDecimal totalTarjetaDebito,
                                 BigDecimal totalEgresos, BigDecimal saldoFinal) {
        CierreCaja caja = repo.findById(id).orElseThrow();
        caja.setFechaCierre(LocalDate.now());
        caja.setTotalVentas(totalVentas);
        caja.setTotalEfectivo(totalEfectivo);
        caja.setTotalTarjetaCredito(totalTarjetaCredito);
        caja.setTotalTarjetaDebito(totalTarjetaDebito);
        caja.setTotalEgresos(totalEgresos);
        caja.setSaldoFinal(saldoFinal);
        caja.setEstado("cerrada");
        CierreCaja cajaGuardada = repo.save(caja);

        DetalleMovimiento det = new DetalleMovimiento();
        det.setIdCierresDeCaja(cajaGuardada.getId());
        det.setTipo("cierre");
        det.setDescripcion("Cierre de caja");
        det.setMonto(saldoFinal != null ? saldoFinal.toString() : "0");
        det.setFecha(LocalDate.now());
        detalleMovimientoRepo.save(det);

        return cajaGuardada;
    }

    public Optional<CierreCaja> buscarCajaAbierta() {
        return repo.findFirstByEstadoOrderByFechaInicioDesc("abierta");
    }
}
