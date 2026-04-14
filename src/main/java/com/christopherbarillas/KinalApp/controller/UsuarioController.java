package com.christopherbarillas.KinalApp.controller;

import com.christopherbarillas.KinalApp.entity.Cliente;
import com.christopherbarillas.KinalApp.entity.Usuario;
import com.christopherbarillas.KinalApp.service.IClienteService;
import com.christopherbarillas.KinalApp.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
//@RestController = @Controller + @ResponseBody
@RequestMapping("/usuarios")
//Todas las rutas de este controlador deben empezar con /usuarios

public class UsuarioController {

    //Inyectamos el SERVICIO y No el repositorio
    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    //Responde a peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codigo HTTP y el cuerpo
    public ResponseEntity<List<Usuario>> listar(){
        List<Usuario> usuarios = usuarioService.listarTodos();
        //delegamos al servicio
        return ResponseEntity.ok(usuarios);
        //200 ok con la lista clientes
    }

    //{codigo} es una variable de ruta(valor a buscar)
    @GetMapping("/{codigo}")
    public  ResponseEntity<Usuario> buscarPorCodigo(@PathVariable String codigo){
        return usuarioService.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST: crear un nuevo Usuario
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Usuario usuario){
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Cliente
        //<?> significa "tipo generico" puede ser cliente o un String
        try{
            Usuario nuevoUsuario = usuarioService.guardar(usuario);
            //Intentamos guardar el cliente pero puede lanzar una excepcion
            // de IllegalArgumentException
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
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
            if (!usuarioService.existePorCodigo(codigo)) {
                return ResponseEntity.notFound().build();
                //404 si no existe
            }
            usuarioService.eliminar(codigo);
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
    public ResponseEntity<?> actualizar(@PathVariable String codigo, @RequestBody Usuario usuario){
        try{
            if(!usuarioService.existePorCodigo(codigo)){
                return ResponseEntity.notFound().build();
            }
            //Actualizar el cliente pero puede lanzar una exception
            Usuario usuarioActualizado = usuarioService.actualizar(codigo, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        }catch (IllegalArgumentException e){
            //Error cuando los datos son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> listarPorActivo(){
        List<Usuario> usuarios = usuarioService.listarPorActivo();
        return ResponseEntity.ok(usuarios);

    }
}

