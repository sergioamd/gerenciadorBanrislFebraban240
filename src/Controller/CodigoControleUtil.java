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
public class CodigoControleUtil {
      // Calcula o primeiro DV com Módulo 10
    public static int calcularPrimeiroDV(String nossoNumero) {
        int soma = 0;
        int[] pesos = {2, 1}; // Alternado da direita pra esquerda

        for (int i = nossoNumero.length() - 1, p = 0; i >= 0; i--, p = (p + 1) % 2) {
            int num = Character.getNumericValue(nossoNumero.charAt(i));
            int mult = num * pesos[p];
            if (mult > 9) mult -= 9;
            soma += mult;
        }

        int resto = soma % 10;
        return (resto == 0) ? 0 : 10 - resto;
    }

    // Calcula o segundo DV com Módulo 11
    public static int calcularSegundoDV(String nossoNumeroComDV1) {
        int soma = 0;
        int[] pesos = {2, 3, 4, 5, 6, 7}; // Ciclo de 2 a 7

        for (int i = nossoNumeroComDV1.length() - 1, p = 0; i >= 0; i--, p = (p + 1) % pesos.length) {
            int num = Character.getNumericValue(nossoNumeroComDV1.charAt(i));
            soma += num * pesos[p];
        }

        int resto = soma % 11;
        if (resto == 0) return 0;
        if (resto == 1) return -1; // DV inválido
        return 11 - resto;
    }

    // Método principal para gerar o número de controle final (dois dígitos)
    public static String gerarNumeroControle(String nossoNumero8Digitos) {
        if (nossoNumero8Digitos.length() != 8)
            throw new IllegalArgumentException("O Nosso Número deve ter 8 dígitos.");

        int dv1 = calcularPrimeiroDV(nossoNumero8Digitos);
        String nossoNumeroComDV1 = nossoNumero8Digitos + dv1;

        int dv2 = calcularSegundoDV(nossoNumeroComDV1);

        if (dv2 == -1) {
            if (dv1 == 9) {
                dv1 = 0;
            } else {
                dv1 += 1;
            }
            nossoNumeroComDV1 = nossoNumero8Digitos + dv1;
            dv2 = calcularSegundoDV(nossoNumeroComDV1);
        }

        return String.valueOf(dv1) + dv2;
    }
    
    
}
