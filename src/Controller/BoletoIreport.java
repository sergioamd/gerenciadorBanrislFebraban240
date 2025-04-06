
package Controller;

import Model.Cliente;
import Model.Empresa;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

public class BoletoIreport {
    
     
   
     private final LocalDate DATA_BASE = LocalDate.of(1997, 10, 7);
     private  final int FATOR_MAXIMO = 9999;
     private  final int FATOR_INICIAL = 1000;
     String dataFormatada = "";
     int fatorVencimento = 0;
     String codigoBarras, linhaDigitavel;
     String caminhoArquivo;
     
    
    /*Classe para criar o codigo de barra
     *28/03/2025
     */
     public String codigoBarras(Cliente cliente){
         
         /*Modelo 04192.10026 80132.580129 24049.140791 6 99570000067950*/
        
        String banco = "041"; //3 numeros
        String moeda = "9"; //1 numero
        String valorNominal = cliente.getValor().substring(2,12); // 10 numeros
        String constante2 = "2"; //1numero
        String constante1 = "1";//1nuemro
        String codigoAgencia = "0028";//4numeros
        String codigoBeneficiario = "1234567"; //8nuemros
        String nossoNumeroBase = cliente.getCodCliente().substring(0,8);//0325000368 //10nuemros
        String constante40 = "40";//1numero
        String fatorVencimento = "0000"; // Para documentos específicos pode ser 0000
        
        //Cálculo do primeiro DV (Módulo 10)
        int dv1 = calcularModulo10(nossoNumeroBase);
        String nossoNumeroComDV1 = nossoNumeroBase + dv1;
        
        // Cálculo do segundo DV (Módulo 11)
        int dv2 = calcularModulo11(nossoNumeroComDV1);
        String nossoNumero = nossoNumeroComDV1 + dv2;
        
         // Montagem do campo livre antes do cálculo do número de controle
        String campoLivre = constante2 + constante1 + codigoAgencia + codigoBeneficiario + nossoNumero + constante40;
        
        // Cálculo do número de controle (Módulo 10 e 11)
        int numeroControle = calcularModulo10(campoLivre) * 10 + calcularModulo11(campoLivre);
        
        // Montagem do código base antes do DAC
        String codigoBarrasSemDAC = banco + moeda + fatorVencimento + valorNominal + campoLivre + numeroControle;
        
        // Cálculo do DAC (Dígito de Autoconferência)
        int dac = calcularDAC(codigoBarrasSemDAC);
        
        // Montagem do código de barras completo
        String codigoBarras = banco + moeda + dac + fatorVencimento + valorNominal + campoLivre + numeroControle;
        //654321
        // Formatação da linha digitável
        
              linhaDigitavel = String.format(                                           //65432.1
            "%s%s%s.%s%s%d %s%s%s%d %s%s%d %d %s%s",
            banco, moeda, constante2, constante1, codigoAgencia.substring(0, 3), calcularModulo10(banco + moeda + constante2 + constante1 + codigoAgencia),
             codigoAgencia.substring(3, 4),codigoBeneficiario.substring(0, 4) + "." + codigoBeneficiario.substring(4), nossoNumero.substring(0, 2),
             calcularModulo10(codigoAgencia + codigoBeneficiario + nossoNumero.substring(0, 4)), nossoNumeroBase.substring(2, 7) +"." + 
             nossoNumeroBase.substring(7) + "40", numeroControle, calcularModulo10(nossoNumero.substring(5) + "40" + numeroControle), dac, fatorVencimento, valorNominal
        );                               //
        // 04192.16660 67654.321870 65432.12740.696  3 00000000001234
        
        System.out.println("Linha Digitável: " + linhaDigitavel);
        


         
         return codigoBarras;
         
   
     } 

     
     public void boleto(Cliente cliente) {  
         List<Cliente> lista = new ArrayList<>();
         lista.add(cliente);
          Empresa empresa = new Empresa();
        try {
            
            
             caminhoArquivo = "codigo_barras.jpg";
            gerarCodigoBarras(codigoBarras(cliente), caminhoArquivo);
            String teste = caminhoArquivo;
            
            // Dados do cedente
            String cedente = "Empresa XYZ.";
            String cnpjCedente = "0028/1234567.89";
         
            // Dados do sacado
            /*String sacado = cliente.getCliente();
            String cpfSacado = cliente.getCnpj();
            String enderecoSacado = cliente.getEndereço();
            String cidade = cliente.getCidade();
            String cep = cliente.getCep() + cliente.getPrefixo();
            */
             
            // Dados do boleto
            //String nossoNumero = cliente.getCodCliente();
            BigDecimal valor = new BigDecimal(cliente.getValor());
            String dataVencimento = dataFormatada(cliente.getDataVencimento()); //data formatada
            String dataDocumento = cliente.getDataGeracao();
            //Date dataDocumento = new Date();
           // Calendar calendar = Calendar.getInstance();
           // calendar.add(Calendar.DAY_OF_MONTH, 5); // Vencimento em 5 dias
           // Date dataVencimento = calendar.getTime();
            String localPagamento = "Qualquer Agência Bancária até o Vencimento";
            String instrucoes = "Após vencimento multa de 2% + juros de 2% ao mês";
            
             
             String templatePath = "C:\\Users\\Sergio\\Documents\\NetBeansProjects\\gerenciadorBanrislFebraban240\\Jasper\\Banrisul.jrxml";
             
            // Compilar o relatório
            JasperReport jasperReport = JasperCompileManager.compileReport(templatePath);
            
            
            for(Cliente item : lista){
             //modificados   
             String nossoNUmeroModificado = cliente.getCodCliente().substring(0, 8) +"-" + cliente.getCodCliente().substring(8, 10) ;   
             String cep = cliente.getCep() +"-"+ cliente.getPrefixo();
             String valorModificado = cliente.getValor().substring(0, 10).replaceFirst("^0+", "") + "," + cliente.getValor().substring(10,12);
             
             
             
            // Dados para o relatório
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("cedente", cedente);
            parameters.put("cnpjCedente", cnpjCedente);
            parameters.put("sacado", item.getCliente());
            parameters.put("cpfSacado", item.getCnpj());
            parameters.put("enderecoSacado", item.getEndereço());
            parameters.put("cidade", item.getCidade());
            parameters.put("cep", cep);
            parameters.put("nossoNumero", nossoNUmeroModificado);
            parameters.put("valor", valorModificado);
            parameters.put("dataDocumento", dataDocumento);
            parameters.put("dataVencimento", dataVencimento);
            parameters.put("localPagamento", localPagamento);
            parameters.put("instrucoes", instrucoes);
            parameters.put("codigoBarras", codigoBarras(cliente));
            parameters.put("imagemBarras", linhaDigitavel);
            
            
            
            // Lista de boletos (para JRBeanCollectionDataSource)
           // List<Map<String, Object>> boletos = new ArrayList<>();
           // boletos.add(parameters);
          
            // Caminho do template do boleto
             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
             
             
             String outputPath = "C:..\\gerenciadorBanrislFebraban240\\PDF\\" + item.getCodCliente() + ".pdf";
             
             JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

          
            }
             
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
     
     public static int calcularModulo10(String numero) {
        int soma = 0;
        int peso = 2;
        for (int i = numero.length() - 1; i >= 0; i--) {
            int num = Character.getNumericValue(numero.charAt(i)) * peso;
            soma += (num > 9) ? num - 9 : num;
            peso = (peso == 2) ? 1 : 2;
        }
        int resto = soma % 10;
        return (resto == 0) ? 0 : 10 - resto;
    }
     
     public static int calcularModulo11(String numero) {
        int soma = 0;
        int peso = 2;
        for (int i = numero.length() - 1; i >= 0; i--) {
            soma += Character.getNumericValue(numero.charAt(i)) * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        int resto = soma % 11;
        return (resto == 0 || resto == 1) ? 1 : (resto == 10 ? 1 : 11 - resto);
    }
    
    public static int calcularDAC(String numero) {
        int soma = 0;
        int peso = 2;
        for (int i = numero.length() - 1; i >= 0; i--) {
            soma += Character.getNumericValue(numero.charAt(i)) * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        int resto = soma % 11;
        return (resto == 0 || resto == 1) ? 1 : 11 - resto;
    }
   
     
    /*Classe para criar o fator de vencimento
     *28/03/2025
     */
    /*public  int calcularFatorVencimento(LocalDate data) {
        
        // Calcular a diferença de dias entre a data base e a data de vencimento
        long dias = ChronoUnit.DAYS.between(DATA_BASE, data);

        // Ajustar o fator de vencimento considerando o ciclo de 1000 a 9999
              fatorVencimento = FATOR_INICIAL + (int) dias % (FATOR_MAXIMO - FATOR_INICIAL + 1);

        return fatorVencimento;
        
        
    }*/

   /* private static String calcularDAC(String codigoBarras) {
        int soma = 0;
        int peso = 2;
        for (int i = codigoBarras.length() - 1; i >= 0; i--) {
            int valor = Character.getNumericValue(codigoBarras.charAt(i));
            soma += valor * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        int resto = soma % 11;
        int dac = (resto == 0 || resto == 1) ? 0 : 11 - resto;
        return String.valueOf(dac);
    }
    
    private static String calcularDuploDigito(String campoLivre) {
        int soma = 0;
        int peso = 2;
        for (int i = campoLivre.length() - 1; i >= 0; i--) {
            int valor = Character.getNumericValue(campoLivre.charAt(i));
            soma += valor * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        int resto = soma % 11;
        int digito1 = (resto == 0 || resto == 1) ? 0 : 11 - resto;

        soma = 0;
        peso = 2;
        String campoLivreComDigito1 = campoLivre + digito1;
        for (int i = campoLivreComDigito1.length() - 1; i >= 0; i--) {
            int valor = Character.getNumericValue(campoLivreComDigito1.charAt(i));
            soma += valor * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        resto = soma % 11;
        int digito2 = (resto == 0 || resto == 1) ? 0 : 11 - resto;

        return String.valueOf(digito1) + String.valueOf(digito2);
    }*/
    
     
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
    
      
     
}

