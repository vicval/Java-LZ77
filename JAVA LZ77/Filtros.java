import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Filtros {

	public BufferedImage filtroGaussiano(BufferedImage imagemBruta, double desvio,
			int tamanhoMascara) {
		if (imagemBruta == null) {
			return null;
		}
		if (desvio < 0) {
			return imagemBruta;
		}
		if (tamanhoMascara <= 2) {
			return imagemBruta;
		}
		BufferedImage imageFiltrada = imagemBruta;
		int rgb = new Color(0, 0, 0).getRGB();
		int i, j, z, cont1 = 0, cont2 = 0;
		z = tamanhoMascara / 2;
		double medianaR = 0, medianaG = 0, medianaB = 0;
		double m[][] = new double[30][30];//provavelmente esse é o erro!
//		double m2[][] = new double[30][30];//provavelmente esse é o erro!

		for (i = -z; i <= z; i++) {
			for (j = -z; j <= z; j++) {
				m[cont1][cont2] = (1 / (2 * Math.PI * (desvio * desvio)))
						* Math.pow(
								Math.E,
								-((((i * i) + (j * j)) / (2 * (desvio * desvio)))));
				cont2++;
			}
			cont1++;
			cont2 = 0;
		}

		double somatoria = 0;
		// System.out.print(" Iniciando ");

		for (i = 0; i <= z * 2; i++) {
			for (j = 0; j <= z * 2; j++) {
				somatoria += m[i][j];
			}
		}
		for (i = 0; i <= z * 2; i++) {
			for (j = 0; j <= z * 2; j++) {
				m[i][j] = m[i][j] / somatoria;
			}
		}
		// System.out.println("Somatoria = " + somatoria);

		for (i = z; i < (((int) imagemBruta.getWidth()) - z); i++) {
			for (j = z; j < (((int) imagemBruta.getHeight()) - z); j++) {
				cont1 = 0;
				cont2 = 0;
				medianaR = 0;
				medianaG = 0;
				medianaB = 0;
				for (int k = -z; k <= z; k++) {
					for (int l = -z; l <= z; l++) {
						rgb = imagemBruta.getRGB(i - k, j - l);
						medianaR += ((rgb >> 16) & 0xFF) * ((m[cont1][cont2]));
						medianaG += ((rgb >> 8) & 0xFF) * ((m[cont1][cont2]));
						medianaB += (rgb & 0xFF) * ((m[cont1][cont2]));
						cont2++;

					}
					cont1++;
					cont2 = 0;
				}

				rgb = new Color((int) medianaR, (int) medianaG, (int) medianaB)
						.getRGB();

				imageFiltrada.setRGB(i, j, rgb);
			}
		}
		return imageFiltrada;
	}
	
	
	
	public BufferedImage executaRuidoPimenta(BufferedImage imagemBuferizada) {
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
		return imagemBuferizada;
//		g.dispose();
//		labelDaImagem.getParent().repaint();

	}
	
	
	
	/**
	 * metodo executa ruido sal, pintando pixels randomicamente de branco
	 */
	public BufferedImage executaRuidoSal(BufferedImage imagemBuferizada) {
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
		return imagemBuferizada;
//		g.dispose();
//		labelDaImagem.getParent().repaint();

	}
}
