package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.dto.AuthRequest;
import com.utp.integradorspringboot.dto.AuthResponse;
import com.utp.integradorspringboot.models.Usuario;
import com.utp.integradorspringboot.repositories.UsuarioRepository;
import com.utp.integradorspringboot.security.CustomUserDetails;
import com.utp.integradorspringboot.security.CustomUserDetailsService;
import com.utp.integradorspringboot.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador encargado de autenticar usuarios y emitir tokens JWT.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8081")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Endpoint para iniciar sesión. Retorna un token JWT si las credenciales son válidas.
     * @param request Objeto con correo y contraseña.
     * @return ResponseEntity con token y datos del usuario.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasenia())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Usuario usuario = usuarioRepository.findByCorreo(userDetails.getUsername()).orElseThrow();

            String token = jwtUtil.generarToken(usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", Map.of(
                    "id", usuario.getId(),
                    "nombre", usuario.getNombre(),
                    "correo", usuario.getCorreo(),
                    "restauranteId", usuario.getRestauranteId(),
                    "roles", usuario.getRoles().stream().map(r -> r.getNombre()).toList()
            ));

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }
}