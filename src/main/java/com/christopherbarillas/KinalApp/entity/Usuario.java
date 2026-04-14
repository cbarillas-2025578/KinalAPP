package com.christopherbarillas.KinalApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @Column(name = "codigo_usuario")
    private String CodigoUsuario;
    @Column
    private String Username;
    @Column
    private String Password;
    @Column
    private String Email;
    @Column
    private String Rol;
    @Column
    private int estado;

    public Usuario() {
    }

    public Usuario(String codigoUsuario, String username, String password, String email, String rol, int estado) {
        CodigoUsuario = codigoUsuario;
        Username = username;
        Password = password;
        Email = email;
        Rol = rol;
        this.estado = estado;
    }

    public String getCodigoUsuario() {
        return CodigoUsuario;
    }

    public String setCodigoUsuario(Usuario codigoUsuario) {
        return CodigoUsuario;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
