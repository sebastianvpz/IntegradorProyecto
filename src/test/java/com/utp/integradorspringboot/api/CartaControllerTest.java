package com.utp.integradorspringboot.api;

import com.utp.integradorspringboot.models.Carta;
import com.utp.integradorspringboot.repositories.CartaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class CartaControllerTest {

    @Mock
    private CartaRepository cartaRepository;

    @InjectMocks
    private CartaController cartaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {
        Carta carta = new Carta(1L, 1L, "Ceviche", "Con camote", 18L, "activo");
        when(cartaRepository.findAll()).thenReturn(List.of(carta));

        ResponseEntity<List<Carta>> response = cartaController.getAll(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Ceviche", response.getBody().get(0).getNombre());
    }

    @Test
    void getById() {
        Carta carta = new Carta(2L, 1L, "Ají de gallina", "Con arroz", 15L, "activo");
        when(cartaRepository.findById(2L)).thenReturn(Optional.of(carta));

        ResponseEntity<Carta> response = cartaController.getById(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ají de gallina", response.getBody().getNombre());
    }

    @Test
    void create() {
        Carta entrada = new Carta(null, null, "Lomo Saltado", "Clásico peruano", 20L, null);
        Carta guardada = new Carta(3L, 1L, "Lomo Saltado", "Clásico peruano", 20L, "activo");

        when(cartaRepository.save(any())).thenReturn(guardada);

        ResponseEntity<Carta> response = cartaController.create(entrada);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Lomo Saltado", response.getBody().getNombre());
        assertEquals(1L, response.getBody().getIdRestaurante());
        assertEquals("activo", response.getBody().getEstado());
    }

    @Test
    void update() {
        Carta original = new Carta(4L, 1L, "Chaufa", "Con pollo", 14L, "activo");
        Carta cambios = new Carta(null, 1L, "Chaufa Especial", "Con chancho", 16L, "activo");

        when(cartaRepository.findById(4L)).thenReturn(Optional.of(original));
        when(cartaRepository.save(any())).thenReturn(original);

        ResponseEntity<Carta> response = cartaController.update(4L, cambios);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Chaufa Especial", response.getBody().getNombre());
        assertEquals(16L, response.getBody().getPrecio());
    }

    @Test
    void delete() {
        doNothing().when(cartaRepository).deleteById(5L);

        ResponseEntity<HttpStatus> response = cartaController.delete(5L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(cartaRepository, times(1)).deleteById(5L);
    }
}