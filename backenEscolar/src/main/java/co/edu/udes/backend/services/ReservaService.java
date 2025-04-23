package co.edu.udes.backend.services;
import java.util.stream.Collectors;
import co.edu.udes.backend.models.Cliente;
import co.edu.udes.backend.models.Habitacion;
import co.edu.udes.backend.models.Reserva;
import co.edu.udes.backend.repositories.ClienteRepository;
import co.edu.udes.backend.repositories.HabitacionRepository;
import co.edu.udes.backend.repositories.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    public Reserva crearReserva(Long clienteId, Long habitacionId, LocalDate fechaInicio, LocalDate fechaFin) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        Habitacion habitacion = habitacionRepository.findById(habitacionId)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada"));

        List<Reserva> reservasExistentes = reservaRepository.findByHabitacionId(habitacionId);

        for (Reserva r : reservasExistentes) {
            if (fechaInicio.isBefore(r.getFechaFin()) && fechaFin.isAfter(r.getFechaInicio())) {
                throw new IllegalArgumentException("La habitación ya está reservada en este rango de fechas.");
            }
        }

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setHabitacion(habitacion);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        return reservaRepository.save(reserva);
    }

    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
    }

    public List<Reserva> obtenerReservasPorCliente(Long clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }

    public List<Reserva> obtenerReservasPorHabitacion(Long habitacionId) {
        return reservaRepository.findByHabitacionId(habitacionId);
    }

    @Transactional
    public Reserva actualizarFechasReserva(Long id, LocalDate nuevaFechaInicio, LocalDate nuevaFechaFin) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        List<Reserva> reservasExistentes = reservaRepository.findByHabitacionId(reserva.getHabitacion().getId());

        for (Reserva r : reservasExistentes) {
            if (!id.equals(r.getId()) &&
                    r.getFechaInicio().isBefore(nuevaFechaFin) &&
                    nuevaFechaInicio.isBefore(r.getFechaFin())) {
                throw new IllegalArgumentException("La habitación ya está reservada en este rango de fechas.");
            }
        }

        reserva.setFechaInicio(nuevaFechaInicio);
        reserva.setFechaFin(nuevaFechaFin);
        return reservaRepository.save(reserva);
    }

    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        reservaRepository.delete(reserva);
    }

    public boolean verificarDisponibilidad(Long habitacionId, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Reserva> reservas = reservaRepository.findByHabitacionId(habitacionId);
        for (Reserva r : reservas) {
            if (fechaInicio.isBefore(r.getFechaFin()) && fechaFin.isAfter(r.getFechaInicio())) {
                return false;
            }
        }
        return true;
    }

    public List<Habitacion> obtenerHabitacionesDisponibles(LocalDate fechaInicio, LocalDate fechaFin, int capacidad) {
        List<Habitacion> habitaciones = habitacionRepository.findByCapacidadGreaterThanEqual(capacidad);
        return habitaciones.stream()
                .filter(h -> verificarDisponibilidad(h.getId(), fechaInicio, fechaFin))
                .collect(Collectors.toList());
    }

    public List<Reserva> obtenerReservasRecientes(int dias) {
        LocalDate desde = LocalDate.now().minusDays(dias);
        return reservaRepository.findByFechaInicioAfter(desde);
    }
}
