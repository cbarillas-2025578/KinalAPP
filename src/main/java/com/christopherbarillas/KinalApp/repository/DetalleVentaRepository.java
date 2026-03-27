package com.christopherbarillas.KinalApp.repository;

import com.christopherbarillas.KinalApp.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {
    // Usando la relación @ManyToOne con Venta
    List<DetalleVenta> findByVenta_CodigoVenta(Integer codigoVenta);

    // Usando la relación @ManyToOne con Producto
    List<DetalleVenta> findByProducto_CodigoProducto(Integer codigoProducto);
}