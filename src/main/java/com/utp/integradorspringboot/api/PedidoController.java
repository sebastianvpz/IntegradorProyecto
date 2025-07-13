package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.PedidoConDetallesDTO;
import com.utp.integradorspringboot.dto.DetallePedidoDTO;
import com.utp.integradorspringboot.models.Pedido;
import com.utp.integradorspringboot.models.DetallesPedido;
import com.utp.integradorspringboot.repositories.PedidoRepository;
import com.utp.integradorspringboot.repositories.DetallesPedidoRepository;
import com.utp.integradorspringboot.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private DetallesPedidoRepository detallesRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/pedidos")
    public ResponseEntity<List<Pedido>> getAll(HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);

        List<Pedido> lista = pedidoRepo.findByRestauranteId(restauranteId);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<Pedido> getById(@PathVariable Long id, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);

        Optional<Pedido> opt = pedidoRepo.findByIdAndRestauranteId(id, restauranteId);
        return opt.map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/pedidos")
    public ResponseEntity<Void> createConDetalles(@RequestBody PedidoConDetallesDTO dto, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        Long usuarioId = jwtUtil.extraerUsuarioId(token);

        Pedido p = new Pedido();
        p.setRestauranteId(restauranteId);
        p.setUsuarioId(usuarioId);
        p.setMesaId(dto.getNumeroMesa().longValue());
        p.setNumeroMesa(dto.getNumeroMesa());
        p.setComensal(dto.getComensal());
        p.setNPersona(dto.getnPersona());
        p.setFechaCreacion(LocalDate.now());
        p.setHoraInicio(LocalTime.now());
        p.setHoraEntrega(null);
        p.setEstadoEntrega(dto.getEstadoEntrega());
        p.setEstadoPago(dto.getEstadoPago());
        p.setSubtotal(dto.getSubtotal());
        p.setDescuento(dto.getDescuento());
        p.setCostoFinal(dto.getCostoFinal());
        pedidoRepo.save(p);

        for (DetallePedidoDTO d : dto.getPlatos()) {
            DetallesPedido dp = new DetallesPedido();
            dp.setIdPedido(p.getId());
            dp.setIdProducto(d.getIdProducto());
            dp.setNombreProducto(d.getNombreProducto());
            dp.setCantidad(d.getCantidad());
            dp.setPrecioUnitario(d.getPrecioUnitario());
            dp.setSubtotal(d.getSubtotal());
            detallesRepo.save(dp);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/pedidos/{id}")
    public ResponseEntity<Pedido> update(@PathVariable Long id, @RequestBody Pedido dto, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);

        Optional<Pedido> opt = pedidoRepo.findByIdAndRestauranteId(id, restauranteId);
        if (!opt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Pedido p = opt.get();
        p.setNumeroMesa(dto.getNumeroMesa());
        p.setComensal(dto.getComensal());
        p.setNPersona(dto.getNPersona());

        if (dto.getHoraEntrega() != null) {
            p.setHoraEntrega(dto.getHoraEntrega());
        }
        if (dto.getEstadoEntrega() != null) {
            p.setEstadoEntrega(dto.getEstadoEntrega());
        }
        if (dto.getEstadoPago() != null) {
            p.setEstadoPago(dto.getEstadoPago());
        }

        p.setSubtotal(dto.getSubtotal());
        p.setDescuento(dto.getDescuento());
        p.setCostoFinal(dto.getCostoFinal());

        Pedido updated = pedidoRepo.save(p);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);

        Optional<Pedido> opt = pedidoRepo.findByIdAndRestauranteId(id, restauranteId);
        if (!opt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        pedidoRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalles_pedido/{idPedido}")
    public ResponseEntity<List<DetallesPedido>> getDetalles(@PathVariable Long idPedido) {
        List<DetallesPedido> lista = detallesRepo.findByIdPedido(idPedido);
        return ResponseEntity.ok(lista);
    }
}
