package com.utp.integradorspringboot;

import com.utp.integradorspringboot.models.CierreCaja;
import com.utp.integradorspringboot.models.DetalleMovimiento;
import com.utp.integradorspringboot.services.CajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@Controller
public class CajaController {
     @RequestMapping("/caja")
    public String vistaCaja() {
        return "caja"; // Sin extensión .html
    }

    @Autowired
    private CajaService cajaService;

    @GetMapping("/cierres")
    public List<CierreCaja> listarCierres() {
        return cajaService.listarCierres();
    }

    @GetMapping("/cierres/{id}")
    public ResponseEntity<CierreCaja> buscarPorId(@PathVariable Long id) {
        return cajaService.buscarCierrePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cierres")
    public CierreCaja crearCierre(@RequestBody CierreCaja cierre) {
        return cajaService.guardarCierre(cierre);
    }

    @DeleteMapping("/cierres/{id}")
    public void eliminarCierre(@PathVariable Long id) {
        cajaService.eliminarCierre(id);
    }

    @GetMapping("/movimientos/{idCierre}")
    public List<DetalleMovimiento> listarMovimientos(@PathVariable Long idCierre) {
        return cajaService.listarMovimientosPorCaja(idCierre);
    }

    @PostMapping("/movimientos")
    public DetalleMovimiento guardarMovimiento(@RequestBody DetalleMovimiento movimiento) {
        return cajaService.guardarMovimiento(movimiento);
    }

}
