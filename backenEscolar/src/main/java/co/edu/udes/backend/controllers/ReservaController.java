package co.edu.udes.backend.controllers;

import co.edu.udes.backend.models.Reserva;
import co.edu.udes.backend.repositories.ReservaRepository;
import co.edu.udes.backend.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    // Obtener todas las reservas
    @GetMapping
    public List<Reserva> obtenerTodasReservas() {
        return reservaRepository.findAll();
    }

    // Crear una nueva reserva
    @PostMapping
    public Reserva crearReserva(@RequestBody Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    // Obtener reserva por id
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no existe con el id :" + id));
        return ResponseEntity.ok(reserva);
    }

    // Obtener reservas por cliente
    @GetMapping("/cliente/{clienteId}")
    public List<Reserva> obtenerReservasPorCliente(@PathVariable Long clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }

    // Obtener reservas por habitación
    @GetMapping("/habitacion/{habitacionId}")
    public List<Reserva> obtenerReservasPorHabitacion(@PathVariable Long habitacionId) {
        return reservaRepository.findByHabitacionId(habitacionId);
    }

    // Actualizar fechas de reserva
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReservaFechas(@PathVariable Long id, @RequestBody Reserva reservaDetalles) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no existe con el id :" + id));

        reserva.setFechaInicio(reservaDetalles.getFechaInicio());
        reserva.setFechaFin(reservaDetalles.getFechaFin());
        Reserva reservaActualizada = reservaRepository.save(reserva);
        return ResponseEntity.ok(reservaActualizada);
    }

    // Cancelar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> cancelarReserva(@PathVariable Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no existe con el id :" + id));

        reservaRepository.delete(reserva);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("cancelada", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    // Verificar disponibilidad de habitación
    @GetMapping("/disponibilidad/{habitacionId}")
    public boolean verificarDisponibilidad(@PathVariable Long habitacionId,
                                           @RequestParam("fechaInicio") LocalDate fechaInicio,
                                           @RequestParam("fechaFin") LocalDate fechaFin) {
        return reservaRepository.verificarDisponibilidad(habitacionId, fechaInicio, fechaFin);
    }
}
