INSERT INTO categorias (id, nombre) VALUES (1, 'Destacados') ON CONFLICT (id) DO NOTHING;
INSERT INTO categorias (id, nombre) VALUES (2, 'General') ON CONFLICT (id) DO NOTHING;

INSERT INTO productos (id, nombre, precio, imagen_url, destacado, categoria_id) VALUES
(1, 'NSPA BODY SPLASH AMEIXA NEGRA', 149000, 'https://via.placeholder.com/300x300?text=Producto+1', true, 1),
(2, 'NATIVA SPA LOCION HIDRATANTE CORPORAL AMEIXA NEGRA 400ml', 135000, 'https://via.placeholder.com/300x300?text=Producto+2', false, 2),
(3, 'NATIVA SPA OLEO HIDRATANTE CORPORAL DE AMEIXA NEGRA 200ml', 140000, 'https://via.placeholder.com/300x300?text=Producto+3', false, 2),
(4, 'ESTUCHE NATIVA SPA AMEIXA', 142000, 'https://via.placeholder.com/300x300?text=Producto+4', false, 2),
(5, 'NATIVA SPA LOCION HIDRATANTE CORPORAL JASMIN SAMBAC 400ml', 135000, 'https://via.placeholder.com/300x300?text=Producto+5', false, 2),
(6, 'NATIVA SPA BODY SPLASH JASMIN SAMBAC 200ml', 149000, 'https://via.placeholder.com/300x300?text=Producto+6', false, 2)
ON CONFLICT (id) DO NOTHING;
