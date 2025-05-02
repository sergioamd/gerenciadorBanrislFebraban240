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
public class FormatadorUtil {
    
    public static String formatarCpfCnpj(String numero) {
        numero = numero.replaceAll("\\D", ""); // remove tudo que não for dígito

        if (numero.length() == 11) {
            // Formato CPF: 000.000.000-00
            return numero.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        } else if (numero.length() == 14) {
            // Formato CNPJ: 00.000.000/0000-00
            return numero.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        } else {
            return numero; // Retorna como está se não for válido
        }
    }

    public static String formatarTelefone(String telefone) {
        telefone = telefone.replaceAll("\\D", ""); // remove não-dígitos

        if (telefone.length() == 10) {
            // Formato: (XX) XXXX-XXXX
            return telefone.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        } else if (telefone.length() == 11) {
            // Formato: (XX) 9XXXX-XXXX
            return telefone.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        } else {
            return telefone;
        }   
}
}
