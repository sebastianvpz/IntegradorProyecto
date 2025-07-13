package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.api.ProductoSolicitadoController;
import com.utp.integradorspringboot.dto.ProductoSolicitadoDto;
import com.utp.integradorspringboot.models.Producto;
import com.utp.integradorspringboot.models.ProductoSolicitado;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.ProductoRepository;
import com.utp.integradorspringboot.repositories.ProductoSolicitadoRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;


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

    public void guardarSolicitudes(List<ProductoSolicitadoController.SolicitudDTO> solicitudes, Long restauranteId, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        for (var dto : solicitudes) {
            Producto producto = productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            ProductoSolicitado ps = ProductoSolicitado.builder()
                    .producto(producto)
                    .cantidad(dto.getCantidad())
                    .fechaSolicitud(LocalDateTime.now())
                    .solicitadoPor(usuario)
                    .estado("pendiente")
                    .restauranteId(restauranteId)
                    .build();

            repository.save(ps);
        }
    }
}
