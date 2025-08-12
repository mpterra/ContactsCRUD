package banco;

import java.sql.SQLException;
import java.sql.Statement;

public class CriarTabela {
	
private ConexaoBanco conexao;
	
	public CriarTabela (ConexaoBanco conexao) {
		this.conexao = conexao;
	}
	
	
	public void criarTabela() {
		
		String sql = "CREATE TABLE IF NOT EXISTS contato"
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "nome TEXT NOT NULL,"
				+ "email TEXT NOT NULL,"
				+ "instituicao TEXT NOT NULL,"
				+ "interesse TEXT NOT NULL,"
				+ "enviou INTEGER DEFAULT 0);";
		
		boolean conectou = false;
		
		try {
			conectou = this.conexao.conectar();			
			Statement stmt = this.conexao.criarStatement();			
			stmt.execute(sql);
			System.out.println("Tabela criada com sucesso!!");
			
						
		} catch(SQLException e) {
			System.out.println(""+e);
			
		} finally {
			if(conectou) {
				this.conexao.desconectar();
			}
		}		
		
	}

}
