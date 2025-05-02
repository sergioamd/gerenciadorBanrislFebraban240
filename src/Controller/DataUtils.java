/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Sergio
 */
public class DataUtils {
    
    // Função que retorna o mês anterior de uma data fornecida
    public static String getMesAnterior(String dataStr) {
        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterSaida = DateTimeFormatter.ofPattern("MM/yyyy");

        try {
            LocalDate data = LocalDate.parse(dataStr, formatterEntrada);
            LocalDate umMesAntes = data.minusMonths(1);
            return umMesAntes.format(formatterSaida);
        } catch (DateTimeParseException e) {
            return "Data inválida: " + e.getMessage();
        }
    }
    
     public static String getMesAnteriorComFormatos(String dataStr) {
        dataStr = dataStr.trim();

        List<String> formatosPossiveis = Arrays.asList(
                "dd/MM/yyyy",
                "dd-MM-yyyy",
                "ddMMyy",
                "yyyyMMdd"
        );

        for (String formato : formatosPossiveis) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
                LocalDate data = LocalDate.parse(dataStr, formatter);
                return data.minusMonths(1).format(DateTimeFormatter.ofPattern("MM/yyyy"));
            } catch (DateTimeParseException e) {
                // tenta o próximo formato
            }
        }

        // Se nenhum formato funcionar, retorna erro
        return "Data inválida ou formato não suportado: " + dataStr;
    }
}
