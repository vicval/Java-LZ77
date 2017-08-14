import java.awt.image.BufferedImage;

public class ControleFiltros {
	Filtros filtros;
	Controle controle;

	public ControleFiltros(Controle pcontrole) {
		filtros = new Filtros();
		controle=pcontrole;
	}

	public void FiltroGaussianno(BufferedImage imagemBruta) {
//		int totalPixels=imagemBruta.getHeight()*imagemBruta.getWidth();
		ViewFiltroGaussiana viewFiltroGaussiana = new ViewFiltroGaussiana(imagemBruta,this);
//		double desvio= viewFiltroGaussiana.getDesvio();
//		int tamanhoMascara = viewFiltroGaussiana.getTamanhoMascara();

	}
	
	public void executarRuidoPimenta(BufferedImage imagemBruta) {
		filtros.executaRuidoPimenta(imagemBruta);
	}

	public void executaRuidoSal(BufferedImage imagem) {
		filtros.executaRuidoSal(imagem);
		
	}

	public void executarFiltro(BufferedImage imagemBruta,int mascara, double desvio) {
		System.out.println("Aplicando gaussciana....mascara:"+mascara+" desvio:"+desvio);
		BufferedImage imagemcomfiltro = filtros.filtroGaussiano(imagemBruta, desvio, mascara);
		controle.view.setImagem(imagemcomfiltro);
	}

}
