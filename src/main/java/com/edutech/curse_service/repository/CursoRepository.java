package com.edutech.curse_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.curse_service.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{
// Puedes agregar consultas personalizadas aquí
    List<Curso> findByIdProfesor(Long idProfesor);

}
