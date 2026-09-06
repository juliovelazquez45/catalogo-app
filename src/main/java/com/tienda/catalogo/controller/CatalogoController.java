package com.tienda.catalogo.controller;

import com.tienda.catalogo.model.Producto;
import com.tienda.catalogo.repository.CategoriaRepository;
import com.tienda.catalogo.repository.ProductoRepository;
import com.tienda.catalogo.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CatalogoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/tienda")
    public String verCatalogo(@RequestParam(required = false) Long categoriaId, Model model) {
        List<Producto> productos = (categoriaId == null)
                ? productoRepository.findAll()
                : productoRepository.findByCategoriaId(categoriaId);

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("cantidadCarrito", carritoService.getCantidadTotal());
        return "catalogo";
    }
}
