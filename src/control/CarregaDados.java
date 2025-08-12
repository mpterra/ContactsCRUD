package control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import banco.ConexaoBanco;
import model.Pessoa;

public class CarregaDados {

	private ConexaoBanco conexao;

	public CarregaDados(ConexaoBanco conexao) {
		this.conexao = conexao;
	}

	public void atualizaTabela(DefaultTableModel tabela) {
		
		tabela.setRowCount(0);
		
		conexao.conectar();
		
		Statement stmt = conexao.criarStatement();
		String sql = "SELECT id, nome, email, instituicao, interesse, enviou FROM contato";
		
        try {
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String instituicao = rs.getString("instituicao");
                String interesse = rs.getString("interesse");
                int enviou = rs.getInt("enviou");
                tabela.addRow(new Object[]{id, nome, email, instituicao, interesse, enviou});
            }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conexao.desconectar();
		}
	}

	public Pessoa buscaPessoaPorId(int id) {
		
		Pessoa encontrado = new Pessoa();
		
		conexao.conectar();
		String sql = "SELECT * FROM contato WHERE id = ?";
		PreparedStatement ppst = conexao.criarPreparedStatement(sql);
		
		try {
			ppst.setInt(1, id);
			ResultSet rs = ppst.executeQuery();
			encontrado.setId(rs.getInt("id"));
			encontrado.setNome(rs.getString("nome"));
			encontrado.setEmail(rs.getString("email"));
			encontrado.setInstituicao(rs.getString("instituicao"));
			encontrado.setInteresse(rs.getString("interesse"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(conexao != null) {
				conexao.desconectar();
			}
		}
		
		return encontrado;
	}

}
