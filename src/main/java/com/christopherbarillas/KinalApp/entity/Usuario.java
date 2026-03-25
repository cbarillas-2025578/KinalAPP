package com.christopherbarillas.KinalApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @Column(name = "codigo_venta")
    private String CodigoVenta;
    @Column
    private String FechaVenta;
    @Column
    private String Total;
    @Column
    private int estado;

    public Usuario() {
    }

    public Usuario(String codigoVenta, String fechaVenta, String total, int estado) {
        CodigoVenta = codigoVenta;
        FechaVenta = fechaVenta;
        Total = total;
        this.estado = estado;
    }

    public String getCodigoVenta() {
        return CodigoVenta;
    }

    public void setCodigoVenta(String codigoVenta) {
        CodigoVenta = codigoVenta;
    }

    public String getFechaVenta() {
        return FechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        FechaVenta = fechaVenta;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
