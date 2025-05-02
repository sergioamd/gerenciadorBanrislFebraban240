/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author Sergio
 */
public class ValorUtil {
    
    private static final Locale LOCALE_BR = new Locale("pt", "BR");

    /**
     * Formata um BigDecimal para o formato de moeda brasileira (R$).
     * 
     * @param valor O valor a ser formatado.
     * @return Valor formatado como moeda brasileira.
     */
    public static String formatarParaReal(BigDecimal valor) {
        NumberFormat formatador = NumberFormat.getCurrencyInstance(LOCALE_BR);
        return formatador.format(valor);
    }

    /**
     * Converte uma String numérica para BigDecimal e formata como Real.
     * 
     * @param valorStr String com valor (ex: "1234.56")
     * @return Valor formatado como moeda brasileira.
     */
    public static String formatarParaReal(String valorStr) {
        try {
            BigDecimal valor = new BigDecimal(valorStr);
            return formatarParaReal(valor);
        } catch (NumberFormatException e) {
            return "Valor inválido";
        }
    }
    
    
    /**
     * Converte um valor numérico no formato de 15 dígitos (ex: "00000000076155")
     * para o formato monetário brasileiro (ex: "761,55").
     *
     * @param valorString valor com 15 dígitos, sem ponto decimal.
     * @return valor formatado como moeda brasileira.
     */
    public static String formatarParaRealBrasileiro(String valorString) {
        try {
            BigDecimal valor = new BigDecimal(valorString).movePointLeft(2);
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator('.');

            DecimalFormat formato = new DecimalFormat("#,##0.00", symbols);
            return formato.format(valor);
            
        } catch (Exception e) {
            return "Valor inválido";
        }
    
    }
    
     /**
     * Converte uma string no formato brasileiro (ex: "761,55")
     * para double (ex: 761.55).
     */
    public static double converterRealParaDouble(String valorReal) {
        valorReal = valorReal.replace(".", "").replace(",", ".");
        return Double.parseDouble(valorReal);
    }
    
    
    public static String converterDoubleParaValorEmCentavos(double valor) {
    long valorCentavos = Math.round(valor * 100);
    return String.format("%012d", valorCentavos);
}
    
    
    /**
     * Converte uma string representando um valor monetário (pt-BR) para BigDecimal.
     * Ex: "1.234,56" → BigDecimal("1234.56")
     *
     * @param valorString valor como string (pode conter vírgula ou ponto como separador)
     * @return BigDecimal correspondente ou null em caso de erro
     */
    public static BigDecimal stringParaBigDecimal(String valorString) {
        try {
            if (valorString == null || valorString.trim().isEmpty()) {
                return null;
            }

            Locale brasil = new Locale("pt", "BR");
            NumberFormat formatador = NumberFormat.getInstance(brasil);
            Number numero = formatador.parse(valorString);
            return new BigDecimal(numero.toString());

        } catch (ParseException e) {
            System.err.println("Erro ao converter string para BigDecimal: " + e.getMessage());
            return null;
        }
    
    }
    
     public static String doubleParaDecimalFormatado(double valor) {
        // Converte para BigDecimal com precisão de 2 casas decimais
        BigDecimal bd = BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP);
        
        // Multiplica por 100 para obter valor em centavos (ex: 751.00 -> 75100)
        BigDecimal centavos = bd.multiply(BigDecimal.valueOf(100));
        
        // Remove parte decimal (caso existam centavos exatos)
        long valorLong = centavos.longValue();

        // Formata com zeros à esquerda para 9 dígitos
        return String.format("%017d", valorLong);
    }
}
    
