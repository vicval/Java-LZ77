import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controle {
	ControleFiltros ctrlFiltros;
	View view;
	ControleCompactacao ctrCompactacao;

	public Controle() {
		view = new View(this);
		ctrlFiltros = new ControleFiltros(this);
		ctrCompactacao = new ControleCompactacao(this);
	}

	public void abrirImagem() {
		abrirImagemCtrl();

	}

	public void salvarImagem() {
		salvarImagemCtrl();

	}

	public void sairDoPrograma() {
		System.exit(0);

	}

	public void executaRuidoSal() {
		ctrlFiltros.executaRuidoSal(view.getImagem());

	}

	public void executaRuidoPimenta() {
		ctrlFiltros.executarRuidoPimenta(view.getImagem());

	}

	public void executaEscalaDeCinza() {
		// TODO Auto-generated method stub

	}

	public void executarFiltroGaussianno() {
		ctrlFiltros.FiltroGaussianno(view.getImagem());

	}

	public void compactarEsalvar() {
		ctrCompactacao.iniciarCompressaoLZ77(view.getImagem());

	}

	private void abrirImagemCtrl() {
		JFileChooser fc = new JFileChooser();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fc.setAcceptAllFileFilterUsed(false);
		fc.setMultiSelectionEnabled(false);
		FileFilter filtro1 = new FileNameExtensionFilter(
				"Portable Network Graphics (.png)", "png");
		fc.addChoosableFileFilter(filtro1);

		FileFilter filtro2 = new FileNameExtensionFilter(
				"Joint Photographic Experts Group (.jpg)", "jpg");
		fc.addChoosableFileFilter(filtro2);

		FileFilter filtro3 = new FileNameExtensionFilter(
				"Bitmap Image File (.bmp)", "bmp");
		fc.addChoosableFileFilter(filtro3);

		FileFilter filtro4 = new FileNameExtensionFilter("Data Unifei (.data)",
				"data");
		fc.addChoosableFileFilter(filtro4);

		int returnVal = fc.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			System.out.println(file);
			String enderecoArquivo = file.toString();

			try {
				BufferedImage imagemBuferizada = view.getImagem();
				if (fc.getFileFilter() == filtro4) {
					imagemBuferizada = abrirData(new File(enderecoArquivo));
					System.out.println("fora abrirData");
					view.setImagem(imagemBuferizada);
				} else {
					imagemBuferizada = ImageIO.read(new File(enderecoArquivo));
					view.setImagem(imagemBuferizada);
				}
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

	private BufferedImage abrirData(File file) {
		// DataOutputStream arquivoSaida = new DataOutputStream(
		// new FileOutputStream(endereco + ".data"));
		ArrayList<Integer> listaInteiro = new ArrayList<Integer>();
		try {
			DataInputStream arquivoEntrada = new DataInputStream(
					new BufferedInputStream(new FileInputStream(file)));
			// arquivoEntrada.r
			try {
				int tipo = arquivoEntrada.read();
				if (tipo == 1) {
					int width = arquivoEntrada.readInt();
					int height = arquivoEntrada.readInt();
					int tamanhoJanela = arquivoEntrada.read();
					int tamanhoBuffer = arquivoEntrada.read();
					while (arquivoEntrada.available()!=0) {
						if (tamanhoJanela == 8) {
							int janela = arquivoEntrada.read();
							if (janela == -1) {
//								arquivoEntrada.close();
								break;
							}
							listaInteiro.add(janela);
						}
						if (tamanhoJanela == 16) {
							int janela = arquivoEntrada.readShort();
							if (janela == -1) {
								break;
//								arquivoEntrada.close();
								
							}
							listaInteiro.add((int) janela);
						}
						if (tamanhoJanela == 28) {
							int janela = arquivoEntrada.readInt();
							if (janela == -1) {
								break;
//								arquivoEntrada.close();
								
							}
							listaInteiro.add(janela);
						}
						if (tamanhoBuffer == 8) {
							listaInteiro.add(arquivoEntrada.read());
						}
						if (tamanhoBuffer == 16) {
							listaInteiro.add((int) arquivoEntrada.readShort());
						}
						if (tamanhoBuffer == 28) {
							listaInteiro.add(arquivoEntrada.readInt());
						}
						listaInteiro.add(arquivoEntrada.read());
						listaInteiro.add(arquivoEntrada.read());
						listaInteiro.add(arquivoEntrada.read());
//						System.out.println(listaInteiro);
					}
//					BufferedImage imagem = transformarBytesEmImagem(
//							listaInteiro, width, height, tipoDeImagem);
					arquivoEntrada.close();
					//============================
					//teste
					for (int i = 0; i < listaInteiro.size(); i++) {
						i++;
						i++;
						int red=listaInteiro.get(i);
						i++;
						int green=listaInteiro.get(i);
						i++;
						int blue=listaInteiro.get(i);
						if(red<0||red>255||green<0||green>255||blue<0||blue>255){
							System.out.println("ERRO!!!!");
						}
					}

					
					
					
					//============================
					
					
//					BufferedImage imagem = transformarBytesEmImagem2(
//							listaInteiro, width, height, BufferedImage.TYPE_3BYTE_BGR);
					BufferedImage imagem=ctrCompactacao.TESTEtransformarBytesEmImagem2(listaInteiro, width, height, BufferedImage.TYPE_3BYTE_BGR);
					return imagem;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private BufferedImage transformarBytesEmImagem(
			ArrayList<Integer> listaInteiro, int width, int height,
			int imageType) {
		BufferedImage imagem = new BufferedImage(width, height, imageType);
		int pixelx = 0;
		int pixely = 0;
		int janela;
		int buffer;
		for (int i = 0; i < listaInteiro.size(); i++) {
			 janela = listaInteiro.get(i);
			i++;
			buffer = listaInteiro.get(i);
			i++;
			System.out.println("janela:" + janela + " buffer:" + buffer);
			if (janela == 0 || buffer == 0) {
				int corRed = listaInteiro.get(i);
				i++;
				int corGreen = listaInteiro.get(i);
				i++;
				int corBlue = listaInteiro.get(i);
				// i++;
				Color corPixel = new Color(corRed, corGreen, corBlue);
				System.out.println("x:" + pixelx + " y:" + pixely);
				System.out.println("corRed:" + corRed + " corGreen:" + corGreen
						+ " corBlue:" + corBlue);
				imagem.setRGB(pixelx, pixely, corPixel.getRGB());
				pixelx++;
				if (pixelx > width) {
					pixelx = 0;
					pixely++;
				}

			}

		}
		return imagem;
	}

	private BufferedImage transformarBytesEmImagem2(
			ArrayList<Integer> listaInteiro, int width, int height,
			int imageType) {
		BufferedImage imagem = new BufferedImage(width, height, imageType);
		System.out.println("transformarBytesEmImagem2");
		ArrayList<Color> listaDeCores = new ArrayList<Color>();
		int i;
		for (i = 0; i < listaInteiro.size(); i++) {
			int janela = listaInteiro.get(i);
			i++;
			int buffer = listaInteiro.get(i);
			i++;
			System.out.println("janela:" + janela + " buffer:" + buffer);
			if (janela == 0 || buffer == 0) {
				int corRed = listaInteiro.get(i);
				i++;
				int corGreen = listaInteiro.get(i);
				i++;
				int corBlue = listaInteiro.get(i);
//				 i++;
				System.out.println("Unica corRed:" + corRed + " corGreen:" + corGreen
						+ " corBlue:" + corBlue+" listaDeCores.size:"+listaDeCores.size());
				if(corRed>255||corRed<0||corGreen>255||corGreen<0||corBlue>255||corBlue<0){
					break;
				}
				Color corPixel = new Color(corRed, corGreen, corBlue);
//				System.out.println("x:" + pixelx + " y:" + pixely);
				
				listaDeCores.add(corPixel);
			} else {
				for (int j = listaDeCores.size()-janela; j < buffer; j++) {
					if(j<0){
						System.out.println("Erro j<0");
						j=0;
					}
					Color cor = listaDeCores.get(j);
					System.out.println("LISTA corRed:" + cor.getRed() + " corGreen:" + cor.getGreen()
							+ " corBlue:" + cor.getBlue()+" listaDeCores.size:"+listaDeCores.size());
					listaDeCores.add(cor);
				}
				
				int corRed = listaInteiro.get(i);
				i++;
				int corGreen = listaInteiro.get(i);
				i++;
				int corBlue = listaInteiro.get(i);
//				 i++;
				if(corRed>255||corRed<0||corGreen>255||corGreen<0||corBlue>255||corBlue<0){
					System.out.println("break");
					break;
				}
				System.out.println("depoisListacorRed:" + corRed + " corGreen:" + corGreen
						+ " corBlue:" + corBlue+" listaDeCores.size:"+listaDeCores.size());
				Color corPixel = new Color(corRed, corGreen, corBlue);
//				System.out.println("x:" + pixelx + " y:" + pixely);
				
				listaDeCores.add(corPixel);
			}
//			System.out.println(listaDeCores);
		}
		//System.out.println(listaDeCores);
		System.out.println("Comeca a desenhar na imagem");
		System.out.println("listaDeCores.size:"+listaDeCores.size());
		int pixelx = 0;
		int pixely = 0;
		
		for ( i = 0; i < listaDeCores.size(); i++) {
			Color cor =listaDeCores.get(i);
			imagem.setRGB(pixelx, pixely, cor.getRGB());
			if (pixelx > width) {
				pixelx = 0;
				pixely++;
			}
		}
		return imagem;
	}

	/**
 * 
 */
	private void salvarImagemCtrl() {
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
			BufferedImage imagemBuferizada = view.getImagem();
			if (imagemBuferizada == null) {
				JOptionPane.showMessageDialog(null,
						"Não existe mensagem a ser salva");
				return;
			}
			File file = fc.getSelectedFile();
			System.out.println(file);
			System.out.println(file.getName());
			// String enderecoArquivo = file.toString();

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
}
