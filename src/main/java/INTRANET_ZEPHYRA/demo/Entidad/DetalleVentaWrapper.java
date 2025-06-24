package INTRANET_ZEPHYRA.demo.Entidad;

import java.util.ArrayList;
import java.util.List;

public class DetalleVentaWrapper {

    private List<DetalleVenta> detalles = new ArrayList<>();

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
}