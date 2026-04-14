package com.christopherbarillas.KinalApp.repository;

import com.christopherbarillas.KinalApp.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    // El 'Long' de aquí debe coincidir con el tipo de @Id en la entidad
}