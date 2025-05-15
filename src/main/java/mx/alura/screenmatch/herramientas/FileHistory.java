package mx.alura.screenmatch.herramientas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.alura.screenmatch.modelos.Titulo;

public record FileHistory(List<Titulo> history, String outputDir) {
    private static final Logger logger = Logger.getLogger(FileHistory.class.getName());
    private static final String FILE_NAME = "consultasDeTitulos.json";

    public void toFile() throws IOException {
    Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .create();

    File directorio = new File(outputDir);
    if (!directorio.exists() && !directorio.mkdirs()) {
        throw new IOException("No se pudo crear el directorio: " + outputDir);
    }

    File outputFile = new File(directorio, FILE_NAME);
    List<Titulo> totalTitulos = new ArrayList<>();

    // 1. Leer archivo si ya existe
    if (outputFile.exists() && outputFile.length() > 0) {
        try (Scanner scanner = new Scanner(outputFile)) {
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
            String contenidoExistente = sb.toString();
            Titulo[] titulosExistentes = gson.fromJson(contenidoExistente, Titulo[].class);
            if (titulosExistentes != null) {
                totalTitulos.addAll(List.of(titulosExistentes));
            }
        } catch (Exception e) {
            new LimpiarConsola().start();
            logger.warning("[ERROR al leer el archivo existente] " + e.getMessage());
        }
    }

    // 2. Agregar nuevos títulos
    totalTitulos.addAll(history);

    // 3. Sobrescribir el archivo con todos los títulos
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, false))) {
        writer.write(gson.toJson(totalTitulos));
        logger.config("[Archivo actualizado exitosamente]: " + outputFile.getAbsolutePath());
    } catch (IOException e) {
        new LimpiarConsola().start();
        logger.warning("[ERROR al escribir archivo] " + e.getMessage());
        throw e;
    }
}

}
