package com.christopherbarillas.KinalApp.controller;

import com.christopherbarillas.KinalApp.entity.Cliente;
import com.christopherbarillas.KinalApp.entity.Producto;
import com.christopherbarillas.KinalApp.service.IClienteService;
import com.christopherbarillas.KinalApp.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RestController = @Controller + @ResponseBody
@RequestMapping("/Productos")
//Todas las rutas de este controlador deben empezar con /Productos
public class ProductoController {

    //Inyectamos el SERVICIO y No el repositorio
    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }


    //Responde a peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos = productoService.listarTodos();
        //delegamos al servicio
        return ResponseEntity.ok(productos);
        //200 ok con la lista productos
    }

    //{dpi} es una variable de ruta(valor a buscar)
    @GetMapping("/{codigo}")
    public  ResponseEntity<Producto> buscarPorDPI(@PathVariable String dpi){
        return productoService.buscarPorCodigoProducto(dpi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST: crear un nuevo cliente
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Producto producto){
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo producto
        //<?> significa "tipo generico" puede ser cliente o un String
        try{
            Producto nuevoProducto = productoService.guardar(producto);
            //Intentamos guardar el cliente pero puede lanzar una excepcion
            // de IllegalArgumentException
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
            //201 CREATED(much mas especifico que el 2200 para la creacion de un cliente)
        }catch (IllegalArgumentException e){
            //Si hay error de validacion
            return  ResponseEntity.badRequest().body(e.getMessage());
            //400 BAD REQUEST con el mensaje de error

        }
    }

    //DELETE elimina un cliente
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigo) {
        //ResponseEntity<Void>: no devuelve cuerpo en la respuesta
        try {
            if (!productoService.existePorCodigoProducto(codigo)) {
                return ResponseEntity.notFound().build();
                //404 si no existe
            }
            productoService.eliminar(codigo);
            return ResponseEntity.noContent().build();
            // 204 NO CONTENT (se ejecuto correctamente y no devuelve cuerpo)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //{dpi} es una variable de ruta(valor a buscar)


    //Actualizar cliente a traves de  DPI
    @PutMapping("/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable String codigo, @RequestBody Producto producto){
        try{
            if(!productoService.existePorCodigoProducto(codigo)){
                return ResponseEntity.notFound().build();
            }
            //Actualizar el cliente pero puede lanzar una exception
            Producto productoActualizado = productoService.actualizar(codigo, producto);
            return ResponseEntity.ok(productoActualizado);
        }catch (IllegalArgumentException e){
            //Error cuando los datos son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> listarPorActivo(){
        List<Producto> productos = productoService.listarPorActivo();
        return ResponseEntity.ok(productos);

    }
}
