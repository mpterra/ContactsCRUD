package arquivo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import banco.ConexaoBanco;

public class GerarCSV {

    ConexaoBanco conexao;
    Statement statement;
    ResultSet resultSet;

    public GerarCSV(ConexaoBanco conexao) {
        this.conexao = conexao;
    }

    public void gerarArquivoCSV() {

        try {
            // Conexão com o banco de dados SQLite
            conexao.conectar();
            statement = conexao.criarStatement();

            // Consulta SQL para obter os dados da tabela
            String query = "SELECT * FROM contato";  // Substitua "contato" pelo nome real da tabela
            resultSet = statement.executeQuery(query);

            // Abre uma janela para o usuário escolher onde salvar o arquivo CSV
            JFileChooser fileChooser = new JFileChooser();

            // Sugere o diretório Desktop
            File desktopDir = new File(System.getProperty("user.home"), "Desktop");
            fileChooser.setCurrentDirectory(desktopDir);

            fileChooser.setDialogTitle("Salvar arquivo CSV");

            // Define o nome sugerido para o arquivo
            fileChooser.setSelectedFile(new File("contatosEAIE.csv"));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                // Verifica se o arquivo já existe
                if (fileToSave.exists()) {
                    // Pergunta ao usuário se deseja sobrescrever o arquivo
                    int overwriteOption = JOptionPane.showConfirmDialog(null,
                            "O arquivo já existe. Deseja sobrescrevê-lo?",
                            "Arquivo existente",
                            JOptionPane.YES_NO_OPTION);

                    if (overwriteOption == JOptionPane.NO_OPTION) {
                        // Se o usuário escolher não sobrescrever, sai do método
                        System.out.println("Operação cancelada pelo usuário.");
                        return;
                    }
                }

                // Escreve os dados no arquivo CSV com codificação UTF-8
                try (Writer csvWriter = new OutputStreamWriter(new FileOutputStream(fileToSave), "UTF-8")) {
                    // Escrever cabeçalhos da tabela
                    csvWriter.write("id,nome,email,instituicao,interesse,enviou\n");

                    // Escrever linhas da tabela
                    while (resultSet.next()) {
                        csvWriter.write(resultSet.getString("id") + ",");
                        csvWriter.write(resultSet.getString("nome") + ",");
                        csvWriter.write(resultSet.getString("email") + ",");
                        csvWriter.write(resultSet.getString("instituicao") + ",");
                        csvWriter.write(resultSet.getString("interesse") + ",");
                        csvWriter.write(resultSet.getInt("enviou") + "\n");
                    }

                    System.out.println("CSV gerado com sucesso em: " + fileToSave.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conexao != null) conexao.desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
