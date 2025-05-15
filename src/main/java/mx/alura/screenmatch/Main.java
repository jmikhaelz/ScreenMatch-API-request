package mx.alura.screenmatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.google.gson.JsonSyntaxException;

import mx.alura.screenmatch.exceptions.ErrorValueDuracionException;
import mx.alura.screenmatch.herramientas.ConfigUtils;
import mx.alura.screenmatch.herramientas.FileHistory;
import mx.alura.screenmatch.herramientas.LimpiarConsola;
import mx.alura.screenmatch.herramientas.ResponseApi;
import mx.alura.screenmatch.modelos.Titulo;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final LimpiarConsola tool = new LimpiarConsola();

    public static void main(String[] args) throws IOException, InterruptedException {
        // Limpiar Consola
        tool.start();
        // Configuracion del entorno
        String apiKey = ConfigUtils.getProperty("omdbapi_key");
        String outputDir = ConfigUtils.getProperty("output.directory");
        validarConfiguracion(apiKey, outputDir);
        // Manejo de los resultados/respuesta de la API
        List<Titulo> history = new ArrayList<>();
        // Variables de entrada
        Scanner in = new Scanner(System.in);
        // Manjeo de Entrada de peticiones (Titulos)
        while (true) {
            try {
                System.out.println("================================================================================");
                // Solicitud de busqueda
                logger.info("Para Salir ingrese exit, Si no es el caso continue con");
                System.out.print("\tIngresar el título de la película: ");
                String queryTitle = in.nextLine().trim();
                // Validacion
                if (queryTitle.equalsIgnoreCase("exit")) {
                    break;
                } else if (queryTitle.isBlank()) {
                    tool.start();
                    logger.warning("El título de la película no puede estar vacío."
                            + "Intenta nuevamente.");
                    continue;
                }
                // Procesar respuesta de la API
                ResponseApi response = new ResponseApi(queryTitle, apiKey);
                if (response.obtenerTituloDesdeApi() != null) {
                    System.out.println(response.obtenerTituloDesdeApi());
                    history.add(response.obtenerTituloDesdeApi());
                } else {
                    logger.severe("No se encontró la película con el título: " +
                            queryTitle);
                }
            } catch (JsonSyntaxException e) {
                tool.start();
                logger.severe("Hubo un problema al procesar la respuesta. " +
                        "Verifica que la API esté funcionando correctamente.");
            } catch (ErrorValueDuracionException e) {
                System.out.println("WARNING : " + e.getMessage());
            } catch (Exception e) {
                tool.start();
                logger.warning(e.getMessage());
            }
        }
        // Reporte de titulos almacenados a JSON
        in.close();
        tool.start();
        logger.info("=========Titulos encontrados==========");
        for (Titulo titulo : history) {
            System.out.println(" - " + titulo);
        }
        FileHistory progress = new FileHistory(history, outputDir);
        progress.toFile();
        System.out.println("===================================");
        System.out.println("\nFinalizacion del Programa");
    }

    // Validacion protiedades del proyecto
    private static void validarConfiguracion(String apiKey, String outputDir) {
        if (apiKey == null) {
            tool.start();
            logger.warning("API Key no encontrada. Por favor, verifica tu archivo de configuración.");
            System.exit(1);
        }
        if (outputDir == null) {
            tool.start();
            logger.warning("Directorio no encontrada. Por favor, verifica tu archivo de configuración.");
            System.exit(1);
        }
    }

}
