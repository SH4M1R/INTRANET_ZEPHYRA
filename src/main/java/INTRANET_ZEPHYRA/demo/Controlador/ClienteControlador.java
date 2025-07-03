package INTRANET_ZEPHYRA.demo.Controlador;

import INTRANET_ZEPHYRA.demo.Entidad.Cliente;
import INTRANET_ZEPHYRA.demo.Servicios.ClienteService;
import INTRANET_ZEPHYRA.demo.Servicios.ExcelExportServicio;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteControlador {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public String mostrarClientes(Model model) {
//        model.addAttribute("clientes", clienteService.listarClientes());
//        return "cliente"; // cliente.html en templates
        List<Cliente> lista = clienteService.listarClientes();
        model.addAttribute("clientes", lista);
        return "cliente";
    }

    @GetMapping
    public String verClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes());
        return "clientes";
    }


//    @PostMapping("/registrar")
//    public String registrar(@RequestParam String documento,
//                            @RequestParam String nombre,
//                            @RequestParam String correo) {
//        clienteService.registrarCliente(documento, nombre, correo);
//        return "redirect:/clientes";
//    }

    @PostMapping("/registrar")
    public String registrar(@RequestParam String documento,
                            @RequestParam String nombre,
                            @RequestParam String correo,
                            RedirectAttributes redirectAttributes) {

        System.out.println("=== DATOS RECIBIDOS ===");
        System.out.println("Documento: " + documento);
        System.out.println("Nombre: " + nombre);
        System.out.println("Correo: " + correo);

        try {
            clienteService.registrarCliente(documento, nombre, correo);
            redirectAttributes.addFlashAttribute("success", "Cliente registrado exitosamente");
            System.out.println("Cliente registrado correctamente");
            return "redirect:/clientes";

        } catch (Exception e) {
            System.err.println("=== ERROR EN CONTROLADOR ===");
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();

            redirectAttributes.addFlashAttribute("error", "Error al registrar cliente: " + e.getMessage());
            return "redirect:/clientes/nuevo"; // o la ruta de tu formulario
        }
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "form_cliente"; // Este será el nombre del archivo HTML con el formulario
    }
    @GetMapping("/exportar")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        List<Cliente> clientes = clienteService.listarClientes();
        ExcelExportServicio.exportarClientes(clientes, response);
    }
}