package com.utp.integradorspringboot.services;


import com.utp.integradorspringboot.dto.UsuarioDto;
import com.utp.integradorspringboot.models.Rol;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.RolRepository;
import com.utp.integradorspringboot.repositories.UsuarioRepository;

// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.JoinTable;
// import jakarta.persistence.ManyToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Servicio encargado de la lógica de negocio relacionada a los usuarios.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Retorna la lista de usuarios activos de un restaurante.
     *
     * @param restauranteId ID del restaurante
     * @return Lista de usuarios activos en el restaurante
     */
    public List<Usuario> listarActivosPorRestaurante(Long restauranteId) {
        return usuarioRepository.findByEstadoAndRestauranteId("activo", restauranteId);
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

    /**
     * Devuelve los roles asociados a un usuario.
     */

    public Set<Rol> obtenerRolesDeUsuario(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        return usuario.map(Usuario::getRoles).orElse(null);
    }

    @Autowired
    private RolRepository rolRepository;

    public Usuario guardarDesdeDTO(UsuarioDto dto, Long restauranteId) {
        Rol rol = rolRepository.findById(dto.getRolId().longValue())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = (dto.getId() != null)
                ? usuarioRepository.findById(dto.getId()).orElse(new Usuario())
                : new Usuario();

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        if (dto.getContrasenia() != null && !dto.getContrasenia().isEmpty()) {
            usuario.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
        }
        usuario.setEstado(dto.getEstado() != null ? dto.getEstado() : "activo");
        usuario.setRestauranteId(restauranteId); //
        usuario.setFechaCreacion(usuario.getFechaCreacion() != null
                ? usuario.getFechaCreacion()
                : LocalDateTime.now());

        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }


}