package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.dto.ProductoSolicitadoDto;
import com.utp.integradorspringboot.models.ProductoSolicitado;
import com.utp.integradorspringboot.repositories.ProductoSolicitadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoSolicitadoService {

    @Autowired
    private ProductoSolicitadoRepository repository;

    public List<ProductoSolicitadoDto> listarPorRestaurante(Long restauranteId) {
        List<ProductoSolicitado> lista = repository.findByRestauranteId(restauranteId);

        return lista.stream().map(p -> {
            ProductoSolicitadoDto dto = new ProductoSolicitadoDto();
            dto.setId(p.getId());
            dto.setProductoId(p.getProducto().getId());
            dto.setNombreProducto(p.getProducto().getNombre());
            dto.setCategoria(p.getProducto().getCategoria());
            dto.setCantidad(p.getCantidad());
            dto.setFechaSolicitud(p.getFechaSolicitud().toString());
            dto.setSolicitadoPor(p.getSolicitadoPor().getNombre());
            dto.setEstado(p.getEstado());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<ProductoSolicitado> listarPendientes(Long restauranteId) {
        return repository.findByEstadoAndProducto_IdRestaurante("pendiente", restauranteId);
    }

    public ProductoSolicitado guardar(ProductoSolicitado solicitado) {
        solicitado.setFechaSolicitud(LocalDateTime.now());
        solicitado.setEstado("pendiente");
        return repository.save(solicitado);
    }

    public Optional<ProductoSolicitado> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public ProductoSolicitado actualizarEstado(Long id, String nuevoEstado) {
        Optional<ProductoSolicitado> op = repository.findById(id);
        if (op.isPresent()) {
            ProductoSolicitado solicitado = op.get();
            solicitado.setEstado(nuevoEstado);
            return repository.save(solicitado);
        }
        return null;
    }
}
