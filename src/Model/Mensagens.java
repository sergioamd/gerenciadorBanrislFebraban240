/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Sergio
 */
public class Mensagens {
    
    private String dadosComplementares;
    private String observacoes;

    public Mensagens(String dadosComplementares, String observacoes) {
        this.dadosComplementares = dadosComplementares;
        this.observacoes = observacoes;
    }
    
    public Mensagens(){
        
    }

    public String getDadosComplementares() {
        return dadosComplementares;
    }

    public void setDadosComplementares(String dadosComplementares) {
        this.dadosComplementares = dadosComplementares;
    }

    public String getObservaçoes() {
        return observacoes;
    }

    public void setObservaçoes(String observaçoes) {
        this.observacoes = observaçoes;
    }

   
    
    
    
}
