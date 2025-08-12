package view;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import arquivo.GerarCSV;
import banco.ConexaoBanco;
import banco.CriarTabela;
import conexaoEmail.EnviarEmail;
import control.CarregaDados;
import control.PersisteContato;
import model.Pessoa;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TelaPrincipal {

	private JFrame mainFrame;
	private JTextField nomeTextField;
	private JTextField emailTextField;
	private JTextField instituicaoTextField;
	private Pessoa pessoa;
	private ConexaoBanco conexao;
	private CarregaDados carregaDados;
	private CriarTabela criarTabela;
	private DefaultTableModel modeloTabela;
	private JTable tabela;


	public static void main(String[] args) {
		
		FlatLaf.install(new FlatLightLaf());
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public TelaPrincipal() {
		initialize();
	}


	private void initialize() {
		
		//Conexao Banco
		conexao = new ConexaoBanco();
		criarTabela = new CriarTabela(conexao);
		criarTabela.criarTabela();
		
		//Tela
		mainFrame = new JFrame();
		mainFrame.setSize(800, 600);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setTitle("Cadastro de Contatos");
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		//Menu Section
		JPanel menuPanel = new JPanel();
		menuPanel.setBounds(0, 0, 784, 28);
		
		mainFrame.getContentPane().add(menuPanel);
		menuPanel.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 53, 22);
		menuPanel.add(menuBar);
		
		JMenu menuArquivo = new JMenu("Arquivo");
		menuBar.add(menuArquivo);
		
		JMenuItem menuExportar = new JMenuItem("Exportar contatos");
		menuExportar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				GerarCSV gerar = new GerarCSV(conexao);
				gerar.gerarArquivoCSV();
			}
		});
		menuArquivo.add(menuExportar);
		
		//Logo section
		JPanel logoPanel = new JPanel();
		logoPanel.setBounds(10, 28, 764, 100);
		
		ImageIcon logo = new ImageIcon(getClass().getResource("/logo.png"));
		ImageIcon texto = new ImageIcon(getClass().getResource("/texto.png"));
		
		JLabel logoLabel = new JLabel(logo);
		JLabel textoLabel = new JLabel(texto);
		logoPanel.add(logoLabel);
		logoPanel.add(textoLabel);		
		mainFrame.getContentPane().add(logoPanel);
		
		//Form Section
		JPanel formPanel = new JPanel();
		formPanel.setBounds(114, 136, 562, 171);
		mainFrame.getContentPane().add(formPanel);
		formPanel.setLayout(null);
		
		JLabel nomeLabel = new JLabel("Nome:");
		nomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nomeLabel.setBounds(74, 13, 46, 22);
		formPanel.add(nomeLabel);
		
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		emailLabel.setBounds(74, 42, 46, 22);
		formPanel.add(emailLabel);
		
		JLabel instituicaoLabel = new JLabel("Instituição:");
		instituicaoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		instituicaoLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		instituicaoLabel.setBounds(42, 69, 78, 22);
		formPanel.add(instituicaoLabel);
		
		JLabel interesseLabel = new JLabel("Interesse:");
		interesseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		interesseLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		interesseLabel.setBounds(52, 98, 68, 22);
		formPanel.add(interesseLabel);
		
		nomeTextField = new JTextField();
		nomeTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nomeTextField.setBounds(125, 15, 367, 27);
		formPanel.add(nomeTextField);
		nomeTextField.setColumns(10);
		
		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		emailTextField.setColumns(10);
		emailTextField.setBounds(125, 44, 367, 27);
		formPanel.add(emailTextField);
		
		instituicaoTextField = new JTextField();
		instituicaoTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		instituicaoTextField.setColumns(10);
		instituicaoTextField.setBounds(125, 71, 367, 27);
		formPanel.add(instituicaoTextField);
		
		JComboBox<String> interessesComboBox = new JComboBox<>();
		interessesComboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		interessesComboBox.setBounds(125, 99, 367, 26);
		interessesComboBox.setFocusable(false);
		interessesComboBox.addItem("");
		interessesComboBox.addItem("Acordo de Cooperação");
		interessesComboBox.addItem("Mobilidade");
		interessesComboBox.addItem("Convênio");
		interessesComboBox.addItem("Outros");
		formPanel.add(interessesComboBox);
		
		
		//Table Section
		
		String[] colunas = {"id", "Nome", "Email", "Instituição", "Interesse"};
		modeloTabela = new DefaultTableModel(colunas, 0){
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		carregaDados = new CarregaDados(conexao);
		atualizarTabela();
		
		tabela = new JTable(modeloTabela);
		tabela.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) { // Detecta o duplo clique
		            int row = tabela.rowAtPoint(e.getPoint());
		            if (row >= 0) {
		                int id = (Integer) modeloTabela.getValueAt(row, 0);
		                
		                Pessoa contato;
		                CarregaDados carrega = new CarregaDados(conexao);
		                contato = carrega.buscaPessoaPorId(id);
		                TelaUpdate telaUpdate = new TelaUpdate(contato, telaPrincipal);
		                telaUpdate.initialize();
		                
		            }
		        }
		    }
		});
		
		ajustarColunas(tabela);
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(10, 327, 764, 223);	
		
		mainFrame.getContentPane().add(scrollPane);
		
		
		//Salvar Contato
		JButton salvarButton = new JButton("Salvar");
		salvarButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String nome = nomeTextField.getText();
				String email = emailTextField.getText();
				String instituicao = instituicaoTextField.getText();
				String interesse = interessesComboBox.getSelectedItem().toString();
				
				pessoa = new Pessoa(nome, email, instituicao, interesse);
				
				PersisteContato persistencia = new PersisteContato(conexao);
				persistencia.insereContato(pessoa);
				
				nomeTextField.setText("");
				emailTextField.setText("");
				instituicaoTextField.setText("");
				
				atualizarTabela();
				
				EnviarEmail enviar = new EnviarEmail(pessoa);
				enviar.enviar();

				
			}
		});
		salvarButton.setFocusable(false);
		salvarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		salvarButton.setBounds(245, 137, 89, 23);
		formPanel.add(salvarButton);
		
	}
	
	private void ajustarColunas (JTable tabela) {
		int[] larguras = {40, 199, 200, 170, 149};

        for (int i = 0; i < tabela.getColumnCount(); i++) {
            TableColumn coluna = tabela.getColumnModel().getColumn(i);
            coluna.setPreferredWidth(larguras[i]);
            coluna.setMinWidth(larguras[i]);
            coluna.setMaxWidth(larguras[i]);
            coluna.setResizable(false);
        }
        
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	public void atualizarTabela() {

		modeloTabela.setRowCount(0);
		carregaDados.atualizaTabela(modeloTabela);
	}
	
	TelaPrincipal telaPrincipal = this;

}

