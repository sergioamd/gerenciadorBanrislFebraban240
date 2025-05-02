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
public class CodigoBarrasUtil {
    
  
    
     

    public static String gerarCodigoDeBarras(Cliente cliente) {
       
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        LocalDate dataVencimento = LocalDate.parse(cliente.getDataVencimento(), formatter);

        String banco = "041";
        String moeda = "9";
        String valorNominal = cliente.getValor();
        String agencia = "0028";
        String beneficiario = "00280132580";
        String nossoNumeroBase = cliente.getCodCliente();
        String constante40 = "40";
        String fatorVencimento = calcularFatorVencimento(dataVencimento);
        String constante1 = "1";
        String constante2 = "2";
        
        String nossoNumero = nossoNumeroBase.substring(0, 8);
        String codigoBeneficiario = beneficiario.substring(4, 11); 
        String valor = valorNominal.substring(2, 12);
        
        /***************************************************************/
         // === Geração do Nosso Número com DV1 (módulo 10) e DV2 (módulo 11)
        int dv1 = calcularModulo10(nossoNumero);
        String baseComDV1 = nossoNumero + dv1;
        int dv2 = calcularModulo11(baseComDV1);

        // Se DV2 for inválido, ajusta DV1 e recalcula
        if (dv2 == 1) {
            dv1 = (dv1 == 9) ? 0 : dv1 + 1;
            baseComDV1 = nossoNumero + dv1;
            dv2 = calcularModulo11(baseComDV1);
        }
     /***********************************************************************/

        // === Campo livre (posição 20 a 42 do código de barras)
        String campoLivreBase = constante2 + constante1 + agencia + codigoBeneficiario + nossoNumero + constante40;
        //21002801325800325001440 
        
        //faz o calculo do duplo digito solicitado pelo Banrisul
         int[] duploDv = calcularDuploDigito(campoLivreBase);
        String digito1 = String.valueOf(duploDv[0]);
        String digito2 = String.valueOf(duploDv[1]);
           //campo livre com os digitos posição 20 a 44
           String duploDV = digito1 + digito2 ;
           String campoLivre = campoLivreBase + duploDV;

           
        // === Calcula o DAC (dígito verificador geral)
        String baseSemDAC = banco + moeda + fatorVencimento + valor + campoLivre;
        
        int dac = calcularDAC(baseSemDAC);

        // === Monta o código final com DAC na posição 5

        String codigoBarras = banco + moeda + dac + fatorVencimento + valor + campoLivre;

        
            return codigoBarras;          

          }  
    
        public static String gerarLinhaDigitavel(String codigoBarras) {

         // Campo 1: Banco + Moeda + Parte do campo livre
    String campo1 = codigoBarras.substring(0, 4) + codigoBarras.substring(19, 24); // banco + moeda + parte do campo livre
    int dv1 = calcularModulo10(campo1);
    campo1 = campo1.substring(0, 5) + "." + campo1.substring(5) + dv1;

    // Campo 2: Continuação do campo livre
    String campo2 = codigoBarras.substring(24, 34); // continuação do campo livre
    int dv2 = calcularModulo10(campo2);
    campo2 = campo2.substring(0, 5) + "." + campo2.substring(5) + dv2;

    // Campo 3: Final do campo livre
    String campo3 = codigoBarras.substring(34, 44); // final do campo livre
    int dv3 = calcularModulo10(campo3);
    campo3 = campo3.substring(0, 5) + "." + campo3.substring(5) + dv3;

    // Campo 4: DAC
    String campo4 = codigoBarras.substring(4, 5); // DAC

    // Campo 5: Fator + Valor
    String campo5 = codigoBarras.substring(5, 9) + codigoBarras.substring(9, 19); // fator + valor
    
    
    // Montagem da linha digitável
    String linhaDigitavel = campo1 + " " + campo2 + " " + campo3 + " " + campo4 + " " + campo5;
     
       
         System.out.println("codigo Barras " + codigoBarras);
         System.out.println("linha digitavel " + linhaDigitavel);
    
    return linhaDigitavel;
       
    }

      // Calcula o módulo 10
    public static int calcularModulo10(String numero) {
        int soma = 0;
        int peso = 2;

        for (int i = numero.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(numero.charAt(i));
            int mult = digito * peso;
            soma += (mult > 9) ? (mult - 9) : mult;
            peso = (peso == 2) ? 1 : 2;
        }

        int resto = soma % 10;
        return (resto == 0) ? 0 : (10 - resto);
    }
    
    //calculo do modulo 11
    public static int calcularModulo11(String numero) {
        int[] pesos = {2, 3, 4, 5, 6, 7}; // pesos se repetem a partir da direita
        int soma = 0;
        int pesoIndex = 0;

        for (int i = numero.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(numero.charAt(i));
            soma += digito * pesos[pesoIndex];
            pesoIndex = (pesoIndex + 1) % pesos.length;
        }

        int resto = (soma < 11) ? soma : (soma % 11);
        if (resto == 0) return 0;
        if (resto == 1) return -1; // DV inválido
        return 11 - resto;
    }

    public static int[] calcularDuploDigito(String base) {
        int dv1 = calcularModulo10(base);
        String numeroComDv1 = base + dv1;

        int dv2 = calcularModulo11(numeroComDv1);

        if (dv2 == -1) {
            if (dv1 == 9) {
                dv1 = 0;
            } else {
                dv1++;
            }
            numeroComDv1 = base + dv1;
            dv2 = calcularModulo11(numeroComDv1); // Recalcula DV2
        }

        return new int[]{dv1, dv2};
    }
    //calculo fator vencimento
   public static String calcularFatorVencimento(LocalDate dataVencimento) {
        LocalDate base = LocalDate.of(1997, 10, 7);
        long dias = ChronoUnit.DAYS.between(base, dataVencimento);
        long fator = dias % 9000;
        if (fator < 1000) {
            fator += 1000;
        }
        return String.format("%04d", fator);
      
    } 
   //calculo dac
   public static int calcularDAC(String codigoSemDAC) {
        
          int soma = 0;
        int peso = 2;

        for (int i = codigoSemDAC.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(codigoSemDAC.charAt(i));
            soma += digito * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }

        int resto = soma % 11;
        if (resto == 0 || resto == 1) return 1;
        return 11 - resto;
    
    }
  
}

