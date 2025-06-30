package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Producto;
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

    /**
     * Lista todos los productos que se encuentran en estado "activo".
     *
     * @return Lista de productos activos.
     */
    public List<Producto> listarTodos() {
        return productoRepository.findByEstado("activo");
    }

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



}
