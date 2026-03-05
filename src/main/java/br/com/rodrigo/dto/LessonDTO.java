package br.com.rodrigo.dto;

import jakarta.validation.constraints.NotBlank;

public class LessonDTO {

    @NotBlank(message = "Name is required")
    public String name;
}