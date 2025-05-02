/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Sergio
 */
public class FatorVencimentoUtil {
    
   private static final LocalDate DATA_BASE = LocalDate.of(1997, 10, 7);

    public static String calcularFator(String dataVencimentoStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr, formatter);

        long dias = ChronoUnit.DAYS.between(DATA_BASE, dataVencimento);
        int fator = (int) dias;

        // Regra especial: fator reinicia em 1000 após 21/02/2025
        LocalDate limite = LocalDate.of(2025, 2, 21);
        if (dataVencimento.isAfter(limite)) {
            long diasApos = ChronoUnit.DAYS.between(LocalDate.of(2025, 2, 22), dataVencimento);
            fator = 1000 + (int) diasApos;
        }

        return String.format("%04d", fator);
    } 
}
