package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {

	private Connection conexao;

	public boolean conectar() {

		try {

			String url = "jdbc:sqlite:banco_de_dados.db";
			this.conexao = DriverManager.getConnection(url);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}

		System.out.println("conectou!!!");

		return true;
	}

	public boolean desconectar() {

		try {
			if (this.conexao.isClosed() == false) {
				this.conexao.close();
			}

		} catch (SQLException e) {

			System.err.println(e.getMessage());
			return false;
		}
		System.out.println("desconectou!!!");
		return true;

	}

	public Statement criarStatement() {

		try {
			return this.conexao.createStatement();
		} catch (SQLException e) {
			return null;
		}

	}

	public PreparedStatement criarPreparedStatement(String sql) {

		try {
			return this.conexao.prepareStatement(sql);
		} catch (SQLException e) {
			return null;
		}

	}

	public Connection getConexao() {
		return this.conexao;
	}

}
