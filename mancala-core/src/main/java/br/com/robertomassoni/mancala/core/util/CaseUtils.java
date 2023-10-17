package br.com.robertomassoni.mancala.core.util;

public class CaseUtils {

    public static String camelCaseToSnakeCase(final String source) {
        final var result = source.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z])([A-Z])", "$1_$2");
        return result.toLowerCase();
    }
}
