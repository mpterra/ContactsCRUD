package control;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import banco.ConexaoBanco;

public class ExcluirContato {
	private ConexaoBanco conexao;

	public ExcluirContato() {

	}
	
	public void excluirContato(int id) {
		conexao = new ConexaoBanco();
		conexao.conectar();

		String sql =  "DELETE FROM contato WHERE id = ?";

		PreparedStatement ppst = conexao.criarPreparedStatement(sql);
		System.out.println(ppst.toString());

		try {
			ppst.setInt(1, id);			

			int resultado = ppst.executeUpdate();

			if (resultado == 1) {
				JOptionPane.showMessageDialog(null, "Contato excluído com sucesso!");
				System.out.println("Usuario deletado com sucesso!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ppst != null) {
				try {
					ppst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				conexao.desconectar();
			}	
		}
	
	}
}
