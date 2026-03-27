package com.christopherbarillas.KinalApp.repository;

import com.christopherbarillas.KinalApp.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByEstado(int estado);
    List<Venta> findByFechaVentaBetween(Date fechaInicio, Date fechaFin);
    List<Venta> findByCliente_DPICliente(String dpiCliente);
    List<Venta> findByUsuario_CodigoUsuario(String codigoUsuario);
}