import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	/**
	 * @author walsan
	 * @throws IOException 
	 * 
	 */
	public static void main(String[] args) throws IOException {
		//----- ATRIBUTOS -----//
		
		//recebendo o valor de k
		//Scanner s = new Scanner( System.in );
		//System.out.print("Digite o valor de k: ");
		//int k = s.nextInt();
		int k = 2;
		//s.close();
		
		// criando a tabela
		Tabela tabela = new Tabela(k);
		
		
		//leitura do arquivo de texto
		String nomeDoArquivo = "/home/walsan/workspace/PPM/src/arquivosDeTexto/mamada.txt";
		try {
			FileReader arquivoEntrada = new FileReader(nomeDoArquivo);
			BufferedReader lerArquivo = new BufferedReader(arquivoEntrada);
			String linha;
			do {
				linha = lerArquivo.readLine();
				if(linha == null) {
					break; //final do arquivo
				}

				//adiciona todos os caracteres da linha ao texto
				char arr[]=linha.toCharArray();
				for(int j=0;j<arr.length;j++){
					
					if( (arr[j] < 0) || (arr[j] > 255) ) {
						System.out.println("caractere fora do escopo (0-255)");
					} else {
						tabela.lTexto.add( new Character( arr[j] ) );
					}
					
				}//fim do for j
			} while(linha != null);
			arquivoEntrada.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
			          e.getMessage());
		} //fim da leitura
		
		//debug da leitura do arquivo
		//System.out.print("Texto = ");
		for(int i = 0; i < tabela.lTexto.size(); i++) {
			//System.out.print( tabela.lTexto.get(i) );
		}
		
		//calcula o tamanho do texto de entrada
		tabela.tamLTexto = tabela.lTexto.size();
		//mostrando o tamanho do texto de leitura
		//System.out.println( "tamLTexto = " + tabela.lTexto.size() );
		
		//definindo o alfabeto
		for(int i = 0; i < tabela.lTexto.size(); i++) {
			if( tabela.lAlfabeto.contains( tabela.lTexto.get(i) ) ) {
				//o simbolo já existe no alfabeto
			} else {
				tabela.lAlfabeto.add( tabela.lTexto.get(i) );
			}
		}
		
		//ordena o alfabeto
		Collections.sort( tabela.lAlfabeto );
		
		//debug da ordenacao do alfabeto
		System.out.print("Alfabeto = ");
		for(int i = 0; i < tabela.lAlfabeto.size(); i++) {
			System.out.print( tabela.lAlfabeto.get(i) );
		}
		System.out.println();
		
		//calcula o tamanho do alfabeto
		tabela.tamLAlfabeto = tabela.lAlfabeto.size();
		//mostrando o tamanho do texto de leitura
		System.out.println( "tamLAlfabeto = " + tabela.lAlfabeto.size() );
		
		/*
		//algoritmo para escrever na saida.txt
		FileWriter arquivoSaida;
		arquivoSaida = new FileWriter("/home/walsan/workspace/PPM/src/arquivosDeTexto/saida.txt");
		PrintWriter gravarArquivoSaida = new PrintWriter(arquivoSaida);
		//escrevendo a entrada na saida
		for(int i = 0; i < tabela.lTexto.size(); i++) {
			gravarArquivoSaida.printf("");
		}
		*/
		
		//setando o contador (coluna do k = -1)
		tabela.contador = tabela.tamLAlfabeto;
		
		/*
		Contexto contextoAuxParaExclusao = new Contexto();
		System.out.println( contextoAuxParaExclusao.contexto == null );
		Simbolo simboloAuxParaExclusao = new Simbolo();
		System.out.println( simboloAuxParaExclusao.simbolo == null );
		*/
		
		//tabela.lContextos.get(1).lContexto.add( new Contexto( "ma" ) );
		//tabela.lContextos.get(1).lContexto.get(0).lSimbolo.add( new Simbolo( 'm' ) );
		//tabela.lContextos.get(1).lContexto.get(0).lSimbolo.add( new Simbolo( 'd' ) );
		
		/*
		 * codigo teste de conversão de char para string
		 * 
		char c = tabela.lTexto.get(0).charValue();
		System.out.println(c);
		int low = tabela.lAlfabeto.indexOf(c);
		System.out.println("low = "+low);
		int high = tabela.lAlfabeto.indexOf(c)+1;
		System.out.println("high = "+high);
		int total = tabela.contador;
		
		//atuali2ando
		tabela.contador--;
		System.out.println("total = "+total);
		tabela.lAlfabeto.remove(0);
		
		System.out.println(tabela.coluna0.lSimbolo.size());
		tabela.coluna0.lSimbolo.add(new Simbolo());
		tabela.coluna0.lSimbolo.get(0).simbolo = new Character(c);
		System.out.println(tabela.coluna0.lSimbolo.size());
		
		//setando a variavel c com o codigo asc correspondente ao esc
		System.out.println(tabela.coluna0.lSimbolo.get(0).simbolo);
		
		c = 98;
		tabela.coluna0.lSimbolo.add(new Simbolo());
		tabela.coluna0.lSimbolo.get(0).simbolo = new Character(c);
		System.out.println(tabela.coluna0.lSimbolo.get(0).simbolo);
		*/
		
		
		//teste da coluna 0
		//tabela.coluna0.lSimbolo.add( new Simbolo() );
		//tabela.coluna0.lSimbolo.get(0).simbolo = 'm';
		
		//le todos os caracteres do texto e atualisa a tabela
		for(int i = 0; i < tabela.tamLTexto; i++) {
			tabela = lerSimboloCodificaEAtualizaTabela(tabela, i);
		}
		
		/*
		//ler o texto caracter por caracter e atualisar a tabela do ppm
		for(int i = 0; i < tabela.lTexto.size(); i++) {
			
			Character cAux = tabela.lTexto.get(i);
			
			System.out.println("i = " + i);
			//char cAux = tabela.lTexto.get(i).charValue();
			//percorre as colunas de conteto da tabela
			for(int j = k; j >= 0; j--) {
				System.out.println("j = " + j);
				String contextoAux = new String();
				for(int w = i-k; w < i; w++ ) {
					try{
						contextoAux = contextoAux + tabela.lTexto.get(w).toString();
					
					} catch (IndexOutOfBoundsException e) {
						//System.out.println("deu erro try 1");
					}
				}
				System.out.println("ja tenho o contx"); //aqui eu jah tenho o contexto
				
				//System.out.println("j = "+j);
				try{
					//System.out.println( tabela.lContextos.get(j).lContexto.isEmpty() );
					
					if( existeContexto(tabela, contextoAux, j) ) {
						//passa direto
						System.out.println(contextoAux + " existe");
						
						
					} else {
						System.out.println(contextoAux + " nao existe");
						
						if( j == 0 ) {
							System.out.println("aaaaaaaaai");
							//coluna do k = 0
							if(existeSimbolo(tabela, cAux)) {
								
							}	
						} else {
							//demais colunas
							System.out.println("uuuuuuuuui");
						}	
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("deu erro minha try2");
				} 
			} //fim do for que percorre os contextos
		}
		 */
		
	}//fim do metodo main

	private static Tabela lerSimboloCodificaEAtualizaTabela(Tabela tabela, int indiceDeLeitura) {
		
		Character simbolo = tabela.lTexto.get(indiceDeLeitura);
		
		//for que percorre o array de contextos
		for(int i = tabela.lContextos.size()-1; i >= 0; i--) {
			boolean contextoValido = contextoValido( i+1, tabela.lTexto, indiceDeLeitura );
			if( contextoValido == true ) {
				String contextoAux = new String();
				for(int w = indiceDeLeitura-(i+1); w < indiceDeLeitura; w++ ) {
					contextoAux = contextoAux + tabela.lTexto.get(w).toString();
				}
				int indiceDoContextoNaColuna = existeContextoNaColuna(i, tabela.lContextos, contextoAux);
				//System.out.println(indiceDoContextoNaColuna);
				
				if( indiceDoContextoNaColuna >= 0 ) { //existe o contexto na coluna
					//int indiceDoSimboloNoContexto = existeSimboloNoContexto(simbolo, tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
					
					//pergunto se o contexto aux estah vasio
					if(tabela.contextoAuxParaExclusao.contexto == null) {  //fas normal

						int indiceDoSimboloNoContexto = existeSimboloNoContexto(simbolo, 
								tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
						if( indiceDoSimboloNoContexto >= 0 ) { //o simbolo existe no contexto
							//codifica
							int low = 0;
							int high = 0;
							int total = 0;
							for(int j = 0; j < indiceDoSimboloNoContexto; j++) {
								low = low + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(j).contador;
							}
							high = low + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador;
							for(int j = 0; j < tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.size(); j++) {
								total = total + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(j).contador;
							}
							//System.out.println("low: " + low);
							//System.out.println("high: " + high);
							//System.out.println("total: " + total);
							tabela.lInformacao.add( new Informacao( low, high, total ) );
							
							//atualisar contexto
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador++;
							//System.out.println(tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador);
							 
						} else { //o simbolo nao existe no contexto
							//codifica
							int low = 0;
							int high = 0;
							int total = 0;
							int indiceDoEscNoContexto = existeSimboloNoContexto((char) 27, tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo); 
							for(int j = 0; j < indiceDoEscNoContexto; j++) {
								low = low + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(j).contador;
							}
							high = low + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoEscNoContexto).contador;
							for(int j = 0; j < tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.size(); j++) {
								total = total + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(j).contador;
							}
							//System.out.println("low: " + low);
							//System.out.println("high: " + high);
							//System.out.println("total: " + total);
							tabela.lInformacao.add( new Informacao( low, high, total ) );
							
							//atualisei o contador do esc
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoEscNoContexto).contador++;
							
							//exclusao
							if( i > 0 ) { //trabalhando com contextos
								//copia o contexto atual
								tabela.contextoAuxParaExclusao = tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna);
								//diminui o contexto em 1
								tabela.contextoAuxParaExclusao.contexto = tabela.contextoAuxParaExclusao.contexto.substring(1);
								//exclui o esc do aux
								tabela.contextoAuxParaExclusao.lSimbolo.remove( 
										existeSimboloNoContexto((char) 27, tabela.contextoAuxParaExclusao.lSimbolo));
							} else { //trabalhando com simbolos
								//copia o simbolos atual
								tabela.simbolosAuxParaExclusao.lSimbolo = tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo;
								//exclui o esc do aux
								tabela.simbolosAuxParaExclusao.lSimbolo.remove( 
										existeSimboloNoContexto((char) 27, tabela.simbolosAuxParaExclusao.lSimbolo));
							}
						}
						
						
					} else { // fas com exclusao
						
						Contexto copia = tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna);
						for(int j = 0; j < copia.lSimbolo.size(); j++) {
							int indiceAux = existeSimboloNoContexto(copia.lSimbolo.get(j).simbolo, tabela.contextoAuxParaExclusao.lSimbolo);
							if( indiceAux >= 0 ) {
								copia.lSimbolo.remove(indiceAux);
							}
						}
						
						int indiceDoSimboloNoContexto = existeSimboloNoContexto(simbolo, 
								copia.lSimbolo);
						if( indiceDoSimboloNoContexto >= 0 ) { //o simbolo existe no contexto
							//codifica
							int low = 0;
							int high = 0;
							int total = 0;
							for(int j = 0; j < indiceDoSimboloNoContexto; j++) {
								low = low + copia.lSimbolo.get(j).contador;
							}
							high = low + copia.lSimbolo.get(indiceDoSimboloNoContexto).contador;
							for(int j = 0; j < copia.lSimbolo.size(); j++) {
								total = total + copia.lSimbolo.get(j).contador;
							}
							//System.out.println("low: " + low);
							//System.out.println("high: " + high);
							//System.out.println("total: " + total);
							tabela.lInformacao.add( new Informacao( low, high, total ) );
							
							//atualisar contexto
							indiceDoSimboloNoContexto = existeSimboloNoContexto(simbolo, 
									tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador++;
							//System.out.println(tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador);
							 
						} else { //o simbolo nao existe no contexto
							
							//----------------------------------------------------------------
							//----------------------------------------------------------------
							//----------------------------------------------------------------
							//----------------------------------------------------------------
							//----------------------------------------------------------------
							
							//codifica o esc
							int low = 0;
							int high = 0;
							int total = 0;
							int indiceDoEscNoContexto = existeSimboloNoContexto((char) 27, tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo); 
							for(int j = 0; j < indiceDoEscNoContexto; j++) {
								low = low + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(j).contador;
							}
							high = low + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoEscNoContexto).contador;
							for(int j = 0; j < tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.size(); j++) {
								total = total + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(j).contador;
							}
							//System.out.println("low: " + low);
							//System.out.println("high: " + high);
							//System.out.println("total: " + total);
							tabela.lInformacao.add( new Informacao( low, high, total ) );
							
							//atualisei o contador do esc
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoEscNoContexto).contador++;
							
							//adiciona o simbolo que nao existe no contexto
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.add( new Simbolo( simbolo ) );
							
							//exclusao
							if( i > 0 ) { //trabalhando com contextos
								//copia o contexto atual
								tabela.contextoAuxParaExclusao = tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna);
								//diminui o contexto em 1
								tabela.contextoAuxParaExclusao.contexto = tabela.contextoAuxParaExclusao.contexto.substring(1);
								//exclui o esc do aux
								tabela.contextoAuxParaExclusao.lSimbolo.remove( 
										existeSimboloNoContexto((char) 27, tabela.contextoAuxParaExclusao.lSimbolo));
							} else { //trabalhando com simbolos
								//copia o simbolos atual
								tabela.simbolosAuxParaExclusao.lSimbolo = tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo;
								//exclui o esc do aux
								tabela.simbolosAuxParaExclusao.lSimbolo.remove( 
										existeSimboloNoContexto((char) 27, tabela.simbolosAuxParaExclusao.lSimbolo));
							}
						}
						
						
						
					}// fim do else
					
					
				} else { // nao existe contexto
					//adiciona contexto
					tabela.lContextos.get(i).lContexto.add( new Contexto( contextoAux, simbolo ) );
					//System.out.println(contextoAux);
				}
			}
		}//fim do for que percorre as colunas
		
		//codigo da coluna do k = 0
		
		if( tabela.coluna0.lSimbolo.isEmpty() ) {  //o primeiro simbolo chegou
			System.out.println("uau");
		}
		
		int indiceAux2 = existeSimboloNoContexto(simbolo, tabela.coluna0.lSimbolo);
		if( indiceAux2 >= 0 ) { //o simbolo existe na coluna 0
			//codifica
			int low = 0;
			int high = 0;
			int total = 0;
			for(int j = 0; j < indiceAux2; j++) {
				low = low + tabela.coluna0.lSimbolo.get(j).contador;
			}
			high = low + tabela.coluna0.lSimbolo.get(indiceAux2).contador;
			for(int j = 0; j < tabela.coluna0.lSimbolo.size(); j++) {
				total = total + tabela.coluna0.lSimbolo.get(j).contador;
			}
			//System.out.println("low: " + low);
			//System.out.println("high: " + high);
			//System.out.println("total: " + total);
			tabela.lInformacao.add( new Informacao( low, high, total ) );
			
		} else { // o simbolo NAO existe na coluna 0
			
			//codifica o esc
			int low = 0;
			int high = 0;
			int total = 0;
			int indiceDoEscNosSimbolos = existeSimboloNoContexto((char) 27, tabela.coluna0.lSimbolo);
			System.out.println(indiceDoEscNosSimbolos);
			for(int j = 0; j < indiceDoEscNosSimbolos; j++) {
				low = low + tabela.coluna0.lSimbolo.get(j).contador;
			}
			high = low + tabela.coluna0.lSimbolo.get(indiceDoEscNosSimbolos).contador;
			for(int j = 0; j < tabela.coluna0.lSimbolo.size(); j++) {
				total = total + tabela.coluna0.lSimbolo.get(j).contador;
			}
			//System.out.println("low: " + low);
			//System.out.println("high: " + high);
			//System.out.println("total: " + total);
			tabela.lInformacao.add( new Informacao( low, high, total ) );
			
			//atualisei o contador do esc
			tabela.coluna0.lSimbolo.get(indiceDoEscNosSimbolos).contador++;
			
			//adiciona o simbolo que nao existe no contexto
			tabela.coluna0.lSimbolo.add( new Simbolo( simbolo ) );
			
			//ordena
			
		}
		
		return tabela;
	}

	private static int existeSimboloNoContexto(Character simbolo,
			ArrayList<Simbolo> lSimbolo) {
		
		if( lSimbolo.isEmpty() == true ) {
			return -1;
		} else {
			for(int i = 0; i < lSimbolo.size(); i++) {
				if( simbolo.equals( lSimbolo.get(i).simbolo ) == true ) {
					//System.out.println( lSimbolo.get(i).simbolo );
					return i;
				}
			}
			return -1;
		}// fim do else
	}//fim do metodo 

	private static int existeContextoNaColuna(int indice,
			ArrayList<Contextos> lContextos, String contextoAux) {
		
		if ( lContextos.get(indice).lContexto.isEmpty() == true ) {
			return -1;
		} else {
			
			for(int i = 0; i < lContextos.get(indice).lContexto.size(); i++ ) {
				if ( contextoAux.equals( lContextos.get(indice).lContexto.get(i).contexto ) ) {
					//System.out.println( lContextos.get(indice).lContexto.get(i).contexto );
					return i;
				}
			}
		}
		return -1;
	}

	private static boolean existeSimbolo(Tabela tabela, Character cAux) {
		System.out.println("metodo existeSimbolo() ");
		if(tabela.coluna0.lSimbolo.isEmpty()) {
			return false;
		}
		for(int i = 0; i < tabela.coluna0.lSimbolo.size(); i++) {
			if( cAux.toString().equals( tabela.coluna0.lSimbolo.get(i).toString() ) ) {
				System.out.println("simbolo encontrado = "+tabela.coluna0.lSimbolo.get(i).toString());
				return true;
			}
		}
		
		return false;
	}

	private static boolean contextoValido(int i, ArrayList<Character> lTexto, int indiceDeLeitura  ) {
		if( (indiceDeLeitura-i) < 0 ) {
			return false;
		}
		return true;
	}
	
}//fim da classe main
