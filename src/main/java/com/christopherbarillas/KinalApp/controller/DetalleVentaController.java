package com.christopherbarillas.KinalApp.controller;

import com.christopherbarillas.KinalApp.entity.DetalleVenta;
import com.christopherbarillas.KinalApp.service.IDetalleVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController = @Controller + @ResponseBody
@RequestMapping("/detalles-venta")
//Todas las rutas de este controlador deben empezar con /detalles-venta

public class DetalleVentaController {

    //Inyectamos el SERVICIO y No el repositorio
    private final IDetalleVentaService detalleVentaService;

    public DetalleVentaController(IDetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    //Responde a peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<DetalleVenta>> listar(){
        List<DetalleVenta> detallesVenta = detalleVentaService.listarTodos();
        //delegamos al servicio
        return ResponseEntity.ok(detallesVenta);
        //200 ok con la lista de detalles de venta
    }

    //{codigo} es una variable de ruta(valor a buscar)
    @GetMapping("/{codigo}")
    public ResponseEntity<DetalleVenta> buscarPorCodigo(@PathVariable Integer codigo){
        return detalleVentaService.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST: crear un nuevo DetalleVenta
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody DetalleVenta detalleVenta){
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo DetalleVenta
        //<?> significa "tipo generico" puede ser detalleVenta o un String
        try{
            DetalleVenta nuevoDetalleVenta = detalleVentaService.guardar(detalleVenta);
            //Intentamos guardar el detalle de venta pero puede lanzar una excepcion
            // de IllegalArgumentException
            return new ResponseEntity<>(nuevoDetalleVenta, HttpStatus.CREATED);
            //201 CREATED(much mas especifico que el 200 para la creacion de un detalle de venta)
        }catch (IllegalArgumentException e){
            //Si hay error de validacion
            return ResponseEntity.badRequest().body(e.getMessage());
            //400 BAD REQUEST con el mensaje de error
        }
    }

    //DELETE elimina un detalle de venta
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer codigo) {
        //ResponseEntity<Void>: no devuelve cuerpo en la respuesta
        try {
            if (!detalleVentaService.existePorCodigo(codigo)) {
                return ResponseEntity.notFound().build();
                //404 si no existe
            }
            detalleVentaService.eliminar(codigo);
            return ResponseEntity.noContent().build();
            //204 NO CONTENT (se ejecuto correctamente y no devuelve cuerpo)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //Actualizar detalle de venta a traves de codigo
    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable Integer codigo, @RequestBody DetalleVenta detalleVenta){
        try{
            if(!detalleVentaService.existePorCodigo(codigo)){
                return ResponseEntity.notFound().build();
            }
            //Actualizar el detalle de venta pero puede lanzar una exception
            DetalleVenta detalleVentaActualizado = detalleVentaService.actualizar(codigo, detalleVenta);
            return ResponseEntity.ok(detalleVentaActualizado);
        }catch (IllegalArgumentException e){
            //Error cuando los datos son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    //Endpoint para listar detalles de venta por venta
    @GetMapping("/venta/{codigoVenta}")
    public ResponseEntity<List<DetalleVenta>> listarPorVenta(@PathVariable Integer codigoVenta){
        List<DetalleVenta> detallesVenta = detalleVentaService.listarPorVenta(codigoVenta);
        return ResponseEntity.ok(detallesVenta);
    }

    //Endpoint para listar detalles de venta por producto
    @GetMapping("/producto/{codigoProducto}")
    public ResponseEntity<List<DetalleVenta>> listarPorProducto(@PathVariable Integer codigoProducto){
        List<DetalleVenta> detallesVenta = detalleVentaService.listarPorProducto(codigoProducto);
        return ResponseEntity.ok(detallesVenta);
    }
}