package com.tienda.catalogo.controller;

import com.tienda.catalogo.model.Categoria;
import com.tienda.catalogo.model.Producto;
import com.tienda.catalogo.repository.CategoriaRepository;
import com.tienda.catalogo.repository.ProductoRepository;
import com.tienda.catalogo.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ImagenService imagenService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "admin";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "admin-form";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "admin-form";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Long id,
                           @RequestParam String nombre,
                           @RequestParam BigDecimal precio,
                           @RequestParam(required = false) Long categoriaId,
                           @RequestParam(required = false) boolean destacado,
                           @RequestParam(required = false) MultipartFile imagen) throws IOException {

        Producto producto = (id != null)
                ? productoRepository.findById(id).orElse(new Producto())
                : new Producto();

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDestacado(destacado);

        if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
            producto.setCategoria(categoria);
        }

        if (imagen != null && !imagen.isEmpty()) {
            if (producto.getImagenUrl() != null) {
                imagenService.borrar(producto.getImagenUrl());
            }
            String rutaNueva = imagenService.guardar(imagen);
            producto.setImagenUrl(rutaNueva);
        }

        productoRepository.save(producto);
        return "redirect:/admin";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoRepository.findById(id).ifPresent(producto -> {
            imagenService.borrar(producto.getImagenUrl());
            productoRepository.delete(producto);
        });
        return "redirect:/admin";
    }
}
