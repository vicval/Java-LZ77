import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author Vicente F. Valcarcel
 *
 */

public class ViewCompressao implements ActionListener, ChangeListener {
	ControleCompactacao controleCompactacao;
	JFrame frame;
	JSlider sliderTamJanela;
	JSlider sliderTamBuffer;
	JSpinner spinnerTamJanela;
	JSpinner spinnerTamBuffer;
	JRadioButton rdbtn8Bits;
	JRadioButton rdbtn16Bits;
	JRadioButton rdbtn24Bits;
	JRadioButton rdbtnOutro;
	JProgressBar progressBar;
	JSpinner spinnerBits;
	int totalPixels;
	int compacatacaoEmBits;

	public ViewCompressao(ControleCompactacao pcontroleCompactacao,
			int ptotalPixels) {
		totalPixels = ptotalPixels;
		controleCompactacao = pcontroleCompactacao;
	}

	public int gerarInterface() {
		frame = new JFrame("Menu");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel painel = painel(totalPixels);
		frame.setBounds(100, 100, 440, 594);
		frame.add(painel);
		frame.setVisible(true);
		return 0;
	}

	private JPanel painel(int ptotalPixels) {
		totalPixels = ptotalPixels;
		JPanel contentPane = new JPanel();
		// frame.add(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		// titulo principal
		JLabel lblTituloPrincipal = new JLabel("Opçoes de Compressão");
		lblTituloPrincipal.setBounds(128, 30, 178, 15);
		contentPane.add(lblTituloPrincipal);

		// label tamanho Janela
		JLabel lblTamJanela = new JLabel("Tamanho da Janela");
		lblTamJanela.setBounds(80, 83, 188, 15);
		contentPane.add(lblTamJanela);

		// slider tamanho da janela
		sliderTamJanela = new JSlider(1, totalPixels);
		sliderTamJanela.setBounds(64, 110, 230, 16);
		sliderTamJanela.addChangeListener(this);
		contentPane.add(sliderTamJanela);

		// mostrador do tamanho da janela
		spinnerTamJanela = new JSpinner();
		spinnerTamJanela.setBounds(313, 106, 75, 20);
		spinnerTamJanela.setValue(sliderTamJanela.getValue());
		spinnerTamJanela.addChangeListener(this);
		contentPane.add(spinnerTamJanela);

		// label tamanho Buffer
		JLabel label = new JLabel("Tamanho do Buffer");
		label.setBounds(80, 154, 188, 15);
		contentPane.add(label);

		// slider tamanho do buffer
		sliderTamBuffer = new JSlider(1, totalPixels);
		sliderTamBuffer.setBounds(64, 181, 230, 16);
		sliderTamBuffer.addChangeListener(this);
		contentPane.add(sliderTamBuffer);

		// mostrador do tamanho de Buffer
		spinnerTamBuffer = new JSpinner();
		spinnerTamBuffer.setBounds(313, 177, 75, 20);
		spinnerTamBuffer.setValue(sliderTamBuffer.getValue());
		spinnerTamBuffer.addChangeListener(this);
		contentPane.add(spinnerTamBuffer);

		// separador
		JSeparator separator = new JSeparator();
		separator.setBounds(28, 220, 369, 16);
		contentPane.add(separator);

//		JLabel lblEscolhaACodificacao = new JLabel("Escolha a codificacao");
//		lblEscolhaACodificacao.setBounds(64, 248, 166, 15);
//		contentPane.add(lblEscolhaACodificacao);
//
//		ButtonGroup grupoDeBotoes = new ButtonGroup();
//		// ButtonGroup grupoDeBotoes = new
//		// grupoDeBotoes.ad
//
//		rdbtn8Bits = new JRadioButton("8 bits");
//		rdbtn8Bits.setBounds(80, 279, 149, 23);
//		rdbtn8Bits.addActionListener(this);
//		grupoDeBotoes.add(rdbtn8Bits);
//		contentPane.add(rdbtn8Bits);
//
//		rdbtn16Bits = new JRadioButton("16 bits");
//		rdbtn16Bits.setBounds(80, 306, 149, 23);
//		rdbtn16Bits.addActionListener(this);
//		// rdbtn16Bits.setSelected();
//		grupoDeBotoes.add(rdbtn16Bits);
//		contentPane.add(rdbtn16Bits);
//
//		rdbtn24Bits = new JRadioButton("24 bits");
//		rdbtn24Bits.setBounds(80, 333, 149, 23);
//		rdbtn24Bits.addActionListener(this);
//		grupoDeBotoes.add(rdbtn24Bits);
//		contentPane.add(rdbtn24Bits);
//
//		rdbtnOutro = new JRadioButton("Outro:");
//		rdbtnOutro.setBounds(80, 360, 75, 23);
//		rdbtnOutro.addActionListener(this);
//		grupoDeBotoes.add(rdbtnOutro);
//		contentPane.add(rdbtnOutro);
//
//		// contentPane.add(grupoDeBotoes);
//
//		// mostrador de bits
//		spinnerBits = new JSpinner();
//		spinnerBits.setBounds(163, 362, 42, 20);
//		spinnerBits.setValue(24);
//		spinnerBits.addChangeListener(this);
//		// spinnerBits.
//		spinnerBits.setEnabled(false);
//
//		contentPane.add(spinnerBits);

		// separador
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(28, 409, 360, 20);
		contentPane.add(separator_1);

//		// label
//		JLabel lblProgresso = new JLabel("Progresso");
//		lblProgresso.setBounds(64, 427, 91, 15);
//		contentPane.add(lblProgresso);
//		
//		// barra progresso
//		progressBar = new JProgressBar();
//		progressBar.setBounds(175, 427, 148, 14);
//		progressBar.setStringPainted(true);
//		contentPane.add(progressBar);
		
		JButton btnComprimir = new JButton("Comprimir");
		btnComprimir.setBounds(64, 499, 117, 25);
		btnComprimir.addActionListener(this);
		contentPane.add(btnComprimir);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(248, 499, 117, 25);
		btnCancelar.addActionListener(this);
		contentPane.add(btnCancelar);

		return contentPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof JButton) {
			JButton source = (JButton) e.getSource();
			if (source.getText() == "Comprimir") {
				controleCompactacao.setTamBuffer(sliderTamBuffer.getValue());
				controleCompactacao.setTamJanela(sliderTamJanela.getValue());
					int resp =controleCompactacao.comprimirLZ77();
					frame.dispose();
//				controleCompactacao.teste();

			}
			if (source.getText() == "Cancelar") {
				frame.dispose();
			}
		}
		if (e.getSource() instanceof JRadioButton) {
			JRadioButton source = (JRadioButton) e.getSource();
			if (rdbtnOutro.isSelected()) {
				spinnerBits.setEnabled(true);
			} else {
				spinnerBits.setEnabled(false);
			}
			if (rdbtn8Bits.isSelected()) {
				compacatacaoEmBits = 8;
			}
			if (rdbtn16Bits.isSelected()) {
				compacatacaoEmBits = 16;
			}
			if (rdbtn24Bits.isSelected()) {
				compacatacaoEmBits = 24;
			}

		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {

		if (e.getSource() instanceof JSlider) {
			JSlider source = (JSlider) e.getSource();
			if (source == sliderTamJanela) {
				spinnerTamJanela.setValue(source.getValue());
			}
			if (source == sliderTamBuffer) {
				spinnerTamBuffer.setValue(source.getValue());
			}
		}
		if (e.getSource() instanceof JSpinner) {

			JSpinner source = (JSpinner) e.getSource();
			Integer valor = (Integer) source.getValue();
			if (valor < 1) {
				source.setValue(1);
				valor = 1;
			}
			if (valor > totalPixels) {
				source.setValue(totalPixels);
				valor = totalPixels;
			}

			if (source == spinnerTamJanela) {
				if (valor <= totalPixels && valor > 0) {
					sliderTamJanela.setValue(valor);
				}
			}
			if (source == spinnerTamBuffer) {
				if (valor <= totalPixels && valor > 0) {
					sliderTamBuffer.setValue(valor);
				}
			}
			if (source == spinnerBits) {
				Integer valorAnterior = (Integer) source.getPreviousValue();
				if (valorAnterior < valor) {
					// aumentou o valor
					if (64 >= valor && valor > 1) {
						if (valor % 2 == 0) {
							// spinnerBits.setValue(valor );
						} else {
							valor++;
							spinnerBits.setValue(valor);

						}
					}else{
						valor--;
						spinnerBits.setValue(valor);
					}
				} else {
					//diminuiu o valor
					if (64 >= valor && valor > 1) {
						if (valor % 2 == 0) {
							// spinnerBits.setValue(valor );
						} else {
							valor--;
							spinnerBits.setValue(valor);

						}
					}else{
						valor++;
						spinnerBits.setValue(valor);
					}
				}

			}
		}
	}
	public void alterarJmenuBar(int valor) {
		progressBar.setValue(valor);
		//progressBar.getParent().repaint();
		frame.repaint();
	}

	
}
