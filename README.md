# ScreenMatch - API Request

(curso de Aula-Oracle-2025)

Este es un proyecto en Java que permite buscar información de películas utilizando la API de OMDb (Open Movie Database).

## 📌 Descripción

La aplicación permite ingresar el título de una película y consultar su información a través de la API de OMDb. La respuesta de la API se procesa y se muestra al usuario de manera amigable en la consola.Se centra en la gestión de **películas y series**, incluyendo clasificación, cálculo de duración y recomendaciones.

### Características

- Búsqueda de películas por título.
- Consulta de detalles como nombre, año, género y director.
- Manejo de errores si la película no se encuentra o si hay problemas con la API.

## 📌 Requisitos

- Java 11 o superior
- Maven
- Conexión a Internet para acceder a la API externa de OMDb.

## 🚀 Instalación y Ejecución

### **1. Clonar el repositorio**

```sh
git clone https://github.com/jmikhaelz/ScreenMatch-API-request.git
cd ScreenMatch-API-request
```

### **2. Configurar el entorno**

El proyecto requiere **Java 17** y **VS Code 1.99.3**. Si no tienes las versiones adecuadas, puedes instalarlas con:

```sh
sudo apt update
sudo apt install openjdk-17-jdk
```

Para verificar la instalación:

```sh
javac -version
java -version
```

### **3. Configura tu archivo `config.properties` con la clave API de OMDb:**

Crea un archivo **config/config.properties** y agrega la siguiente línea con tu propia clave de API:

```plaintext
    File : properties
    omdbapi_key=tu_clave_api
```

Puedes obtener una clave de API gratuita en [OMDb API](http://www.omdbapi.com/).

---

## 🏃‍♂️ **3. Cómo ejecutar el proyecto**

Opcional si no estas en apoyo de un IDE para la ejecucion de JAVA

### ✅ **1. Compilar el código fuente**

Usa el comando:

```sh
javac -cp lib/gson-2.8.8.jar -d bin -sourcepath src src/main/java/mx/alura/screenmatch/*.java

```

📌 Esto hace lo siguiente:

- `-cp lib/gson-2.8.8.jar`: Copiamos los archivos del JAR para la ejecucion de Gson (Libreria)
- `-d bin`: guarda los `.class` compilados en la carpeta `bin`.
- `-sourcepath src`: le dice al compilador dónde buscar las demás clases usadas en los paquetes.
- `src/main/java/mx/alura/screenmatch/Main.java`: es la clase principal que quieres compilar.

---

### ✅ **2. Ejecutar el programa**

Una vez compilado, puedes ejecutarlo con:

```sh
java -cp bin:lib/gson-2.8.8.jar mx.alura.screenmatch.Main
```

📌 Esto significa:

- `-cp bin`: usa `bin` como ruta donde están los `.class` ya compilados.
- `:lib/gson-2.8.8.jar` : Inclusion de la libreria pára su utilidad.
- `src/main/java/mx/alura/screenmatch/Main`: es el nombre **completo del paquete + clase** con el método `main`.

---

## ✅ Funcionalidades del Proyecto

- **Gestión de títulos (películas y series):** Se pueden crear, evaluar y calcular duración.
- **Cálculo de tiempo total:** La clase `CalculadoraDeTiempo` permite sumar el tiempo de reproducción.
- **Filtros de recomendación:** `FiltroRecomendacion` sugiere contenido basado en visualizaciones y evaluaciones.

## 🛠️ Entorno de Desarrollo

- **Lenguaje:** Java 17 (OpenJDK 17.0.14)
- **IDE:** Visual Studio Code 1.99.3
- **Paradigma:** Programación Orientada a Objetos
- **Gestión de código:** Git y GitHub

---

## 📂 **Estructura del Proyecto**

```plaintext
├── .gitignore                    # Archivos y directorios a ignorar por Git.
├── pom.xml                        # Archivo de configuración de Maven (gestión de dependencias y configuración de construcción).
├── README.md                      # Este archivo de documentación.
├── src                            # Código fuente del proyecto.
│   ├── main                       # Código principal de la aplicación.
│   │   ├── java                   # Archivos fuente de Java.
│   │   │   └── mx
│   │   │       └── alura
│   │   │           └── screenmatch
│   │   │               ├── calculos  # Lógica de cálculos y clasificaciones.
│   │   │               │   ├── CalculadoraDeTiempo.java
│   │   │               │   ├── Clasificable.java
│   │   │               │   └── FiltroRecomendacion.java
│   │   │               ├── herramientas  # Clases auxiliares.
│   │   │               │   └── LimpiarConsola.java
│   │   │               ├── Main.java      # Punto de entrada del programa (método main).
│   │   │               └── modelos  # Modelos que representan las entidades de la aplicación.
│   │   │                   ├── Episodio.java
│   │   │                   ├── Pelicula.java
│   │   │                   ├── Serie.java
│   │   │                   └── Titulo.java
│   │   └── resources               # Archivos de configuración y otros recursos.
│   │       └── config
│   │           ├── config.properties      # Archivo de configuración con la API Key y otros valores.
│   │           └── config.properties.example  # Ejemplo del archivo de configuración (sin datos sensibles).
│   └── test                        # Pruebas unitarias del proyecto (por defecto no contiene nada en tu estructura actual).
│       └── java
├── target                          # Archivos generados por Maven (compilados, artefactos).
│   ├── classes                     # Clases compiladas y otros archivos generados.
│   │   ├── config
│   │   │   ├── config.properties
│   │   │   └── config.properties.example
│   │   └── mx
│   │       └── alura
│   │           └── screenmatch
│   │               ├── calculos
│   │               │   ├── CalculadoraDeTiempo.class
│   │               │   ├── Clasificable.class
│   │               │   └── FiltroRecomendacion.class
│   │               ├── herramientas
│   │               │   └── LimpiarConsola.class
│   │               ├── Main.class
│   │               └── modelos
│   │                   ├── Episodio.class
│   │                   ├── Pelicula.class
│   │                   ├── Serie.class
│   │                   └── Titulo.class
└── └── 
```

## Ejemplo De Uso

```bash
[INFO] Ingresa el título de la película: Inception
[RESULTADO] Título encontrado:
    Título: Inception
    Año: 2010
    Género: Action, Adventure, Sci-Fi
    Director: Christopher Nolan
