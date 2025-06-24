package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.UsuarioDto;
import com.utp.integradorspringboot.models.Rol;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST que expone endpoints para la gestión de usuarios.
 * Permite listar, buscar, crear, actualizar e inactivar usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Lista todos los usuarios activos.
     *
     * @return Lista de usuarios con estado 'activo'
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() {
        List<UsuarioDto> usuarios = usuarioService.listarActivos()
                .stream()
                .map(u -> {
                    UsuarioDto dto = new UsuarioDto();
                    dto.setId(u.getId());
                    dto.setNombre(u.getNombre());
                    dto.setCorreo(u.getCorreo());
                    dto.setFechaCreacion(u.getFechaCreacion());
                    dto.setRol(
                            u.getRoles()
                                    .stream()
                                    .findFirst()
                                    .map(Rol::getNombre)
                                    .orElse("SIN ROL")
                    );
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario
     * @return Usuario encontrado o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param usuario Datos del usuario a registrar
     * @return Usuario creado
     */
    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id ID del usuario a actualizar
     * @param usuario Nuevos datos del usuario
     * @return Usuario actualizado o 404 si no se encuentra
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.buscarPorId(id)
                .map(actual -> {
                    actual.setNombre(usuario.getNombre());
                    actual.setCorreo(usuario.getCorreo());
                    actual.setContrasenia(usuario.getContrasenia());
                    actual.setRestauranteId(usuario.getRestauranteId());
                    return ResponseEntity.ok(usuarioService.guardar(actual));
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Inactiva un usuario, cambiando su estado a 'inactivo'.
     *
     * @param id ID del usuario
     * @return Usuario inactivado o 404 si no se encuentra
     */
    @PutMapping("/inactivar/{id}")
    public ResponseEntity<Usuario> inactivar(@PathVariable Long id) {
        Usuario actualizado = usuarioService.inactivar(id);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
