package co.edu.udes.backend.services;

import co.edu.udes.backend.models.Cliente;
import co.edu.udes.backend.repositories.ClienteRepository;
import co.edu.udes.backend.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente createCustomer(String name, String email, String phoneNumber) {
        Cliente cliente = new Cliente(name, email, phoneNumber);
        return clienteRepository.save(cliente);
    }

    public Cliente getCustomerById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }

    public List<Cliente> getAllCustomers() {
        return clienteRepository.findAll();
    }

    public Cliente updateCustomer(Long id, String name, String email, String phoneNumber) {
        Cliente cliente = getCustomerById(id);
        cliente.setNombre(name);
        cliente.setEmail(email);
        cliente.setTelefono(phoneNumber);
        return clienteRepository.save(cliente);
    }

    public void deleteCustomer(Long id) {
        Cliente cliente = getCustomerById(id);
        clienteRepository.delete(cliente);
    }
}
