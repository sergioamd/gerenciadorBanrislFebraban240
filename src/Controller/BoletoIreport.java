
package Controller;

import static Controller.FormatadorUtil.formatarCpfCnpj;
import Model.Cliente;
import Model.CodigoBarrasUtil;
import Model.Empresa;
import Model.Mensagens;
import exemplotexgit.TelaPrincipal;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import net.sf.jasperreports.engine.util.JRLoader;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

public class BoletoIreport {
    
    
     private final LocalDate DATA_BASE = LocalDate.of(1997, 10, 7);
     private  final int FATOR_MAXIMO = 9999;
     private  final int FATOR_INICIAL = 1000;
     String dataFormatada = "";
     int fatorVencimento = 0;
     //String codigoBarras, linhaDigitavel;
     String caminhoArquivo;
     String dadosComplementares, observacao;
     BigDecimal valor;
     // Variáveis da barra de progresso
     private JProgressBar progressBar;
     private JFrame frame;
     
     TelaPrincipal mensagem = new TelaPrincipal();
     
     
     public String dadosComplementar(){
          
         if("0001 - Honorários".equals(mensagem.DadosComplementares())){
             
           dadosComplementares = "HONORÁRIOS Referente: "; 
           
         }

         return dadosComplementares;
     }
     
 
     

     public void gerarBoletos(List<Cliente> clientes) {
         System.out.println(observacao);
          // Criar janela de progresso
        criarBarraDeProgresso(clientes.size());
        
        int progresso = 0;
        
        for (Cliente cliente : clientes) {
            boleto(cliente);
            
            // Atualizar a barra
            progresso++;
            atualizarProgresso(progresso);
        }
        
         // Fechar a barra
        fecharBarraDeProgresso();
    }
     
     
     public void boleto(Cliente cliente) {
      
          
         try {

            
            // Gera código de barras e linha digitável
            String codigoBarras = CodigoBarrasUtil.gerarCodigoDeBarras(cliente);
            String linhaDigitavel = CodigoBarrasUtil.gerarLinhaDigitavel(codigoBarras);
            
              //System.out.println(codigoBarras);
              //System.out.println(linhaDigitavel);
           
            caminhoArquivo = "codigo_barras.jpg";
            gerarCodigoBarras(codigoBarras, caminhoArquivo);
            String teste = caminhoArquivo;
            
            // Dados do cedente
            String cedente = "Empresa xxyyzz.";
            String cnpjCedente = "0000/0000000.00";
         
             
            // Dados do boleto
            //String nossoNumero = cliente.getCodCliente();
            BigDecimal valor = new BigDecimal(cliente.getValor());
            String dataVencimento = dataFormatada(cliente.getDataVencimento()); //data formatada
            String dataDocumento = cliente.getDataGeracao();
            dataDocumento = dataDocumento.trim(); //tirar os espaços em branco 
            
            //subtração de numero bigdecimal
            BigDecimal resultado = valor.subtract(new BigDecimal("600"));
            
            //converter de big deimal para numeros com virgula
            BigDecimal valorFinal = resultado.divide(new BigDecimal("100"));
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String formatado = df.format(valorFinal);
            
                        
            String mesAnterior = DataUtils.getMesAnterior(dataVencimento);
            //Date dataDocumento = new Date();
           // Calendar calendar = Calendar.getInstance();
           // calendar.add(Calendar.DAY_OF_MONTH, 5); // Vencimento em 5 dias
           // Date dataVencimento = calendar.getTime();
            String localPagamento = "Pagável preferencialmente na rede integrada Banrisul";
            String instrucoes = "Após vencimento multa de 2% + juros de 2% ao mês";
       

            //modificados   
             //String //nossoNUmeroModificado = cliente.getCodCliente().substring(0, 8) +"-" + cliente.getCodCliente().substring(8, 10) ;   
             String cep = cliente.getCep() +"-"+ cliente.getPrefixo();
             String valorModificado = cliente.getValor().substring(0, 10).replaceFirst("^0+", "") + "," + cliente.getValor().substring(10,12);
             
             //mensagem complementar
             String mensagem = dadosComplementar() + "       " + mesAnterior + "        " + formatado 
                     + "              0060 - OUTRAS TAXA      SP/SP     6,00 "; 
            
             String observa = observacao;
                         
             String cnpj = cliente.getCnpj();
             String cnpjFormatado = formatarCpfCnpj(cnpj);
             
            // Dados para o relatório
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("cedente", cedente);
            parameters.put("cnpjCedente", cnpjCedente);
            parameters.put("sacado", cliente.getCliente());
            parameters.put("cpfSacado", cnpjFormatado);
            parameters.put("enderecoSacado", cliente.getEndereço());
            parameters.put("cidade", cliente.getCidade());
            parameters.put("cep", cep);
            parameters.put("nossoNumero", cliente.getCodCliente());
            parameters.put("valor", valorModificado);
            parameters.put("dataDocumento", dataFormatada(dataDocumento));
            parameters.put("dataVencimento", dataVencimento);
            parameters.put("localPagamento", localPagamento);
            parameters.put("instrucoes", instrucoes);
            parameters.put("codigoBarras", codigoBarras);
            parameters.put("imagemBarras", linhaDigitavel);
            parameters.put("dataAnterior", mesAnterior);
            parameters.put("complementar", mensagem);
            parameters.put("observacao", observa);
            
            
                        
            // Lista de boletos (para JRBeanCollectionDataSource)
           // List<Map<String, Object>> boletos = new ArrayList<>();
           // boletos.add(parameters);
           
            
           
             InputStream input = getClass().getResourceAsStream("/Jasper/Banrisul.jasper");
             if (input == null) {
                throw new RuntimeException("Arquivo .jasper não encontrado no JAR!");
               }
             JasperReport jasperReport = (JasperReport) JRLoader.loadObject(input);
                
             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

              // Caminho da pasta onde está o JAR
                String jarDir = new File(".").getCanonicalPath();
                File pastaPDF = new File(jarDir, "PDF"); 

                //verificação se existe a pasta
                if (!pastaPDF.exists()) {
                    pastaPDF.mkdirs();
                }
                //grava o arquivo
                File arquivo = new File(pastaPDF, cliente.getCodCliente() + ".pdf");
                //exporta o arquivo
                JasperExportManager.exportReportToPdfFile(jasperPrint, arquivo.getAbsolutePath());
                
               
                    
  
            
             //JOptionPane.showMessageDialog(null, "Boleto gerado com sucesso!");
        } catch (JRException e) {
            e.printStackTrace();
        } catch (Exception ex) {
             Logger.getLogger(BoletoIreport.class.getName()).log(Level.SEVERE, null, ex);
         }
         
          System.out.println("Boleto gerado com sucesso!"); 
          
    }
     
    
     /*Classe para formatar a data
     *28/03/2025
     */
     public String dataFormatada(String dt){
         
                 
            // Definir o formato original da data
            DateTimeFormatter formatoOriginal = DateTimeFormatter.ofPattern("ddMMyy");
            
            // Definir o formato desejado da data
            DateTimeFormatter formatoDesejado = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    
            // Parse a data original para um objeto LocalDate
             LocalDate data = LocalDate.parse(dt, formatoOriginal);
            
            

            // Format a data para o formato desejado
             dataFormatada = data.format(formatoDesejado);
               
             //calcularFatorVencimento(data);

         return dataFormatada;
     }
     
   
    
    
     
    public static void gerarCodigoBarras(String dados, String caminhoArquivo) throws Exception {
        
            
        
        Code128Bean codigoBarras = new Code128Bean();
        final int dpi = 150;

        File arquivo = new File(caminhoArquivo);
        FileOutputStream fos = new FileOutputStream(arquivo);
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(fos, "image/png", dpi, 
                BufferedImage.TYPE_BYTE_BINARY, false, 0);
        
        codigoBarras.generateBarcode(canvas, dados);
        canvas.finish();
        fos.close();
    }
    
    
       public static String formatarCNPJ(String cnpj) {
        // Remove qualquer caractere que não seja número
        cnpj = cnpj.replaceAll("\\D", "");

        // Verifica se tem 14 dígitos
        if (cnpj.length() != 14) {
            throw new IllegalArgumentException("CNPJ deve conter 14 dígitos.");
        }

        return String.format("%s.%s.%s/%s-%s",
                cnpj.substring(0, 2),
                cnpj.substring(2, 5),
                cnpj.substring(5, 8),
                cnpj.substring(8, 12),
                cnpj.substring(12, 14));
    }
      
       // Métodos da barra de progresso
    private void criarBarraDeProgresso(int maximo) {
        frame = new JFrame("Gerando Boletos...");
        progressBar = new JProgressBar(0, maximo);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        frame.add(progressBar);
        frame.setSize(400, 100);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }

    private void atualizarProgresso(int valor) {
        progressBar.setValue(valor);
    }

    private void fecharBarraDeProgresso() {
        frame.dispose();
       // JOptionPane.showMessageDialog(null, "Boletos gerados com sucesso!");
    }
      
     
}

