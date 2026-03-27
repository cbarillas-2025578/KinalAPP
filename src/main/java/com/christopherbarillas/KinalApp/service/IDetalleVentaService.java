package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.DetalleVenta;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {
    //Interfaz: Es un contrato que dice QUE metodos debe tener
    //cualquier servicio de DetalleVenta, no tiene
    //implementacion, solo la definicion de los metodos

    //Metodo que devuelve una lista de todos los Detalles de Venta
    List<DetalleVenta> listarTodos();
    //List<DetalleVenta> lo que hace es devolver una lista de objetos de la entidad DetalleVenta

    //Metodo que guarda un detalle de venta en BD
    DetalleVenta guardar(DetalleVenta detalleVenta);
    //Parametros - Recibe un objeto de tipo DetalleVenta con los datos a guardar

    //Optional - Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<DetalleVenta> buscarPorCodigo(Integer codigo);

    //Metodo que actualiza un DetalleVenta
    DetalleVenta actualizar(Integer codigo, DetalleVenta detalleVenta);
    //Parametros - codigo: codigo del detalle de venta a actualizar
    //DetalleVenta detalleVenta: Objeto con los datos nuevos
    //Retorna un objeto de tipo DetalleVenta ya actualizado

    //Metodo de tipo void para eliminar un detalle de venta
    //void: no retorna ningun dato
    //Elimina un detalle de venta por su codigo
    void eliminar(Integer codigo);

    //boolean - Retorna true si existe, false si no existe
    boolean existePorCodigo(Integer codigo);

    //Metodo que devuelve una lista por venta
    List<DetalleVenta> listarPorVenta(Integer codigoVenta);

    //Metodo que devuelve una lista por producto
    List<DetalleVenta> listarPorProducto(Integer codigoProducto);
}
