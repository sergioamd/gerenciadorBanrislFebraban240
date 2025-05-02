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

/**
 *
 * @author Sergio
 */
public class ArquivoUtil {
  
    /**
     * Salva um conteúdo em arquivo .txt na mesma pasta (ou subpasta) onde está o executável do programa.
     *
     * @param subPasta Nome da subpasta onde salvar o arquivo (ex: "output", "logs", "remessas").
     * @param prefixo Nome base do arquivo (ex: "boleto", "remessa").
     * @param conteudo Conteúdo a ser salvo no arquivo.
     * @return Caminho completo do arquivo salvo.
     */
    public static String salvarNaPastaDoPrograma(String subPasta, String prefixo, String conteudo) {
        try {
            // Pega o diretório onde o programa está rodando
            String caminhoBase = new File("").getAbsolutePath();

            // Cria o caminho da subpasta
            String caminhoCompleto = caminhoBase + File.separator + subPasta;

            // Cria a pasta se não existir
            File pasta = new File(caminhoCompleto);
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            // Gera o nome do arquivo com data e hora
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nomeArquivo = prefixo + "_" + timestamp + ".txt";

            // Cria o arquivo e salva o conteúdo
            File arquivo = new File(pasta, nomeArquivo);
            try (FileWriter writer = new FileWriter(arquivo)) {
                writer.write(conteudo);
            }

            System.out.println("✅ Arquivo salvo em: " + arquivo.getAbsolutePath());
            return arquivo.getAbsolutePath();

        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar arquivo: " + e.getMessage());
            return null;
        }
    }
}
