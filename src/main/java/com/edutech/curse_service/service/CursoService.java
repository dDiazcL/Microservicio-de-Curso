package com.edutech.curse_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.edutech.curse_service.model.Curso;
import com.edutech.curse_service.model.dto.Alumno;
import com.edutech.curse_service.model.dto.Materia;
import com.edutech.curse_service.model.dto.Profesor;
import com.edutech.curse_service.repository.CursoRepository;
import com.edutech.exception.CursoDuplicadoException;

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
            if (profesor == null) {
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
        if (cursoYaExiste(curso)) {
            throw new CursoDuplicadoException("Ya existe un curso con los mismos datos.");
        }
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
        curso.setMateria(curso.getMateria());

        return cursoRepository.save(curso);
    }

    public boolean cursoYaExiste(Curso nuevoCurso) {
        return cursoRepository.findAll().stream().anyMatch(curso ->
            curso.getNombreCurso().equalsIgnoreCase(nuevoCurso.getNombreCurso()) &&
            curso.getIdProfesor().equals(nuevoCurso.getIdProfesor()) &&
            curso.getMateria().getIdMateria().equals(nuevoCurso.getMateria().getIdMateria())
        );
    }


    public List<Materia> findMateriasByIdAlumno(Long idAlumno) {
        String url = "http://localhost:8082/alumnos/" + idAlumno; //Ajustar puerto
        Alumno alumno =  restTemplate.getForObject(url, Alumno.class);

        if (alumno == null || alumno.getIdsCursos() == null || alumno.getIdsCursos().isEmpty()) {
            throw new RuntimeException("Alumno no encontrado o sin cursos");
        }

        List<Curso> cursos = cursoRepository.findAllById(alumno.getIdsCursos());

        return cursos.stream()
        .map(curso -> {
            Materia dto = new Materia();
            dto.setIdMateria(curso.getMateria().getIdMateria());
            dto.setNombreMateria(curso.getMateria().getNombreMateria());
            return dto;
        })
        .distinct() //Evita duplicados si es que el alumno esta en mas de un curso con la misma materia
        .collect(Collectors.toList());

    }
}
