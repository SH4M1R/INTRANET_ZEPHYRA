package INTRANET_ZEPHYRA.demo.Servicios;

import java.util.List;

import INTRANET_ZEPHYRA.demo.Entidad.Producto;

public interface ProductoServicio {
List<Producto> listaProductos();
public Producto guardarProducto(Producto Producto);
public Producto obtenerProducto(Integer idProducto);
public Producto actualizarProducto(Producto Producto);
public void eliminarProducto(Integer idProducto);
}
