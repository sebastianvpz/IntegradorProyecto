package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.UsuarioDto;
import com.utp.integradorspringboot.models.Rol;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.RolRepository;
import com.utp.integradorspringboot.security.JwtUtil;
import com.utp.integradorspringboot.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Lista todos los usuarios activos segun el Id del restaurante.
     * @param  request token del usuario logeado
     * @return Lista de usuarios con estado 'activo' e Id del restaurante correpondiente.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar(HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);

        List<UsuarioDto> usuarios = usuarioService.listarPorRestaurante(restauranteId)
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
     * @param dto Datos del usuario a registrar
     * @param request token del usuario logeado
     * @return Usuario creado
     */
    @PostMapping
    public ResponseEntity<UsuarioDto> crear(@RequestBody UsuarioDto dto, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);

        Usuario nuevoUsuario = usuarioService.guardarDesdeDto(dto, restauranteId);

        UsuarioDto respuesta = new UsuarioDto();
        respuesta.setId(nuevoUsuario.getId());
        respuesta.setNombre(nuevoUsuario.getNombre());
        respuesta.setCorreo(nuevoUsuario.getCorreo());
        respuesta.setFechaCreacion(nuevoUsuario.getFechaCreacion());
        respuesta.setRol(dto.getRol());

        return ResponseEntity.ok(respuesta);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id ID del usuario a actualizar
     * @param dto Nuevos datos del usuario
     * @return Usuario actualizado o 404 si no se encuentra
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> actualizar(@PathVariable Long id, @RequestBody UsuarioDto dto) {
        return usuarioService.buscarPorId(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNombre(dto.getNombre());
                    usuarioExistente.setCorreo(dto.getCorreo());

                    // Si la contraseña viene vacía o ya está encriptada, no la modificamos
                    if (dto.getContrasenia() != null && !dto.getContrasenia().isBlank()
                            && !dto.getContrasenia().startsWith("$2a$")) {
                        usuarioExistente.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
                    }

                    // Actualizar el rol
                    Rol nuevoRol = rolRepository.findByNombre(dto.getRol().toUpperCase())
                            .orElseThrow(() -> new IllegalArgumentException("Rol no válido: " + dto.getRol()));
                    usuarioExistente.getRoles().clear();
                    usuarioExistente.getRoles().add(nuevoRol);

                    Usuario actualizado = usuarioService.guardar(usuarioExistente, usuarioExistente.getRestauranteId());

                    UsuarioDto respuesta = new UsuarioDto();
                    respuesta.setId(actualizado.getId());
                    respuesta.setNombre(actualizado.getNombre());
                    respuesta.setCorreo(actualizado.getCorreo());
                    respuesta.setFechaCreacion(actualizado.getFechaCreacion());
                    respuesta.setRol(nuevoRol.getNombre());

                    return ResponseEntity.ok(respuesta);
                })
                .orElse(ResponseEntity.notFound().build());
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
