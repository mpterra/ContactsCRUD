package view;

import javax.swing.JFrame;

import model.Pessoa;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import conexaoEmail.EnviarEmail;
import control.AtualizarContato;
import control.ExcluirContato;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;

public class TelaUpdate extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nomeField;
	private JTextField emailField;
	private JTextField instituicaoField;
	private Pessoa contato;
	private TelaPrincipal telaPrincipal;
	
	public TelaUpdate (Pessoa contato, TelaPrincipal telaPrincipal) {
		this.contato = contato;
		this.telaPrincipal = telaPrincipal;
	}
	
	public void initialize() {
		
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setTitle("Atualizar Contato");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JPanel formPanel = new JPanel();
		formPanel.setLayout(null);
		formPanel.setBounds(10, 11, 464, 239);
		getContentPane().add(formPanel);
		
		JLabel nomeLabel = new JLabel("Nome:");
		nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nomeLabel.setBounds(32, 31, 46, 22);
		formPanel.add(nomeLabel);
		
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		emailLabel.setBounds(32, 60, 46, 22);
		formPanel.add(emailLabel);
		
		JLabel instituicaoLabel = new JLabel("Instituição:");
		instituicaoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		instituicaoLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		instituicaoLabel.setBounds(0, 87, 78, 22);
		formPanel.add(instituicaoLabel);
		
		JLabel interesseLabel = new JLabel("Interesse:");
		interesseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		interesseLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		interesseLabel.setBounds(10, 116, 68, 22);
		formPanel.add(interesseLabel);
		
		nomeField = new JTextField();
		nomeField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nomeField.setColumns(10);
		nomeField.setBounds(83, 33, 367, 27);
		formPanel.add(nomeField);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		emailField.setColumns(10);
		emailField.setBounds(83, 62, 367, 27);
		formPanel.add(emailField);
		
		instituicaoField = new JTextField();
		instituicaoField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		instituicaoField.setColumns(10);
		instituicaoField.setBounds(83, 89, 367, 27);
		formPanel.add(instituicaoField);
		
		JComboBox<String> interessesComboBox = new JComboBox<>();
		interessesComboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		interessesComboBox.setBounds(83, 116, 367, 27);
		interessesComboBox.setFocusable(false);
		interessesComboBox.addItem("");
		interessesComboBox.addItem("Acordo de Cooperação");
		interessesComboBox.addItem("Mobilidade");
		interessesComboBox.addItem("Convênio");
		interessesComboBox.addItem("Outros");
		formPanel.add(interessesComboBox);
		
		nomeField.setText(contato.getNome());
		emailField.setText(contato.getEmail());
		instituicaoField.setText(contato.getInstituicao());
		
		TelaUpdate telaUpdate = this;
		
		
		//botao de atualizar
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAtualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				contato.setNome(nomeField.getText());
				contato.setEmail(emailField.getText());
				contato.setInstituicao(instituicaoField.getText());
				contato.setInteresse(interessesComboBox.getSelectedItem().toString());
				
				AtualizarContato atualizar = new AtualizarContato();
				atualizar.atualizaContato(contato);
				
				EnviarEmail enviar = new EnviarEmail(contato);
				enviar.enviar();
				
				telaPrincipal.atualizarTabela();
				telaUpdate.dispose();
			}
		});
		btnAtualizar.setFocusable(false);
		btnAtualizar.setBounds(135, 156, 89, 23);
		formPanel.add(btnAtualizar);
		this.setVisible(true);
		
		//bnotao de excluir
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
								
				ExcluirContato excluir = new ExcluirContato();
				excluir.excluirContato(contato.getId());
				
				telaPrincipal.atualizarTabela();
				telaUpdate.dispose();
			}
		});
		btnExcluir.setFocusable(false);
		btnExcluir.setBounds(280, 156, 89, 23);
		btnExcluir.setBackground(new Color(200,0,50));
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnExcluir.setForeground(Color.white);
		formPanel.add(btnExcluir);
		this.setVisible(true);
		
		
		
	}
}
