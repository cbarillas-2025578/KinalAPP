package com.christopherbarillas.KinalApp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalleventa")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_detalle_venta")
    private long codigoDetalleVenta;

    @Column
    private int cantidad;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    @Column
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "Ventas_codigo_venta", referencedColumnName = "codigo_venta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "Productos_codigo_producto", referencedColumnName = "codigo_producto")
    private Producto producto;

    public DetalleVenta() {}

    public DetalleVenta(long codigoDetalleVenta, int cantidad, BigDecimal precioUnitario,
                        BigDecimal subtotal, Venta venta, Producto producto) {
        this.codigoDetalleVenta = codigoDetalleVenta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.venta = venta;
        this.producto = producto;
    }


    public long getCodigoDetalleVenta() { return codigoDetalleVenta; }
    public void setCodigoDetalleVenta(long codigoDetalleVenta) { this.codigoDetalleVenta = codigoDetalleVenta; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
}