import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author Vicente F. Valcarcel
 *
 */
public class View implements ActionListener {
	private JLabel labelDaImagem;
	private BufferedImage imagemBuferizada = null;
	private Filtros filtros = new Filtros();
	private Controle ctrl;

	/**
	 * Construtor da View, responsavel por criar a interface
	 */
	public View(Controle pctrl) {
		ctrl = pctrl;
		JFrame frame = new JFrame("Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(this.createMenuBar());

		labelDaImagem = new JLabel();
		frame.getContentPane().add(labelDaImagem, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		// frame
		frame.setSize(750, 600);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

	/**
	 * Metodo cria JMenuBar e os submenus
	 * 
	 * @return JMenuBar
	 */
	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;

		menuBar = new JMenuBar();

		menu = new JMenu("Arquivo");
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);

		menuItem = new JMenuItem("Nova Imagem", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Salvar");
		menuItem.setMnemonic(KeyEvent.VK_D);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Sair");
		menuItem.setMnemonic(KeyEvent.VK_D);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu = new JMenu("Modificar");
		menu.setMnemonic(KeyEvent.VK_N);

		submenu = new JMenu("Gerar Ruidos");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("Ruido sal");
		menuItem.addActionListener(this);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.SHIFT_MASK));
		submenu.add(menuItem);

		menuItem = new JMenuItem("Ruido pimenta");
		menuItem.addActionListener(this);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.SHIFT_MASK));
		submenu.add(menuItem);

		menu.add(submenu);

		menu.addSeparator();
		submenu = new JMenu("Filtro");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("Filtro gaussiana");
		menuItem.addActionListener(this);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Filtro mediana");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
		menu.add(submenu);

		menuItem = new JMenuItem("Escala de Cinza");
		menuItem.setMnemonic(KeyEvent.VK_D);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);

		menuItem = new JMenuItem("método de compressão LZ77");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);
		/**
		 * Retorna o menu JBAr pronto para ser inserido na janela
		 */
		return menuBar;
	}

	/**
	 * metodo gerencia a ativacao do JMenuBar atraves do mouse
	 */
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		System.out.println(source.getText() + "|");
		if (source.getText() == "Nova Imagem") {
			ctrl.abrirImagem();

		}
		if (source.getText() == "Salvar") {
			ctrl.salvarImagem();

		}
		if (source.getText() == "Sair") {
			ctrl.sairDoPrograma();

		}
		if (source.getText() == "Ruido sal") {
			ctrl.executaRuidoSal();

		}
		if (source.getText() == "Ruido pimenta") {
			ctrl.executaRuidoPimenta();

		}
		if (source.getText() == "Filtro gaussiana") {
			ctrl.executarFiltroGaussianno();

		}
		if (source.getText() == "Filtro mediana") {
			// BufferedImage nova = mediana(imagemBuferizada, 3);
			// setImagem(nova);
			// labelDaImagem.getParent().repaint();
		}
		if (source.getText() == "Escala de Cinza") {
			ctrl.executaEscalaDeCinza();
			executaEscalaDeCinza();
		}
		/**
		 * Executa o metodo de compressao LZ77
		 */
		if (source.getText() == "método de compressão LZ77") {
			ctrl.compactarEsalvar();
		}

	}

	// private void janelaDecisaoCompactacaoLZ77Imagem(int i, int j,
	// BufferedImage imagemBuferizada2) {
	// JFrame frame = new JFrame("Menu Compactação");
	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// //frame.setJMenuBar(this.createMenuBar());
	//
	// labelDaImagem = new JLabel();
	// frame.getContentPane().add(labelDaImagem, BorderLayout.CENTER);
	// //frame.pack();
	// // RefineryUtilities ccc= new
	// // RefineryUtilities.centerFrameOnScreen(nomedoframe);
	// frame.setLocationRelativeTo(null);
	// // frame
	// frame.setSize(300, 400);
	// //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	// frame.setVisible(true);
	//
	// }

	/**
	 * metodo executa ruido sal, pintando pixels randomicamente de branco
	 */
	private void executaRuidoSal() {
		Graphics g = imagemBuferizada.getGraphics();
		g.setColor(Color.white);

		Random gerador = new Random();

		for (int i = 0; i < imagemBuferizada.getHeight() * 0.3; i++) {
			for (int j = 0; j < imagemBuferizada.getWidth() * 0.3; j++) {
				int x_pixel = gerador.nextInt(imagemBuferizada.getWidth());
				int y_pixel = gerador.nextInt(imagemBuferizada.getHeight());
				g.drawLine(x_pixel, y_pixel, x_pixel, y_pixel);
			}
		}

		g.dispose();
		labelDaImagem.getParent().repaint();

	}

	/**
	 * metodo executa ruido sal, pintando pixels randomicamente de preto
	 */
	private void executaRuidoPimenta() {
		Graphics g = imagemBuferizada.getGraphics();
		g.setColor(Color.black);

		Random gerador = new Random();

		for (int i = 0; i < imagemBuferizada.getHeight() * 0.3; i++) {
			for (int j = 0; j < imagemBuferizada.getWidth() * 0.3; j++) {
				int x_pixel = gerador.nextInt(imagemBuferizada.getWidth());
				int y_pixel = gerador.nextInt(imagemBuferizada.getHeight());
				g.drawLine(x_pixel, y_pixel, x_pixel, y_pixel);
			}
		}

		g.dispose();
		labelDaImagem.getParent().repaint();

	}

	/**
	 * transforma a imagem em escala de cinza
	 */
	private void executaEscalaDeCinza() {

		BufferedImage imagemNova = new BufferedImage(
				imagemBuferizada.getWidth(), imagemBuferizada.getHeight(),
				BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = imagemNova.getGraphics();
		g.drawImage(imagemBuferizada, 0, 0, null);
		g.dispose();
		setImagem(imagemNova);

	}

	/**
	 * abre a imagem
	 */
	public void abrirImagem() {
		JFileChooser fc = new JFileChooser();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int returnVal = fc.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			System.out.println(file);
			String enderecoArquivo = file.toString();

			try {
				imagemBuferizada = ImageIO.read(new File(enderecoArquivo));
				setImagem(imagemBuferizada);
			} catch (Exception e) {
				// Erro ao abrir a imagem
				JOptionPane.showMessageDialog(null,
						"Erro! Formato não reconhecido!", "Erro de abertura",
						JOptionPane.ERROR_MESSAGE, null);
				e.printStackTrace();
				// System.exit(1);
			}
		}
	}

	/**
	 * Salva imagem comprimida ESTA EM TESTE AINDA E POSSIVELMENTE ESTA CLASSE
	 * VAI DESAPARECER
	 */
	public void salvarImagemComprimidaEmByte(int tamJanela, int tamBuffer,
			ArrayList<Object> tuplas) {
		System.out.println("salvarImagemComprimidaEmByte");
		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		fc.setMultiSelectionEnabled(false);
		FileFilter wordType = new FileNameExtensionFilter(
				"Bitmap image file (.bmp)", "bmp");
		fc.addChoosableFileFilter(wordType);
		fc.setSelectedFile(new File("nomeImagem"));
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int returnVal = fc.showSaveDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (imagemBuferizada == null) {
				JOptionPane.showMessageDialog(null,
						"Não existe mensagem a ser salva");
				return;
			}
			File file = fc.getSelectedFile();
			// System.out.println(file);
			// System.out.println(file.getName());
			String enderecoArquivo = file.toString();

			try {
				imprimeBinario2(tamJanela, tamBuffer, tuplas, enderecoArquivo);
				// imprimeBinarioComflagZero(tamJanela, tamBuffer, tuplas,
				// enderecoArquivo);

			} catch (Exception e) {
				e.printStackTrace();
				//System.exit(1);
			}
		}
	}

	@Deprecated
	public void imprimeBinario(int tamJanela, int tamBuffer,
			ArrayList<Object> tuplas, String endereco) throws IOException {
		// System.out.println("imprimeBinario");
		// String[] vetor = tuplas.split(",");
		// DataOutputStream arquivoBinarioUTF = new DataOutputStream(
		// new FileOutputStream("SaidaUTF.data"));
		// // arquivoBinario.writeUTF("uf");
		// for (int i = 0; i < vetor.length; i++) {
		// arquivoBinarioUTF.writeUTF(vetor[i]);
		// }
		// arquivoBinarioUTF.close();
		// //========================================
		// //TEM LIMITACAO DE TAMANHO, POIS O INT SÓ CHEGA ATÉ UM NIVEL
		// DataOutputStream arquivoBinarioInt = new DataOutputStream(
		// new FileOutputStream("SaidaiNT.data"));
		// for (int i = 0; i < vetor.length; i++) {
		// int nro =Integer.parseInt(vetor[i]);
		// arquivoBinarioInt.writeInt(nro);
		// }
		// arquivoBinarioInt.close();
		// //=======================================
		// DataOutputStream arquivoBinarioNormal = new DataOutputStream(
		// new FileOutputStream("SaidaBinarioNormal.data"));
		// for (int i = 0; i < vetor.length; i++) {
		// int nro =Integer.parseInt(vetor[i]);
		// arquivoBinarioInt.write(nro);
		// }
		// arquivoBinarioInt.close();
		// DataOutputStream arquivoBinarioUTF = new DataOutputStream(
		// new FileOutputStream("SaidaUTF.data"));
		DataOutputStream arquivoBinarioInt = new DataOutputStream(
				new FileOutputStream(endereco + "binariInt.data"));
		DataOutputStream arquivoBinarioNormal = new DataOutputStream(
				new FileOutputStream(endereco + "BinarioNormal.data"));
		int casaBinarias = 1;
		if (tuplas.size() > 255 && tamJanela > 255 || tuplas.size() > 255
				&& tamBuffer > 255) {
			// como a instrucao que escreve em binario normal (write) vai até
			// 255
			// no maximo,então temos que tratar uma imagem que tenha mais de 255
			// pixels
			// e uma janela que tenha mais de 255 pixels.
			// Não tem necidade de tratar
			// uma condicao sem a outra, pois se a imagem tem mais de 255 pixels
			// porem não tem uma janela maior que esse valor, as tuplas de
			// compressao
			// nunca sairam com mais de 255 em nenhuma das tres posicoes, ja que
			// nao
			// tem como voltar mais que 255 posicoes.Porem o buffer pode ser
			// maior que
			// 255 e pode repetir o padrao da janela mais de uma vez, entao
			// existe a necessidade
			// de tratar isso tambem.
			// Repare, que para necessitar tratar isso, primeiro é necessario
			// ver
			// se o arquivo tem mais de 255 tuplas, pois sem isso não ha
			// necessidade de tratamento,
			// caso tenha.Ai será necessario ver se ele satisfaz os outros dois
			// criterios
			// O buffer é maior que 255 ou a janela é maior que 255

			// A atribuição abaixo tem um acrescentado pois o tamanho da uma
			// imagem pode ser
			// 256, o que é maior que 255, porem sem essa soma de 1 as variaveis
			// nroCasaBinarias1
			// e nroCasaBinarias2 seria igual a 1,o que impediria o algoritmo de
			// escrever 256 em binario, pelo problema acima representado
			// é necessario fazer as duas verificacoes pois as tuplas podem
			// voltar
			// até o final da janela e ir ate o final do buffer
			int nroCasaBinarias1 = (tamJanela / 255) + 1;
			int nroCasaBinarias2 = (tamBuffer / 255) + 1;
			if (nroCasaBinarias1 > nroCasaBinarias2) {
				casaBinarias = nroCasaBinarias1;
			} else {
				casaBinarias = nroCasaBinarias2;
			}

		}
		for (int i = 0; i < tuplas.size() - 1; i++) {
			if (tuplas.get(i) instanceof Color) {
				Color cor = (Color) tuplas.get(i);
				arquivoBinarioInt.writeInt(cor.getRed());
				arquivoBinarioNormal.write(cor.getRed());
				// System.out.println(cor.getRed());

				arquivoBinarioInt.writeInt(cor.getGreen());
				arquivoBinarioNormal.write(cor.getGreen());
				// System.out.println(cor.getGreen());

				arquivoBinarioInt.writeInt(cor.getBlue());
				arquivoBinarioNormal.write(cor.getBlue());
				// System.out.println(cor.getBlue());
			} else {
				// int nro = Integer.parseInt((String) tuplas.get(i)+"");
				arquivoBinarioInt.writeInt((int) tuplas.get(i));// int data o
																// nro max é
																// 2.147.483.647

				// //3 casas binarias
				// //numero q queroo representar é 520
				// //vai ficar=2.03
				// //=10 0000 1000 =
				//
				// int quociente = ((int)tuplas.get(i))/255;
				// int resto = ((int)tuplas.get(i))% 255;
				// for (int j = 0; j < casaBinarias; j++) {
				// if(j+1>=casaBinarias){
				// //ultima iteracao
				// arquivoBinarioNormal.write((int) resto);
				// }else{
				// if(casaBinarias-j<=quociente){
				// arquivoBinarioNormal.write(255);
				// quociente--;
				// }
				// }
				//
				//
				// }

				arquivoBinarioNormal.write((int) tuplas.get(i));// pode dar erro
																// pois só pode
																// inserir ate
																// 255

				// System.out.println(tuplas.get(i));

			}

		}
		arquivoBinarioInt.close();
		arquivoBinarioNormal.close();
	}

	/**
 * 
 */
	private void salvarImagem() {
		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		fc.setMultiSelectionEnabled(false);
		FileFilter filtro1 = new FileNameExtensionFilter(
				"Portable Network Graphics (.png)", "png");
		FileFilter filtro2 = new FileNameExtensionFilter(
				"Joint Photographic Experts Group (.jpg)", "jpg");
		// FileFilter filtro3 = new
		// FileNameExtensionFilter("Bitmap image file (.bmp)", "bmp");
		fc.addChoosableFileFilter(filtro1);
		fc.addChoosableFileFilter(filtro2);
		// fc.addChoosableFileFilter(filtro3);
		fc.setSelectedFile(new File("nomeImagem"));
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int returnVal = fc.showSaveDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (imagemBuferizada == null) {
				JOptionPane.showMessageDialog(null,
						"Não existe mensagem a ser salva");
				return;
			}
			File file = fc.getSelectedFile();
			System.out.println(file);
			System.out.println(file.getName());
			String enderecoArquivo = file.toString();

			try {
				System.out.println(fc.getFileFilter());
				if (fc.getFileFilter() == filtro1) {
					ImageIO.write(imagemBuferizada, "PNG", new File(file
							+ ".png"));
				}
				if (fc.getFileFilter() == filtro2) {
					ImageIO.write(imagemBuferizada, "JPG", new File(file
							+ ".jpg"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				// System.exit(1);
			}
		}
	}

	/**
	 * seta a imagem que será visualizada
	 * 
	 * @param imagem
	 */
	public void setImagem(BufferedImage imagem) {
		ImageIcon imageIcon = new ImageIcon(imagem);
		labelDaImagem.setIcon(imageIcon);
		imagemBuferizada = imagem;
		labelDaImagem.repaint();
		System.out.println("Repintar");
	}

	public void imprimeBinario2(int tamJanela, int tamBuffer,
			ArrayList<Object> tuplas, String endereco) throws IOException {

		DataOutputStream arquivoSaida = new DataOutputStream(
				new FileOutputStream(endereco + ".data"));

		if (tuplas.get(2) instanceof Color) {
			// se o terceiro elemento da lista é uma cor
			for (int i = 0; i < tuplas.size(); i++) {

				if (tamJanela <= 255) {
					// 8bits = 1 byte
					arquivoSaida.write((int) tuplas.get(i));
				}
				if (tamJanela > 255 && tamJanela <= 65535) {
					// 16bits = 2 bytes
					arquivoSaida.writeShort((int) tuplas.get(i));
				}
				if (tamJanela > 65535 && tamJanela <= 16777215) {
					// 24 bits= 3bytes
					arquivoSaida.writeInt((int) tuplas.get(i));// POUCO
																// OTIMIZADO!!!!!
																// TA
																// ESCREVENDO
																// EM
																// 4BYTES,
																// O Q
																// PODIA
																// SE
																// ESCRITO
																// EM 3
																// BYTES
				}
				if (tamJanela > 16777215) {
					// 28 bits= 4bytes
					arquivoSaida.writeInt((int) tuplas.get(i + 1));// ATE
																	// 4294967295
				}

				i++;

				if (tamBuffer <= 255) {
					// 8bits = 1 byte
					arquivoSaida.write((int) tuplas.get(i));
				}
				if (tamBuffer > 255 && tamBuffer <= 65535) {
					// 16bits = 2 bytes
					arquivoSaida.writeShort((int) tuplas.get(i));
				}
				if (tamBuffer > 65535 && tamBuffer <= 16777215) {
					// 24 bits= 3bytes
					arquivoSaida.writeInt((int) tuplas.get(i));// POUCO
																// OTIMIZADO!!!!!
																// TA
																// ESCREVENDO
																// EM
																// 4BYTES,
																// O Q
																// PODIA
																// SE
																// ESCRITO
																// EM 3
																// BYTES
				}
				if (tamBuffer > 16777215) {
					// 28 bits= 4bytes
					arquivoSaida.writeInt((int) tuplas.get(i));
				}
				i++;
				Color cor = (Color) tuplas.get(i);

				arquivoSaida.write(cor.getRed());

				arquivoSaida.write(cor.getGreen());

				arquivoSaida.write(cor.getBlue());
			}
		} else {
			for (int i = 0; i < tuplas.size() - 1; i++) {

				if (tamJanela <= 255) {
					// 8bits = 1 byte
					arquivoSaida.write((int) tuplas.get(i));
				}
				if (tamJanela > 255 && tamJanela <= 65535) {
					// 16bits = 2 bytes
					arquivoSaida.writeShort((int) tuplas.get(i));
				}
				if (tamJanela > 65535 && tamJanela <= 16777215) {
					// 24 bits= 3bytes
					arquivoSaida.writeInt((int) tuplas.get(i));// POUCO
																// OTIMIZADO!!!!!
																// TA
																// ESCREVENDO
																// EM
																// 4BYTES,
																// O Q
																// PODIA
																// SE
																// ESCRITO
																// EM 3
																// BYTES
				}
				if (tamJanela > 16777215) {
					// 28 bits= 4bytes
					arquivoSaida.writeInt((int) tuplas.get(i));// ATE
																// 4294967295
				}

				i++;

				if (tamBuffer <= 255) {
					// 8bits = 1 byte
					arquivoSaida.write((int) tuplas.get(i));
				}
				if (tamBuffer > 255 && tamBuffer <= 65535) {
					// 16bits = 2 bytes
					arquivoSaida.writeShort((int) tuplas.get(i));
				}
				if (tamBuffer > 65535 && tamBuffer <= 16777215) {
					// 24 bits= 3bytes
					arquivoSaida.writeInt((int) tuplas.get(i));// POUCO
																// OTIMIZADO!!!!!
																// TA
																// ESCREVENDO
																// EM
																// 4BYTES,
																// O Q
																// PODIA
																// SE
																// ESCRITO
																// EM 3
																// BYTES
				}
				if (tamBuffer > 16777215) {
					// 28 bits= 4bytes
					arquivoSaida.writeInt((int) tuplas.get(i));
				}

				i++;
				if (tuplas.get(i) != null && tuplas.get(i + 1) != null
						&& tuplas.get(i + 2) != null) {
					arquivoSaida.write((int) tuplas.get(i));

					i++;
					arquivoSaida.write((int) tuplas.get(i));

					i++;
					arquivoSaida.write((int) tuplas.get(i));
				} else {
					// se for final de arquivo
					// arquivoSaida.write(EOF);
				}

			}
		}
		arquivoSaida.close();
	}

	public void imprimeBinarioComflagZero(int tamJanela, int tamBuffer,
			ArrayList<Object> tuplas, String endereco) throws IOException {

		DataOutputStream arquivoSaida = new DataOutputStream(
				new FileOutputStream(endereco + ".data"));

		if (tuplas.get(2) instanceof Color) {
			// se o terceiro elemento da lista é uma cor
			for (int i = 0; i < tuplas.size(); i++) {
				if ((int) tuplas.get(i) == 0 && (int) tuplas.get(i + 1) == 0) {
					arquivoSaida.write(0);
					// se janela e buffer forem zero
					i += 2;
					Color cor = (Color) tuplas.get(i);

					arquivoSaida.write(cor.getRed());

					arquivoSaida.write(cor.getGreen());

					arquivoSaida.write(cor.getBlue());

				} else {
					if ((int) tuplas.get(i) == 0
							&& (int) tuplas.get(i + 1) != 0) {
						// se a janela for zero e o buffer diferente de zero
						arquivoSaida.write(1);
						i++;
						i++;
						arquivoSaida.write(i);// escreve buffer

					} else {
						if ((int) tuplas.get(i) == 0
								&& (int) tuplas.get(i + 1) == 1) {
							// se a janela for diferente de zero e o buffer for
							// zero
							arquivoSaida.write(2);
							arquivoSaida.write(i);// escreve a janela

						} else {
							// a janela e o buffer são diferentes de zero
							if (tamJanela <= 255) {
								// 8bits = 1 byte
								arquivoSaida.write((int) tuplas.get(i));
							}
							if (tamJanela > 255 && tamJanela <= 65535) {
								// 16bits = 2 bytes
								arquivoSaida.writeShort((int) tuplas.get(i));
							}
							if (tamJanela > 65535 && tamJanela <= 16777215) {
								// 24 bits= 3bytes
								arquivoSaida.writeInt((int) tuplas.get(i));// POUCO
																			// OTIMIZADO!!!!!
																			// TA
																			// ESCREVENDO
																			// EM
																			// 4BYTES,
																			// O
																			// Q
																			// PODIA
																			// SE
																			// ESCRITO
																			// EM
																			// 3
																			// BYTES
							}
							if (tamJanela > 16777215) {
								// 28 bits= 4bytes
								arquivoSaida.writeInt((int) tuplas.get(i + 1));// ATE
																				// 4294967295
							}

							i++;

							if (tamBuffer <= 255) {
								// 8bits = 1 byte
								arquivoSaida.write((int) tuplas.get(i));
							}
							if (tamBuffer > 255 && tamBuffer <= 65535) {
								// 16bits = 2 bytes
								arquivoSaida.writeShort((int) tuplas.get(i));
							}
							if (tamBuffer > 65535 && tamBuffer <= 16777215) {
								// 24 bits= 3bytes
								arquivoSaida.writeInt((int) tuplas.get(i));// POUCO
																			// OTIMIZADO!!!!!
																			// TA
																			// ESCREVENDO
																			// EM
																			// 4BYTES,
																			// O
																			// Q
																			// PODIA
																			// SE
																			// ESCRITO
																			// EM
																			// 3
																			// BYTES
							}
							if (tamBuffer > 16777215) {
								// 28 bits= 4bytes
								arquivoSaida.writeInt((int) tuplas.get(i));
							}

						}
					}
					i++;
					Color cor = (Color) tuplas.get(i);

					arquivoSaida.write(cor.getRed());

					arquivoSaida.write(cor.getGreen());

					arquivoSaida.write(cor.getBlue());

				}
			}
		} else {
			for (int i = 0; i < tuplas.size() - 1; i++) {

				if (tamJanela <= 255) {
					// 8bits = 1 byte
					arquivoSaida.write((int) tuplas.get(i));
				}
				if (tamJanela > 255 && tamJanela <= 65535) {
					// 16bits = 2 bytes
					arquivoSaida.writeShort((int) tuplas.get(i));
				}
				if (tamJanela > 65535 && tamJanela <= 16777215) {
					// 24 bits= 3bytes
					arquivoSaida.writeInt((int) tuplas.get(i));// POUCO
																// OTIMIZADO!!!!!
																// TA
																// ESCREVENDO
																// EM
																// 4BYTES,
																// O Q
																// PODIA
																// SE
																// ESCRITO
																// EM 3
																// BYTES
				}
				if (tamJanela > 16777215) {
					// 28 bits= 4bytes
					arquivoSaida.writeInt((int) tuplas.get(i));// ATE
																// 4294967295
				}

				i++;

				if (tamBuffer <= 255) {
					// 8bits = 1 byte
					arquivoSaida.write((int) tuplas.get(i));
				}
				if (tamBuffer > 255 && tamBuffer <= 65535) {
					// 16bits = 2 bytes
					arquivoSaida.writeShort((int) tuplas.get(i));
				}
				if (tamBuffer > 65535 && tamBuffer <= 16777215) {
					// 24 bits= 3bytes
					arquivoSaida.writeInt((int) tuplas.get(i));// POUCO
																// OTIMIZADO!!!!!
																// TA
																// ESCREVENDO
																// EM
																// 4BYTES,
																// O Q
																// PODIA
																// SE
																// ESCRITO
																// EM 3
																// BYTES
				}
				if (tamBuffer > 16777215) {
					// 28 bits= 4bytes
					arquivoSaida.writeInt((int) tuplas.get(i));
				}

				i++;
				if (tuplas.get(i) != null && tuplas.get(i + 1) != null
						&& tuplas.get(i + 2) != null) {
					arquivoSaida.write((int) tuplas.get(i));

					i++;
					arquivoSaida.write((int) tuplas.get(i));

					i++;
					arquivoSaida.write((int) tuplas.get(i));
				} else {
					// se for final de arquivo
					// arquivoSaida.write(EOF);
				}

			}
		}
		arquivoSaida.close();
	}

	public BufferedImage getImagem() {
		// TODO Auto-generated method stub
		return imagemBuferizada;
	}
}
