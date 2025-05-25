package INTRANET_ZEPHYRA.demo.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import INTRANET_ZEPHYRA.demo.Entidad.Producto;

@Service
public class ProductoServicioImpl implements ProductoServicio{
    @Autowired
    private INTRANET_ZEPHYRA.demo.DAO.ProductoDAO ProductoDAO;
    @Override
    public List<Producto> listaProductos(){
      return ProductoDAO.findAll();
    }
}
