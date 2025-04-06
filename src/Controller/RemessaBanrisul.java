/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Cliente;
import Model.Empresa;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.Texgit;

/**
 *
 * @author Sergio
 */
public class RemessaBanrisul {
    
   private int i = 0;
   Cliente cliente = new Cliente();
   Empresa empresa = new Empresa();
   
   //remover acentos
    public static String removerAcentos(String str) {
    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
    
       LocalDate currentDate = LocalDate.now();
       LocalDate currentDates = LocalDate.now();
       LocalDateTime agora = LocalDateTime.now();
       DateTimeFormatter forma = DateTimeFormatter.ofPattern("ddMMyyyy");
       DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HHmmss");
       
       String formatteData = currentDate.format(forma);
       String horaFormatada = formatterHora.format(agora);
    
   public void listCliente(){
       
       List <Cliente> clientes = new ArrayList<>();
       BoletoIreport ireport = new BoletoIreport();
             
       
       //formatar data atual
       
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
       String formatteDate = currentDate.format(formatter);
       //System.out.println("Data atual: " + "Re"+formatteDate+".001");
       
       
       File layout = new File("C:\\Users\\Sergio\\Documents\\NetBeansProjects\\gerenciadorBanrislFebraban240\\src\\layoutBanrisul\\Remessabanrisul240.xml");   
       FlatFile<Record> ff = Texgit.createFlatFile(layout);
       
      String path = "C:\\Users\\Sergio\\Documents\\NetBeansProjects\\Nova pasta\\Re200325teste.txt";
 
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
				//i++;
                            ireport.boleto(cliente);
                    }    
                        }    
                        
          i  = 0;		
		ff.addRecord(CreateHeader(ff, i));
		
		ff.addRecord(CreateHeaderLote(ff, i));
		i++;
                             
			   
      
			i++;
			ff.addRecord(CreateTraillerLote(ff, i));
			i++;
			i++;//Soma mais um porque tem que contar o cabeçalho do arquivo. 
			ff.addRecord(CreateTraillerArquivo(ff, i));
			
                     
      
       
            FileUtils.writeLines(new File("Re"+formatteDate+".001"), ff.write(), System.getProperty("line.separator"));
             
                  
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public Record CreateHeader(FlatFile<Record> ff,  int seq){
         
         //04100000fffffffff022918330001840028013258009fffffff00028f000200132580ffeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
         //04100011f01ff060202291833000184
        Record header = ff.createRecord("HeaderArquivo");
       //codigo beneficiario 0028013258009  informar a esquerda e espacos em branco a direita
       header.setValue("CNAB1", " ");
       header.setValue("Empresa-TipoInscricao", 2);
       header.setValue("Empresa-numeroInscricao", "12345678000084");//long
       header.setValue("Empresa-CodigoConvenio",empresa.getCodBeneficiario()); //long  -  verificar o codigo
       header.setValue("Empresa-AgenciaNumero", "0028");
       header.setValue("Empresa-AgenciaDv", "");
       header.setValue("Empresa-ContaCorrenteNumero", 123456789);//long
       header.setValue("Empresa-ContaCorrenteDv", "3");
       header.setValue("Empresa-DvAgenciaConta", "");
       header.setValue("Empresa-Nome", "Empresa XYZ");//G013
       header.setValue("CNAB2", ""); //G014
       header.setValue("Arquivo-CodigoRetorno", 1);//G015
       header.setValue("Arquivo-GeracaoData", formatteData); //G016
       header.setValue("Arquivo-GeracaoHora", horaFormatada); //G017
       header.setValue("Arquivo-SequenciaNumero", 0);//G018
       header.setValue("Arquivo-GravacaoDensidade", " "); //deixar em branco G020
       header.setValue("Reservado-Banco", ""); //G021
       header.setValue("Reservado-Empresa", ""); //G022
       header.setValue("CNAB3", ""); //G004
      
       
      
       return header;
       
    }
    
      public Record CreateHeaderLote(FlatFile<Record> ff,  int seq){
       
       Record headerLote = ff.createRecord("HeaderLote");
   
       headerLote.setValue("TipoOperacao", "");
       headerLote.setValue("CNAB1", "");
       headerLote.setValue("CNAB2", "");
       headerLote.setValue("Empresa-TipoInscricao", 2);
       headerLote.setValue("Empresa-numeroInscricao", "12345678000084");//long
       headerLote.setValue("Empresa-CodigoConvenio", empresa.getCodBeneficiario());//long - verificar o codigo
       headerLote.setValue("Empresa-AgenciaNumero", "0028");
       headerLote.setValue("Empresa-AgenciaDv", 0);
       headerLote.setValue("Empresa-ContaCorrenteNumero", 123456789); //long
       headerLote.setValue("Empresa-ContaCorrenteDv", "3");
       headerLote.setValue("Empresa-DvAgenciaConta", "");
       headerLote.setValue("NomeEmpresa", "Empresa XYZ");
       headerLote.setValue("Mensagem1", "");
       headerLote.setValue("Mensagem2", "");
       headerLote.setValue("Retorno-Numero", 0);
       headerLote.setValue("Retorno-DataGravacao", formatteData); //date
       headerLote.setValue("DataCredito", 0); //date
       headerLote.setValue("CNAB3", "");
        
       
        return headerLote;
     }  
      
       public Record CreateSegmentP(FlatFile<Record> ff, Cliente cliente,  int seq){
           
           String dataVencimento = cliente.getDataVencimento();//10/05/25
           String dataDocumento = cliente.getDataGeracao();
           dataDocumento = dataDocumento.trim();
           DateTimeFormatter forma = DateTimeFormatter.ofPattern("ddMMyy");
           DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("ddMMyyyy");
           currentDate = LocalDate.parse(dataVencimento, forma);
           currentDates = LocalDate.parse(dataDocumento, forma);
           String novaDataVencimento = currentDate.format(formatoSaida);
           String novaDataDocumento = currentDates.format(formatoSaida);
           System.out.println(novaDataVencimento);
           System.out.println(novaDataDocumento);
          
        Record segmentP = ff.createRecord("Segmento-P");
    
        segmentP.setValue("CNAB", "");
        segmentP.setValue("CodigoMovimento", 0);
        segmentP.setValue("Empresa-AgenciaNumero", "00028");
        segmentP.setValue("Empresa-AgenciaDv", "");
        segmentP.setValue("Empresa-ContaCorrenteNumero", 0);
        segmentP.setValue("Empresa-ContaCorrenteDv", "");
        segmentP.setValue("Empresa-DvAgenciaConta", "");
        segmentP.setValue("Banco-IdentificacaoTitulo", ""); //G069
        segmentP.setValue("CodigoCarteira", 1); //cobrança simples C006
        segmentP.setValue("Banco-CadastramentoTitulo", 1); //C007
        segmentP.setValue("TipoDocumento", 1);
        segmentP.setValue("IdentificacaoEmissaoBoleto", 2); //C009
        segmentP.setValue("Distribuicao", "2"); //C010
        segmentP.setValue("DocumentoCobranca", cliente.getCodCliente()); //C011
        segmentP.setValue("TituloVencimento", novaDataVencimento);//date
        segmentP.setValue("ValorNominal", cliente.getValor());//BIGDECIMAL
        segmentP.setValue("AgenciaCobranca", 0);
        segmentP.setValue("AgenciaDV", "");
        segmentP.setValue("TituloEspecie", 2); //C015
        segmentP.setValue("TituloAceito", ""); //C016
        segmentP.setValue("BoletoEmissao", novaDataDocumento); //date
        segmentP.setValue("CodigoJuros", 2); //C018
        segmentP.setValue("DataJuros", 0); //date C019
        segmentP.setValue("JurosDiaTaxa", 04); //BIGDECIMAL
        segmentP.setValue("CodigoDesconto1", 0);
        segmentP.setValue("DataDesconto1", 0); //date
        segmentP.setValue("ValorPercentualConcedido", 0); //BIGDECIMAL
        segmentP.setValue("ValorIOF", 0); //BIGDECIMAL
        segmentP.setValue("ValorAbatimento", 0); //BIGDECIMAL
        segmentP.setValue("IdentTituloEmpresa", "");
        segmentP.setValue("CodigoProtesto", 0);
        segmentP.setValue("DiasProtesto", 0);
        segmentP.setValue("DevolucaoTitulo", 0);
        segmentP.setValue("DiasDevolucao", 0);
        //segmentP.setValue("CodigoMoeda", 09);
        segmentP.setValue("EspecieCobranca", 805076); //long
        segmentP.setValue("BancoEmpresa", "1");
        
        i++;
	segmentP.addInnerRecord(CreateSegmentQ(ff, cliente, i));
                
        return segmentP;
      }  
       
       public Record CreateSegmentQ(FlatFile<Record> ff, Cliente cliente,  int seq){
       
        Record segmentQ = ff.createRecord("Segmento-Q");
    
        segmentQ.setValue("CNAB", "");
        segmentQ.setValue("CodigoMovimento", 0);
        segmentQ.setValue("TipoInscricao", 0);
        segmentQ.setValue("NumeroInscricao", cliente.getCodCliente()); //long
        segmentQ.setValue("Nome", cliente.getCliente());
        segmentQ.setValue("Endereco", "");
        segmentQ.setValue("Bairro", "");
        segmentQ.setValue("CEP", cliente.getCep());
        segmentQ.setValue("SufixoCEP", cliente.getPrefixo());
        segmentQ.setValue("Cidade", cliente.getCidade());
        segmentQ.setValue("UF", cliente.getEstado());
        segmentQ.setValue("Sacador-Inscricao", 0);
        segmentQ.setValue("Sacador-Numero",0); //long
        segmentQ.setValue("SacadorNome", "");
        segmentQ.setValue("BancoCorrespondente", 0);
        segmentQ.setValue("NumeroBancoCorrespondente", "");
        segmentQ.setValue("CNAB2", "");
        
        
        
        return segmentQ;
      }  
        
         public Record CreateTraillerLote(FlatFile<Record> ff,  int seq){
       
        Record traillerLote = ff.createRecord("TraillerLote");
    
         traillerLote.setValue("CNAB", "");
         traillerLote.setValue("QtdRegistros", 0);
         traillerLote.setValue("Simples-QtdTitulos", 0);
         traillerLote.setValue("Simples-ValorTitulos", 0); //BigDecimal
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
         
          public Record CreateTraillerArquivo(FlatFile<Record> ff,  int seq){
       
        Record traillerArquivo = ff.createRecord("TraillerArquivo");
    
        traillerArquivo.setValue("CNAB", "");
        traillerArquivo.setValue("QtdLotes", 0);
        traillerArquivo.setValue("QtdRegistros", 0);
        traillerArquivo.setValue("QtdContas", 0);
        traillerArquivo.setValue("CNAB2", "");
        
        
        return traillerArquivo;
      }  
       
        
    }
    
     

