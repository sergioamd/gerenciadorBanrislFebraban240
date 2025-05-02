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
public class Empresa{
    
    private int tipoInscrição;
    private int inscrição;
    private String codBeneficiario;
    private int agencia;
    private String digitoAgencia;
    private int conta;
    private String digitoConta;
    private String verificadorConta;
    private String antigoCedente;
    private String dataGeracao;
    
    private String empresa = "Lisboa Assessoria Contábil";
    private String endereço = "Av. Assis Brasil 6186 2º andar";
    private String cep = "91100-000";
    private String cidade = "Porto Alegre";
    private String bairro = "Sarandi";
    private String estado = "RS";
    private String fone = "3014-9009";
    private String CNPJ = "02.291.833/0001-84";
   
    
    public Empresa(){
        
    }
    
    public Empresa(int tipoInscrição, int inscrição, String codBeneficiario, int agencia, String digitoAgencia, int conta, String digitoConta, String verificadorConta, 
            String antigoCedente, String dataGeracao) {
        
        this.tipoInscrição = tipoInscrição;
        this.inscrição = inscrição;
        this.codBeneficiario = codBeneficiario;
        this.agencia = agencia;
        this.digitoAgencia = digitoAgencia;
        this.conta = conta;
        this.digitoConta = digitoConta;
        this.verificadorConta = verificadorConta;
        this.antigoCedente = antigoCedente;
        this.dataGeracao = dataGeracao;
    }
    
    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public int getTipoInscrição() {
        return tipoInscrição;
    }

    public void setTipoInscrição(int tipoInscrição) {
        this.tipoInscrição = tipoInscrição;
    }

    public int getInscrição() {
        return inscrição;
    }

    public void setInscrição(int inscrição) {
        this.inscrição = inscrição;
    }

    public String getCodBeneficiario() {
        return codBeneficiario;
    }

    public void setCodBeneficiario(String codBeneficiario) {
        this.codBeneficiario = codBeneficiario;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public String getDigitoAgencia() {
        return digitoAgencia;
    }

    public void setDigitoAgencia(String digitoAgencia) {
        this.digitoAgencia = digitoAgencia;
    }

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    public String getDigitoConta() {
        return digitoConta;
    }

    public void setDigitoConta(String digitoConta) {
        this.digitoConta = digitoConta;
    }

    public String getVerificadorConta() {
        return verificadorConta;
    }

    public void setVerificadorConta(String verificadorConta) {
        this.verificadorConta = verificadorConta;
    }

    public String getAntigoCedente() {
        return antigoCedente;
    }

    public void setAntigoCedente(String antigoCedente) {
        this.antigoCedente = antigoCedente;
    }

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(String dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public Empresa getCodBeneficiario(String substring) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
