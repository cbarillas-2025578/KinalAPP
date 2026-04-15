package com.christopherbarillas.KinalApp.controller;

import com.christopherbarillas.KinalApp.entity.Cliente;
import com.christopherbarillas.KinalApp.entity.Usuario;
import com.christopherbarillas.KinalApp.entity.Venta;
import com.christopherbarillas.KinalApp.service.IClienteService;
import com.christopherbarillas.KinalApp.service.IProductoService;
import com.christopherbarillas.KinalApp.service.IUsuarioService;
import com.christopherbarillas.KinalApp.service.IVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Date;


@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final IVentaService ventaService;
    private final IProductoService productoService;
    private final IClienteService clienteService;
    private final IUsuarioService usuarioService;

    public VentaController(IVentaService ventaService, IProductoService productoService,
                           IClienteService clienteService, IUsuarioService usuarioService) {
        this.ventaService = ventaService;
        this.productoService = productoService;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarTodos());
        return "ventas/lista";
    }

    @GetMapping("/nueva")
    public String nuevaVenta(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.listarPorActivo());
        model.addAttribute("usuarios", usuarioService.listarPorActivo());
        model.addAttribute("productos", productoService.listarPorActivo());
        return "ventas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam("clienteDpi") String clienteDpi,
                          @RequestParam("usuarioCodigo") String usuarioCodigo,
                          @RequestParam("total") BigDecimal total,
                          @RequestParam(value = "estado", defaultValue = "1") int estado) {

        Venta venta = new Venta();

        // Buscar y asignar el cliente
        Cliente cliente = clienteService.buscarPorDPI(clienteDpi)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        venta.setCliente(cliente);

        // Buscar y asignar el usuario
        Usuario usuario = usuarioService.buscarPorCodigo(usuarioCodigo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        venta.setUsuario(usuario);

        venta.setTotal(total);
        venta.setEstado(estado);
        venta.setFechaVenta(new Date());

        ventaService.guardar(venta);
        return "redirect:/ventas";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable Integer codigo, Model model) {
        Venta venta = ventaService.buscarPorCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.listarPorActivo());
        model.addAttribute("usuarios", usuarioService.listarPorActivo());
        model.addAttribute("productos", productoService.listarPorActivo());
        return "ventas/formulario";
    }

    @GetMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable Integer codigo) {
        ventaService.eliminar(codigo);
        return "redirect:/ventas";
    }
}