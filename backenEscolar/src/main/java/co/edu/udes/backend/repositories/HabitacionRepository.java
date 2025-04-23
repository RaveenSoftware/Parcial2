package co.edu.udes.backend.repositories;

import co.edu.udes.backend.models.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    // aqui busco habitaciones con capacidad mayor o igual a la indicada
    List<Habitacion> findByCapacidadGreaterThanEqual(int capacidad);
}
