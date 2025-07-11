package com.utp.integradorspringboot.repositories;

import com.utp.integradorspringboot.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio que proporciona operaciones CRUD para la entidad Usuario.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByEstado(String estado);
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByEstadoAndRestauranteId(String estado, Long restauranteId);
    @Query("SELECT u FROM Usuario u WHERE u.tokenRecuperacion = :token AND u.tokenRecuperacion IS NOT NULL")
    Optional<Usuario> findByTokenRecuperacion(@Param("token") String token);
}
