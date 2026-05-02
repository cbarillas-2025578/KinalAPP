package com.christopherbarillas.KinalApp.controller;

import com.christopherbarillas.KinalApp.entity.Usuario;
import com.christopherbarillas.KinalApp.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    private final IUsuarioService usuarioService;

    public RegistroController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @PostMapping
    public String registrar(@ModelAttribute Usuario usuario,
                            RedirectAttributes redirectAttributes) {
        try {
            if (usuarioService.existePorUsername(usuario.getUsername())) {
                redirectAttributes.addFlashAttribute("error",
                        "El usuario '" + usuario.getUsername() + "' ya está en uso.");
                return "redirect:/registro";
            }

            // El usuario elige su propio rol desde el formulario
            usuario.setEstado(1);
            usuarioService.guardar(usuario);

            redirectAttributes.addFlashAttribute("exito",
                    "Cuenta creada exitosamente. Ya puedes iniciar sesión.");
            return "redirect:/login";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/registro";
        }
    }
}