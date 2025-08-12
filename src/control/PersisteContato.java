package control;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import banco.ConexaoBanco;
import model.Pessoa;

public class PersisteContato {

	private ConexaoBanco conexao;
	private Pessoa pessoa;

	public PersisteContato(ConexaoBanco conexao) {
		this.conexao = conexao;
	}

	@SuppressWarnings("finally")
	public boolean insereContato(Pessoa pessoa) {

		this.pessoa = pessoa;
		conexao.conectar();
		boolean inseriu = false;

		String sql = "INSERT INTO contato (id, nome, email, instituicao, interesse) VALUES (NULL, ?, ?, ?, ?);";

		PreparedStatement ppst = conexao.criarPreparedStatement(sql);

		try {
			ppst.setString(1, this.pessoa.getNome());
			ppst.setString(2, this.pessoa.getEmail());
			ppst.setString(3, this.pessoa.getInstituicao());
			ppst.setString(4, this.pessoa.getInteresse());

			int resultado = ppst.executeUpdate();

			if (resultado == 1) {
				inseriu = true;
				System.out.println("Usuario cadastrado com sucesso!");
				JOptionPane.showMessageDialog(null, "Contato cadastrado com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				inseriu = false;
				System.out.println("Erro na inserção!");
			}
		} catch (SQLException e) {
			inseriu = false;
			System.out.println("Erro na inserção");
		} finally {
			if (ppst != null) {
				try {
					ppst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				conexao.desconectar();
			}

			return inseriu;

		}

	}

}
