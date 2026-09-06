package com.tienda.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogoApplication.class, args);
        System.out.println("=====================================");
        System.out.println(" Catalogo corriendo en: http://localhost:8080/tienda");
        System.out.println("=====================================");
    }
}
