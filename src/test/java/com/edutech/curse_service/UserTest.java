package com.edutech.curse_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.edutech.curse_service.model.Curso;
import com.edutech.curse_service.model.entity.Materia;
import com.edutech.curse_service.repository.CursoRepository;
import com.edutech.curse_service.service.CursoService;

public class UserTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CursoService cursoService;
    
    private Curso curso;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        Materia materia = new Materia();
        materia.setIdMateria(1L);
        materia.setNombreMateria("Programacion");

        curso = new Curso();
        curso.setIdCurso(1L);
        curso.setNombreCurso("Java Avanzado");
        curso.setDescripcion("Curso Intensivo de Java");
        curso.setModalidad("online");
        curso.setCupoMaximo(25);
        curso.setIdProfesor(5L);
    }

    @Test
    public void testCrearCurso_exito() {
        when(cursoRepository.existsByNombreCursoAndIdProfesorAndMateria_IdMateria(
            any(), anyLong(), anyLong())).thenReturn(false);

        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);

        Curso result = cursoService.createCurso(curso);
        assertNotNull(result);
        assertEquals("Java Avanzado", result.getNombreCurso());
    }

    @Test
    public void testBuscarCursoPorId_existente() {
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        Optional<Curso> result = cursoService.getCursoById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdCurso());
    }

}
