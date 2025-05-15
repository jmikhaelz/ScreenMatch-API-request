package mx.alura.screenmatch;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import mx.alura.screenmatch.exceptions.ErrorValueDuracionException;
import mx.alura.screenmatch.herramientas.ConfigUtils;
import mx.alura.screenmatch.herramientas.LimpiarConsola;
import mx.alura.screenmatch.herramientas.TituloOmdbApi;
import mx.alura.screenmatch.modelos.Titulo;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Asignar valores desde la class, para manejos del proyecto
        String apiKey = ConfigUtils.getProperty("omdbapi_key");
        String outputDir = ConfigUtils.getProperty("output.directory");

        if (apiKey == null) {
            new LimpiarConsola().start();
            System.out.println("\t[ERROR] API Key no encontrada. Por favor, verifica tu archivo de configuración.");
            return;
        }
        if (outputDir == null) {
            new LimpiarConsola().start();
            System.out.println("\t[ERROR] Directorio no encontrada. Por favor, verifica tu archivo de configuración.");
            return;
        }

        // Definir el título de la película que quieres buscar
        new LimpiarConsola().start();
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("\n[INFO] Ingresa el título de la película: ");
            String movieTitle = in.nextLine().trim(); // Usamos nextLine() para permitir títulos con espacios.

            if (movieTitle.isEmpty()) {
                System.out
                        .println("\t[ADVERTENCIA] El título de la película no puede estar vacío. Intenta nuevamente.");
                return;
            }
            String encodedTitle = URLEncoder.encode(movieTitle, StandardCharsets.UTF_8.toString());

            // Construir la URL con el título de la película y la API Key
            String urlString = "http://www.omdbapi.com/?t=" + encodedTitle + "&apikey=" + apiKey;

            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .build();

            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            String resp_json = response.body();

            // Imprimir la respuesta JSON
            /*
             * Forma de imprementacion de JSON a Class
             * 1.- En este caso agregamos directo la class Titulo, para los valores de JSON,
             * con ayuda de la alias @SerializedName("Atributo-JSON")
             * CODE:
             * Gson gson = new Gson();
             * Titulo movie_trans = gson.fromJson(resp_json, Titulo.class);
             * 
             * 2.- En este caso agregamos un record para la transformacion de daros de JSON
             * a la class Titulo
             */

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(
                            FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create();
            TituloOmdbApi movie_resp = gson.fromJson(resp_json, TituloOmdbApi.class);
            // Mostrar el resultado
            if (movie_resp != null && movie_resp.title() != null) {
                Titulo movie_trans = new Titulo(movie_resp);
                System.out.println("\n[RESULTADO] Título encontrado:");
                System.out.println("\t" + movie_trans);

                // Asegurar que la carpeta exista
                File directorio = new File(outputDir);
                if (!directorio.exists()) {
                    if (!directorio.mkdirs()) {
                        throw new IOException("No se pudo crear el directorio: " + outputDir);
                    }
                }

                // Almacenar el dato
                File outputFile = new File(directorio, "titulos.txt");
                try (BufferedWriter printFile = new BufferedWriter(new FileWriter(outputFile, true))) { // true = modo append
                    printFile.write(movie_trans.toString());
                    printFile.newLine();
                    System.out.println("Archivo actualizado en: " + outputFile.getAbsolutePath());
                }

            } else {
                System.out.println("\n[ERROR] No se encontró la película con el título: " +
                        movieTitle);
            }
        } catch (JsonSyntaxException e) {
            new LimpiarConsola().start();
            System.out.println("\n[ERROR] Hubo un problema al procesar la respuesta. " +
                    "Verifica que la API esté funcionando correctamente.");
            e.printStackTrace();
        } catch (ErrorValueDuracionException e) {
            System.out.println("\n[ERROR] Ocurrió un error inesperado: " + e.getMessage());
        } catch (Exception e) {
            // new LimpiarConsola().start();
            System.out.println("\n[ERROR] Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("[>] Finalizo el programa!");
        }
    }
}
