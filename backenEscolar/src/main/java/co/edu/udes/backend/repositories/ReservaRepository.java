package co.edu.udes.backend.repositories;

import co.edu.udes.backend.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Buscar todas las reservas de una habitación específica
    List<Reserva> findByHabitacionId(Long habitacionId);

    // Buscar todas las reservas de un cliente
    List<Reserva> findByClienteId(Long clienteId);

    // Buscar reservas cuya fecha de inicio sea posterior a una fecha dada
    List<Reserva> findByFechaInicioAfter(LocalDate fecha);

    // Buscar reservas de una habitación en un rango de fechas (si lo necesitas)
    List<Reserva> findByHabitacionIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            Long habitacionId, LocalDate fechaFin, LocalDate fechaInicio);
}
