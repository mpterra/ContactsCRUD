package control;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import banco.ConexaoBanco;
import model.Pessoa;

public class AtualizarContato {
	
	private ConexaoBanco conexao;
	private Pessoa contato;

	public AtualizarContato() {

	}

	@SuppressWarnings("finally")
	public boolean atualizaContato(Pessoa contato) {

		this.contato = contato;
		conexao = new ConexaoBanco();
		conexao.conectar();
		boolean inseriu = false;

		String sql = "UPDATE contato "
						+ "SET nome = ?, "
						+ "email = ?,"
						+ "instituicao = ?, "
						+ "interesse = ? "
					+ "WHERE id = ?;";

		PreparedStatement ppst = conexao.criarPreparedStatement(sql);
		System.out.println(ppst.toString());

		try {
			ppst.setString(1, this.contato.getNome());
			ppst.setString(2, this.contato.getEmail());
			ppst.setString(3, this.contato.getInstituicao());
			ppst.setString(4, this.contato.getInteresse());
			ppst.setInt(5, this.contato.getId());			

			int resultado = ppst.executeUpdate();

			if (resultado == 1) {
				inseriu = true;
				System.out.println("Usuario atualizado com sucesso!");
				JOptionPane.showMessageDialog(null, "Contato atualizado com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				inseriu = false;
				System.out.println("Entrou no else");
			}
		} catch (SQLException e) {
			inseriu = false;
			e.printStackTrace();
			System.out.println("Entrou no exception");
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

	public void atualizarEmailEnviado(String email) {
		
		conexao = new ConexaoBanco();
		conexao.conectar();

		String sql =  "UPDATE contato "
					+ "SET enviou = 1 "
					+ "WHERE email = ?;";

		PreparedStatement ppst = conexao.criarPreparedStatement(sql);
		System.out.println(ppst.toString());

		try {
			ppst.setString(1, email);			

			int resultado = ppst.executeUpdate();

			if (resultado == 1) {
				System.out.println("Usuario atualizado com sucesso!");
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
	
public void atualizarEmailNaoEnviado(String email) {
		
		conexao = new ConexaoBanco();
		conexao.conectar();

		String sql =  "UPDATE contato "
					+ "SET enviou = 0 "
					+ "WHERE email = ?;";

		PreparedStatement ppst = conexao.criarPreparedStatement(sql);
		System.out.println(ppst.toString());

		try {
			ppst.setString(1, email);			

			int resultado = ppst.executeUpdate();

			if (resultado == 1) {
				System.out.println("Usuario atualizado com sucesso!");
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
