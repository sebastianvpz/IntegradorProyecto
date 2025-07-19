package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.LoteProducto;
import com.utp.integradorspringboot.models.Producto;
import com.utp.integradorspringboot.repositories.LoteProductoRepository;
import com.utp.integradorspringboot.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoteProductoService {

    @Autowired
    private LoteProductoRepository loteProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public LoteProducto guardar(LoteProducto lote, Long restauranteId) {
        if (lote.getFechaIngreso() == null) {
            lote.setFechaIngreso(LocalDate.now());
        }
        if (lote.getEstado() == null) {
            lote.setEstado("activo");
        }
        if (lote.getIdRestaurante() == null) {
            lote.setIdRestaurante(restauranteId);
        }

        Producto producto= productoRepository.findById(lote.getProductoId()).orElseThrow();
        producto.setCantidad(producto.getCantidad() + lote.getCantidad());
        productoRepository.save(producto);
        return loteProductoRepository.save(lote);
    }

    public List<LoteProducto> listarPorRestaurante(Long restauranteId) {
        return loteProductoRepository.findByEstadoAndIdRestaurante("activo", restauranteId);
    }

    public Optional<LoteProducto> buscarPorId(Long id) {
        return loteProductoRepository.findById(id);
    }

    public LoteProducto inactivar(Long id) {
        Optional<LoteProducto> loteOp = loteProductoRepository.findById(id);
        if (loteOp.isPresent()) {
            LoteProducto lote = loteOp.get();
            lote.setEstado("inactivo");
            loteProductoRepository.save(lote);

            // Recalcular cantidad total del producto
            actualizarCantidadProducto(lote.getProductoId(), lote.getIdRestaurante());

            return lote;
        }
        return null;
    }

    public List<LoteProducto> listarPorProductoYRestaurante(Long productoId, Long restauranteId) {
        return loteProductoRepository.findByProductoIdAndIdRestauranteAndEstado(
                productoId, restauranteId, "activo"
        );
    }

    public LoteProducto actualizar(Long id, LoteProducto lote, Long restauranteId) {
        LoteProducto existente = loteProductoRepository.findById(id).orElse(null);
        if (existente == null || !existente.getIdRestaurante().equals(restauranteId)) {
            return null;
        }

        existente.setCantidad(lote.getCantidad());
        existente.setFechaIngreso(lote.getFechaIngreso());
        existente.setFechaVencimiento(lote.getFechaVencimiento());
        existente.setProveedor(lote.getProveedor());
        loteProductoRepository.save(existente);

        Long productoId = existente.getProductoId();
        int cantidadTotal = loteProductoRepository
                .findByProductoIdAndIdRestauranteAndEstadoOrderByFechaVencimientoAsc(
                        productoId, restauranteId, "activo")
                .stream()
                .mapToInt(LoteProducto::getCantidad)
                .sum();

        Producto producto = productoRepository.findById(productoId).orElseThrow();
        producto.setCantidad(cantidadTotal);
        productoRepository.save(producto);

        return existente;
    }

    private void actualizarCantidadProducto(Long productoId, Long restauranteId) {
        int cantidadTotal = loteProductoRepository
                .findByProductoIdAndIdRestauranteAndEstadoOrderByFechaVencimientoAsc(
                        productoId, restauranteId, "activo")
                .stream()
                .mapToInt(LoteProducto::getCantidad)
                .sum();

        Producto producto = productoRepository.findById(productoId).orElseThrow();
        producto.setCantidad(cantidadTotal);
        productoRepository.save(producto);
    }

}
