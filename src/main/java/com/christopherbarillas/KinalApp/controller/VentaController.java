package com.christopherbarillas.KinalApp.controller;

import com.christopherbarillas.KinalApp.entity.Venta;
import com.christopherbarillas.KinalApp.service.IVentaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
//@RestController = @Controller + @ResponseBody
@RequestMapping("/ventas")
//Todas las rutas de este controlador deben empezar con /ventas

public class VentaController {

    //Inyectamos el SERVICIO y No el repositorio
    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    //Responde a peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<Venta>> listar(){
        List<Venta> ventas = ventaService.listarTodos();
        //delegamos al servicio
        return ResponseEntity.ok(ventas);
        //200 ok con la lista de ventas
    }

    //{codigo} es una variable de ruta(valor a buscar)
    @GetMapping("/{codigo}")
    public ResponseEntity<Venta> buscarPorCodigo(@PathVariable Integer codigo){
        return ventaService.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST: crear una nueva Venta
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Venta venta){
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Venta
        //<?> significa "tipo generico" puede ser venta o un String
        try{
            Venta nuevaVenta = ventaService.guardar(venta);
            //Intentamos guardar la venta pero puede lanzar una excepcion
            // de IllegalArgumentException
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
            //201 CREATED(much mas especifico que el 200 para la creacion de una venta)
        }catch (IllegalArgumentException e){
            //Si hay error de validacion
            return ResponseEntity.badRequest().body(e.getMessage());
            //400 BAD REQUEST con el mensaje de error
        }
    }

    //DELETE elimina una venta
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer codigo) {
        //ResponseEntity<Void>: no devuelve cuerpo en la respuesta
        try {
            if (!ventaService.existePorCodigo(codigo)) {
                return ResponseEntity.notFound().build();
                //404 si no existe
            }
            ventaService.eliminar(codigo);
            return ResponseEntity.noContent().build();
            //204 NO CONTENT (se ejecuto correctamente y no devuelve cuerpo)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //Actualizar venta a traves de codigo
    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable Integer codigo, @RequestBody Venta venta){
        try{
            if(!ventaService.existePorCodigo(codigo)){
                return ResponseEntity.notFound().build();
            }
            //Actualizar la venta pero puede lanzar una exception
            Venta ventaActualizada = ventaService.actualizar(codigo, venta);
            return ResponseEntity.ok(ventaActualizada);
        }catch (IllegalArgumentException e){
            //Error cuando los datos son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    //Endpoint para listar ventas activas
    @GetMapping("/activos")
    public ResponseEntity<List<Venta>> listarPorActivo(){
        List<Venta> ventas = ventaService.listarPorActivo();
        return ResponseEntity.ok(ventas);
    }

    //Endpoint para listar ventas por rango de fechas
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Venta>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin){
        List<Venta> ventas = ventaService.listarPorRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(ventas);
    }

    //Endpoint para listar ventas por cliente
    @GetMapping("/cliente/{dpiCliente}")
    public ResponseEntity<List<Venta>> listarPorCliente(@PathVariable String dpiCliente){
        List<Venta> ventas = ventaService.listarPorCliente(dpiCliente);
        return ResponseEntity.ok(ventas);
    }

    //Endpoint para listar ventas por usuario
    @GetMapping("/usuario/{codigoUsuario}")
    public ResponseEntity<List<Venta>> listarPorUsuario(@PathVariable String codigoUsuario){
        List<Venta> ventas = ventaService.listarPorUsuario(codigoUsuario);
        return ResponseEntity.ok(ventas);
    }
}