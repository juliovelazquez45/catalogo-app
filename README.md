# Catálogo Online (Java + Spring Boot + PostgreSQL)

Proyecto base tipo "Cataly": catálogo de productos con carrito de compras
y panel de administración con subida de imágenes.

## 1. Programas que necesitás instalar

1. **JDK 17+** → https://adoptium.net
2. **PostgreSQL** → https://www.postgresql.org/download/
   (el instalador incluye pgAdmin, la herramienta visual para ver la base)
3. **IntelliJ IDEA Community** (gratis) → https://www.jetbrains.com/idea/download
4. **Git** (solo si vas a publicar la tienda en internet) → https://git-scm.com/downloads

## 2. Crear la base de datos

PostgreSQL NO crea la base de datos sola. Hay que crearla a mano una vez,
desde pgAdmin o psql:

```sql
CREATE DATABASE catalogo_db;
```

## 3. Configurar la conexión

Abrí `src/main/resources/application.properties` y cambiá el valor por
defecto de la contraseña (`TU_CLAVE_AQUI`) por tu contraseña real de
PostgreSQL. También podés cambiar el usuario/clave del panel `/admin`
(por defecto: usuario `admin`, clave `cambiame123`).

## 4. Abrir y correr el proyecto

- En IntelliJ: `File > Open`, seleccioná la carpeta `catalogo-app`
- Configurá el JDK si te lo pide ("Setup SDK")
- Abrí `CatalogoApplication.java` y click en el botón ▶

## 5. Ver la tienda y el panel

- Tienda: http://localhost:8080/tienda
- Panel de administración: http://localhost:8080/admin
  (te va a pedir el usuario/clave configurados en el paso 3)

## Panel de administración

- **Nuevo producto**: nombre, precio, categoría, y subir una foto desde tu compu
- **Editar**: cambiar cualquier dato, incluida la imagen
- **Eliminar**: borra el producto (pide confirmación)

Las imágenes subidas se guardan en una carpeta `uploads/` al lado del
proyecto. Esa carpeta no se borra si recompilás.

## Poner la tienda permanentemente en internet (gratis)

Usamos **Neon** (PostgreSQL gratis en la nube) + **Render** (aloja tu
aplicación Java, plan gratis).

### Paso 1: Crear la base de datos en Neon
1. https://neon.tech → cuenta gratis → proyecto nuevo `catalogo_db`
2. Copiá la "Connection string" que te da

### Paso 2: Subir tu proyecto a GitHub
1. Cuenta gratis en https://github.com
2. Repositorio nuevo `catalogo-app` (vacío, sin README ni .gitignore)
3. Desde la terminal, parado en la carpeta del proyecto:
   ```
   git init
   git add .
   git commit -m "Primera version del catalogo"
   git branch -M main
   git remote add origin TU-URL-DE-GITHUB
   git push -u origin main
   ```

### Paso 3: Desplegar en Render
1. https://render.com → cuenta gratis → "New +" → "Web Service"
2. Conectá tu repositorio de GitHub
3. Configurá:
   - Runtime: Java
   - Build Command: `./mvnw clean package -DskipTests`
   - Start Command: `java -jar target/catalogo-app-1.0.0.jar`
4. Variables de entorno a agregar:

   | Variable | Valor |
   |---|---|
   | DB_URL | connection string de Neon (cambiando `postgresql://` por `jdbc:postgresql://`) |
   | DB_USERNAME | tu usuario de Neon |
   | DB_PASSWORD | tu clave de Neon |
   | ADMIN_USERNAME | usuario para entrar a /admin |
   | ADMIN_PASSWORD | clave segura para /admin |

5. "Create Web Service" → esperá el despliegue → te da tu link público

### Advertencias del plan gratis de Render
- Las fotos subidas desde `/admin` se pierden si el servicio se reinicia
  (el disco no es permanente). Para producción real, conviene subirlas
  a un servicio externo como Cloudinary.
- El servicio "duerme" tras 15 min sin uso; la primera visita después
  tarda ~20-30 seg en despertar. Es normal, no es un error.

## Estructura del proyecto

```
catalogo-app/
├── pom.xml
├── README.md
├── .gitignore
└── src/main/
    ├── java/com/tienda/catalogo/
    │   ├── CatalogoApplication.java
    │   ├── model/          (Producto, Categoria, ItemCarrito)
    │   ├── repository/     (acceso a la base de datos)
    │   ├── service/        (carrito, subida de imagenes)
    │   ├── controller/     (rutas /tienda, /carrito, /admin)
    │   └── config/         (recursos estaticos, seguridad de /admin)
    └── resources/
        ├── application.properties
        ├── data.sql
        ├── templates/       (vistas HTML con Thymeleaf)
        └── static/css/style.css
```
