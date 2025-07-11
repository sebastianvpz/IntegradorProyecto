package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.api.dto.PedidoConDetallesDTO;
import com.utp.integradorspringboot.api.dto.DetallePedidoDTO;
import com.utp.integradorspringboot.models.Pedido;
import com.utp.integradorspringboot.models.DetallesPedido;
import com.utp.integradorspringboot.repositories.PedidoRepository;
import com.utp.integradorspringboot.repositories.DetallesPedidoRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private DetallesPedidoRepository detallesRepo;

    @GetMapping("/pedidos")
    public ResponseEntity<List<Pedido>> getAll() {
        List<Pedido> lista = pedidoRepo.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<Pedido> getById(@PathVariable Long id) {
        Optional<Pedido> opt = pedidoRepo.findById(id);
        return opt.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/pedidos")
    public ResponseEntity<Void> createConDetalles(@RequestBody PedidoConDetallesDTO dto) {
        Pedido p = new Pedido();
        p.setIdRestaurante(dto.getIdRestaurante());
        p.setIdUsuario(dto.getIdUsuario());
        p.setIdMesa(dto.getNumeroMesa().longValue());
        p.setNumeroMesa(dto.getNumeroMesa());
        p.setComensal(dto.getComensal());
        p.setnPersona(dto.getnPersona());
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
    public ResponseEntity<Pedido> update(@PathVariable Long id, @RequestBody Pedido dto) {
        Optional<Pedido> opt = pedidoRepo.findById(id);
        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Pedido p = opt.get();
        p.setNumeroMesa(dto.getNumeroMesa());
        p.setComensal(dto.getComensal());
        p.setnPersona(dto.getnPersona());

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
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!pedidoRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pedidoRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/detalles_pedido/{idPedido}")
    public ResponseEntity<List<DetallesPedido>> getDetalles(@PathVariable Long idPedido) {
        List<DetallesPedido> lista = detallesRepo.findByIdPedido(idPedido);
        return ResponseEntity.ok(lista);
    }
}
