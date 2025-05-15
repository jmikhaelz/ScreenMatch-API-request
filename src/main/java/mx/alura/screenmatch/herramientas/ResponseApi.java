package mx.alura.screenmatch.herramientas;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.alura.screenmatch.modelos.Titulo;

public record ResponseApi(String queryTitle, String apiKey) {

    private static final String BASE_URL = "http://www.omdbapi.com/?t=";
    private static final String KEY_GET = "&apikey=";

    public Titulo obtenerTituloDesdeApi() throws IOException, InterruptedException {
        // Objeto GSON para Tratamiento
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
        // Procesar String a Encoded
        String encodedTitle = URLEncoder.encode(this.queryTitle,
                StandardCharsets.UTF_8.toString());
        // URI para peticion a API
        String urlString = BASE_URL + encodedTitle + KEY_GET + this.apiKey;
        // Crear cliente HTTP
        HttpClient client = HttpClient.newHttpClient();
        // Crear solicitud HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .build();
        // Enviar la solicitud y obtener la respuesta
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        //Procesamos los atributos deseados
        TituloOmdbApi movie_resp = gson.fromJson(response.body(),
                TituloOmdbApi.class);

        return (movie_resp != null && movie_resp.title() != null) ? new Titulo(movie_resp) : null;
    }
}
