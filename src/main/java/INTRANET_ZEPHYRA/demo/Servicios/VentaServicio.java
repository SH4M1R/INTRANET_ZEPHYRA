package INTRANET_ZEPHYRA.demo.Servicios;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import INTRANET_ZEPHYRA.demo.DAO.ProductoDAO;
import INTRANET_ZEPHYRA.demo.DAO.VentaDAO;
import INTRANET_ZEPHYRA.demo.Entidad.DetalleVenta;
import INTRANET_ZEPHYRA.demo.Entidad.Producto;
import INTRANET_ZEPHYRA.demo.Entidad.Venta;

@Service
public class VentaServicio {

    @Autowired 
    private VentaDAO ventaDAO;

    @Autowired
    private ProductoDAO productoDAO;

    public Venta registrarVenta(List<DetalleVenta> detalles) {
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        for (DetalleVenta d : detalles) {
            Producto producto = productoDAO.findById(d.getProducto().getIdProducto()).orElseThrow();
            d.setPrecioUnitario(producto.getPrecio());
            d.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(d.getCantidad())));
            d.setVenta(venta);

            producto.setStock(producto.getStock() - d.getCantidad());
            productoDAO.save(producto);

            total = total.add(d.getSubtotal());
        }

        venta.setTotal(total);
        venta.setDetalles(detalles);

        return ventaDAO.save(venta);
    }
}