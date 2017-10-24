import java.util.ArrayList;


public class Tabela {
	public ArrayList<Contextos> lContextos = new ArrayList<Contextos>();
	public Simbolos coluna0;
	public ArrayList<Character> lTexto = new ArrayList<Character>();
	public ArrayList<Character> lAlfabeto = new ArrayList<Character>();
	public ArrayList<Informacao> lInformacao = new ArrayList<Informacao>();
	public int contador;
	public int tamLTexto;
	public int tamLAlfabeto;
	
	//construtor
	public Tabela(int k) {
		//instancia as colunas da tabela de acordo com o valor de k
		for(int i = 0; i <= k; i++) {
			if (i == 0) {
				//coluna do k = 0
				this.coluna0 = new Simbolos();
				System.out.println("coluna do k = 0 gerada");
			} else {
				//coluna do k >= 1
				this.lContextos.add( new Contextos() );
				System.out.println("coluna do k = "+i+" gerada");
			}
		}
	} //fim do construtor
	
} //fim da classe tabela
