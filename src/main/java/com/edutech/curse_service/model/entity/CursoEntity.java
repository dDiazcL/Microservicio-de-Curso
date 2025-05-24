package com.edutech.curse_service.model.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@EntityScan
@Data
public class CursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCurso;

    @NotBlank(message = "El nombre del curso es obligatorio")
    private String nombreCurso;
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 a 500 caracteres")
    private String descripcion;
    @Pattern(regexp = "Online|Presencial|Hibrida", message = "La modalidad debe ser Online, Presencial o Hibrida")
    private String modalidad;
    @Min(value = 1, message = "El cupo maximo debe ser al menos 1")
    private int cupoMaximo;
    @NotNull(message = "El id del profesor es obligatorio")
    private Long idProfesor;
    @NotNull(message = "El id de la materia es obligatoria")
    private Long idMateria;

}
