package com.edutech.curse_service.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class Alumno {

    private String nombreUsuario;
    private String email;
    private List<Long> idsCursos;
    private String estadoCuenta;
}