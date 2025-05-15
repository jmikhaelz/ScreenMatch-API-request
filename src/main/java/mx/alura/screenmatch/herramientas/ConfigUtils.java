package mx.alura.screenmatch.herramientas;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {

    private static final String CONFIG_FILE = "config/config.properties";
    private static Properties cachedProperties;

    // Constructor privado para que nadie instancie la clase
    private ConfigUtils() {
    }

    // Carga las propiedades una sola vez
    private static Properties getProperties() {
        if (cachedProperties == null) {
            cachedProperties = new Properties();
            try (InputStream input = ConfigUtils.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
                if (input == null) {
                    throw new IOException("[ERROR] No se encontró el archivo de configuración: " + CONFIG_FILE);
                }
                cachedProperties.load(input);
            } catch (IOException e) {
                System.err.println("[ERROR] No se pudo cargar el archivo de configuración: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return cachedProperties;
    }

    // Método público para obtener cualquier propiedad
    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }
}
