package com.util;

import com.exception.ConversorException;
import com.model.Money;
import com.service.ApiService;

import java.io.IOException;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class Bussineslogic {

    public static Money conversor_money(String money_base, String money_conversor, double valor_join)
            throws IOException, InterruptedException {

        // Verificar si el usuario ingresó directamente el código (USD, MXN, etc.)
        String key_base = verificar_codigo_o_nombre(money_base);
        String key_conversor = verificar_codigo_o_nombre(money_conversor);

        // Validar moneda base
        if (key_base.equals("Error")) {
            throw new ConversorException(
                    "❌ Moneda base no válida: '" + money_base + "'\n" +
                            "Usa la opción 1 del menú para ver las monedas disponibles"
            );
        }

        // Validar moneda destino
        if (key_conversor.equals("Error")) {
            throw new ConversorException(
                    "❌ Moneda destino no válida: '" + money_conversor + "'\n" +
                            "Usa la opción 1 del menú para ver las monedas disponibles"
            );
        }

        // Realizar la conversión
        return ApiService.servidor(key_base, key_conversor, valor_join);
    }

    /**
     * Verifica si el input es un código (USD) o un nombre (Dólar estadounidense)
     */
    public static String verificar_codigo_o_nombre(String input) {
        Map<String, String> moneys = allmoney();

        // Primero verificar si es directamente un código válido (USD, MXN, EUR, etc.)
        if (moneys.containsKey(input.toUpperCase())) {
            return input.toUpperCase();
        }

        // Si no, buscar por nombre
        return verificar_map(input);
    }

    /**
     * Busca una moneda por su nombre completo
     */
    public static String verificar_map(String money) {
        Map<String, String> moneys = allmoney();

        for (Map.Entry<String, String> entrada : moneys.entrySet()) {
            String valorNormalizado = normalizar_text(entrada.getValue());
            if (valorNormalizado.equals(normalizar_text(money))) {
                return entrada.getKey();
            }
        }
        return "Error";
    }

    public static String normalizar_text(String texto) {
        return Normalizer
                .normalize(texto.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static Map<String, String> allmoney() {
        Map<String, String> nombres = new HashMap<>();
        nombres.put("USD", "Dólar estadounidense");
        nombres.put("AED", "Dirham de Emiratos Árabes Unidos");
        nombres.put("AFN", "Afgani afgano");
        nombres.put("ALL", "Lek albanés");
        nombres.put("AMD", "Dram armenio");
        nombres.put("ANG", "Florín antillano neerlandés");
        nombres.put("AOA", "Kwanza angoleño");
        nombres.put("ARS", "Peso argentino");
        nombres.put("AUD", "Dólar australiano");
        nombres.put("AWG", "Florín arubeño");
        nombres.put("AZN", "Manat azerbaiyano");
        nombres.put("BAM", "Marco convertible bosnioherzegovino");
        nombres.put("BBD", "Dólar barbadense");
        nombres.put("BDT", "Taka bangladesí");
        nombres.put("BGN", "Lev búlgaro");
        nombres.put("BHD", "Dinar bahreiní");
        nombres.put("BIF", "Franco burundés");
        nombres.put("BMD", "Dólar bermudeño");
        nombres.put("BND", "Dólar de Brunéi");
        nombres.put("BOB", "Boliviano");
        nombres.put("BRL", "Real brasileño");
        nombres.put("BSD", "Dólar bahameño");
        nombres.put("BTN", "Ngultrum butanés");
        nombres.put("BWP", "Pula botsuano");
        nombres.put("BYN", "Rublo bielorruso");
        nombres.put("BZD", "Dólar beliceño");
        nombres.put("CAD", "Dólar canadiense");
        nombres.put("CDF", "Franco congoleño");
        nombres.put("CHF", "Franco suizo");
        nombres.put("CLP", "Peso chileno");
        nombres.put("CNY", "Yuan chino");
        nombres.put("COP", "Peso colombiano");
        nombres.put("CRC", "Colón costarricense");
        nombres.put("CUP", "Peso cubano");
        nombres.put("CVE", "Escudo caboverdiano");
        nombres.put("CZK", "Corona checa");
        nombres.put("DJF", "Franco yibutiano");
        nombres.put("DKK", "Corona danesa");
        nombres.put("DOP", "Peso dominicano");
        nombres.put("DZD", "Dinar argelino");
        nombres.put("EGP", "Libra egipcia");
        nombres.put("ERN", "Nakfa eritreo");
        nombres.put("ETB", "Birr etíope");
        nombres.put("EUR", "Euro");
        nombres.put("FJD", "Dólar fiyiano");
        nombres.put("FKP", "Libra malvinense");
        nombres.put("FOK", "Corona feroesa");
        nombres.put("GBP", "Libra esterlina");
        nombres.put("GEL", "Lari georgiano");
        nombres.put("GGP", "Libra de Guernsey");
        nombres.put("GHS", "Cedi ghanés");
        nombres.put("GIP", "Libra de Gibraltar");
        nombres.put("GMD", "Dalasi gambiano");
        nombres.put("GNF", "Franco guineano");
        nombres.put("GTQ", "Quetzal guatemalteco");
        nombres.put("GYD", "Dólar guyanés");
        nombres.put("HKD", "Dólar de Hong Kong");
        nombres.put("HNL", "Lempira hondureña");
        nombres.put("HRK", "Kuna croata");
        nombres.put("HTG", "Gourde haitiano");
        nombres.put("HUF", "Forinto húngaro");
        nombres.put("IDR", "Rupia indonesia");
        nombres.put("ILS", "Nuevo séquel israelí");
        nombres.put("IMP", "Libra de la Isla de Man");
        nombres.put("INR", "Rupia india");
        nombres.put("IQD", "Dinar iraquí");
        nombres.put("IRR", "Rial iraní");
        nombres.put("ISK", "Corona islandesa");
        nombres.put("JEP", "Libra de Jersey");
        nombres.put("JMD", "Dólar jamaiquino");
        nombres.put("JOD", "Dinar jordano");
        nombres.put("JPY", "Yen japonés");
        nombres.put("KES", "Chelín keniata");
        nombres.put("KGS", "Som kirguís");
        nombres.put("KHR", "Riel camboyano");
        nombres.put("KID", "Dólar de Kiribati");
        nombres.put("KMF", "Franco comorense");
        nombres.put("KRW", "Won surcoreano");
        nombres.put("KWD", "Dinar kuwaití");
        nombres.put("KYD", "Dólar de Islas Caimán");
        nombres.put("KZT", "Tenge kazajo");
        nombres.put("LAK", "Kip laosiano");
        nombres.put("LBP", "Libra libanesa");
        nombres.put("LKR", "Rupia de Sri Lanka");
        nombres.put("LRD", "Dólar liberiano");
        nombres.put("LSL", "Loti lesothense");
        nombres.put("LYD", "Dinar libio");
        nombres.put("MAD", "Dirham marroquí");
        nombres.put("MDL", "Leu moldavo");
        nombres.put("MGA", "Ariary malgache");
        nombres.put("MKD", "Dinar macedonio");
        nombres.put("MMK", "Kyat birmano");
        nombres.put("MNT", "Tugrik mongol");
        nombres.put("MOP", "Pataca de Macao");
        nombres.put("MRU", "Ouguiya mauritano");
        nombres.put("MUR", "Rupia mauriciana");
        nombres.put("MVR", "Rufiyaa maldiva");
        nombres.put("MWK", "Kwacha malaui");
        nombres.put("MXN", "Peso mexicano");
        nombres.put("MYR", "Ringgit malayo");
        nombres.put("MZN", "Metical mozambiqueño");
        nombres.put("NAD", "Dólar namibio");
        nombres.put("NGN", "Naira nigeriana");
        nombres.put("NIO", "Córdoba nicaragüense");
        nombres.put("NOK", "Corona noruega");
        nombres.put("NPR", "Rupia nepalesa");
        nombres.put("NZD", "Dólar neozelandés");
        nombres.put("OMR", "Rial omaní");
        nombres.put("PAB", "Balboa panameño");
        nombres.put("PEN", "Sol peruano");
        nombres.put("PGK", "Kina papuana");
        nombres.put("PHP", "Peso filipino");
        nombres.put("PKR", "Rupia pakistaní");
        nombres.put("PLN", "Złoty polaco");
        nombres.put("PYG", "Guaraní paraguayo");
        nombres.put("QAR", "Riyal catarí");
        nombres.put("RON", "Leu rumano");
        nombres.put("RSD", "Dinar serbio");
        nombres.put("RUB", "Rublo ruso");
        nombres.put("RWF", "Franco ruandés");
        nombres.put("SAR", "Riyal saudí");
        nombres.put("SBD", "Dólar de Islas Salomón");
        nombres.put("SCR", "Rupia seychellense");
        nombres.put("SDG", "Libra sudanesa");
        nombres.put("SEK", "Corona sueca");
        nombres.put("SGD", "Dólar singapurense");
        nombres.put("SHP", "Libra de Santa Elena");
        nombres.put("SLE", "Leone de Sierra Leona");
        nombres.put("SLL", "Leone sierraleonés");
        nombres.put("SOS", "Chelín somalí");
        nombres.put("SRD", "Dólar surinamés");
        nombres.put("SSP", "Libra sursudanesa");
        nombres.put("STN", "Dobra santotomense");
        nombres.put("SYP", "Libra siria");
        nombres.put("SZL", "Lilangeni suazi");
        nombres.put("THB", "Baht tailandés");
        nombres.put("TJS", "Somoni tayiko");
        nombres.put("TMT", "Manat turcomano");
        nombres.put("TND", "Dinar tunecino");
        nombres.put("TOP", "Paʻanga tongano");
        nombres.put("TRY", "Lira turca");
        nombres.put("TTD", "Dólar de Trinidad y Tobago");
        nombres.put("TVD", "Dólar tuvaluano");
        nombres.put("TWD", "Dólar taiwanés");
        nombres.put("TZS", "Chelín tanzano");
        nombres.put("UAH", "Grivna ucraniana");
        nombres.put("UGX", "Chelín ugandés");
        nombres.put("UYU", "Peso uruguayo");
        nombres.put("UZS", "Som uzbeko");
        nombres.put("VES", "Bolívar venezolano");
        nombres.put("VND", "Dong vietnamita");
        nombres.put("VUV", "Vatu vanuatense");
        nombres.put("WST", "Tala samoano");
        nombres.put("XAF", "Franco CFA de África Central");
        nombres.put("XCD", "Dólar del Caribe Oriental");
        nombres.put("XCG", "Moneda genérica XCG");
        nombres.put("XDR", "Derechos especiales de giro");
        nombres.put("XOF", "Franco CFA de África Occidental");
        nombres.put("XPF", "Franco CFP");
        nombres.put("YER", "Rial yemení");
        nombres.put("ZAR", "Rand sudafricano");
        nombres.put("ZMW", "Kwacha zambiano");
        nombres.put("ZWL", "Dólar zimbabuense");
        return nombres;
    }

    public static void mostrarMap() {
        System.out.println("\n💱 MONEDAS DISPONIBLES");
        System.out.println("═".repeat(80));
        System.out.println("Puedes usar el CÓDIGO (ej: USD) o el NOMBRE completo (ej: Dólar estadounidense)");
        System.out.println("═".repeat(80));

        allmoney().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.printf("%-6s → %s%n", entry.getKey(), entry.getValue()));

        System.out.println("═".repeat(80));
    }
}