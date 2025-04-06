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
public class Cliente {
   
     private String codCliente;
     private String cliente;
     private String cnpj;
     private String endereço;
     private String cidade;
     private String estado;
     private String cep;
     private String prefixo;
     private String valor;
     private String dataVencimento;
     private String referencia;
     private String codigobeneficiario;
     private String dataGeracao;

    public Cliente(String codCliente, String cliente, String cnpj, String endereço, String cidade, String estado, String cep, String prefixo, String valor, String dataVencimento, String referencia, String codigobeneficiario, String dataGeracao) {
        this.codCliente = codCliente;
        this.cliente = cliente;
        this.cnpj = cnpj;
        this.endereço = endereço;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.prefixo = prefixo;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.referencia = referencia;
        this.codigobeneficiario = codigobeneficiario;
        this.dataGeracao = dataGeracao;
    }
     
     
        
    

    public Cliente() {
        
   }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }


    public String getCodigobeneficiario() {
        return codigobeneficiario;
    }

    public void setCodigobeneficiario(String codigobeneficiario) {
        this.codigobeneficiario = codigobeneficiario;
    }

    public String getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(String dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    
    
    @Override
    public String toString() {
        return "Cliente{" + "codCliente=" + codCliente + ", "
                + "cliente=" + cliente + ", cnpj=" + cnpj + ","
                + " endere\u00e7o=" + endereço + ", cidade=" + cidade + ", "
                + "estado=" + estado + ", valor=" + valor + ", "
                + "dataVencimento=" + dataVencimento + ","
                + " referencia=" + referencia + ","
                + " codigobeneficiario=" + codigobeneficiario + ", "
                + "dataGeracao=" + dataGeracao + '}';
    }

  
    
 


     
    
}
