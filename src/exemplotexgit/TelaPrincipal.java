 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplotexgit;

import Controller.BoletoIreport;
import Controller.PastaUtil;
import Controller.RemessaBanrisul;
import Model.Mensagens;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;

/**
 *
 * @author Sergio
 */
public class TelaPrincipal extends javax.swing.JFrame {

    //private javax.swing.JProgressBar progressBar;
    /**
     * Creates new form NewJFrame
     */
   
    public TelaPrincipal() {
        initComponents();
      setLocationRelativeTo(null); // Centraliza a janela
      
      //progressBar = new javax.swing.JProgressBar();
      progressBar.setStringPainted(true); // Mostra o número (0% - 100%)
      progressBar.setVisible(false); // Só aparece quando usar
      
    }
    //nome titulo do sistema
    String recebe = "";
    String dadosComplementares= "";
    String observacao = "";
    
    
    
    Mensagens mensagem = new Mensagens(recebe, recebe);

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCaminhoArquivo = new javax.swing.JTextField();
        jButtonSelecionar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        JbDadosComplementares = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerenciador Banrisul");
        setResizable(false);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Localizar arquivo TXT");

        txtCaminhoArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCaminhoArquivoActionPerformed(evt);
            }
        });

        jButtonSelecionar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonSelecionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botaoLocalizar.png"))); // NOI18N
        jButtonSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelecionarActionPerformed(evt);
            }
        });

        jLabel1.setText("Dados complementares");

        jButton1.setText("Gerar Arquivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Abrir pasta PDF");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Abrir pasta remessa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        JbDadosComplementares.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0001 - Honorários" }));

        jButton4.setText("Fechar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(JbDadosComplementares, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCaminhoArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCaminhoArquivo)
                    .addComponent(jButtonSelecionar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                .addGap(60, 60, 60)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(JbDadosComplementares, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        txtCaminhoArquivo.setEditable(false);

        jTabbedPane1.addTab("Remessa", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 688, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 471, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Retorno", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        abrirPastaEnvio();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        abrirPasta();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(txtCaminhoArquivo.getText().trim().isEmpty()){
           JOptionPane.showMessageDialog(null, "Arquivo não Selecionado!!!"); 
        }else{
            DadosComplementares();
            executarProcessoComProgresso(); 
        }
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelecionarActionPerformed
        SelecionarArquivo();
    }//GEN-LAST:event_jButtonSelecionarActionPerformed

    private void txtCaminhoArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCaminhoArquivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCaminhoArquivoActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void executarProcessoComProgresso() {
        
        
    progressBar.setVisible(true);
    progressBar.setIndeterminate(true); // Barra animada enquanto carrega

       SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
         @Override
        protected Void doInBackground() throws Exception {
            Gerar(); // Chama o seu processo de gerar arquivos (longo)
            return null;
        }

        @Override
        protected void done() {
            progressBar.setIndeterminate(false);
            progressBar.setValue(100); // Finaliza em 100%
            
            JOptionPane meuJOPane = new JOptionPane("Processo concluído com sucesso!");
            final JDialog dialog = meuJOPane.createDialog(null, "Processo concluído com sucesso!");
            dialog.setModal(true);
            Timer timer = new Timer(1 * 1000, new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                dialog.dispose();  //o evento(no caso fechar o meu JDialog)
            }
        });
        timer.start();
        dialog.setVisible(true);
        timer.stop();
            
            progressBar.setVisible(false); // Some depois
        }
    };

    worker.execute();
   
}
    
    
    
    private void SelecionarArquivo() {
     
    jButtonSelecionar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonSelecionarActionPerformed(evt);
     }
    });
        
    JFileChooser fileChooser = new JFileChooser();
    int resultado = fileChooser.showOpenDialog(this);

    if (resultado == JFileChooser.APPROVE_OPTION) {
        File arquivoSelecionado = fileChooser.getSelectedFile();
        txtCaminhoArquivo.setText(arquivoSelecionado.getAbsolutePath());

        // Ler o conteúdo do arquivo e exibir na área de texto
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arquivoSelecionado));
            StringBuilder conteudo = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                conteudo.append(linha).append("\n");
           
            }
            reader.close();
           // txtConteudo.setText(conteudo.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo: " + e.getMessage());
        }
    }
     
  
    }
    
    private void Gerar(){
        
        RemessaBanrisul remessa = new RemessaBanrisul();
        
        
        recebe = txtCaminhoArquivo.getText();
        remessa.listCliente(recebe);
        
       //JOptionPane.showMessageDialog(this, recebe); 
       
      
    }
    
    public String  DadosComplementares(){
       
        String selecionado = (String) JbDadosComplementares.getSelectedItem();
            dadosComplementares = selecionado;
            //observaçao = Observacoes.getText();
           return dadosComplementares;
           
    }
    
    
    /*
    *Caminho para abrir a pasta do PDF
    */
    public void abrirPasta(){
        //String caminhoPasta = "C:..\\gerenciadorBanrisul\\PDF";
        
        try {
            File pasta = PastaUtil.getCaminhoPasta("PDF");

            if (pasta.exists() && pasta.isDirectory()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(pasta);
               // System.out.println("Pasta aberta com sucesso.");
            } else {
                System.out.println("A pasta não existe ou não é um diretório.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao tentar abrir a pasta: " + e.getMessage());
        }
    }
    
    /*
    *Caminho para abrir a pasta do PDF
    */
    public void abrirPastaEnvio(){
        //String caminhoPasta = "C:..\\gerenciadorBanrisul\\Envio";
        try {
            //File pasta = new File(caminhoPasta);
           File pastaEnvio = PastaUtil.getCaminhoPasta("Envio");
           
            if (pastaEnvio.exists() && pastaEnvio.isDirectory()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(pastaEnvio);
                System.out.println("Pasta aberta com sucesso.");
            } else {
                System.out.println("A pasta não existe ou não é um diretório.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao tentar abrir a pasta: " + e.getMessage());
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
                
                 PastaUtil.criarPastasNoDiretorioDoSistema(Arrays.asList(
                    "Envio",
                    "PDF"

        ));
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> JbDadosComplementares;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonSelecionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JTextField txtCaminhoArquivo;
    // End of variables declaration//GEN-END:variables
}