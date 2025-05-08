package com.example.tareas.Task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
@Data
public class TaskDto {
    @NotNull(groups = {Editar.class, ActualizarEstado.class}, message = "El id es obligatorio")
    private Long id;
    @NotNull(groups = {Crear.class, Editar.class}, message = "El titulo es obligatorio")
    private String titulo;
    @NotNull(groups = {Crear.class, Editar.class}, message = "La descripcion es obligatoria")
    private String descripcion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCreacion;
    @NotNull(groups = {Crear.class, ActualizarEstado.class}, message = "El estado es obligatorio")
    private String estatus;

    public interface Crear{}

    public interface Editar{}

    public interface ActualizarEstado{}

}
