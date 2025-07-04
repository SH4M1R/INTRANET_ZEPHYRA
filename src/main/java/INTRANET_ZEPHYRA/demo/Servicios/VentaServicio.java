package INTRANET_ZEPHYRA.demo.Servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import INTRANET_ZEPHYRA.demo.DAO.DetalleVentaDAO;
import INTRANET_ZEPHYRA.demo.DAO.ProductoDAO;
import INTRANET_ZEPHYRA.demo.DAO.UsuarioRepositorio;
import INTRANET_ZEPHYRA.demo.DAO.VentaDAO;
import INTRANET_ZEPHYRA.demo.Entidad.Usuario;
import INTRANET_ZEPHYRA.demo.Entidad.DetalleVenta;
import INTRANET_ZEPHYRA.demo.Entidad.Producto;
import INTRANET_ZEPHYRA.demo.Entidad.Venta;

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

public void registrarVenta(Venta venta, String username) {
    Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

    venta.setUsuario(usuario);
    venta.setFecha(LocalDate.now());

    venta = ventaRepository.save(venta);

    for (DetalleVenta detalle : venta.getDetalles()) {
        Producto producto = productoRepository.findById(detalle.getProducto().getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < detalle.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        producto.setStock(producto.getStock() - detalle.getCantidad());
        productoRepository.save(producto);
        detalle.setVenta(venta);
        detalle.setProducto(producto);
        detalleVentaRepository.save(detalle);
    }
    System.out.println("Registrando venta para usuario: " + usuario.getUsername());

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
        List<Integer> valores = new ArrayList<>();
        for (Integer mes : conteo.keySet()) {
            valores.add(conteo.get(mes));
        }
        return valores;
    }

    public long obtenerTotalVentas() {
        return ventaRepository.count();
    }

    public List<Double> obtenerTotalVentaPorMes() {
    List<Venta> ventas = ventaRepository.findAll();
    Map<Integer, Double> totalPorMes = new TreeMap<>();

    for (Venta v : ventas) {
        int mes = v.getFecha().getMonthValue();
        double total = v.getTotalVenta();

        totalPorMes.put(mes, totalPorMes.getOrDefault(mes, 0.0) + total);
    }

    List<Double> valores = new ArrayList<>();
    for (Integer mes : totalPorMes.keySet()) {
        valores.add(totalPorMes.get(mes));
    }
    return valores;
}
}