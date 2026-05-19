package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.DetalleVenta;
import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {
    List<DetalleVenta> listarTodos();
    DetalleVenta guardar(DetalleVenta detalle);
    Optional<DetalleVenta> buscarPorId(Long id); // Cambiar a Long si decía Integer
    void eliminar(Long id); // Cambiar a Long si decía Integer
}