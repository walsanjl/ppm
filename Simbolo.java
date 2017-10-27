
/**
 * @author walsan
 *
 */
public class Simbolo implements Comparable<Simbolo>{
	public Character simbolo;
	public int contador;
	public byte simboloByte;
	
	public Simbolo(Character c, int cont) {
		this.simbolo = c;
		this.contador = cont;
		this.simboloByte = (byte) this.simbolo.hashCode();
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
