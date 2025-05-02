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
public class PrefixoSequencialGenerator {
    
    private int sequencia = 1;
    private final char[] sufixos = {'P', 'Q', 'R'};
    private int sufixoIndex = 0;

    public String gerarPrefixo() {
        String prefixo = String.format("04100013%04d%c", sequencia, sufixos[sufixoIndex]);
        sequencia++;
        sufixoIndex = (sufixoIndex + 1) % sufixos.length;
        return prefixo;
    }

    public void reset() {
        sequencia = 1;
        sufixoIndex = 0;
    }
}
