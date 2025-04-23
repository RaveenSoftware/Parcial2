package co.edu.udes.backend.controllers;

import co.edu.udes.backend.models.Habitacion;
import co.edu.udes.backend.repositories.HabitacionRepository;
import co.edu.udes.backend.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionRepository habitacionRepository;

    // Obtener todas las habitaciones
    @GetMapping
    public List<Habitacion> obtenerTodasHabitaciones() {
        return habitacionRepository.findAll();
    }

    // Crear una nueva habitación
    @PostMapping
    public Habitacion crearHabitacion(@RequestBody Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }

    // Obtener habitación por id
    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> obtenerHabitacionPorId(@PathVariable Long id) {
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no existe con el id :" + id));
        return ResponseEntity.ok(habitacion);
    }

    // Actualizar habitación
    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> actualizarHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacionDetalles) {
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no existe con el id :" + id));

        habitacion.setTipo(habitacionDetalles.getTipo());
        habitacion.setPrecio(habitacionDetalles.getPrecio());
        habitacion.setCapacidad(habitacionDetalles.getCapacidad());
        Habitacion habitacionActualizada = habitacionRepository.save(habitacion);
        return ResponseEntity.ok(habitacionActualizada);
    }

    // Eliminar habitación
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarHabitacion(@PathVariable Long id) {
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no existe con el id :" + id));

        habitacionRepository.delete(habitacion);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminada", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
