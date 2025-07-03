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

//    public void registrarCliente(String documento, String nombre, String correo) {
//        if (clienteRepository.existsByDocumento(documento)) return;
//
//        Cliente cliente = new Cliente();
//        cliente.setDocumento(documento);
//        cliente.setNombre(nombre);
//        cliente.setCorreo(correo);
//
//        Rol rolCliente = RolRepositorio.findByNombre("CLIENTE")
//                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no existe"));
//
//        cliente.agregarRol(rolCliente);
//        clienteRepository.save(cliente);
//    }
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
}