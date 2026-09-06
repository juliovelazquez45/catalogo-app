package com.tienda.catalogo.model;

import java.math.BigDecimal;

public class ItemCarrito {

    private Long productoId;
    private String nombre;
    private BigDecimal precio;
    private int cantidad;

    public ItemCarrito() {}

    public ItemCarrito(Long productoId, String nombre, BigDecimal precio, int cantidad) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
