package INTRANET_ZEPHYRA.demo.Entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private Integer idProducto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "detalle", columnDefinition = "TEXT")
    private String detalle;

    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    @Column(name = "tamaño", length = 20)
    private String tamaño;

    @Column(name = "color", length = 20)
    private String color;


    public Producto() {
    }

    public Producto(Integer idProducto, String nombre, BigDecimal precio,
                    String detalle, Integer stock, String tamaño, 
                    String color) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.detalle = detalle;
        this.stock = stock;
        this.tamaño = tamaño;
        this.color = color;
        this.idProducto = idProducto;
    }

    public Integer getIdProducto() {   return idProducto; } 
    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) {
        this.detalle = detalle; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) {
        this.stock = stock; }

    public String getTamaño() { return tamaño; }
    public void setTamaño(String tamaño) {
        this.tamaño = tamaño; }

    public String getColor() { return color; }
    public void setColor(String color) {
        this.color = color; }
}