import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author Vicente F. Valcarcel
 *
 */

public class ControleCompactacao {
	Controle controle;
	private Compactacao compactacao;
	 ViewCompressao viewCompressao;
	private View view;
	private BufferedImage imagem;
	private int tamJanela;
	private int tamBuffer;
	private int totalPixels;
	private int codificacaoEmBits;

	public ControleCompactacao(Controle pcontrole) {
		controle = pcontrole;
		compactacao = new Compactacao();
	}

	public ControleCompactacao(BufferedImage pimagem, View pview) {
		if (pimagem == null) {
			JOptionPane.showMessageDialog(null,
					"Erro! não tem imagem para comprimir");
			return;
		}
		view = pview;
		imagem = pimagem;
		compactacao = new Compactacao(this);
		totalPixels = imagem.getHeight() * imagem.getWidth();
		viewCompressao = new ViewCompressao(this, totalPixels);
		viewCompressao.gerarInterface();

	}

	public int comprimirLZ77Obsoleto() {
		long tempoInicial = System.currentTimeMillis();
		ArrayList<Object> listaPixels = compactacao
				.transformarImagemNumArrayDeColor(imagem);

		// totalPixels = listaPixels.size();
		// ArrayList<Object> listaPixels =
		// transformarImagemNumArrayDe255RGB(Arquivo);
		System.out.println("tamanho da lista de pixels:" + listaPixels.size());
		long tempoFinal = System.currentTimeMillis();

		System.out.println("tempo para transformar matriz num vetor de cores"
				+ (tempoFinal - tempoInicial) / 1000);

		tempoInicial = System.currentTimeMillis();

		ArrayList<Object> arraySaida = compactacao.compactacaoLZ77VetorCor3(
				tamJanela, tamBuffer, listaPixels, viewCompressao);

		// ArrayList<Object> arraySaida = compactacao.compactacaoLZ77VetorCor4(
		// tamJanela, tamBuffer, listaPixels, viewCompressao);

		// ====================================================
		// TESTE
		System.out.println("LALALALALA");
		System.out.println("arraySaida:" + arraySaida.size());
		System.out.println("listaPixels:" + listaPixels.size());
		ArrayList<Integer> listaTeste = new ArrayList<Integer>();
		int jan = 0;
		int buff = 0, red = 0, green = 0, blue = 0;
		Scanner sc = new Scanner(System.in);
		int n1 = sc.nextInt();
		for (int i = 0; i < arraySaida.size(); i++) {
			if (arraySaida.get(i) instanceof Integer) {
				jan = (int) arraySaida.get(i);
				listaTeste.add(jan);
				i++;
			} else {
				System.out.println("Erro");
				i++;
			}
			if (arraySaida.get(i) instanceof Integer) {
				buff = (int) arraySaida.get(i);
				listaTeste.add(buff);
				i++;
			} else {
				System.out.println("Erro");
				i++;
			}

			Object object = arraySaida.get(i);
			if (object instanceof Color) {
				Color cor = (Color) object;
				red = cor.getRed();
				green = cor.getGreen();
				blue = cor.getBlue();
				listaTeste.add(red);
				listaTeste.add(green);
				listaTeste.add(blue);
				System.out.println(jan + "," + buff + "," + red + "," + green
						+ "," + blue);
			} else {
				System.out.println(object);
				System.out.println("ERRO");
			}

			// System.out.println(jan);
		}

		int n2 = sc.nextInt();
		// try {
		// Thread.sleep(4000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		BufferedImage imagemFake = new BufferedImage(300, 300,
				BufferedImage.TYPE_INT_RGB);
		controle.view.setImagem(imagemFake);
		// try {
		// Thread.sleep(4000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		BufferedImage imagem2 = compactacao.TESTEtransformarBytesEmImagem2(
				listaTeste, imagem.getWidth(), imagem.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		// controle.view.setImagem(imagem2);

		// testinho. dentro do teste
		// ArrayList<Color> listateste=(ArrayList<Color>) listaPixels.clone();
		ArrayList<Color> listateste = new ArrayList<Color>();
		// for (Object object : listaPixels) {
		// if(object instanceof Color){
		// listateste.add((Color) object);
		// }else{
		// System.out.println("Erro conversao cor");
		// }
		// }
		listateste = (ArrayList<Color>) listaPixels.clone();
		imagemFake = compactacao.transformarUmArrayDeColorEmUmaImagem(
				listateste, imagemFake);
		controle.view.setImagem(imagemFake);
		// controle.view.
		// TESTE
		// ====================================================

		// ArrayList<Object> arraySaida = compactacao.compactacaoLZ77VetorCor4(
		// tamJanela, tamBuffer, listaPixels, viewCompressao);

		tempoFinal = System.currentTimeMillis();
		System.out.println("tempo para compactar" + (tempoFinal - tempoInicial)
				/ 1000);
		System.out.println("Tamanho do vetor deposi da compactação:"
				+ arraySaida.size() + " x3\n = " + 3 * arraySaida.size());
		System.out.println("tamanho janela:" + tamJanela + " tamanho buffer: "
				+ tamBuffer);

		salvarImagemComprimidaEmByte(tamJanela, tamBuffer, arraySaida);

		tempoInicial = System.currentTimeMillis();
		// compactacao.imprimeBinario(tamJanela, tamBuffer, arraySaida);
		tempoFinal = System.currentTimeMillis();
		System.out.println("tempo para imprimir em arquivo binario"
				+ (tempoFinal - tempoInicial) / 1000);

		return 0;

	}

	public int getTamJanela() {
		return tamJanela;
	}

	public void setTamJanela(int tamJanela) {
		this.tamJanela = tamJanela;
	}

	public int getTamBuffer() {
		return tamBuffer;
	}

	public void setTamBuffer(int tamBuffer) {
		this.tamBuffer = tamBuffer;
	}

	public int getCodificacaoEmBits() {
		return codificacaoEmBits;
	}

	public void setCodificacaoEmBits(int pcodificacaoEmBits) {
		this.codificacaoEmBits = pcodificacaoEmBits;
	}

	public void iniciarCompressaoLZ77(BufferedImage pimagem) {
		if (pimagem == null) {
			JOptionPane.showMessageDialog(null,
					"Erro! não tem imagem para comprimir");
			return;
		}
		// view = pview;
		imagem = pimagem;
		// compactacao = new Compactacao(this);
		totalPixels = imagem.getHeight() * imagem.getWidth();
		viewCompressao = new ViewCompressao(this, totalPixels);
		viewCompressao.gerarInterface();

	}


	public void salvarImagemComprimidaEmByte(int tamJanela, int tamBuffer,
			ArrayList<Object> tuplas) {
		System.out.println("salvarImagemComprimidaEmByte");
		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		fc.setMultiSelectionEnabled(false);
		FileFilter wordType = new FileNameExtensionFilter(
				"Data image file (.data)", "data");
		fc.addChoosableFileFilter(wordType);
		fc.setSelectedFile(new File("nomeImagem"));
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int returnVal = fc.showSaveDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// if (imagemBuferizada == null) {
			// JOptionPane.showMessageDialog(null,
			// "Não existe mensagem a ser salva");
			// return;
			// }
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
//				System.exit(1);
			}
		}
	}

	public void imprimeBinario2(int tamJanela, int tamBuffer,
			ArrayList<Object> tuplas, String endereco) throws IOException {

		DataOutputStream arquivoSaida = new DataOutputStream(
				new FileOutputStream(endereco + ".data"));

		if (tuplas.get(2) instanceof Color) {

			arquivoSaida.write(1);// tipo de imagem:1 é colorida, 2 é escala de
									// cinza
			arquivoSaida.writeInt(imagem.getWidth());
			arquivoSaida.writeInt(imagem.getHeight());

			// arquivoSaida.write(1); //tipo de imagem, colorida, escala de
			// cinza

			if (tamJanela <= 255) {
				// 8bits = 1 byte
				arquivoSaida.write(8);
			}
			if (tamJanela > 255 && tamJanela <= 65535) {
				// 16bits = 2 bytes
				arquivoSaida.write(16);
			}
			if (tamJanela > 65535 && tamJanela <= 16777215) {
				// 24 bits= 3bytes NA VERDADE TA GRAVANDO EM 28BITS
				arquivoSaida.write(28);
			}
			if (tamJanela > 16777215) {
				// 28 bits= 4bytes
				arquivoSaida.write(28);
			}
			// ==================
			if (tamBuffer <= 255) {
				// 8bits = 1 byte
				arquivoSaida.write(8);
			}
			if (tamBuffer > 255 && tamBuffer <= 65535) {
				// 16bits = 2 bytes
				arquivoSaida.write(16);
			}
			if (tamBuffer > 65535 && tamBuffer <= 16777215) {
				// 24 bits= 3bytes NA VERDADE TA GRAVANDO EM 28BITS
				arquivoSaida.write(28);
			}
			if (tamBuffer > 16777215) {
				// 28 bits= 4bytes
				arquivoSaida.write(28);
			}

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
				if (tuplas.get(i) instanceof Color) {
					Color cor = (Color) tuplas.get(i);

					arquivoSaida.write(cor.getRed());

					arquivoSaida.write(cor.getGreen());

					arquivoSaida.write(cor.getBlue());
				} else {
					// imprimir ou não o final de arquivo?EOF?
					// arquivoSaida.write('EOF');
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

	
	
	
	
	
	public void teste() {

		ArrayList<Object> listaPixels = transformarImagemNumArrayDeColor(imagem);
		BufferedImage novaImagem = new BufferedImage(imagem.getWidth(),
				imagem.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		System.out.println("listaPixels:" + listaPixels.size());
		ArrayList<Object> arraySaida = compactacao.compactacaoLZ77VetorCor3(
				tamJanela, tamBuffer, listaPixels, viewCompressao);
		ArrayList<Integer> listaInteiro = transforamaArrayObjectEmInteger(arraySaida);
		BufferedImage imagemSaida =TESTEtransformarBytesEmImagem2(listaInteiro, imagem.getWidth(), imagem.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		controle.view.setImagem(imagemSaida);
		salvarImagemComprimidaEmByte(tamJanela, tamBuffer, arraySaida);
	}
	
	public int comprimirLZ77() {

		ArrayList<Object> listaPixels = transformarImagemNumArrayDeColor(imagem);
		BufferedImage novaImagem = new BufferedImage(imagem.getWidth(),
				imagem.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		System.out.println("listaPixels:" + listaPixels.size());
		ArrayList<Object> arraySaida = compactacao.compactacaoLZ77VetorCor3(
				tamJanela, tamBuffer, listaPixels, viewCompressao);
		ArrayList<Integer> listaInteiro = transforamaArrayObjectEmInteger(arraySaida);
		BufferedImage imagemSaida =TESTEtransformarBytesEmImagem2(listaInteiro, imagem.getWidth(), imagem.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		controle.view.setImagem(imagemSaida);
		salvarImagemComprimidaEmByte(tamJanela, tamBuffer, arraySaida);
		return 0;
	}

	
	
	
	
	
	
	
	
	
	
	public ArrayList<Object> transformarImagemNumArrayDeColor(
			BufferedImage imagem) {
		if (imagem == null) {
			return null;
		}
		System.out.println("transformarImagemNumArrayDeColor");
		ArrayList<Object> listaPixels = new ArrayList<Object>();
		for (int i = 0; i < imagem.getHeight(); i++) {
			for (int j = 0; j < imagem.getWidth(); j++) {
//				System.out.println(" i " + i + " j " + j);
				int pixel = imagem.getRGB(j, i);
				listaPixels.add(new Color(pixel));
			}
		}
		System.out.println(" retornando ");
		return listaPixels;
	}

	public BufferedImage transformarUmArrayDeColorEmUmaImagem(
			ArrayList<Color> lista, BufferedImage imagem) {
		if (lista == null) {
			return null;
		}
		int cont = 0;
		Graphics graphics = imagem.getGraphics();
		System.out.println("transformarUmArrayDeColorEmImagem");
		// ArrayList<Object> listaPixels = new ArrayList<Object>();
		for (int i = 0; i < imagem.getHeight(); i++) {
			for (int j = 0; j < imagem.getWidth(); j++) {
//				System.out.println("cont" + cont);
				Color cor = lista.get(cont);
				graphics.setColor(cor);
				graphics.drawLine(j, i, j, i);
				cont++;
			}
		}
		System.out.println("retorno transformarUmArrayDeColorEmImagem");
		return imagem;
	}

	public ArrayList<Integer> transforamaArrayObjectEmInteger(
			ArrayList<Object> arraySaida) {
		ArrayList<Integer> listaTeste = new ArrayList<Integer>();
		int jan = 0;
		int buff = 0, red = 0, green = 0, blue = 0;
		for (int i = 0; i < arraySaida.size(); i++) {
			if (arraySaida.get(i) instanceof Integer) {
				jan = (int) arraySaida.get(i);
				listaTeste.add(jan);
				i++;
			} else {
				System.out.println("Erro");
				System.exit(0);
				i++;
			}
			if (arraySaida.get(i) instanceof Integer) {
				buff = (int) arraySaida.get(i);
				listaTeste.add(buff);
				i++;
			} else {
				System.out.println("Erro");
				System.exit(0);
				i++;
			}

			Object object = arraySaida.get(i);
			if (object instanceof Color) {
				Color cor = (Color) object;
				red = cor.getRed();
				green = cor.getGreen();
				blue = cor.getBlue();
				listaTeste.add(red);
				listaTeste.add(green);
				listaTeste.add(blue);
//				System.out.println(jan + "," + buff + "," + red + "," + green
//						+ "," + blue);
			} else {
				System.out.println(object);
				System.out.println("ERRO ou EOF");
			}

			// System.out.println(jan);
		}
		return listaTeste;
	}

	public BufferedImage TESTEtransformarBytesEmImagem2(
			ArrayList<Integer> listaInteiro, int width, int height,
			int imageType) {
		System.out.println("Criando imagem");
		BufferedImage imagem = new BufferedImage(width, height, imageType);
		System.out.println("Criou imagem");
		ArrayList<Color> listaDeCores = new ArrayList<Color>();
		int i;
		for (i = 0; i < listaInteiro.size(); i++) {
			int janela = listaInteiro.get(i);
			i++;
			int buffer = listaInteiro.get(i);
			i++;
//			System.out.println("janela:" + janela + " buffer:" + buffer);
			if (janela == 0 || buffer == 0) {
				int corRed = listaInteiro.get(i);
				i++;
				int corGreen = listaInteiro.get(i);
				i++;
				int corBlue = listaInteiro.get(i);
				// i++;
//				System.out.println("Prmeira cor corRed:" + corRed
//						+ " corGreen:" + corGreen + " corBlue:" + corBlue);
				Color corPixel = new Color(corRed, corGreen, corBlue);
				// System.out.println("x:" + pixelx + " y:" + pixely);

				listaDeCores.add(corPixel);
			} else {
//				System.out.println("j:" + (listaDeCores.size() - janela)
//						+ " buffer:" + buffer + " listaDeCores.size()"
//						+ listaDeCores.size() + " listaInteiro.size()"
//						+ listaInteiro.size());
				int finalDoBuffer = (listaDeCores.size() - janela) + buffer;
				for (int j = listaDeCores.size() - janela; j < finalDoBuffer; j++) {
					if (j < 0) {
						System.out.println("Erro j<0");
						//System.exit(0);
						j = 0;
					}
					// System.out.println("*");
					Color cor = listaDeCores.get(j);
//					System.out.println("=>i:" + i + " corRed:" + cor.getRed()
//							+ " corGreen:" + cor.getGreen() + " corBlue:"
//							+ cor.getBlue() + " listaDeCores.size()"
//							+ listaDeCores.size());
					listaDeCores.add(cor);
				}
				if (i < listaInteiro.size()) {
					// System.out.println("*");
					int corRed = listaInteiro.get(i);
					i++;
					int corGreen = listaInteiro.get(i);
					i++;
					int corBlue = listaInteiro.get(i);
					// i++;
					if(corRed>255||corRed<0||corGreen>255||corGreen<0||corBlue>255||corBlue<0){
						System.out.println("Erro....break");
						break;
					}
//					System.out.println("Nova cor corRed:" + corRed
//							+ " corGreen:" + corGreen + " corBlue:" + corBlue);
					Color corPixel = new Color(corRed, corGreen, corBlue);
					// System.out.println("x:" + pixelx + " y:" + pixely);

					listaDeCores.add(corPixel);

				} else {
					// chegou ao "EOF"...FIM DE ARQUIVO
					System.out.println("Fim de arquivo");
				}

			}
			// System.out.println(listaDeCores);
		}
		System.out.println("listaDeCores.size(): " + listaDeCores.size());
		imagem = transformarUmArrayDeColorEmUmaImagem(listaDeCores, imagem);
		return imagem;
	}

}
