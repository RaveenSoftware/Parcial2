package co.edu.udes.backend.controllers;

import co.edu.udes.backend.models.Cliente;
import co.edu.udes.backend.repositories.ClienteRepository;
import co.edu.udes.backend.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Obtener todos los clientes
    @GetMapping
    public List<Cliente> obtenerTodosClientes() {
        return clienteRepository.findAll();
    }

    // Crear un nuevo cliente
    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Obtener cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no existe con el id :" + id));
        return ResponseEntity.ok(cliente);
    }

    // Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteDetalles) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no existe con el id :" + id));

        cliente.setNombre(clienteDetalles.getNombre());
        cliente.setEmail(clienteDetalles.getEmail());
        cliente.setNumeroTelefono(clienteDetalles.getNumeroTelefono());
        Cliente clienteActualizado = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteActualizado);
    }

    // Eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarCliente(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no existe con el id :" + id));

        clienteRepository.delete(cliente);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
