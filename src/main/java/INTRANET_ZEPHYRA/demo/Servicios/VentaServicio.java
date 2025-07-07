package INTRANET_ZEPHYRA.demo.Servicios;

import INTRANET_ZEPHYRA.demo.DAO.*;
import INTRANET_ZEPHYRA.demo.Entidad.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class VentaServicio {

    @Autowired
    private VentaDAO ventaRepository;

    @Autowired
    private DetalleVentaDAO detalleVentaRepository;

    @Autowired
    private ProductoDAO productoRepository;

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private ClienteRepositorio clienteRepository;

    public void registrarVenta(
    Venta venta,
    String username,
    Cliente clienteForm,
    String metodoPago,
    Double montoPagado,
    String codigoOperacion,
    String numeroTarjeta)
 {

        // Buscar usuario autenticado
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        venta.setFecha(LocalDateTime.now());
        venta.setUsuario(usuario);

        // Buscar o registrar cliente
        Cliente cliente = clienteRepository.findByDocumento(clienteForm.getDocumento())
                .orElseGet(() -> {
                    Cliente nuevo = new Cliente();
                    nuevo.setDocumento(clienteForm.getDocumento());
                    nuevo.setNombre(clienteForm.getNombre());
                    nuevo.setCorreo(clienteForm.getCorreo());
                    return clienteRepository.save(nuevo);
                });

        venta.setCliente(cliente);

        // Procesar detalles
        double totalVenta = 0;

        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            double subtotal = producto.getPrecio().doubleValue() * detalle.getCantidad();
            totalVenta += subtotal;

            // Actualizar stock
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);

            // Asignar datos al detalle
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setSubtotal(subtotal);
            detalle.setMetodoPago(metodoPago);
            detalle.setMontoPagado(montoPagado);
            detalle.setVuelto(montoPagado != null ? montoPagado - subtotal : 0.0);
            detalle.setCodigoIzipay(codigoOperacion);
            detalle.setNumeroTarjeta(numeroTarjeta);

            detalleVentaRepository.save(detalle);
        }

        venta.setTotal(totalVenta);
        ventaRepository.save(venta);
    }

    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }

    public List<String> obtenerMesesVentas() {
        List<Venta> ventas = ventaRepository.findAll();
       Map<Integer, String> mesesMap = new HashMap<>();
mesesMap.put(1, "Enero");
mesesMap.put(2, "Febrero");
mesesMap.put(3, "Marzo");
mesesMap.put(4, "Abril");
mesesMap.put(5, "Mayo");
mesesMap.put(6, "Junio");
mesesMap.put(7, "Julio");
mesesMap.put(8, "Agosto");
mesesMap.put(9, "Septiembre");
mesesMap.put(10, "Octubre");
mesesMap.put(11, "Noviembre");
mesesMap.put(12, "Diciembre");

        Set<Integer> meses = new TreeSet<>();
        for (Venta v : ventas) {
            meses.add(v.getFecha().getMonthValue());
        }

        List<String> labels = new ArrayList<>();
        for (Integer mes : meses) {
            labels.add(mesesMap.getOrDefault(mes, ""));
        }

        return labels;
    }

    public List<Integer> obtenerVentasPorMes() {
        List<Venta> ventas = ventaRepository.findAll();
        Map<Integer, Integer> conteo = new TreeMap<>();

        for (Venta v : ventas) {
            int mes = v.getFecha().getMonthValue();
            conteo.put(mes, conteo.getOrDefault(mes, 0) + 1);
        }

        return new ArrayList<>(conteo.values());
    }

    public List<Double> obtenerTotalVentaPorMes() {
        List<Venta> ventas = ventaRepository.findAll();
        Map<Integer, Double> totalPorMes = new TreeMap<>();

        for (Venta v : ventas) {
            int mes = v.getFecha().getMonthValue();
            double total = v.getTotal() != null ? v.getTotal() : 0.0;
            totalPorMes.put(mes, totalPorMes.getOrDefault(mes, 0.0) + total);
        }

        return new ArrayList<>(totalPorMes.values());
    }

    public long obtenerTotalVentas() {
        return ventaRepository.count();
    }

    public Venta obtenerVentaPorId(Long id) {
    return ventaRepository.findById(id).orElseThrow(() -> new RuntimeException("Venta no encontrada"));
}
}
