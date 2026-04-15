package com.christopherbarillas.KinalApp.controller;

import com.christopherbarillas.KinalApp.entity.Usuario;
import com.christopherbarillas.KinalApp.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/lista"; // templates/usuarios/lista.html
    }

    @GetMapping("/nuevo")
    public String formulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable String codigo, Model model) {
        Usuario usuario = usuarioService.buscarPorCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "usuarios/formulario";
    }

}