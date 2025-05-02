/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Sergio
 */
public class PastaUtil {
   
    /**
     * Cria uma ou mais pastas automaticamente no diretório onde o sistema está sendo executado.
     *
     * @param nomesDasPastas Lista com os nomes das pastas a serem criadas.
     * @return Caminhos absolutos das pastas criadas.
     */
    public static String[] criarPastasNoDiretorioDoSistema(List<String> nomesDasPastas) {
        String diretorioBase = new File("").getAbsolutePath(); // Diretório atual do sistema
        String[] caminhosCriados = new String[nomesDasPastas.size()];

        for (int i = 0; i < nomesDasPastas.size(); i++) {
            File pasta = new File(diretorioBase, nomesDasPastas.get(i));
            if (!pasta.exists()) {
                boolean criada = pasta.mkdirs();
                if (criada) {
                    System.out.println("Pasta criada: " + pasta.getAbsolutePath());
                } else {
                    System.out.println("Não foi possível criar: " + pasta.getAbsolutePath());
                }
            }
            caminhosCriados[i] = pasta.getAbsolutePath();
        }

        return caminhosCriados;
    }

         public static File getDiretorioBase() {
        return new File(System.getProperty("user.dir"));
    }

    public static void criarPastas(String... nomesPastas) {
        File base = getDiretorioBase();
        for (String nome : nomesPastas) {
            File pasta = new File(base, nome);
            if (!pasta.exists()) {
                boolean criada = pasta.mkdirs();
                if (criada) {
                    System.out.println("Pasta criada: " + pasta.getAbsolutePath());
                } else {
                    System.out.println("Não foi possível criar a pasta: " + pasta.getAbsolutePath());
                }
            }
        }
    }

    public static File getCaminhoPasta(String nomePasta) {
        return new File(getDiretorioBase(), nomePasta);
    }  
      

  
    
}
