package com.example.tareas.Task;

import com.example.tareas.utils.Message;
import com.example.tareas.utils.TypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskDto transformarTaskADto(TaskEntity tarea) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(tarea.getId());
        taskDto.setTitulo(tarea.getTitulo());
        taskDto.setDescripcion(tarea.getDescripcion());
        taskDto.setFechaCreacion(tarea.getFechaCreacion());
        taskDto.setEstatus(tarea.getEstatus());
        return taskDto;
    }

    public TaskEntity transformarDtoAEntity(TaskDto dto) {
        TaskEntity task = new TaskEntity();
        task.setId(dto.getId());
        task.setTitulo(dto.getTitulo());
        task.setDescripcion(dto.getDescripcion());
        task.setFechaCreacion(dto.getFechaCreacion());
        task.setEstatus(dto.getEstatus());
        return task;
    }

    public List<TaskDto> transformarListaTareasADTO(List<TaskEntity> tareas) {
        List<TaskDto> tareasDTO = new ArrayList<>();
        for (TaskEntity tarea : tareas) {
            tareasDTO.add(transformarTaskADto(tarea));
        }
        return tareasDTO;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> obtenerTareas() {
        List<TaskEntity> tareas = taskRepository.findAll();
        return new ResponseEntity<>(
                new Message(transformarListaTareasADTO(tareas), "Listado de tareas", TypeResponse.SUCCESS),
                HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> obtenerTarea(Long id) {
        Optional<TaskEntity> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            return new ResponseEntity<>(
                    new Message(transformarTaskADto(taskOpt.get()), "Tarea encontrada", TypeResponse.SUCCESS),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new Message("Tarea no encontrada", TypeResponse.WARNING),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<Message> crearTarea(TaskDto taskDto) {
        try {
            TaskEntity entity = transformarDtoAEntity(taskDto);
            TaskEntity saved = taskRepository.save(entity);
            return new ResponseEntity<>(
                    new Message(transformarTaskADto(saved), "Tarea creada correctamente", TypeResponse.SUCCESS),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Message("Error al crear la tarea", TypeResponse.ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<Message> actualizarTarea(Long id, TaskDto taskDto) {
        Optional<TaskEntity> existingOpt = taskRepository.findById(id);
        if (existingOpt.isPresent()) {
            TaskEntity task = existingOpt.get();
            task.setTitulo(taskDto.getTitulo());
            task.setDescripcion(taskDto.getDescripcion());
            task.setEstatus(taskDto.getEstatus());
            TaskEntity updated = taskRepository.save(task);
            return new ResponseEntity<>(
                    new Message(transformarTaskADto(updated), "Tarea actualizada correctamente", TypeResponse.SUCCESS),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new Message("Tarea no encontrada", TypeResponse.WARNING),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<Message> eliminarTarea(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return new ResponseEntity<>(
                    new Message("Tarea eliminada", TypeResponse.SUCCESS),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new Message("Tarea no encontrada", TypeResponse.WARNING),
                    HttpStatus.NOT_FOUND);
        }
    }
}
