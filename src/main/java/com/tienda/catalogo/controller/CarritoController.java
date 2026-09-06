package com.tienda.catalogo.controller;

import com.tienda.catalogo.model.Producto;
import com.tienda.catalogo.repository.ProductoRepository;
import com.tienda.catalogo.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public String verCarrito(Model model) {
        model.addAttribute("items", carritoService.getItems().values());
        model.addAttribute("total", carritoService.getTotal());
        return "carrito";
    }

    @PostMapping("/agregar")
    public String agregar(@RequestParam Long productoId,
                           @RequestParam(defaultValue = "1") int cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        carritoService.agregar(producto, cantidad);
        return "redirect:/tienda";
    }

    @PostMapping("/quitar")
    public String quitar(@RequestParam Long productoId) {
        carritoService.quitar(productoId);
        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciar() {
        carritoService.vaciar();
        return "redirect:/carrito";
    }
}
