package com.tienda.catalogo.service;

import com.tienda.catalogo.model.ItemCarrito;
import com.tienda.catalogo.model.Producto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)
public class CarritoService {

    private final Map<Long, ItemCarrito> items = new LinkedHashMap<>();

    public void agregar(Producto producto, int cantidad) {
        items.merge(
                producto.getId(),
                new ItemCarrito(producto.getId(), producto.getNombre(), producto.getPrecio(), cantidad),
                (existente, nuevo) -> {
                    existente.setCantidad(existente.getCantidad() + cantidad);
                    return existente;
                }
        );
    }

    public void quitar(Long productoId) {
        items.remove(productoId);
    }

    public void vaciar() {
        items.clear();
    }

    public Map<Long, ItemCarrito> getItems() {
        return items;
    }

    public int getCantidadTotal() {
        return items.values().stream().mapToInt(ItemCarrito::getCantidad).sum();
    }

    public BigDecimal getTotal() {
        return items.values().stream()
                .map(ItemCarrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
