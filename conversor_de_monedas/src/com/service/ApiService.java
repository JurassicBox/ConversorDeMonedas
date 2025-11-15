package com.service;

import com.exception.ConversorException;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.Money;
import com.model.MoneyRate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

import static java.net.http.HttpClient.newHttpClient;

public class ApiService {

    private static String cargarApiKey() {
        Properties properties = new Properties();

        System.out.println("🔍 Intentando cargar config.properties...");

        // Método 1: Desde classpath (resources compilado)
        try {
            InputStream input = ApiService.class.getClassLoader()
                    .getResourceAsStream("config.properties");

            if (input != null) {
                properties.load(input);
                input.close();
                String key = properties.getProperty("API_KEY");

                if (key != null && !key.isBlank()) {
                    System.out.println("✓ API Key cargada desde resources (classpath)");
                    return key;
                }
            }
        } catch (IOException e) {
            System.out.println("⚠ Método 1 falló: " + e.getMessage());
        }

        // Método 2: Desde /config.properties en classpath
        try {
            InputStream input = ApiService.class.getResourceAsStream("/config.properties");

            if (input != null) {
                properties.load(input);
                input.close();
                String key = properties.getProperty("API_KEY");

                if (key != null && !key.isBlank()) {
                    System.out.println("✓ API Key cargada desde /config.properties");
                    return key;
                }
            }
        } catch (IOException e) {
            System.out.println("⚠ Método 2 falló: " + e.getMessage());
        }

        // Método 3: Desde src/resources/config.properties
        try {
            Path path = Paths.get("src/resources/config.properties");
            if (Files.exists(path)) {
                try (InputStream input = new FileInputStream(path.toFile())) {
                    properties.load(input);
                    String key = properties.getProperty("API_KEY");

                    if (key != null && !key.isBlank()) {
                        System.out.println("✓ API Key cargada desde src/resources/config.properties");
                        return key;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("⚠ Método 3 falló: " + e.getMessage());
        }

        // Método 4: Desde out/production/convertion_money/config.properties
        try {
            Path path = Paths.get("out/production/convertion_money/config.properties");
            if (Files.exists(path)) {
                try (InputStream input = new FileInputStream(path.toFile())) {
                    properties.load(input);
                    String key = properties.getProperty("API_KEY");

                    if (key != null && !key.isBlank()) {
                        System.out.println("✓ API Key cargada desde out/production/");
                        return key;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("⚠ Método 4 falló: " + e.getMessage());
        }

        // Método 5: Desde la raíz del proyecto
        try {
            Path path = Paths.get("config.properties");
            if (Files.exists(path)) {
                try (InputStream input = new FileInputStream(path.toFile())) {
                    properties.load(input);
                    String key = properties.getProperty("API_KEY");

                    if (key != null && !key.isBlank()) {
                        System.out.println("✓ API Key cargada desde raíz del proyecto");
                        return key;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("⚠ Método 5 falló: " + e.getMessage());
        }

        // Si ningún método funcionó, mostrar error detallado
        System.out.println("\n❌ No se pudo cargar config.properties desde ninguna ubicación");
        System.out.println("\nUbicaciones intentadas:");
        System.out.println("1. Classpath (resources compilado)");
        System.out.println("2. /config.properties en classpath");
        System.out.println("3. src/resources/config.properties");
        System.out.println("4. out/production/convertion_money/config.properties");
        System.out.println("5. config.properties (raíz del proyecto)");

        System.out.println("\n📁 Directorio actual de trabajo: " + System.getProperty("user.dir"));

        throw new ConversorException(
                """
                        
                        ❌ ERROR: No se pudo cargar la API KEY
                        
                        SOLUCIÓN RÁPIDA:
                        1. Crea el archivo 'config.properties' en la RAÍZ del proyecto
                           (al mismo nivel que la carpeta 'src')
                        
                        2. Agrega esta línea al archivo:
                           API_KEY=tu_clave_de_exchangerate_api
                        
                        3. Obtén tu clave gratis en:
                           https://www.exchangerate-api.com/
                        
                        4. Guarda el archivo y vuelve a ejecutar el programa"""
        );
    }

    public static Money servidor(String money_base, String money_conversor, double valor_join)
            throws IOException, InterruptedException {

        try {
            // Cargar API Key
            String key = cargarApiKey();

            // Construir URL (asegúrate de que los parámetros estén correctos)
            String direccion = String.format(
                    "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s",
                    key, money_base, money_conversor
            );

            System.out.println("🔗 URL generada: " + direccion);

            // Crear cliente HTTP
            HttpClient client = newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .timeout(Duration.ofSeconds(10))
                    .build();

            // Enviar petición
            System.out.println("⏳ Conectando con la API de Exchange Rate...");
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            // Verificar código de respuesta
            if (response.statusCode() != 200) {
                throw new ConversorException(
                        "❌ Error de la API (Código HTTP: " + response.statusCode() + ")\n" +
                                "Verifica que tu API_KEY sea válida y esté bien escrita"
                );
            }

            // Parsear respuesta JSON
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            MoneyRate rate = gson.fromJson(response.body(), MoneyRate.class);

            // Verificar que la tasa sea válida
            if (rate == null || rate.conversion_rate() == 0) {
                throw new ConversorException(
                        "❌ Error: No se pudo obtener la tasa de conversión.\n" +
                                "Verifica que los códigos de moneda sean correctos"
                );
            }

            System.out.println("✓ Conversión realizada exitosamente");
            return new Money(rate, valor_join);

        } catch (ConversorException e) {
            throw e;
        } catch (IOException e) {
            throw new ConversorException(
                    "❌ Error de conexión: " + e.getMessage() +
                            "\nVerifica tu conexión a internet"
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConversorException("❌ Operación interrumpida");
        } catch (Exception e) {
            throw new ConversorException(
                    "❌ Error inesperado: " + e.getMessage()
            );
        }
    }
}