package com.utp.integradorspringboot.services;

import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de la lógica de negocio relacionada a los usuarios.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Retorna la lista de usuarios activos (estado = 'activo').
     *
     * @return Lista de usuarios activos
     */
    public List<Usuario> listarActivos() {
        return usuarioRepository.findByEstado("activo");
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario
     * @return Optional con el usuario si se encuentra
     */
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Guarda un nuevo usuario o actualiza uno existente.
     * Se asegura de establecer fecha de creación, estado y restaurante por defecto si no se proporcionan.
     *
     * @param usuario Usuario a guardar
     * @return Usuario persistido
     */
    public Usuario guardar(Usuario usuario) {
        if (usuario.getFechaCreacion() == null) {
            usuario.setFechaCreacion(LocalDateTime.now());
        }
        if (usuario.getEstado() == null) {
            usuario.setEstado("activo");
        }
        if (usuario.getRestauranteId() == null) {
            usuario.setRestauranteId(1L); // valor por defecto
        }
        return usuarioRepository.save(usuario);
    }

    /**
     * Inactiva un usuario cambiando su estado a 'inactivo'.
     *
     * @param id ID del usuario a inactivar
     * @return Usuario actualizado o null si no existe
     */
    public Usuario inactivar(Long id) {
        Optional<Usuario> op = usuarioRepository.findById(id);
        if (op.isPresent()) {
            Usuario u = op.get();
            u.setEstado("inactivo");
            return usuarioRepository.save(u);
        }
        return null;
    }
}