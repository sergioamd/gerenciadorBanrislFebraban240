/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Sergio
 */
public class ConversorUtil {
    
    /**
     * Converte uma string de valor monetário brasileiro (ex: "761,55")
     * para um tipo double (ex: 761.55).
     *
     * @param valorString Valor no formato brasileiro.
     * @return Valor em double.
     */
    public static double converterParaDouble(String valorString) {
        try {
            valorString = valorString.replace(".", "").replace(",", ".");
            return Double.parseDouble(valorString);
        } catch (Exception e) {
            System.err.println("Erro ao converter valor: " + valorString);
            return 0.0;
        }
    
    }
}
