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
public class DacCodigoBarrasUtil {
     public static int calcularDAC(String codigoSemDAC) {
        // códigoSemDAC = posições 1-4 + 6-44 (sem o dígito da posição 5)
        int soma = 0;
        int peso = 2;

        for (int i = codigoSemDAC.length() - 1; i >= 0; i--) {
            int num = Character.getNumericValue(codigoSemDAC.charAt(i));
            soma += num * peso;
            peso++;
            if (peso > 9) peso = 2;
        }

        int resto = soma % 11;
        if (resto == 0 || resto == 1) return 1;
        return 11 - resto;
    }
}
