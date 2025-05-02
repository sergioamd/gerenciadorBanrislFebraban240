/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.ConversorUtil.converterParaDouble;
import static Controller.ValorUtil.converterDoubleParaValorEmCentavos;
import static Controller.ValorUtil.doubleParaDecimalFormatado;
import static Controller.ValorUtil.formatarParaRealBrasileiro;
import Model.Cliente;
import Model.Empresa;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.Texgit;

/**
 *
 * @author Sergio
 */
public class RemessaBanrisul {
    
   private int i = 1;
   int contadorClientes = 0, totalRegistros;
   int totalGeral = 1;
   char[] sufixos = {'P', 'Q', 'R'};
   Cliente cliente = new Cliente();
   Empresa empresa = new Empresa();
   double total;
   String valorTotal, valorRegistros ;

   //remover acentos
    public static String removerAcentos(String str) {
    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
    
        PrefixoSequencialGenerator prefixoGen = new PrefixoSequencialGenerator();
        StringBuilder arquivo = new StringBuilder();
    
       /*private String gerarIdentificacaoTitulo(int sequencia) {
        String prefixo = "04100013";
        //char[] sufixos = {'P', 'Q'};
        String numero = String.format("%05d", sequencia);
        char sufixo = sufixos[(sequencia - 1) % sufixos.length];
        return prefixo + numero + sufixo; // 18 posições com padding
        }*/
        
        /**
     * Gera a identificação do título no formato completo para o campo "NumeroSequencialRegistro".
     * Exemplo: 0410001300001P
     *
     * @param sequencia número sequencial (incremental)
     * @param sufixo sufixo do segmento (ex: "P", "Q", "R")
     * @return identificação do título formatada
     */
    public String gerarIdentificacaoTitulo(int sequencia, String sufixo) {
        String prefixo = "04100013";
        String numeroSequencial = String.format("%05d", sequencia);
        char letra = sufixos[sequencia % 3];
        return numeroSequencial;
    }
    
       LocalDate currentDate = LocalDate.now();
       LocalDate currentDates = LocalDate.now();
       LocalDateTime agora = LocalDateTime.now();
       DateTimeFormatter forma = DateTimeFormatter.ofPattern("ddMMyyyy");
       DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HHmmss");
       
       String formatteData = currentDate.format(forma);
       String horaFormatada = formatterHora.format(agora);
    
   //public void listCliente(String path){
       public void listCliente(String path){
       List <Cliente> clientes = new ArrayList<>();
       BoletoIreport ireport = new BoletoIreport();
             
       //System.out.println(pathe);
       //formatar data atual
       
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
       String formatteDate = currentDate.format(formatter);
       //System.out.println("Data atual: " + "Re"+formatteDate+".001");
       
               // PastaUtil.criarPastas("Layout");
               // File pastaLayout = PastaUtil.getCaminhoPasta("Layout");
               // File arquivo = new File(pastaEnvio, "Remessabanrisul240.xml\"");
       
       File layout = new File("layout/Remessabanrisul240.xml");   
       FlatFile<Record> ff = Texgit.createFlatFile(layout);
      
       //String path = "C:\\Users\\Sergio\\Documents\\NetBeansProjects\\Nova pasta\\Re200325.txt";
 
      try 
          
          (BufferedReader br = new BufferedReader(new FileReader(path))) {
        
			 String linha; 
                         
			while ((linha = br.readLine()) != null) {
                        
                       
                    if (linha.startsWith("01")) { // Detalhe - Pagamento recebido
                        empresa.setCodBeneficiario(linha.substring(26, 39));
                        cliente.setDataGeracao(linha.substring(90, 100));               
                        
                     
                    }
                    
                    if (linha.startsWith("1")){
                                               
                        cliente.setCodCliente(linha.substring(37, 47));
                        cliente.setDataVencimento(linha.substring(120, 126));
                        cliente.setReferencia(linha.substring(107, 111));
                        cliente.setValor(linha.substring(127,139));
                        cliente.setCnpj(linha.substring(220, 234));
                        cliente.setCliente(removerAcentos(linha.substring(239, 273)));
                        cliente.setEndereço(removerAcentos(linha.substring(274, 321)));
                        cliente.setCidade(removerAcentos(linha.substring(334, 348)));
                        cliente.setCep(linha.substring(326, 331));
                        cliente.setPrefixo(linha.substring(331, 334));
                        cliente.setEstado(removerAcentos(linha.substring(349, 351)));
                         
                        clientes.add(cliente);
                         
                       
                                
				ff.addRecord(CreateSegmentP(ff, cliente, i));
                                contadorClientes++;
                                totalGeral++;
                                totalRegistros++;
                               	i++;
                            ireport.boleto(cliente);
                            
                            }    
                        }
                        //mensagem ao usuário
                        JOptionPane.showMessageDialog(null, "Arquivo gerado com sucesso Total " + contadorClientes + " Boletos" );
                        
                //i  = 0;		
		ff.addRecord(CreateHeader(ff, i));
                totalGeral++;
                totalRegistros++;
		ff.addRecord(CreateHeaderLote(ff, i));
                i++;
                totalGeral++;
                totalRegistros++;
                
                             
            			   
      
			i++;
			ff.addRecord(CreateTraillerLote(ff, i));
                        totalGeral++;
                        totalRegistros++;
			i++;
			i++;//Soma mais um porque tem que contar o cabeçalho do arquivo. 
			ff.addRecord(CreateTraillerArquivo(ff, i));
                        totalGeral++;
                        totalRegistros++;
		
                 //criar pasta para salvar os arquivos de remessa       
                PastaUtil.criarPastas("Envio");
                File pastaEnvio = PastaUtil.getCaminhoPasta("Envio");
                File arquivo = new File(pastaEnvio, "Re" + formatteDate+".001" );
       
            FileUtils.writeLines(arquivo, ff.write(), System.getProperty("line.separator"));
             
          //System.out.println("Arquivo salvo em: " + arquivo.getAbsolutePath());
          
         
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
       
       
    
    public Record CreateHeader(FlatFile<Record> ff,  int seq){
        
        Record header = ff.createRecord("HeaderArquivo");
       i++;
       header.setValue("CNAB1", " ");
       header.setValue("Empresa-TipoInscricao", 2);
       header.setValue("Empresa-numeroInscricao", "00000000000000");//long
       header.setValue("Empresa-CodigoConvenio",empresa.getCodBeneficiario()); //long  -  verificar o codigo
       header.setValue("Empresa-AgenciaNumero", "0028");
       header.setValue("Empresa-AgenciaDv", "");
       header.setValue("Empresa-ContaCorrenteNumero", 200000000);//long
       header.setValue("Empresa-ContaCorrenteDv", "3");
       header.setValue("Empresa-DvAgenciaConta", "");
       header.setValue("Empresa-Nome", "Empresa xxyyzz");//G013
       header.setValue("CNAB2", ""); //G014
       header.setValue("Arquivo-CodigoRetorno", 1);//G015
       header.setValue("Arquivo-GeracaoData", formatteData); //G016
       header.setValue("Arquivo-GeracaoHora", horaFormatada); //G017
       header.setValue("Arquivo-SequenciaNumero", 001);//G018
       header.setValue("Arquivo-GravacaoDensidade", " "); //deixar em branco G020
       header.setValue("Reservado-Banco", ""); //G021
       header.setValue("Reservado-Empresa", ""); //G022
       header.setValue("CNAB3", ""); //G004
      
       
      
       return header;
       
    }
    
      public Record CreateHeaderLote(FlatFile<Record> ff,  int seq){
       
       Record headerLote = ff.createRecord("HeaderLote");
       
       headerLote.setValue("TipoOperacao", "R");
       headerLote.setValue("CNAB1", "");
       headerLote.setValue("CNAB2", "");
       headerLote.setValue("Empresa-TipoInscricao", 2);
       headerLote.setValue("Empresa-numeroInscricao", "00000000000000");//long
       headerLote.setValue("Empresa-CodigoConvenio", empresa.getCodBeneficiario());//long - verificar o codigo
       headerLote.setValue("Empresa-AgenciaNumero", "0028");
       headerLote.setValue("Empresa-AgenciaDv", 0);
       headerLote.setValue("Empresa-ContaCorrenteNumero", 200000000); //long
       headerLote.setValue("Empresa-ContaCorrenteDv", "3");
       headerLote.setValue("Empresa-DvAgenciaConta", "");
       headerLote.setValue("NomeEmpresa", "Empresa xxyyzz");
       headerLote.setValue("Mensagem1", "");
       headerLote.setValue("Mensagem2", "");
       headerLote.setValue("Retorno-Numero", 0);
       headerLote.setValue("Retorno-DataGravacao", formatteData); //date
       headerLote.setValue("DataCredito", 0); //date
       headerLote.setValue("CNAB3", "");
        
       
        return headerLote;
     }  
      
       public Record CreateSegmentP(FlatFile<Record> ff, Cliente cliente,  int sequencia){
           
           String dataVencimento = cliente.getDataVencimento();//10/05/25
           String dataDocumento = cliente.getDataGeracao();
           dataDocumento = dataDocumento.trim();
           DateTimeFormatter form = DateTimeFormatter.ofPattern("ddMMyy");
           DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("ddMMyyyy");
           currentDate = LocalDate.parse(dataVencimento, form);
           currentDates = LocalDate.parse(dataDocumento, form);
           String novaDataVencimento = currentDate.format(formatoSaida);
           String novaDataDocumento = currentDates.format(formatoSaida);
           
          
        Record segmentP = ff.createRecord("Segmento-P");
                     
        segmentP.setValue("CNAB", "");
        segmentP.setValue("CodigoMovimento", 01);
        segmentP.setValue("NumeroSequencialRegistro", gerarIdentificacaoTitulo(sequencia, "P"));
        segmentP.setValue("Empresa-AgenciaNumero", "00028");
        segmentP.setValue("Empresa-AgenciaDv", "");//1 em branco
        segmentP.setValue("Empresa-ContaCorrenteNumero", 0);//12 
        segmentP.setValue("Empresa-ContaCorrenteDv", "");//1 em branco
        segmentP.setValue("Empresa-DvAgenciaConta", "");//1 em branco
        segmentP.setValue("Banco-IdentificacaoTitulo", cliente.getCodCliente()); //G069
        segmentP.setValue("CodigoCarteira", 1); //cobrança simples C006
        segmentP.setValue("Banco-CadastramentoTitulo", 1); //C007
        segmentP.setValue("TipoDocumento", 1);
        segmentP.setValue("IdentificacaoEmissaoBoleto", 2); //C009
        segmentP.setValue("Distribuicao", "2"); //C010
        segmentP.setValue("DocumentoCobranca", cliente.getCodCliente()); //C011
        segmentP.setValue("TituloVencimento", novaDataVencimento);//date
        segmentP.setValue("ValorNominal", cliente.getValor());//BIGDECIMAL
        segmentP.setValue("AgenciaCobranca", 0);//00000
        segmentP.setValue("AgenciaDV", "");
        segmentP.setValue("TituloEspecie", 02); //C015
        segmentP.setValue("TituloAceito", "N"); //C016
        segmentP.setValue("BoletoEmissao", novaDataDocumento); //date
        segmentP.setValue("CodigoJuros", 2); //C018
        segmentP.setValue("DataJuros", 0); //date C019 8
        segmentP.setValue("JurosDiaTaxa", 200); //BIGDECIMAL 15
        segmentP.setValue("CodigoDesconto1", 0);//1
        segmentP.setValue("DataDesconto1", 0); //date 8
        segmentP.setValue("ValorPercentualConcedido", 0); //BIGDECIMAL 15
        segmentP.setValue("ValorIOF", 0); //BIGDECIMAL 15
        segmentP.setValue("ValorAbatimento", 0); //BIGDECIMAL 15
        segmentP.setValue("IdentTituloEmpresa", ""); //25
        segmentP.setValue("CodigoProtesto", 0);//1
        segmentP.setValue("DiasProtesto", 0);//2
        segmentP.setValue("DevolucaoTitulo", 0);//1
        segmentP.setValue("DiasDevolucao", "");//3
        //segmentP.setValue("CodigoMoeda", 09);
        segmentP.setValue("EspecieCobranca", 805076); //long //10
        segmentP.setValue("BancoEmpresa", "1");//1
        
        alteraValor(cliente.getValor());
        
        i++;
	segmentP.addInnerRecord(CreateSegmentQ(ff, cliente, i));
        totalGeral++;
        totalRegistros++;
        i++;
        segmentP.addInnerRecord(CreateSegmentR(ff, cliente, i));
        totalGeral++;
        totalRegistros++;
              
         
                    
        return segmentP;
        
      }  
       
       public Record CreateSegmentQ(FlatFile<Record> ff, Cliente cliente,  int sequencia){
       
        Record segmentQ = ff.createRecord("Segmento-Q");
        
         //não esquecer de incluir o endereço
        segmentQ.setValue("CNAB", ""); //1
        segmentQ.setValue("CodigoMovimento", 01);//2
        segmentQ.setValue("Lote-SequencialRegistro", gerarIdentificacaoTitulo(sequencia, "Q"));
        segmentQ.setValue("TipoInscricao", 2);//1
        segmentQ.setValue("NumeroInscricao", cliente.getCnpj()); //15
        segmentQ.setValue("Nome", cliente.getCliente());//40
        segmentQ.setValue("Endereco", cliente.getEndereço().subSequence(0, 40));//
        segmentQ.setValue("Bairro", "");
        segmentQ.setValue("CEP", cliente.getCep());//5
        segmentQ.setValue("SufixoCEP", cliente.getPrefixo());//3
        segmentQ.setValue("Cidade", cliente.getCidade());//15
        segmentQ.setValue("UF", cliente.getEstado());//2
        segmentQ.setValue("Sacador-Inscricao", 0);//1
        segmentQ.setValue("Sacador-Numero",0); //15
        segmentQ.setValue("SacadorNome", "");//40
        segmentQ.setValue("BancoCorrespondente", 0);//3
        segmentQ.setValue("NumeroBancoCorrespondente", "");
        segmentQ.setValue("CNAB2", "");
  
        return segmentQ;
      }  
       
        public Record CreateSegmentR(FlatFile<Record> ff, Cliente cliente,  int sequencia){
            
             Record segmentR = ff.createRecord("Segmento-R");
             
             segmentR.setValue("SequencialRegistro", gerarIdentificacaoTitulo(sequencia, "R"));
             segmentR.setValue("CNAB", "");//G004
             segmentR.setValue("CodigoMovimento", 01);//C004
             segmentR.setValue("CodigoDesconto2", 0);//C021
             segmentR.setValue("DataDesconto2", 0);//C022
             segmentR.setValue("ValorConcedido2", 0);//C023
             segmentR.setValue("CodigoDesconto3", 0);
             segmentR.setValue("DataDescont3", 0);
             segmentR.setValue("ValorConcedido3", 0);
             segmentR.setValue("CodigoMulta", "2");
             segmentR.setValue("dataMulta", 0); //g074
             segmentR.setValue("ValorAplicadoMulta", 200);///G075
             segmentR.setValue("InformacoesPagador", "");//G036
             segmentR.setValue("Mensagem3", "");//C037
             segmentR.setValue("Mensagem4", "");
             segmentR.setValue("CNAB2", "");
             segmentR.setValue("OcorPagador", 0);//C038
             segmentR.setValue("BancoContaDebito", 0);
             segmentR.setValue("AgenciaDebito", 0);
             segmentR.setValue("VerificadorAgencia", "");
             segmentR.setValue("ContaDebito", 0);
             segmentR.setValue("DigitoConta", "");
             segmentR.setValue("DigitoVerificador", "");
             segmentR.setValue("AvisoDebito", 0);
             segmentR.setValue("CNAB3", "");
             
            
          return segmentR;  
        }
        
         public Record CreateTraillerLote(FlatFile<Record> ff,  int i){
         
        Record traillerLote = ff.createRecord("TraillerLote");
         
         traillerLote.setValue("CNAB", "");
         traillerLote.setValue("QtdRegistros", totalRegistros); //somatorio das linhas
         traillerLote.setValue("Simples-QtdTitulos", contadorClientes); //somatorio dos titulos
         traillerLote.setValue("Simples-ValorTitulos", valorRegistros); //BigDecimal valor total dos titulos em cobrança
         traillerLote.setValue("Vinculada-QtdCarteira", 0);
         traillerLote.setValue("Vinculada-ValorCarteira", 0); //BigDecimal
         traillerLote.setValue("Caucionada-QtdCarteira", 0);
         traillerLote.setValue("Caucionada-ValorCarteira", 0);
         traillerLote.setValue("Descontada-QtdCarteira", 0);
         traillerLote.setValue("Descontada-ValorCarteira", 0);
         traillerLote.setValue("NumeroLancamento", "");
         traillerLote.setValue("CNAB2", "");
         
        
        
        return traillerLote;
      }  
         
          public Record CreateTraillerArquivo(FlatFile<Record> ff,  int i){
       
        Record traillerArquivo = ff.createRecord("TraillerArquivo");
    
        traillerArquivo.setValue("CNAB", "");
        traillerArquivo.setValue("QtdLotes", 01);
        traillerArquivo.setValue("QtdRegistros", totalGeral);
        traillerArquivo.setValue("QtdContas", 0);
        traillerArquivo.setValue("CNAB2", "");
        
         
        return traillerArquivo;
      }  
    
        public  String alteraValor(String valorTotal){
            
            String valorFormatado = formatarParaRealBrasileiro(valorTotal);
 
            total += converterParaDouble(valorFormatado); 

            valorRegistros = doubleParaDecimalFormatado(total);

            return null;

        }
        
      
       
    }
    
     

