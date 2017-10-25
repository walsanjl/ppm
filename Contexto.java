import java.util.ArrayList;


public class Contexto {
	public String contexto;
	public ArrayList<Simbolo> lSimbolo = new ArrayList<Simbolo>();
	
	public Contexto(String contexto, Character simbolo) {
		//super();
		this.contexto = contexto;
		this.lSimbolo.add( new Simbolo(simbolo) );
		this.lSimbolo.add( new Simbolo(  (char) 27 ) );
	}
	
	public Contexto() {
		//super();
	}
	
}
