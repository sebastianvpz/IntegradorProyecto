package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.UsuarioDto;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.security.JwtUtil;
import com.utp.integradorspringboot.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

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

    /**
     * Lista todos los usuarios activos de un restaurante.
     *
     * @param request Petición HTTP para extraer el token JWT
     * @return Lista de usuarios activos del restaurante
     */
    @GetMapping
    public List<Usuario> listar(HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        return usuarioService.listarActivosPorRestaurante(restauranteId);
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
    public Usuario crear(@RequestBody UsuarioDto dto, HttpServletRequest request) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);
        return usuarioService.guardarDesdeDTO(dto, restauranteId);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id  ID del usuario a actualizar
     * @param dto Nuevos datos del usuario
     * @return Usuario actualizado o 404 si no se encuentra
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody UsuarioDto dto, HttpServletRequest request) {
        if (!id.equals(dto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long restauranteId = jwtUtil.extraerRestauranteId(token);

        try {
            Usuario actualizado = usuarioService.guardarDesdeDTO(dto, restauranteId);
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

    /**
     * Cambia los datos del usuario logeado
     *
     * @param dto Usuario modificado
     * @param request token
     * @return Usuario actualizado o un status 500 si es error.
     */
    @PutMapping("/perfil")
    public ResponseEntity<Usuario> actualizarPerfil(HttpServletRequest request, @RequestBody UsuarioDto dto) {
        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long usuarioId = jwtUtil.extraerUsuarioId(token);

        dto.setId(usuarioId);

        try {
            Usuario actualizado = usuarioService.actualizarPerfil(dto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Cambia la contraseña del usuario logeado
     *
     * @param request para obtener el token
     * @param body contiene actual y nueva
     * @return 200 si ok, 400 si contraseña actual incorrecta
     */
    @PutMapping("/cambiar-password")
    public ResponseEntity<Void> cambiarPassword(
            HttpServletRequest request,
            @RequestBody Map<String, String> body) {

        String token = jwtUtil.obtenerTokenDesdeRequest(request);
        Long usuarioId = jwtUtil.extraerUsuarioId(token);

        String actual = body.get("actual");
        String nueva = body.get("nueva");

        boolean ok = usuarioService.cambiarPassword(usuarioId, actual, nueva);

        return ok ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }



}
