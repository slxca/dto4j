package com.slxca.converter;

/**
 * Hauptschnittstelle für DTO-Konvertierungen.
 *
 * @param <S> Quelltyp
 * @param <T> Zieltyp
 */

public interface DtoConverter<S, T> {
    /**
     * Konvertiert ein Objekt vom Quell- zum Zieltyp.
     *
     * @param source Das Quellobjekt
     * @return Das konvertierte Zielobjekt
     * @throws IllegalArgumentException wenn source null ist
     */
    T convert(S source);
}
