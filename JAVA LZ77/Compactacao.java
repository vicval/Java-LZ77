import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * 
 * @author Vicente F. Valcarcel
 *
 */
public class Compactacao {
	ControleCompactacao controleCompactacao;

	public Compactacao(ControleCompactacao pcontroleCompactacao) {
		controleCompactacao = pcontroleCompactacao;
	}

	public Compactacao() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @deprecated Funcao que gerencia compactação de String. Chama uma funcao q
	 *             transforma uma imagem em uma List de Color chama a fincao
	 *             compactacao
	 * 
	 * @param tamJanela
	 * @param tamBuffer
	 * @param Arquivo
	 */
	public void compactacaoLZ77Imagem(int tamJanela, int tamBuffer,
			BufferedImage Arquivo) {
		// transforma uma imgamem num array de cor
		// =============================

		long tempoInicial = System.currentTimeMillis();

		ArrayList<Object> listaPixels = transformarImagemNumArrayDeColor(Arquivo);
		if (listaPixels == null) {
			return;
		}
		// ArrayList<Object> listaPixels =
		// transformarImagemNumArrayDe255RGB(Arquivo);
		System.out.println("tamanho da lista de pixels:" + listaPixels.size());
		long tempoFinal = System.currentTimeMillis();
		System.out.println("tempo para transformar matriz num vetor de cores"
				+ (tempoFinal - tempoInicial) / 1000);

		tempoInicial = System.currentTimeMillis();

		// compactacaoLZ77VetorCor3 tem leitura invertida
		ArrayList<Object> arraySaida = compactacaoLZ77VetorCor3(tamJanela,
				tamBuffer, listaPixels, null);
		// ArrayList<Object> arraySaida = compactacaoLZ77VetorCor4(tamJanela,
		// tamBuffer, listaPixels, null);

		// ===================
		// for (int i = 0; i < arraySaida.size(); i++) {
		// int jan=(int) arraySaida.get(i);
		// i++;
		// int buff=(int) arraySaida.get(i);
		// if(jan){
		//
		// }
		// }

		// ===================
		tempoFinal = System.currentTimeMillis();
		System.out.println("tempo para compactar" + (tempoFinal - tempoInicial)
				/ 1000);
		System.out.println("Tamanho do vetor deposi da compactação:"
				+ arraySaida.size() + " x3\n = " + 3 * arraySaida.size());
		try {
			tempoInicial = System.currentTimeMillis();
			imprimeBinario(tamJanela, tamBuffer, arraySaida);
			tempoFinal = System.currentTimeMillis();
			System.out.println("tempo para imprimir em arquivo binario"
					+ (tempoFinal - tempoInicial) / 1000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sem leitura invertida, PASSOU NOS TESTES
	 * 
	 * @param tamJanela
	 * @param tamBuffer
	 * @param Arquivo
	 * @param viewCompressao
	 * @return
	 */
	public ArrayList<Object> compactacaoLZ77VetorCor4(int tamJanela,
			int tamBuffer, ArrayList<Object> Arquivo,
			ViewCompressao viewCompressao) {
		ArrayList<Object> saida = new ArrayList<Object>();
		int i2;
		int j = 0;
		int ponteiroInicioSemelhanca = 0;
		int ponteiroInicioMaiorSequencia = 0;
		int tamanhoDaMaiorSequencia = 0;
		int ponteiroFinalMaiorSequencia = 0;
		int tamanhoUltimaSequencia = 0;
		int ponteiroFinalUltimaSequencia = 0;
		int flag = 0;// numero de caracteres iguais

		int pixelsadicionados = 0;
		int intervalosadicionados = 0;

		int umPorCento = Arquivo.size() / 100;
		if (umPorCento == 0) {
			// o arquivo é muito pequeno
			umPorCento = Integer.MAX_VALUE;
		}
		int umPorCentoFlag = umPorCento;

		for (int i = 0; i < Arquivo.size(); i++) {
			// for que anda pelo vetor
			// //for que anda pela janela, do final para o inicio
			if (i > umPorCentoFlag) {
				System.out.println(i / umPorCento + "%");
				// viewCompressao.progressBar.setValue(i / umPorCento);
				// viewCompressao.alterarJmenuBar(i / umPorCento);
				umPorCentoFlag += umPorCento;
				// controleCompactacao.v
			}
			i2 = i;
			ponteiroInicioSemelhanca = 0;
			ponteiroInicioMaiorSequencia = 0;
			tamanhoDaMaiorSequencia = 0;
			ponteiroFinalMaiorSequencia = 0;
			tamanhoUltimaSequencia = 0;
			ponteiroFinalUltimaSequencia = 0;
			flag = 0;

			if (i > 0) {
				// i ja andou uma posicao, logo a janela tem pelo menos um
				// elemento
				j = i - tamJanela;
				if (j < 0) {
					j = 0;
				}
				for (; j < i; j++) {// j nao pode se
									// negativo!!!! ele corre pela janela
					// for que anda pela janela, do inicio da mesma ate o final
					// System.out.println("   " + Arquivo.get(j) + " X "
					// + Arquivo.get(i2));
					if (Arquivo.get(j).equals(Arquivo.get(i2))) {

						// se o um objeto do buffer for igual a
						// um objeto da janela

						ponteiroFinalUltimaSequencia = j;
						flag++;
						if ((j - 1) >= 0
								&& (i2 - 1) >= 0
								&& Arquivo.get(j - 1).equals(
										Arquivo.get(i2 - 1)) && i2 != i) {// pode

							// estamos no meio duas sequencia de caracteres
							// iguais.Eba!!!

						} else {

							// Este é o primeiro carater da sequencia, ou nao
							// sequencia

							ponteiroInicioSemelhanca = j;// ponteiro aponta para
															// o
															// primeiro
															// caractere
															// igual da
															// sequencia
						}
						i2++;// como foi achado um carater semelhante,
								// aumentamos i2
								// para poder ver se o proximo caracter será
								// tambem
								// semalhante, esta varival corre pelo buffer

						if (i2 - i > tamBuffer || i2 >= Arquivo.size()) {
							// estorou o buffer ou o arquivo chegou ao final

							// mesmo....vamos sair do for

							// aquela outra implementacao entra aki!!!!!!!!!

							// ver maior sequencia

							if (flag >= tamanhoDaMaiorSequencia && flag != 0) {
								ponteiroInicioMaiorSequencia = ponteiroInicioSemelhanca;
								tamanhoDaMaiorSequencia = flag;

								// o ponteiro que corrre no buffer volta
								// para o inicio do mesmo
							}
							tamanhoUltimaSequencia = 0;
							ponteiroInicioSemelhanca = 0;
							flag = 0;
							ponteiroFinalUltimaSequencia = 0;

							// // o ponteiro que corrre no buffer
							// // volta
							// // para o inicio do mesmo
							// tamanhoUltimaSequencia = 0;
							// ponteiroInicioSemelhanca = 0;
							//
							// // o ponteiro que corrre no buffer
							// // volta
							// // para o inicio do mesmo
							// }

							if (i2 >= Arquivo.size()) {
								// o ponteiro chegou no final do arqivo!!!
								// temos duas opcoes, ou arquivo termina com
								// (x,x,EOF), ou temos que fazer duas iteracoes
								// sen da ultima (0,0,EOF)

								System.out
										.println((i - ponteiroInicioMaiorSequencia)
												+ ","
												+ (tamanhoDaMaiorSequencia)
												+ "," + "EOF");

								saida.add((i - ponteiroInicioMaiorSequencia));
								saida.add(tamanhoDaMaiorSequencia);
								saida.add("EOF");
								intervalosadicionados += tamanhoDaMaiorSequencia;
								System.out.println("pixels adicionados:"
										+ pixelsadicionados
										+ " intervalos adicionados"
										+ intervalosadicionados);

								return saida;

							} else {

								// acabamos de sair de uma sequencia.Pois acabou
								// o buffer....
								// Mas sabemos que os
								// ultimos objetos eram iguais
								System.out.println("Estourou o buffer!!");
								System.out
										.println((i - ponteiroInicioMaiorSequencia)
												+ ","
												+ (tamanhoUltimaSequencia + 1)
												+ ","
												+ Arquivo
														.get(i
																+ tamanhoDaMaiorSequencia));
								saida.add((i - ponteiroInicioMaiorSequencia));
								saida.add((tamanhoUltimaSequencia + 1));
								saida.add(Arquivo.get(i
										+ tamanhoDaMaiorSequencia));

								intervalosadicionados += tamanhoDaMaiorSequencia;
								pixelsadicionados++;

								tamanhoUltimaSequencia = 0;
								ponteiroInicioSemelhanca = 0;

								// j fica menor que i, entao saimos do
								i2 = i; // for...
							}
						} else {
							// o buffer não foi estourado e o arquivo não acabou
							// o elemento apontado por j esta concidindo ao
							// elemento apontado por i2
							// System.out.println("++");
							// if (j - 1 >= 0 && j + 1 == i
							// && Arquivo.get(j - 1) == Arquivo.get(i2)) {
							// // j+1==i verifica se esta no final da janela.
							// // Arquivo.get(j-1)==Arquivo.get(i2) verifica
							// // se a leitura invertida da janela é
							// // coincidente com a proxima posicao do buffer
							// System.out.println("--");
							// int limiteInversoJanela = i - tamJanela;
							//
							// if (limiteInversoJanela < 0) {
							// limiteInversoJanela = 0;
							// }
							//
							// for (int jInverso = j - 1; jInverso >
							// limiteInversoJanela; jInverso--) {
							// if (Arquivo.get(jInverso) == Arquivo
							// .get(i2)) {
							// flag++;
							// i2++;
							// } else {
							// jInverso = limiteInversoJanela - 1;// pra
							// // sair
							// // do
							// // for
							// }
							// }
							// }
						}

					} else {
						// os objetos da janela e do buffer
						// nao sao iguais
						// zera contagem
						if ((j - 1) >= 0
								&& (i2 - 1) >= 0
								&& i != i2
								&& Arquivo.get(j - 1).equals(
										Arquivo.get(i2 - 1)) && i != i2) {// pode

							// acabamos de sair de uma sequencia.Q pena =(

							if (flag >= tamanhoDaMaiorSequencia && flag != 0) {
								ponteiroInicioMaiorSequencia = ponteiroInicioSemelhanca;
								tamanhoDaMaiorSequencia = flag;

								i2 = i;// o ponteiro que corrre no buffer volta
										// para o inicio do mesmo
							}
							tamanhoUltimaSequencia = 0;
							ponteiroInicioSemelhanca = 0;
							flag = 0;
							ponteiroFinalUltimaSequencia = 0;

						} else {
							// estamos no meio de uma nao sequencia
							// somente anda o contador j da janela

						}

					}
				}

			}
			// a varredura da janela acabou
			// a maior sequencia foi

			if (flag >= tamanhoDaMaiorSequencia && flag != 0) {
				ponteiroInicioMaiorSequencia = ponteiroInicioSemelhanca;
				tamanhoDaMaiorSequencia = flag;

				i2 = i;// o ponteiro que corrre no buffer volta
						// para o inicio do mesmo
			}
			tamanhoUltimaSequencia = 0;
			ponteiroInicioSemelhanca = 0;
			flag = 0;

			if (tamanhoDaMaiorSequencia == 0) {
				// System.out.println((0) + "," + 0 + "," + Arquivo.get(i));

				saida.add(0);
				saida.add(0);
				saida.add(Arquivo.get(i));

				intervalosadicionados += tamanhoDaMaiorSequencia;
				pixelsadicionados++;

			} else {
				if ((i + tamanhoDaMaiorSequencia) > Arquivo.size()) {

					// System.out.println((i - ponteiroInicioMaiorSequencia) +
					// ","
					// + tamanhoDaMaiorSequencia + ","
					// + Arquivo.get(Arquivo.size()));

					saida.add(i - ponteiroInicioMaiorSequencia);
					saida.add(tamanhoDaMaiorSequencia);
					saida.add(Arquivo.get(Arquivo.size()));

					intervalosadicionados += tamanhoDaMaiorSequencia;
					pixelsadicionados++;

				} else {
					// System.out.println((i - ponteiroInicioMaiorSequencia) +
					// ","
					// + tamanhoDaMaiorSequencia + ","
					// + Arquivo.get(i + tamanhoDaMaiorSequencia));

					saida.add((i - ponteiroInicioMaiorSequencia));
					saida.add(tamanhoDaMaiorSequencia);
					saida.add(Arquivo.get(i + tamanhoDaMaiorSequencia));

					intervalosadicionados += tamanhoDaMaiorSequencia;
					pixelsadicionados++;
				}
				i = i + tamanhoDaMaiorSequencia;
				// um novo caracter foi adicionado a tupla por isso i
				// anda 1
			}

		}
		System.out.println("pixels adicionados:" + pixelsadicionados
				+ " intervalos adicionados" + intervalosadicionados);
		return saida;
	}

	/**
	 * Passou nos teste,consertei a parte final....agora está passanda tudo
	 * 
	 * @param tamJanela
	 * @param tamBuffer
	 * @param Arquivo
	 * @param viewCompressao
	 * @return
	 */
	public ArrayList<Object> compactacaoLZ77VetorCor3(int tamJanela,
			int tamBuffer, ArrayList<Object> Arquivo,
			ViewCompressao viewCompressao) {
		ArrayList<Object> saida = new ArrayList<Object>();
		int i2;
		int j = 0;
		int ponteiroInicioSemelhanca = 0;
		int ponteiroInicioMaiorSequencia = 0;
		int tamanhoDaMaiorSequencia = 0;
		int ponteiroFinalMaiorSequencia = 0;
		int tamanhoUltimaSequencia = 0;
		int ponteiroFinalUltimaSequencia = 0;
		int flag = 0;// numero de caracteres iguais
		int conttt=0;
		int pixelsadicionados = 0;
		int intervalosadicionados = 0;

		int umPorCento = Arquivo.size() / 100;
		if (umPorCento == 0) {
			// o arquivo é muito pequeno
			umPorCento = Integer.MAX_VALUE;
		}
		int umPorCentoFlag = umPorCento;

		for (int i = 0; i < Arquivo.size(); i++) {
			// for que anda pelo vetor
			// //for que anda pela janela, do final para o inicio
			if (i > umPorCentoFlag) {
				System.out.println(i / umPorCento + "%");
				umPorCentoFlag += umPorCento;
			
			}
			i2 = i;
			ponteiroInicioSemelhanca = 0;
			ponteiroInicioMaiorSequencia = 0;
			tamanhoDaMaiorSequencia = 0;
			ponteiroFinalMaiorSequencia = 0;
			tamanhoUltimaSequencia = 0;
			ponteiroFinalUltimaSequencia = 0;
			flag = 0;

			if (i > 0) {
				// i ja andou uma posicao, logo a janela tem pelo menos um
				// elemento
				j = i - tamJanela;
				if (j < 0) {
					j = 0;
				}
				for (; j < i; j++) {// j nao pode se
									// negativo!!!! ele corre pela janela
					// for que anda pela janela, do inicio da mesma ate o final
					// System.out.println("   " + Arquivo.get(j) + " X "
					// + Arquivo.get(i2));
					if (Arquivo.get(j).equals(Arquivo.get(i2))) {

						// se o um objeto do buffer for igual a
						// um objeto da janela

						ponteiroFinalUltimaSequencia = j;
						flag++;
						if ((j - 1) >= 0
								&& (i2 - 1) >= 0
								&& Arquivo.get(j - 1).equals(
										Arquivo.get(i2 - 1)) && i2 != i) {// pode

							// estamos no meio duas sequencia de caracteres
							// iguais.Eba!!!

						} else {

							// Este é o primeiro carater da sequencia, ou nao
							// sequencia

							ponteiroInicioSemelhanca = j;// ponteiro aponta para
															// o
															// primeiro
															// caractere
															// igual da
															// sequencia
						}
						i2++;// como foi achado um carater semelhante,
								// aumentamos i2
								// para poder ver se o proximo caracter será
								// tambem
								// semalhante, esta varival corre pelo buffer

						if (i2 - i > tamBuffer || i2 >= Arquivo.size()) {
							// estorou o buffer ou o arquivo chegou ao final

							// mesmo....vamos sair do for

							// aquela outra implementacao entra aki!!!!!!!!!

							// ver maior sequencia

							if (flag >= tamanhoDaMaiorSequencia && flag != 0) {
								ponteiroInicioMaiorSequencia = ponteiroInicioSemelhanca;
								tamanhoDaMaiorSequencia = flag;

								// o ponteiro que corrre no buffer volta
								// para o inicio do mesmo
							}
							tamanhoUltimaSequencia = 0;
							ponteiroInicioSemelhanca = 0;
							flag = 0;
							ponteiroFinalUltimaSequencia = 0;

							// // o ponteiro que corrre no buffer
							// // volta
							// // para o inicio do mesmo
							// tamanhoUltimaSequencia = 0;
							// ponteiroInicioSemelhanca = 0;
							//
							// // o ponteiro que corrre no buffer
							// // volta
							// // para o inicio do mesmo
							// }

							if (i2 >= Arquivo.size()) {
								// o ponteiro chegou no final do arqivo!!!
								// temos duas opcoes, ou arquivo termina com
								// (x,x,EOF), ou temos que fazer duas iteracoes
								// sen da ultima (0,0,EOF)

								System.out
										.println((i - ponteiroInicioMaiorSequencia)
												+ ","
												// + (tamanhoDaMaiorSequencia)
												+ (i2 - i) + "," + "EOF");

								saida.add((i - ponteiroInicioMaiorSequencia));
								// saida.add(tamanhoDaMaiorSequencia);
								saida.add(i2 - i);
								saida.add("EOF");
								intervalosadicionados += (i2 - i);
								// pixelsadicionados++;
//								System.out.println("pixels adicionados:"
//										+ pixelsadicionados
//										+ " intervalos adicionados"
//										+ intervalosadicionados);
//								System.out.println("i:" + i + " Arquivo.size()"
//										+ Arquivo.size());
//								System.out.println("i2:" + i2);
//								System.out.println("TESTE:"
//										+ (i - ponteiroInicioMaiorSequencia)
//										+ "," + (i2 - i) + "," + "EOF");
								return saida;

							} else {

								// acabamos de sair de uma sequencia.Pois acabou
								// o buffer....
								// Mas sabemos que os
								// ultimos objetos eram iguais
								// System.out.println("Estourou o buffer!!");
//								System.out
//										.println((i - ponteiroInicioMaiorSequencia)
//												+ ","
//												+ (tamanhoUltimaSequencia + 1)
//												+ ","
//												+ Arquivo
//														.get(i
//																+ tamanhoDaMaiorSequencia));

								saida.add((i - ponteiroInicioMaiorSequencia));
								saida.add((tamanhoUltimaSequencia + 1));
								saida.add(Arquivo.get(i
										+ tamanhoDaMaiorSequencia));

								intervalosadicionados += tamanhoDaMaiorSequencia;
								pixelsadicionados++;

								tamanhoUltimaSequencia = 0;
								ponteiroInicioSemelhanca = 0;

								// j fica menor que i, entao saimos do
								i2 = i; // for...
							}
						} else {
							// o buffer não foi estourado e o arquivo não acabou
							// o elemento apontado por j esta concidindo ao
							// elemento apontado por i2
							
						}

					} else {
						// os objetos da janela e do buffer
						// nao sao iguais
						// zera contagem
						if ((j - 1) >= 0
								&& (i2 - 1) >= 0
								&& i != i2
								&& Arquivo.get(j - 1).equals(
										Arquivo.get(i2 - 1)) && i != i2) {// pode

							// acabamos de sair de uma sequencia.Q pena =(

							if (flag >= tamanhoDaMaiorSequencia && flag != 0) {
								ponteiroInicioMaiorSequencia = ponteiroInicioSemelhanca;
								tamanhoDaMaiorSequencia = flag;

								i2 = i;// o ponteiro que corrre no buffer volta
										// para o inicio do mesmo
							}
							tamanhoUltimaSequencia = 0;
							ponteiroInicioSemelhanca = 0;
							flag = 0;
							ponteiroFinalUltimaSequencia = 0;

						} else {
							// estamos no meio de uma nao sequencia
							// somente anda o contador j da janela

						}

					}
				}

			}
			// a varredura da janela acabou
			// a maior sequencia foi

			if (flag >= tamanhoDaMaiorSequencia && flag != 0) {
				ponteiroInicioMaiorSequencia = ponteiroInicioSemelhanca;
				tamanhoDaMaiorSequencia = flag;

				i2 = i;// o ponteiro que corrre no buffer volta
						// para o inicio do mesmo
			}
			tamanhoUltimaSequencia = 0;
			ponteiroInicioSemelhanca = 0;
			flag = 0;

			if (tamanhoDaMaiorSequencia == 0) {
//				System.out.println((0) + "," + 0 + "," + Arquivo.get(i));

				saida.add(0);
				saida.add(0);
				saida.add(Arquivo.get(i));

				intervalosadicionados += tamanhoDaMaiorSequencia;
				pixelsadicionados++;

			} else {
				if ((i + tamanhoDaMaiorSequencia) > Arquivo.size()) {

//					System.out.println((i - ponteiroInicioMaiorSequencia) + ","
//							+ tamanhoDaMaiorSequencia + ","
//							+ Arquivo.get(Arquivo.size()));

					saida.add(i - ponteiroInicioMaiorSequencia);
					saida.add(tamanhoDaMaiorSequencia);
					saida.add(Arquivo.get(Arquivo.size()));

					intervalosadicionados += tamanhoDaMaiorSequencia;
					pixelsadicionados++;

				} else {
//					System.out.println((i - ponteiroInicioMaiorSequencia) + ","
//							+ tamanhoDaMaiorSequencia + ","
//							+ Arquivo.get(i + tamanhoDaMaiorSequencia));

					saida.add((i - ponteiroInicioMaiorSequencia));
					saida.add(tamanhoDaMaiorSequencia);
					saida.add(Arquivo.get(i + tamanhoDaMaiorSequencia));

					intervalosadicionados += tamanhoDaMaiorSequencia;
					pixelsadicionados++;
				}
				i = i + tamanhoDaMaiorSequencia;
				// um novo caracter foi adicionado a tupla por isso i
				// anda 1
			}
//			System.out.println("i:" + i + " Arquivo.size()" + Arquivo.size());
		}
//		System.out.println("pixels adicionados:" + pixelsadicionados
//				+ " intervalos adicionados" + intervalosadicionados);

		return saida;
	}

	@Deprecated
	public void imprimeBinario(int tamJanela, int tamBuffer,
			ArrayList<Object> tuplas) throws IOException {
		DataOutputStream arquivoBinarioInt = new DataOutputStream(
				new FileOutputStream("SaidaiNT.data"));
		DataOutputStream arquivoBinarioNormal = new DataOutputStream(
				new FileOutputStream("SaidaBinarioNormal.data"));
		int casaBinarias = 1;
		if (tuplas.size() > 255 && tamJanela > 255 || tuplas.size() > 255
				&& tamBuffer > 255) {

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

	public void imprimeBinario2(int tamJanela, int tamBuffer,
			ArrayList<Object> tuplas, String endereco) throws IOException {

		DataOutputStream arquivoSaida = new DataOutputStream(
				new FileOutputStream(endereco + ".data"));

		if (tuplas.get(2) instanceof Color) {
			// se o terceiro elemento da lista é uma cor
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

	// @Deprecated
	// private void compactarBinario(int tamJanela, int tamBuffer,
	// ArrayList<Integer> listaPixels) {
	//
	// }

	/**
	 * Transforma um imagem BufferedImage em um array de Color
	 * 
	 * @param imagem
	 * @return
	 */
	public ArrayList<Object> transformarImagemNumArrayDeColor(
			BufferedImage imagem) {
		if (imagem == null) {
			return null;
		}
		System.out.println("transformarImagemNumArrayDeColor");
		ArrayList<Object> listaPixels = new ArrayList<Object>();
		for (int i = 0; i < imagem.getHeight(); i++) {
			for (int j = 0; j < imagem.getWidth(); j++) {
				int pixel = imagem.getRGB(j, i);
				listaPixels.add(new Color(pixel));
			}
		}
		return listaPixels;
	}

	private ArrayList<Object> transformarImagemNumArrayDe255RGB(
			BufferedImage imagem) {
		ArrayList<Object> listaPixels = new ArrayList<Object>();
		for (int i = 0; i < imagem.getHeight(); i++) {
			for (int j = 0; j < imagem.getWidth(); j++) {
				int pixel = imagem.getRGB(j, i);
				Color c = new Color(pixel);
				listaPixels.add(c.getRed());
				listaPixels.add(c.getGreen());
				listaPixels.add(c.getBlue());
			}
		}
		return listaPixels;
	}

	@Deprecated
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
						System.exit(0);
						j = 0;
					}
					//System.out.println("*");
					Color cor = listaDeCores.get(j);
//					System.out.println("=>i:" + i + " corRed:" + cor.getRed()
//							+ " corGreen:" + cor.getGreen() + " corBlue:"
//							+ cor.getBlue() + " listaDeCores.size()"
//							+ listaDeCores.size());
					listaDeCores.add(cor);
				}
				if (i < listaInteiro.size()) {
//					System.out.println("*");
					int corRed = listaInteiro.get(i);
					i++;
					int corGreen = listaInteiro.get(i);
					i++;
					int corBlue = listaInteiro.get(i);
					// i++;
//					System.out.println("Nova cor corRed:" + corRed
//							+ " corGreen:" + corGreen + " corBlue:" + corBlue);
					Color corPixel = new Color(corRed, corGreen, corBlue);
					// System.out.println("x:" + pixelx + " y:" + pixely);

					listaDeCores.add(corPixel);

				}else{
					//chegou ao "EOF"...FIM DE ARQUIVO
					System.out.println("Fim de arquivo");
				}

			}

		}
		System.out.println("listaDeCores.size(): " + listaDeCores.size());

		imagem=transformarUmArrayDeColorEmUmaImagem(listaDeCores, imagem);
		return imagem;
	}

	public BufferedImage transformarUmArrayDeColorEmUmaImagem(
			ArrayList<Color> lista,BufferedImage imagem) {
		if (lista == null) {
			return null;
		}
		int cont=0;
		Graphics graphics=imagem.getGraphics();
		System.out.println("transformarImagemNumArrayDeColor");
		//ArrayList<Object> listaPixels = new ArrayList<Object>();
		for (int i = 0; i < imagem.getHeight(); i++) {
			for (int j = 0; j < imagem.getWidth(); j++) {
				//int pixel = imagem.getRGB(j, i);
				//listaPixels.add(new Color(pixel));
				Color cor = lista.get(cont);
				graphics.setColor(cor);
				graphics.drawLine(j, i, j, i);
				cont++;
			}
		}
		return imagem;
	}
	
	
}
