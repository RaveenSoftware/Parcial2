package co.edu.udes.backend.services;

import co.edu.udes.backend.models.Habitacion;
import co.edu.udes.backend.repositories.HabitacionRepository;
import co.edu.udes.backend.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    public Habitacion createRoom(String type, double price, int capacity) {
        Habitacion habitacion = new Habitacion(type, price, capacity);
        return habitacionRepository.save(habitacion);
    }

    public Habitacion getRoomById(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con id: " + id));
    }

    public List<Habitacion> getAllRooms() {
        return habitacionRepository.findAll();
    }

    public Habitacion updateRoom(Long id, String type, double price, int capacity) {
        Habitacion habitacion = getRoomById(id);
        habitacion.setTipo(type);
        habitacion.setPrecio(price);
        habitacion.setCapacidad(capacity);
        return habitacionRepository.save(habitacion);
    }

    public void deleteRoom(Long id) {
        Habitacion habitacion = getRoomById(id);
        habitacionRepository.delete(habitacion);
    }
}
