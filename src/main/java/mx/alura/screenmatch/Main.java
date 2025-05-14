package mx.alura.screenmatch;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import mx.alura.screenmatch.herramientas.LimpiarConsola;
import mx.alura.screenmatch.modelos.Titulo;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Leer la API Key desde el archivo config.properties
        String apiKey = getApiKeyFromConfig();

        if (apiKey == null) {
            new LimpiarConsola().start();
            System.out.println("\t[ERROR] API Key no encontrada. Por favor, verifica tu archivo de configuración.");
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

            // Imprimir la respuesta JSON
            String resp_json = response.body();
            Gson gson = new Gson();
            Titulo movie_resp = gson.fromJson(resp_json, Titulo.class);

            // Mostrar el resultado
            if (movie_resp != null && movie_resp.getNombre() != null) {
                System.out.println("\n[RESULTADO] Título encontrado:");
                System.out.println("\t" + movie_resp);
            } else {
                System.out.println("\n[ERROR] No se encontró la película con el título: " + movieTitle);
            }

        } catch (JsonSyntaxException e) {
            System.out.println("\n[ERROR] Hubo un problema al procesar la respuesta. " +
                    "Verifica que la API esté funcionando correctamente.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("\n[ERROR] Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para obtener la API Key desde el archivo config.properties
    private static String getApiKeyFromConfig() {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config/config.properties")) {
            if (input == null) {
                System.out.println(
                        "\n[ERROR] No se pudo encontrar el archivo de configuración 'config/config.properties'.");
                return null;
            }
            properties.load(input);
            return properties.getProperty("omdbapi_key");
        } catch (IOException ex) {
            System.out.println("\n[ERROR] Hubo un problema al leer el archivo de configuración: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
