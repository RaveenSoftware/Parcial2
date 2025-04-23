package co.edu.udes.backend.repositories;

import co.edu.udes.backend.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // No agregue aqui metodo personalizado porque no se especificava
}
