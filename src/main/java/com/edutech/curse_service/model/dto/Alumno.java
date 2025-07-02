package com.edutech.curse_service.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class Alumno {

    private Long id;
    private String nombreCompleto;
    private List<Long> idsCursos;
    
}