package INTRANET_ZEPHYRA.demo.Servicios;

import INTRANET_ZEPHYRA.demo.DAO.ClienteRepositorio;
import INTRANET_ZEPHYRA.demo.Entidad.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepositorio clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public void registrarCliente(String documento, String nombre, String correo) {
        System.out.println("=== INICIANDO REGISTRO EN SERVICIO ===");

        try {
            // Crear la entidad Cliente
            Cliente cliente = new Cliente();
            cliente.setDocumento(documento);
            cliente.setNombre(nombre);
            cliente.setCorreo(correo);

            System.out.println("Cliente creado: " + cliente);

            // Guardar en base de datos
            Cliente clienteGuardado = clienteRepository.save(cliente);
            System.out.println("Cliente guardado con ID: " + clienteGuardado.getId());

        } catch (Exception e) {
            System.err.println("=== ERROR EN SERVICIO ===");
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-lanzar la excepción
        }
    }

    /**
     * Obtiene un cliente por su documento (DNI), y si no existe lo registra.
     * Si no se proporciona documento, busca o crea el cliente "CLIENTE VARIOS".
     */
    public Cliente obtenerORegistrarCliente(String documento, String nombre, String correo) {
        if (documento != null && !documento.trim().isEmpty()) {
            // Buscar por documento (DNI)
            return clienteRepository.findByDocumento(documento)
                    .orElseGet(() -> {
                        Cliente nuevo = new Cliente();
                        nuevo.setDocumento(documento);
                        nuevo.setNombre(nombre != null && !nombre.trim().isEmpty() ? nombre : "CLIENTE SIN NOMBRE");
                        nuevo.setCorreo(correo);
                        return clienteRepository.save(nuevo);
                    });
        } else {
            // Si no se proporcionó documento, usar o crear CLIENTE VARIOS
            return clienteRepository.findByNombre("CLIENTE VARIOS")
                    .orElseGet(() -> {
                        Cliente clienteVarios = new Cliente();
                        clienteVarios.setDocumento("00000000");    
                        clienteVarios.setNombre("CLIENTE VARIOS");
                        clienteVarios.setCorreo("varios@cliente.com");
                        return clienteRepository.save(clienteVarios);
                    });
        }
    }
}
