package com.tienda.catalogo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ImagenService {

    @Value("${app.upload-dir}")
    private String uploadDir;

    public String guardar(MultipartFile archivo) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }

        Path carpeta = Paths.get(uploadDir);
        if (!Files.exists(carpeta)) {
            Files.createDirectories(carpeta);
        }

        String nombreOriginal = archivo.getOriginalFilename();
        String extension = "";
        if (nombreOriginal != null && nombreOriginal.contains(".")) {
            extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        }

        String nombreUnico = UUID.randomUUID() + extension;
        Path destino = carpeta.resolve(nombreUnico);
        Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + nombreUnico;
    }

    public void borrar(String rutaWeb) {
        if (rutaWeb == null || !rutaWeb.startsWith("/uploads/")) {
            return;
        }
        try {
            String nombreArchivo = rutaWeb.substring("/uploads/".length());
            Files.deleteIfExists(Paths.get(uploadDir).resolve(nombreArchivo));
        } catch (IOException e) {
            System.err.println("No se pudo borrar el archivo: " + e.getMessage());
        }
    }
}
