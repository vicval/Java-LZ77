import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

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
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ViewFiltroGaussiana implements ActionListener, ChangeListener {
	ControleFiltros controleFiltros;
	BufferedImage imagemBruta;
	JFrame frame;
	JSlider sliderTamDesvio;
	JSlider sliderTamMascara;
	JSpinner spinnerTamDesvio;
	JSpinner spinnerTamMascara;
	int totalPixels;
	int compacatacaoEmBits;
	double desvio;
	int mascara;

	public ViewFiltroGaussiana(BufferedImage pimagemBruta,
			ControleFiltros pcontroleFiltros) {
		controleFiltros = pcontroleFiltros;
		imagemBruta = pimagemBruta;
		totalPixels = imagemBruta.getHeight() * imagemBruta.getWidth();
		gerarInterface();
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
		mascara = 3;
		desvio = 5;
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		// titulo principal
		JLabel lblTituloPrincipal = new JLabel("Opçoes do filtro gaussiano");
		lblTituloPrincipal.setBounds(80, 30, 230, 15);
		contentPane.add(lblTituloPrincipal);

		// label tamanho do desvio
		JLabel lblTamDesvio = new JLabel("Tamanho do desvio");
		lblTamDesvio.setBounds(80, 83, 188, 15);
		contentPane.add(lblTamDesvio);

		// slider tamanho do desvio
		sliderTamDesvio = new JSlider(1, 10);
		sliderTamDesvio.setBounds(64, 110, 230, 16);
		
		sliderTamDesvio.setValue((int) Math.round(desvio));
		sliderTamDesvio.addChangeListener(this);
		contentPane.add(sliderTamDesvio);

		// mostrador do tamanho do desvio
		spinnerTamDesvio = new JSpinner();
		spinnerTamDesvio.setBounds(313, 106, 75, 20);
		spinnerTamDesvio.setValue(sliderTamDesvio.getValue());
		spinnerTamDesvio.addChangeListener(this);
		contentPane.add(spinnerTamDesvio);

		// label tamanho da mascara
		JLabel label = new JLabel("Tamanho da mascara");
		label.setBounds(80, 154, 188, 15);
		contentPane.add(label);

		// slider tamanho da mascara
		sliderTamMascara = new JSlider(3, 21);
		sliderTamMascara.setBounds(64, 181, 230, 16);
		sliderTamMascara.setValue(mascara);
		sliderTamMascara.addChangeListener(this);
		contentPane.add(sliderTamMascara);

		// mostrador do tamanho da mascara
		spinnerTamMascara = new JSpinner();
		spinnerTamMascara.setBounds(313, 177, 75, 20);
		spinnerTamMascara.setValue(sliderTamMascara.getValue());
		spinnerTamMascara.addChangeListener(this);
		contentPane.add(spinnerTamMascara);

		// separador
		JSeparator separator = new JSeparator();
		separator.setBounds(28, 220, 369, 16);
		contentPane.add(separator);

		// JLabel lblEscolhaACodificacao = new JLabel("Escolha a codificacao");
		// lblEscolhaACodificacao.setBounds(64, 248, 166, 15);
		// contentPane.add(lblEscolhaACodificacao);
		//
		// ButtonGroup grupoDeBotoes = new ButtonGroup();
		// // ButtonGroup grupoDeBotoes = new
		// // grupoDeBotoes.ad
		//
		// rdbtn8Bits = new JRadioButton("8 bits");
		// rdbtn8Bits.setBounds(80, 279, 149, 23);
		// rdbtn8Bits.addActionListener(this);
		// grupoDeBotoes.add(rdbtn8Bits);
		// contentPane.add(rdbtn8Bits);
		//
		// rdbtn16Bits = new JRadioButton("16 bits");
		// rdbtn16Bits.setBounds(80, 306, 149, 23);
		// rdbtn16Bits.addActionListener(this);
		// // rdbtn16Bits.setSelected();
		// grupoDeBotoes.add(rdbtn16Bits);
		// contentPane.add(rdbtn16Bits);
		//
		// rdbtn24Bits = new JRadioButton("24 bits");
		// rdbtn24Bits.setBounds(80, 333, 149, 23);
		// rdbtn24Bits.addActionListener(this);
		// grupoDeBotoes.add(rdbtn24Bits);
		// contentPane.add(rdbtn24Bits);
		//
		// rdbtnOutro = new JRadioButton("Outro:");
		// rdbtnOutro.setBounds(80, 360, 75, 23);
		// rdbtnOutro.addActionListener(this);
		// grupoDeBotoes.add(rdbtnOutro);
		// contentPane.add(rdbtnOutro);
		//
		// // contentPane.add(grupoDeBotoes);
		//
		// // mostrador de bits
		// spinnerBits = new JSpinner();
		// spinnerBits.setBounds(163, 362, 42, 20);
		// spinnerBits.setValue(24);
		// spinnerBits.addChangeListener(this);
		// // spinnerBits.
		// spinnerBits.setEnabled(false);
		//
		// contentPane.add(spinnerBits);
		//
		// // separador
		// JSeparator separator_1 = new JSeparator();
		// separator_1.setBounds(28, 409, 360, 20);
		// contentPane.add(separator_1);

		// // label
		// JLabel lblProgresso = new JLabel("Progresso");
		// lblProgresso.setBounds(64, 427, 91, 15);
		// contentPane.add(lblProgresso);

		// // barra progresso
		// progressBar = new JProgressBar();
		// progressBar.setBounds(175, 427, 148, 14);
		// contentPane.add(progressBar);

		JButton btnComprimir = new JButton("Executar filtro");
		btnComprimir.setBounds(64, 499, 140, 25);
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
			if (source.getText() == "Executar filtro") {
				controleFiltros.executarFiltro(imagemBruta, mascara, desvio);
				// controleCompactacao.setTamBuffer(sliderTamBuffer.getValue());
				// controleCompactacao.setTamJanela(sliderTamJanela.getValue());
				// if (compacatacaoEmBits == 0) {
				// JOptionPane.showMessageDialog(null,
				// "Entre com os bits da compactação");
				// return;
				// } else {
				// controleCompactacao
				// .setCodificacaoEmBits(compacatacaoEmBits);
				// controleCompactacao.comprimirLZ77();
				// }
				frame.dispose();
			}
			if (source.getText() == "Cancelar") {
				frame.dispose();
			}
		}
		if (e.getSource() instanceof JRadioButton) {
			JRadioButton source = (JRadioButton) e.getSource();
			// if (rdbtnOutro.isSelected()) {
			// spinnerBits.setEnabled(true);
			// } else {
			// spinnerBits.setEnabled(false);
			// }
			// if (rdbtn8Bits.isSelected()) {
			// compacatacaoEmBits = 8;
			// }
			// if (rdbtn16Bits.isSelected()) {
			// compacatacaoEmBits = 16;
			// }
			// if (rdbtn24Bits.isSelected()) {
			// compacatacaoEmBits = 24;
			// }

		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {

		if (e.getSource() instanceof JSlider) {
			JSlider source = (JSlider) e.getSource();
			if (source == sliderTamDesvio) {
				spinnerTamDesvio.setValue(source.getValue());
			}
			if (source == sliderTamMascara) {
				spinnerTamMascara.setValue(source.getValue());
			}
		}
		if (e.getSource() instanceof JSpinner) {

			JSpinner source = (JSpinner) e.getSource();
			Integer valor = (Integer) source.getValue();
			if (valor < 1) {
				source.setValue(1);
				valor = 1;
			}
			if (source.equals(spinnerTamMascara)) {

				if (valor >= 3) {
					if ((valor % 2) == 0) {
						if (mascara > valor) {
							valor--;
						} else {
							valor++;
						}
						source.setValue(valor);
						sliderTamMascara.setValue(valor);
						mascara = valor;
					} else {
						source.setValue(valor);
						sliderTamMascara.setValue(valor);
						mascara = valor;
					}
				} else {
					source.setValue(3);
					valor = 3;
					sliderTamMascara.setValue(valor);
					mascara = valor;
				}
				if (valor > totalPixels) {
					source.setValue(totalPixels);
					valor = totalPixels;
					mascara = valor;
				}
			}
			if (source.equals(spinnerTamDesvio)) {
				desvio = valor;
				sliderTamDesvio.setValue(valor);
			}

		}
	}

	public double getDesvio() {
		return desvio;

	}

	public int getTamanhoMascara() {
		return mascara;
	}
}