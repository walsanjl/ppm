
/**
 * @author walsan
 *
 */
public class Simbolo implements Comparable<Simbolo>{
	public Character simbolo;
	public int contador;
	
	public Simbolo(Character c) {
		this.simbolo = c;
	}
	
	public Simbolo() {
		//super();
	}
	
	public String toString() {
		return simbolo.toString();
	}

	public int compareTo(Simbolo objeto) {
		// TODO Auto-generated method stub
		return simbolo.compareTo( objeto.simbolo );
	}
}
