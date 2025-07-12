package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.DetallesPedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallesPedidoRepository extends JpaRepository<DetallesPedido,Long> {
    List<DetallesPedido> findByIdPedido(Long idPedido);
}
