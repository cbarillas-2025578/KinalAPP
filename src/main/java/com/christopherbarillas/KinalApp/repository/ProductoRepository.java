package com.christopherbarillas.KinalApp.repository;

import com.christopherbarillas.KinalApp.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,String> {
}
