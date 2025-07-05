package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.LoteProducto;
import com.utp.integradorspringboot.models.Producto;
import com.utp.integradorspringboot.repositories.LoteProductoRepository;
import com.utp.integradorspringboot.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona la lógica de negocio relacionada con los productos del inventario.
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private LoteProductoRepository loteProductoRepository;


    /**
     * Guarda o actualiza un producto en la base de datos.
     *
     * @param producto Objeto producto a guardar o actualizar.
     * @return Producto guardado.
     */
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Busca un producto por su identificador único.
     *
     * @param id Identificador del producto.
     * @return Producto encontrado, en un Optional.
     */
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    /**
     * Busca los productos por restaurante.
     *
     * @param restauranteId Identificador del restaurante.
     * @return Producto encontrado, en un Optional.
     */
    public List<Producto> listarPorRestaurante(Long restauranteId) {
        return productoRepository.findByEstadoAndIdRestaurante("activo", restauranteId);
    }

    public void consumirStock(Long productoId, Long restauranteId, int cantidadConsumir) {
        Producto producto = productoRepository.findByIdAndIdRestaurante(productoId, restauranteId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en este restaurante"));

        if (producto.getCantidad() < cantidadConsumir) {
            throw new IllegalArgumentException("Stock insuficiente");
        }

        producto.setCantidad(producto.getCantidad() - cantidadConsumir);
        productoRepository.save(producto);

        List<LoteProducto> lotes = loteProductoRepository
                .findByProductoIdAndIdRestauranteAndEstadoOrderByFechaVencimientoAsc(
                        productoId, restauranteId, "activo");

        for (LoteProducto lote : lotes) {
            if (cantidadConsumir == 0) break;

            if (lote.getCantidad() <= cantidadConsumir) {
                cantidadConsumir -= lote.getCantidad();
                lote.setCantidad(0);
                lote.setEstado("inactivo");
            } else {
                lote.setCantidad(lote.getCantidad() - cantidadConsumir);
                cantidadConsumir = 0;
            }
            loteProductoRepository.save(lote);
        }

        if (cantidadConsumir > 0) {
            throw new IllegalStateException("Inconsistencia: no se encontró stock suficiente en lotes");
        }
    }





}
