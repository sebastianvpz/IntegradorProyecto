package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.UsuarioDTO;

import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Usuario> listar() {
        return usuarioService.listarActivos();
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
     * @param dto Datos del usuario a registrar
     * @return Usuario creado
     */
    @PostMapping
    public Usuario crear(@RequestBody UsuarioDTO dto) {
        return usuarioService.guardarDesdeDTO(dto);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id      ID del usuario a actualizar
     * @param usuario Nuevos datos del usuario
     * @return Usuario actualizado o 404 si no se encuentra
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        if (!id.equals(dto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Usuario actualizado = usuarioService.guardarDesdeDTO(dto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
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
