package com.christopherbarillas.KinalApp.controller;


import com.christopherbarillas.KinalApp.entity.Cliente;
import com.christopherbarillas.KinalApp.service.IClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RestController = @Controller + @ResponseBody
@RequestMapping("/clientes")
//Todas las rutas de este controlador deben empezar con /clientes
public class ClienteController {

    //Inyectamos el SERVICIO y No el repositorio
    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }


    //Responde a peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<Cliente>> listar(){
        List<Cliente> clientes = clienteService.listarTodos();
        //delegamos al servicio
        return ResponseEntity.ok(clientes);
        //200 ok con la lista clientes
    }

    //{dpi} es una variable de ruta(valor a buscar)
    @GetMapping("/{dpi}")
    public  ResponseEntity<Cliente> buscarPorDPI(@PathVariable String dpi){
        return clienteService.buscarPorDPI(dpi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST: crear un nuevo cliente
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente){
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Cliente
        //<?> significa "tipo generico" puede ser cliente o un String
        try{
            Cliente nuevoCliente = clienteService.guardar(cliente);
            //Intentamos guardar el cliente pero puede lanzar una excepcion
            // de IllegalArgumentException
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
            //201 CREATED(much mas especifico que el 2200 para la creacion de un cliente)
        }catch (IllegalArgumentException e){
            //Si hay error de validacion
            return  ResponseEntity.badRequest().body(e.getMessage());
            //400 BAD REQUEST con el mensaje de error

        }
    }

    //DELETE elimina un cliente
    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar(@PathVariable String dpi) {
        //ResponseEntity<Void>: no devuelve cuerpo en la respuesta
        try {
            if (!clienteService.existePorDPI(dpi)) {
                return ResponseEntity.notFound().build();
                //404 si no existe
            }
            clienteService.eliminar(dpi);
            return ResponseEntity.noContent().build();
            // 204 NO CONTENT (se ejecuto correctamente y no devuelve cuerpo)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //{dpi} es una variable de ruta(valor a buscar)


//Actualizar cliente a traves de  DPI
    @PutMapping("/{dpi}")
    public ResponseEntity<?> actualizar(@PathVariable String dpi, @RequestBody Cliente cliente){
        try{
            if(!clienteService.existePorDPI(dpi)){
                return ResponseEntity.notFound().build();
            }
            //Actualizar el cliente pero puede lanzar una exception
            Cliente clienteActualizado = clienteService.actualizar(dpi, cliente);
            return ResponseEntity.ok(clienteActualizado);
        }catch (IllegalArgumentException e){
            //Error cuando los datos son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
          return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Cliente>> listarPorActivo(){
        List<Cliente> clientes = clienteService.listarPorActivo();
        return ResponseEntity.ok(clientes);

    }
}
