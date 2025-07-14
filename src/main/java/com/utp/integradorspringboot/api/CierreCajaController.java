package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.CierreCaja;
import com.utp.integradorspringboot.security.JwtUtil;
import com.utp.integradorspringboot.services.CierreCajaService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/caja")
public class CierreCajaController {

    @Autowired
    private CierreCajaService cierreCajaService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/abrir")
    public ResponseEntity<CierreCaja> abrirCaja(@RequestBody CierreCaja dto, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        Long usuarioId = jwtUtil.extraerUsuarioId(token);

        dto.setIdRestaurante(restauranteId.intValue());
        dto.setIdUsuario(usuarioId.intValue());

        return ResponseEntity.ok(cierreCajaService.abrirCaja(dto));
    }

    @PutMapping("/cerrar/{id}")
    public ResponseEntity<CierreCaja> cerrarCaja(@PathVariable Long id, @RequestBody Map<String, Object> datos) {
        BigDecimal totalVentas = new BigDecimal(datos.getOrDefault("totalVentas", 0).toString());
        BigDecimal totalEfectivo = new BigDecimal(datos.getOrDefault("totalEfectivo", 0).toString());
        BigDecimal totalTarjetaCredito = new BigDecimal(datos.getOrDefault("totalTarjetaCredito", 0).toString());
        BigDecimal totalTarjetaDebito = new BigDecimal(datos.getOrDefault("totalTarjetaDebito", 0).toString());
        BigDecimal totalEgresos = new BigDecimal(datos.getOrDefault("totalEgresos", 0).toString());
        BigDecimal saldoFinal = new BigDecimal(datos.getOrDefault("saldoFinal", 0).toString());

        CierreCaja result = cierreCajaService.cerrarCaja(
                id, totalVentas, totalEfectivo, totalTarjetaCredito, totalTarjetaDebito, totalEgresos, saldoFinal
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/abierta")
    public ResponseEntity<CierreCaja> buscarCajaAbierta() {
        Optional<CierreCaja> cajaOpt = cierreCajaService.buscarCajaAbierta();
        return cajaOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(null));
    }
}
