package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.Venta;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IVentaService {
    //Interfaz: Es un contrato que dice QUE metodos debe tener
    //cualquier servicio de Ventas, no tiene
    //implementacion, solo la definicion de los metodos

    //Metodo que devuelve una lista de todas las Ventas
    List<Venta> listarTodos();
    //List<Venta> lo que hace es devolver una lista de objetos de la entidad Venta

    //Metodo que guarda una venta en BD
    Venta guardar(Venta venta);
    //Parametros - Recibe un objeto de tipo Venta con los datos a guardar

    //Optional - Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<Venta> buscarPorCodigo(Integer codigo);

    //Metodo que actualiza una Venta
    Venta actualizar(Integer codigo, Venta venta);
    //Parametros - codigo: codigo de la venta a actualizar
    //Venta venta: Objeto con los datos nuevos
    //Retorna un objeto de tipo Venta ya actualizado

    //Metodo de tipo void para eliminar una venta
    //void: no retorna ningun dato
    //Elimina una venta por su codigo
    void eliminar(Integer codigo);

    //boolean - Retorna true si existe, false si no existe
    boolean existePorCodigo(Integer codigo);

    //Metodo que devuelve una lista por estado
    List<Venta> listarPorActivo();

    //Metodo que devuelve una lista por rango de fechas
    List<Venta> listarPorRangoFechas(Date fechaInicio, Date fechaFin);

    //Metodo que devuelve una lista por cliente
    List<Venta> listarPorCliente(String dpiCliente);

    //Metodo que devuelve una lista por usuario
    List<Venta> listarPorUsuario(String codigoUsuario);
}