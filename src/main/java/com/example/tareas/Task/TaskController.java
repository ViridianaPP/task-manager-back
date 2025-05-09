package com.example.tareas.Task;


import com.example.tareas.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Message> obtenerTareas(){
        return service.obtenerTareas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> obtenerTarea(@PathVariable Long id){
        return service.obtenerTarea(id);
    }

    @PostMapping
    public ResponseEntity<Message> crear(@Validated(TaskDto.Crear.class) @RequestBody TaskDto dto){
        return service.crearTarea(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> actualizar(@PathVariable Long id, @Validated(TaskDto.Editar.class) @RequestBody TaskDto dto){
        return service.actualizarTarea(id, dto);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Message> eliminar(@PathVariable Long id){
        return service.eliminarTarea(id);
    }
}
