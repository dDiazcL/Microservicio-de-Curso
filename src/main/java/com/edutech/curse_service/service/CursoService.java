package com.edutech.curse_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.edutech.curse_service.model.Curso;
import com.edutech.curse_service.model.dto.Profesor;
import com.edutech.curse_service.repository.CursoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service

public class CursoService {

    private final CursoRepository cursoRepository;
    private final RestTemplate restTemplate;

    private static final String USER_SERVICE_URL = "http://localhost:8082/profesores/";

    public CursoService(CursoRepository cursoRepository, RestTemplate restTemplate) {
        this.cursoRepository = cursoRepository;
        this.restTemplate = restTemplate;
    }

    public List<Curso> findCursosByIdProfesor(Long idProfesor) {
        try {
            // Validar existencia del profesor
            String url = USER_SERVICE_URL + idProfesor;
            Profesor profesor = restTemplate.getForObject(url, Profesor.class);
            if (profesor == null || profesor.getId() == null) {
                throw new RuntimeException("Profesor no encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar el profesor con ID " + idProfesor);
        }

            List<Curso> cursos = cursoRepository.findByIdProfesor(idProfesor);
        if (cursos.isEmpty()) {
        throw new RuntimeException("No se encontraron cursos para el profesor con ID" + idProfesor);
        }
        return cursos;
    }

    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> getCursoById(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso createCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public void deleteCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    public Curso updateCurso(Long id, Curso cursoDetails) {
        Curso curso = cursoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con id: "+ id));

        curso.setNombreCurso(cursoDetails.getNombreCurso());
        curso.setDescripcion(cursoDetails.getDescripcion());
        curso.setModalidad(cursoDetails.getModalidad());
        curso.setCupoMaximo(cursoDetails.getCupoMaximo());
        curso.setIdProfesor(curso.getIdProfesor());
        curso.setIdMateria(curso.getIdMateria());

        return cursoRepository.save(curso);
    }



}
