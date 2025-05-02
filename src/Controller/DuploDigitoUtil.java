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
public class DuploDigitoUtil {
     public static String gerarDuploDigito(String campoLivreSemDV) {
        int dv1 = CodigoControleUtil.calcularPrimeiroDV(campoLivreSemDV);
        String comDv1 = campoLivreSemDV + dv1;
        int dv2 = CodigoControleUtil.calcularSegundoDV(comDv1);

        if (dv2 == -1) {
            dv1 = (dv1 == 9) ? 0 : dv1 + 1;
            comDv1 = campoLivreSemDV + dv1;
            dv2 = CodigoControleUtil.calcularSegundoDV(comDv1);
        }

        return String.valueOf(dv1) + dv2;
    }
}
