package com.christopherbarillas.KinalApp.repository;

import com.christopherbarillas.KinalApp.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,String> {
    List<Producto> findByEstado(int i);
}
