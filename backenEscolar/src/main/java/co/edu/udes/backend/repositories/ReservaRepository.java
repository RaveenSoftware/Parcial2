package co.edu.udes.backend.repositories;

import co.edu.udes.backend.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    //ya corregio porfin
    // con esto busco todas las reservas de una habitación específica
    List<Reserva> findByHabitacionId(Long habitacionId);

    // Buscar todas las reservas de un cliente
    List<Reserva> findByClienteId(Long clienteId);

    // con esto busco reservas cuya fecha de inicio sea posterior a una fecha dada
    List<Reserva> findByFechaInicioAfter(LocalDate fecha);

    // con esto busco reservas de una habitación en un rango de fechas (si lo necesitas)
    List<Reserva> findByHabitacionIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            Long habitacionId, LocalDate fechaFin, LocalDate fechaInicio);

    //con esto verifico la disponibilidad de la habitacion
    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.habitacion.id = :habitacionId AND r.fechaInicio < :fechaFin AND r.fechaFin > :fechaInicio")
    boolean verificarDisponibilidad(@Param("habitacionId") Long habitacionId,
                                    @Param("fechaInicio") LocalDate fechaInicio,
                                    @Param("fechaFin") LocalDate fechaFin);
}
