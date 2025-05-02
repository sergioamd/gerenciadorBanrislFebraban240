/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Sergio
 */
public class CodigoBarrasUtilold {
    
  
    
    public static int calcularModulo10(String numero) {
        int soma = 0, peso = 2;
        for (int i = numero.length() - 1; i >= 0; i--) {
            int num = Character.getNumericValue(numero.charAt(i)) * peso;
            soma += (num > 9) ? num - 9 : num;
            peso = (peso == 2) ? 1 : 2;
        }
        int resto = soma % 10;
        return (resto == 0) ? 0 : 10 - resto;
    }

    /*public static int calcularModulo11(String numero) {
        int soma = 0, peso = 2;
        for (int i = numero.length() - 1; i >= 0; i--) {
            soma += Character.getNumericValue(numero.charAt(i)) * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        int resto = soma % 11;
        int digito = (resto == 0 || resto == 1) ? 1 : (resto == 10 ? 1 : 11 - resto);
        
        return digito;
    }*/
    public static int calcularModulo11(String nossoNumeroComDV1) {
    int[] pesos = {4, 3, 2, 7, 6, 5, 4, 3, 2}; // Da esquerda para a direita
    int soma = 0;

    for (int i = 0; i < pesos.length; i++) {
        int digito = Character.getNumericValue(nossoNumeroComDV1.charAt(i));
        soma += digito * pesos[i];
    }

    int resto = soma % 11;

    if (resto == 0) {
        return 0;
    } else if (resto == 1) {
        return -1; // Indica que o DV1 precisa ser ajustado
    } else {
        return 11 - resto;
    }
}
    
    public static String calcularDVsBanrisul(String nossoNumeroSemDV) {
    // 1. Calcula o primeiro DV (módulo 10)
    int dv1 = calcularModulo10(nossoNumeroSemDV);

    // 2. Calcula o segundo DV com base no número + DV1
    String numeroComDV1 = nossoNumeroSemDV + dv1;
    int dv2 = calcularModulo11(numeroComDV1);

    // 3. Trata o caso especial quando dv2 retorna -1 (resto = 1, DV inválido)
    if (dv2 == -1) {
        dv1 += 1;
        if (dv1 == 10) {
            dv1 = 0;
        }
        numeroComDV1 = nossoNumeroSemDV + dv1;
        dv2 = calcularModulo11(numeroComDV1);
    }

    return String.valueOf(dv1) + String.valueOf(dv2); // Retorna os dois DVs juntos
}

    public static int calcularDAC(String numero) {
        int soma = 0, peso = 2;
        for (int i = numero.length() - 1; i >= 0; i--) {
            soma += Character.getNumericValue(numero.charAt(i)) * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        int resto = soma % 11;
        return (resto == 0 || resto == 1) ? 1 : 11 - resto;
    }

    public static String calcularFatorVencimento(LocalDate dataVencimento) {
        LocalDate base = LocalDate.of(1997, 10, 7);
        long dias = ChronoUnit.DAYS.between(base, dataVencimento);
        long fator = dias % 9000;
        if (fator < 1000) {
            fator += 1000;
        }
        return String.format("%04d", fator);
    }
    
    

    public static String gerarCodigoDeBarras(Cliente cliente) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        LocalDate dataVencimento = LocalDate.parse(cliente.getDataVencimento(), formatter);
       
        
        String banco = "041";
        String moeda = "9";
        String valorNominal = cliente.getValor().substring(2, 12);
        String constante2 = "2";
        String constante1 = "1";
        String agencia = "0028";
        String beneficiario = "0132580";
        String nossoNumeroBase = cliente.getCodCliente().substring(0, 8);
        String constante40 = "40";
        String fatorVencimento = calcularFatorVencimento(dataVencimento);

        int dv1 = calcularModulo10(nossoNumeroBase);
        String comDV1 = nossoNumeroBase + dv1;
        
        int dv2 = calcularModulo11(comDV1);
        String nossoNumero = comDV1 + dv2;

        String campoLivre = constante2 + constante1 + agencia + beneficiario + nossoNumero.substring(0, 8) + constante40;
        int numeroControle = calcularModulo10(campoLivre) * 10 + calcularModulo11(campoLivre);
        
        String campoLivreCompleto = campoLivre + String.format("%02d", numeroControle);
        
        String codigoSemDAC = banco + moeda + fatorVencimento + valorNominal + campoLivreCompleto;
        int dac = calcularDAC(codigoSemDAC);
        
        String codigoBarras = banco + moeda + dac + fatorVencimento + valorNominal + campoLivreCompleto;
        
        return codigoBarras;
    }
         
    public static String gerarLinhaDigitavel(String codigoBarras) {
        String campo1 = codigoBarras.substring(0, 4) + codigoBarras.substring(19, 24);
        int dv1 = calcularModulo10(campo1);
        String campo1Formatado = campo1.substring(0, 5) + "." + campo1.substring(5) + dv1;
        
        String campo2 = codigoBarras.substring(24, 34);
        int dv2 = calcularModulo10(campo2);
        String campo2Formatado = campo2.substring(0, 5) + "." + campo2.substring(5) + dv2;
        
        String campo3 = codigoBarras.substring(34, 44);
        int dv3 = calcularModulo10(campo3);
        String campo3Formatado = campo3.substring(0, 5) + "." + campo3.substring(5) + dv3;
        
        String campo4 = codigoBarras.substring(4, 5);
        String campo5 = codigoBarras.substring(5, 9) + codigoBarras.substring(9, 19);

       
            return campo1Formatado + " " + campo2Formatado + " " + campo3Formatado + " " + campo4 + " " + campo5;
        
       
       
       
    }                   
      
}        
