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
public class Header {
    
    private Integer banco;
    private Integer lote;
    private Integer tipo;
    private String cnab;
    private String nomeBanco;

    public Header(Integer banco, Integer lote, Integer tipo, String cnab, String nomeBanco) {
        this.banco = 041;
        this.lote = 0000;
        this.tipo = 0;
        this.cnab = cnab;
        this.nomeBanco = "Banrisul";
    } 
    
    public Header() {
        
    }

    public Integer getBanco() {
        return banco;
    }

    public void setBanco(Integer banco) {
        this.banco = banco;
    }

    public Integer getLote() {
        return lote;
    }

    public void setLote(Integer lote) {
        this.lote = lote;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getCnab() {
        return cnab;
    }

    public void setCnab(String cnab) {
        this.cnab = cnab;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }
    
    
    
    
    
    
}
