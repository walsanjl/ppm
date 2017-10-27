import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.colloquial.arithcode.*;

public class Main {

	/**
	 * @author walsan
	 * @throws IOException 
	 * 
	 */
	public static void main(String[] args) throws IOException {
		//----- ATRIBUTOS -----//
		
		//}recebendo o valor de k
		//Scanner s = new Scanner( System.in );
		//System.out.print("Digite o valor de k: ");
		//int k = s.nextInt();
		int k = 2;
		//s.close();
		
		// criando a tabela
		Tabela tabela = new Tabela(k);
		
		//leitura do arquivo de texto
		String nomeDoArquivo = "/home/walsan/workspace/PPM/src/arquivosDeTexto/1mb.txt";
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
		tabela.tamLAlfabetoInicial = tabela.lAlfabeto.size();
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
		tabela.contador = tabela.tamLAlfabetoInicial;
		
		tabela.coluna0.lSimbolo.add( new Simbolo( tabela.lTexto.get(0), 1));
		//tabela.coluna0.lSimbolo.get(0).contador = 1;
		tabela.coluna0.lSimbolo.add( new Simbolo( new Character( (char) 27 ), 1 ));
		//tabela.coluna0.lSimbolo.get(1).contador = 1;
		
		int indiceNoAlfabeto = tabela.lAlfabeto.indexOf( tabela.lTexto.get(0) );
		System.out.println("indiceNoAlfabeto = " + indiceNoAlfabeto);
		
		//codifica o primeiro simbolo
		int low = indiceNoAlfabeto;
		int high = 0;
		int total = 0;
		high = low + 1;
		total = tabela.lAlfabeto.size();
		//System.out.println("low: " + low);
		//System.out.println("high: " + high);
		//System.out.println("total: " + total);
		tabela.lInformacao.add( new Informacao( low, high, total, tabela.coluna0.lSimbolo.get(0).simbolo ) );
		//remove o simbolo do alfabeto
		tabela.lAlfabeto.remove( indiceNoAlfabeto );
		//System.out.println(tabela.lAlfabeto.size());
		
		//tabela.lContextos.get(1).lContexto.add( new Contexto( "ma" ) );
		//tabela.lContextos.get(1).lContexto.get(0).lSimbolo.add( new Simbolo( 'm' ) );
		//tabela.lContextos.get(1).lContexto.get(0).lSimbolo.add( new Simbolo( 'd' ) );
		
		//le todos os caracteres do texto e atualisa a tabela
		for(int i = 1; i < tabela.tamLTexto; i++) {
			tabela = lerSimboloCodificaEAtualizaTabela(tabela, i);
		}
		
		//imprimeInformacao(tabela.lInformacao);
		
		OutputStream output;
		try {
            output = new FileOutputStream("/home/walsan/workspace/PPM/src/arquivosDeTexto/saidaEncoder.txt");
            //System.out.println("tchain");
            BitOutput bitOutPut = new BitOutput( output );
    		ArithEncoder arithEncoder = new ArithEncoder( bitOutPut );
    		for( int i = 0; i < tabela.lInformacao.size(); i++ ) {
    			arithEncoder.encode(tabela.lInformacao.get(i).low, tabela.lInformacao.get(i).high, 
    					tabela.lInformacao.get(i).total);
    			//System.out.println("Eu não dou nem cartáissssss");
    		}
    		
    		arithEncoder.close(); 	//esvaziar o buffer
    		output.close();	//
            
	     } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	     }
		
		System.out.println("tam do tempoKsssss = "+tabela.tempoKsssss.length);
		imprimeOTempoDeExecucao(tabela.tempoKsssss);
		
	}//fim do metodo main

	private static void imprimeOTempoDeExecucao(long[] tempoKsssss) {
		for( int i = 0; i < tempoKsssss.length; i++ ) {
			//System.out.println("k = "+i+" - "+tempoKsssss[i]+" mili seg");
			System.out.println("k = "+i+" - "+(tempoKsssss[i]/1000)+" seg");
			//System.out.println("k = "+i+" - "+(tempoKsssss[i]/60000)+" min");
		}
	}

	private static void imprimeInformacao(ArrayList<Informacao> lInformacao) {
		for(int i = 0; i < lInformacao.size(); i++) {
			System.out.println(lInformacao.get(i).simbolo);
			System.out.println("low = "+lInformacao.get(i).low);
			System.out.println("high = "+lInformacao.get(i).high);
			System.out.println("total = "+lInformacao.get(i).total);
			System.out.println();
		}
	}

	private static Tabela lerSimboloCodificaEAtualizaTabela(Tabela tabela, int indiceDeLeitura) {
		
		int podeCodificar = 1;
		
		Character simbolo = tabela.lTexto.get(indiceDeLeitura);
		
		//for que percorre o array de contextos
		for(int i = tabela.lContextos.size()-1; i >= 0; i--) {
			
			// comeca a contar o tempo de execucao do contexto atual
			long start = System.currentTimeMillis();
			
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
					
					
					//comeca aqui o codigo dos contextos sem exclusao
					int indiceDoSimboloNoContexto = existeSimboloByte((byte)simbolo.hashCode(), 
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
					if( indiceDoSimboloNoContexto >= 0 ) { //o simbolo existe no contexto
						
						if( podeCodificar == 1 ) {
							//codifica o simbolo
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
							tabela.lInformacao.add( new Informacao( low, high, total, simbolo ) );
							
							//atualisar contexto
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador++;
							//ordenar os simbolos
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo = ordenaSimbolos(
									tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
							
							//System.out.println(tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador);
							podeCodificar = -1;
						} else { // nao posso codificar, soh incrementar
							//atualisar contexto
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador++;
							//ordenar os simbolos
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo = ordenaSimbolos(
									tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
						}
						
						 
					} else { //o simbolo nao existe no contexto
						//codifica o esc
						int low = 0;
						int high = 0;
						int total = 0;
						//int indiceDoEscNoContexto = existeSimboloNoContexto((char) 27, tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
						int indiceDoEscNoContexto = existeSimboloByte((byte) 27, tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
						//System.out.println("----------------------------"+indiceDoEscNoContexto);
						for(int j = 0; j < indiceDoEscNoContexto; j++) {
							low = low + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(j).contador;
						}
						//ERRORRRRR AQUI!!!!!!!!!!###########################################################################
						//System.out.println("contESC "+tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoEscNoContexto).contador);
						high = low + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoEscNoContexto).contador;
						for(int j = 0; j < tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.size(); j++) {
							total = total + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(j).contador;
						}
						//System.out.println("low: " + low);
						//System.out.println("high: " + high);
						//System.out.println("total: " + total);
						tabela.lInformacao.add( new Informacao( low, high, total, (char) 27 ) );
						
						//atualisei o contador do esc
						tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoEscNoContexto).contador++;
						
						//adiciono o simbolo ao contexto
						tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.add( new Simbolo(simbolo, 1) );
						//ordenar os simbolos
						tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo = ordenaSimbolos(
								tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
						
						
						
						
					}					
					
					/*
					//pergunto se o contexto aux estah vasio
					if(tabela.contextoAuxParaExclusao.contexto == null) {  //fas normal

						int indiceDoSimboloNoContexto = existeSimboloByte((byte)simbolo.hashCode(), 
								tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
						if( indiceDoSimboloNoContexto >= 0 ) { //o simbolo existe no contexto
							
							if( podeCodificar == 1 ) {
								//codifica o simbolo
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
								tabela.lInformacao.add( new Informacao( low, high, total, simbolo ) );
								
								//atualisar contexto
								tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador++;
								//ordenar os simbolos
								tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo = ordenaSimbolos(
										tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
								
								//System.out.println(tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador);
								podeCodificar = -1;
							} else { // nao posso codificar, soh incrementar
								//atualisar contexto
								tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador++;
								//ordenar os simbolos
								tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo = ordenaSimbolos(
										tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
							}
							
							 
						} else { //o simbolo nao existe no contexto
							//codifica o esc
							int low = 0;
							int high = 0;
							int total = 0;
							//int indiceDoEscNoContexto = existeSimboloNoContexto((char) 27, tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
							int indiceDoEscNoContexto = existeSimboloByte((byte) 27, tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
							//System.out.println("----------------------------"+indiceDoEscNoContexto);
							for(int j = 0; j < indiceDoEscNoContexto; j++) {
								low = low + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(j).contador;
							}
							//ERRORRRRR AQUI!!!!!!!!!!###########################################################################
							//System.out.println("contESC "+tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoEscNoContexto).contador);
							high = low + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoEscNoContexto).contador;
							for(int j = 0; j < tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.size(); j++) {
								total = total + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(j).contador;
							}
							//System.out.println("low: " + low);
							//System.out.println("high: " + high);
							//System.out.println("total: " + total);
							tabela.lInformacao.add( new Informacao( low, high, total, (char) 27 ) );
							
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
										existeSimboloByte((byte) 27, tabela.contextoAuxParaExclusao.lSimbolo));
										//existeSimboloNoContexto((char) 27, tabela.contextoAuxParaExclusao.lSimbolo));
							} else { //trabalhando com simbolos
								//copia o simbolos atual
								tabela.simbolosAuxParaExclusao.lSimbolo = tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo;
								//exclui o esc do aux
								tabela.simbolosAuxParaExclusao.lSimbolo.remove(
										existeSimboloByte((byte) 27, tabela.simbolosAuxParaExclusao.lSimbolo));
										//existeSimboloNoContexto((char) 27, tabela.simbolosAuxParaExclusao.lSimbolo));
							}
							
							//adiciono o simbolo ao contexto
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.add( new Simbolo(simbolo, 1) );
							//ordenar os simbolos
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo = ordenaSimbolos(
									tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
							
							
							
							
						}
						
						
					} else { // fas com exclusao
						
						//crio um contexto auxiliar para a codificacao com exclusao
						Contexto copia = tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna);
						System.out.println("copia no contexto");
						imprimeSimbolosDoContexto(copia);
						
						
						int indiceDoSimboloNoContexto = existeSimboloByte((byte)simbolo.hashCode(), 
								copia.lSimbolo);
						if( indiceDoSimboloNoContexto >= 0 ) { //o simbolo existe no contexto
							
							if(podeCodificar == 1) {	//posso codificar
								//codifica o simbolo
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
								tabela.lInformacao.add( new Informacao( low, high, total, simbolo ) );
								
								//atualisar contexto original
								indiceDoSimboloNoContexto = existeSimboloByte((byte)simbolo.hashCode(), 
										tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
								tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador++;
								//System.out.println(tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoSimboloNoContexto).contador);
								
								//ordena os simbolos do contexto original
								tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo = ordenaSimbolos(
										tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
								podeCodificar = -1;
								
							}
							
						} else { //o simbolo nao existe no contexto
							//codifica o esc
							int low = 0;
							int high = 0;
							int total = 0;
							//int indiceDoEscNoContexto = existeSimboloNoContexto((char) 27, tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo); 
							//int indiceDoEscNoContexto = existeSimboloNoContexto((char) 27, copia.lSimbolo);
							int indiceDoEscNoContexto = existeSimboloByte((byte) 27, copia.lSimbolo);
							for(int j = 0; j < indiceDoEscNoContexto; j++) {
								//low = low + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(j).contador;
								low = low + copia.lSimbolo.get(j).contador;
							}
							//System.out.println(low);
							//System.out.println( "copia contexto"+copia.contexto.isEmpty() );
							//System.out.println( "copia contador"+copia.lSimbolo.get(0).contador );
							//System.out.println("contador do esc = "+ copia.lSimbolo.get(indiceDoEscNoContexto).contador);
							//high = low + tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.get(indiceDoEscNoContexto).contador;
							high = low + copia.lSimbolo.get(indiceDoEscNoContexto).contador;
							
							for(int j = 0; j < copia.lSimbolo.size(); j++) {
								total = total + copia.lSimbolo.get(j).contador;
							}
							//System.out.println("low: " + low);
							//System.out.println("high: " + high);
							//System.out.println("total: " + total);
							tabela.lInformacao.add( new Informacao( low, high, total, (char) 27 ) );
							
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
										existeSimboloByte((byte) 27, tabela.contextoAuxParaExclusao.lSimbolo));
										//existeSimboloNoContexto((char) 27, tabela.contextoAuxParaExclusao.lSimbolo));
							} else { //trabalhando com simbolos
								//copia o simbolos atual
								tabela.simbolosAuxParaExclusao.lSimbolo = tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo;
								//exclui o esc do aux
								tabela.simbolosAuxParaExclusao.lSimbolo.remove(
										existeSimboloByte((byte) 27, tabela.simbolosAuxParaExclusao.lSimbolo));
										//existeSimboloNoContexto((char) 27, tabela.simbolosAuxParaExclusao.lSimbolo));
							}
							
							//adiciona o simbolo que nao existe no contexto
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo.add( new Simbolo( simbolo, 1 ) );
							
							//ordena os simbolos
							tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo = ordenaSimbolos(
									tabela.lContextos.get(i).lContexto.get(indiceDoContextoNaColuna).lSimbolo);
							
							
							
							
						}// fim do else do nao existe no contexto
						
						
						
					}// fim do else
					*/
					
				} else { // nao existe contexto
					//adiciona um novo contexto já com o simbolo e com o esc
					tabela.lContextos.get(i).lContexto.add( new Contexto( contextoAux, simbolo ) );
					
					//System.out.println(contextoAux);
				}
			}//fim do for que percorre as colunas
			
			// termina de contar o tempo de execucao do contexto atual
			long elapsed = System.currentTimeMillis() - start;
			//incrementa o tempo da execucao desse contexto para o k
			//tabela.tempoKsssss[i] = tabela.tempoKsssss[i] + (elapsed/60000);	//tempo em minutos
			//tabela.tempoKsssss[i] = tabela.tempoKsssss[i] + (elapsed/1000);	//tempo em segundos
			tabela.tempoKsssss[i] = tabela.tempoKsssss[i] + (elapsed);	//tempo em millisegundos
			
		}//fim do if que valida o contexto
		
		
		//codigo da coluna do k = 0
		
		// comeca a contar o tempo de execucao do contexto k = 0
		long start = System.currentTimeMillis();
		
		//comeca o codigo do k = 0 sem exclusao
		int indiceAux2 = existeSimboloByte((byte)simbolo.hashCode(), tabela.coluna0.lSimbolo);
		if( indiceAux2 >= 0 ) { //o simbolo existe na coluna 0
			
			if(podeCodificar == 1) {	//posso codificar
				//codifica o simbolo
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
				tabela.lInformacao.add( new Informacao( low, high, total, simbolo ) );
				
				//incrementa contador do simbolo
				tabela.coluna0.lSimbolo.get( indiceAux2 ).contador++;
				
				//ordena os simbolos
				tabela.coluna0.lSimbolo = ordenaSimbolos( tabela.coluna0.lSimbolo );
				
				//podeCodificar = -1;
				
			} else {	//nao posso codificar
				//incrementa contador do simbolo
				tabela.coluna0.lSimbolo.get( indiceAux2 ).contador++;
				
				//ordena os simbolos
				tabela.coluna0.lSimbolo = ordenaSimbolos( tabela.coluna0.lSimbolo );
			}
			
		} else { // o simbolo NAO existe na coluna 0
			//System.out.println("----------------------------rains");
			//codifica o esc
			int low = 0;
			int high = 0;
			int total = 0;
			//int indiceDoEscNosSimbolos = existeSimboloNoContexto((char) 27, tabela.coluna0.lSimbolo);
			int indiceDoEscNosSimbolos = existeSimboloByte((byte) 27, tabela.coluna0.lSimbolo);
			//System.out.println(indiceDoEscNosSimbolos);
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
			tabela.lInformacao.add( new Informacao( low, high, total, (char) 27 ) );
			
			//atualisei o contador do esc
			tabela.coluna0.lSimbolo.get(indiceDoEscNosSimbolos).contador++;
			
			//se o contador do esco for igual ao tamAlfabetoInicial eu removo o esc dos simbolos
			if( tabela.coluna0.lSimbolo.get(indiceDoEscNosSimbolos).contador == tabela.tamLAlfabetoInicial ) {
				tabela.coluna0.lSimbolo.remove( indiceDoEscNosSimbolos );
			}
			
			//codifica o simbolo baseado no alfabeto
			int indiceNoAlfabeto = tabela.lAlfabeto.indexOf( simbolo );
			//System.out.println("simbolo = "+simbolo);
			//System.out.println("coluna 0 nao eiste simbolo = indiceNoAlfabeto = "+indiceNoAlfabeto);
			low = indiceNoAlfabeto;
			high = 0;
			total = 0;
			high = low + 1;
			total = tabela.lAlfabeto.size();
			//System.out.println("low: " + low);
			//System.out.println("high: " + high);
			//System.out.println("total: " + total);
			tabela.lInformacao.add( new Informacao( low, high, total, simbolo ) );
			
			//adiciona o simbolo que nao existe na tabela de simbolos
			tabela.coluna0.lSimbolo.add( new Simbolo( simbolo, 1 ) );
			//tabela.coluna0.lSimbolo.get((tabela.coluna0.lSimbolo.size() - 1)).contador = 1;
			//tabela.coluna0.lSimbolo.get( existeSimboloNoContexto(simbolo, tabela.coluna0.lSimbolo) ).contador = 1;
			
			//exclui o simbolo do alfabeto
			tabela.lAlfabeto.remove( indiceNoAlfabeto );
			
			//ordena os simbolos
			tabela.coluna0.lSimbolo = ordenaSimbolos( tabela.coluna0.lSimbolo );
			
		}
		
		/*
		//pergunto se o simbolosAuxParaExclusao aux estah vasio
		if(tabela.simbolosAuxParaExclusao.lSimbolo == null) {	//fas normal
			int indiceAux2 = existeSimboloByte((byte)simbolo.hashCode(), tabela.coluna0.lSimbolo);
			if( indiceAux2 >= 0 ) { //o simbolo existe na coluna 0
				
				if(podeCodificar == 1) {	//posso codificar
					//codifica o simbolo
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
					tabela.lInformacao.add( new Informacao( low, high, total, simbolo ) );
					
					//incrementa contador do simbolo
					tabela.coluna0.lSimbolo.get( indiceAux2 ).contador++;
					
					//ordena os simbolos
					tabela.coluna0.lSimbolo = ordenaSimbolos( tabela.coluna0.lSimbolo );
					
					//podeCodificar = -1;
					
				} else {	//nao posso codificar
					//incrementa contador do simbolo
					tabela.coluna0.lSimbolo.get( indiceAux2 ).contador++;
					
					//ordena os simbolos
					tabela.coluna0.lSimbolo = ordenaSimbolos( tabela.coluna0.lSimbolo );
				}
				
			} else { // o simbolo NAO existe na coluna 0
				System.out.println("----------------------------rains");
				//codifica o esc
				int low = 0;
				int high = 0;
				int total = 0;
				//int indiceDoEscNosSimbolos = existeSimboloNoContexto((char) 27, tabela.coluna0.lSimbolo);
				int indiceDoEscNosSimbolos = existeSimboloByte((byte) 27, tabela.coluna0.lSimbolo);
				//System.out.println(indiceDoEscNosSimbolos);
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
				tabela.lInformacao.add( new Informacao( low, high, total, (char) 27 ) );
				
				//atualisei o contador do esc
				tabela.coluna0.lSimbolo.get(indiceDoEscNosSimbolos).contador++;
				
				//se o contador do esco for igual ao tamAlfabetoInicial eu removo o esc dos simbolos
				if( tabela.coluna0.lSimbolo.get(indiceDoEscNosSimbolos).contador == tabela.tamLAlfabetoInicial ) {
					tabela.coluna0.lSimbolo.remove( indiceDoEscNosSimbolos );
				}
				
				//codifica o simbolo baseado no alfabeto
				int indiceNoAlfabeto = tabela.lAlfabeto.indexOf( simbolo );
				//System.out.println("simbolo = "+simbolo);
				//System.out.println("coluna 0 nao eiste simbolo = indiceNoAlfabeto = "+indiceNoAlfabeto);
				low = indiceNoAlfabeto;
				high = 0;
				total = 0;
				high = low + 1;
				total = tabela.lAlfabeto.size();
				//System.out.println("low: " + low);
				//System.out.println("high: " + high);
				//System.out.println("total: " + total);
				tabela.lInformacao.add( new Informacao( low, high, total, simbolo ) );
				
				//adiciona o simbolo que nao existe na tabela de simbolos
				tabela.coluna0.lSimbolo.add( new Simbolo( simbolo, 1 ) );
				//tabela.coluna0.lSimbolo.get((tabela.coluna0.lSimbolo.size() - 1)).contador = 1;
				//tabela.coluna0.lSimbolo.get( existeSimboloNoContexto(simbolo, tabela.coluna0.lSimbolo) ).contador = 1;
				
				//exclui o simbolo do alfabeto
				tabela.lAlfabeto.remove( indiceNoAlfabeto );
				
				//ordena os simbolos
				tabela.coluna0.lSimbolo = ordenaSimbolos( tabela.coluna0.lSimbolo );
				
			}
		} else {	 // fas com exclusao
			Simbolos copia = tabela.coluna0;
			System.out.println("copia do simbolos");
			imprimeSimbolos(copia);
			
			int indiceAux2 = existeSimboloByte((byte)tabela.hashCode(), copia.lSimbolo);
			if( indiceAux2 >= 0 ) { //o simbolo existe na coluna 0
				
				//codifica o simbolo
				int low = 0;
				int high = 0;
				int total = 0;
				for(int j = 0; j < indiceAux2; j++) {
					low = low + copia.lSimbolo.get(j).contador;
				}
				high = low + copia.lSimbolo.get(indiceAux2).contador;
				for(int j = 0; j < copia.lSimbolo.size(); j++) {
					total = total + copia.lSimbolo.get(j).contador;
				}
				//System.out.println("low: " + low);
				//System.out.println("high: " + high);
				//System.out.println("total: " + total);
				tabela.lInformacao.add( new Informacao( low, high, total, simbolo ) );

				
				//incrementa contador do simbolo no original
				int indiceDoSimbolo = existeSimboloByte((byte)tabela.hashCode(), tabela.coluna0.lSimbolo);
				tabela.coluna0.lSimbolo.get( indiceDoSimbolo ).contador++;
				
				//ordena os simbolos
				tabela.coluna0.lSimbolo = ordenaSimbolos( tabela.coluna0.lSimbolo );
				
			} else { // o simbolo NAO existe na coluna 0
				
				//System.out.println("simbolo ----------------------  "+copia.lSimbolo.get(1).simbolo);
				
				//codifica o esc
				int low = 0;
				int high = 0;
				int total = 0;
				//int indiceDoEscNosSimbolos = existeSimboloNoContexto((char) 27, copia.lSimbolo);
				
				int indiceDoEscNosSimbolos = 0;
				for( int i = 0; i < copia.lSimbolo.size(); i++ ) {
					int esc = copia.lSimbolo.get(i).simbolo.hashCode();
					//System.out.println("==============================================="+esc);
					if(esc == 27) {
						//System.out.println("===============================================");
						indiceDoEscNosSimbolos = i;
					}
				}
				
				//int indiceDoEscNosSimbolos = existeSimboloNoContexto( new Character( (char) 27 ) , copia.lSimbolo);
				//System.out.println("indiceDoEscNosSimbolos = "+indiceDoEscNosSimbolos);
				//System.out.println(indiceDoEscNosSimbolos);
				for(int j = 0; j < indiceDoEscNosSimbolos; j++) {
					low = low + copia.lSimbolo.get(j).contador;
				}
				//System.out.println("indiceESC "+indiceDoEscNosSimbolos);
				//System.out.println(copia.lSimbolo.size());
				high = low + copia.lSimbolo.get(indiceDoEscNosSimbolos).contador;
				for(int j = 0; j < copia.lSimbolo.size(); j++) {
					total = total + copia.lSimbolo.get(j).contador;
				}
				//System.out.println("low: " + low);
				//System.out.println("high: " + high);
				//System.out.println("total: " + total);
				tabela.lInformacao.add( new Informacao( low, high, total, (char) 27 ) );
				
				
				//atualisei o contador do esc no array de simbolos original
				//indiceDoEscNosSimbolos = existeSimboloNoContexto((char) 27, tabela.coluna0.lSimbolo);
				for( int i = 0; i < tabela.coluna0.lSimbolo.size(); i++ ) {
					int esc = tabela.coluna0.lSimbolo.get(i).simbolo.hashCode();
					//System.out.println("==============================================="+esc);
					if(esc == 27) {
						//System.out.println("===============================================");
						indiceDoEscNosSimbolos = i;
					}
				}
				tabela.coluna0.lSimbolo.get(indiceDoEscNosSimbolos).contador++;
				
				//se o contador do esc for igual ao tamAlfabetoInicial eu removo o esc dos simbolos
				if( tabela.coluna0.lSimbolo.get(indiceDoEscNosSimbolos).contador == tabela.tamLAlfabetoInicial ) {
					tabela.coluna0.lSimbolo.remove( indiceDoEscNosSimbolos );
				}
				
				//codifica o simbolo baseado no alfabeto no array de simbolos original
				int indiceNoAlfabeto = tabela.lAlfabeto.indexOf( simbolo );
				//System.out.println("simbolo = "+simbolo);
				//System.out.println("coluna 0 nao eiste simbolo = indiceNoAlfabeto = "+indiceNoAlfabeto);
				low = indiceNoAlfabeto;
				high = 0;
				total = 0;
				high = low + 1;
				total = tabela.lAlfabeto.size();
				//System.out.println("low: " + low);
				//System.out.println("high: " + high);
				//System.out.println("total: " + total);
				tabela.lInformacao.add( new Informacao( low, high, total, simbolo ) );
				
				//adiciona o simbolo que nao existe na tabela de simbolos
				tabela.coluna0.lSimbolo.add( new Simbolo( simbolo,1 ) );
				//tabela.coluna0.lSimbolo.get((tabela.coluna0.lSimbolo.size() - 1)).contador = 1;
				//tabela.coluna0.lSimbolo.get( existeSimboloNoContexto(simbolo, tabela.coluna0.lSimbolo) ).contador = 1;

				//exclui o simbolo do alfabeto
				//System.out.println("valor do indiceNoAlfabeto = "+indiceNoAlfabeto);
				if(indiceNoAlfabeto >= 0) {
					tabela.lAlfabeto.remove( indiceNoAlfabeto );
				}
				
				
				//ordena os simbolos
				tabela.coluna0.lSimbolo = ordenaSimbolos( tabela.coluna0.lSimbolo );
				
			}
		}
		*/
		
		// termina de contar o tempo de execucao do contexto k = 0
		long elapsed = System.currentTimeMillis() - start;
		//incrementa o tempo da execucao desse contexto para o k
		//tabela.tempoKsssss[0] = tabela.tempoKsssss[0] + (elapsed/60000);	//tempo em minutos
		//tabela.tempoKsssss[0] = tabela.tempoKsssss[0] + (elapsed/1000);	//tempo em segundos
		tabela.tempoKsssss[0] = tabela.tempoKsssss[0] + (elapsed);	//tempo em segundos
		
		
		tabela.contextoAuxParaExclusao = new Contexto();
		tabela.simbolosAuxParaExclusao = new Simbolos();
		
		return tabela;
	}

	private static void imprimeSimbolosDoContexto(Contexto copia) {
		for( int i = 0; i < copia.lSimbolo.size(); i++ ) {
			System.out.print(""+copia.lSimbolo.get(i).simboloByte+" ");
		}
		System.out.println();
		
	}

	private static void imprimeSimbolos(Simbolos copia) {
		for( int i = 0; i < copia.lSimbolo.size(); i++ ) {
			System.out.print(""+copia.lSimbolo.get(i).simboloByte+" ");
		}
		System.out.println();
	}

	private static int existeSimboloNoContexto(Character simbolo,
			ArrayList<Simbolo> lSimbolo) {
		if( lSimbolo.isEmpty() == true ) {
			//System.out.println("aaaaaaaaaaaaaaaaaaiiiiiiiii");
			return -1;
		} else {
			
			for(int i = 0; i < lSimbolo.size(); i++) {
				//System.out.println( "simbolo =  "+lSimbolo.get(i).simbolo.hashCode() );
				//if( simbolo.equals( lSimbolo.get(i).simbolo ) == true ) {
				//if( simbolo.equals( lSimbolo.get(i).simbolo.toString() ) == true ) {
				if( simbolo == lSimbolo.get(i).simbolo ) {
					//System.out.println( "simbolo =  "+lSimbolo.get(i).simbolo );
					return i;
				}
			}
			/*
			for( int i = 0; i < lSimbolo.size(); i++ ) {
				int esc = lSimbolo.get(i).simbolo.hashCode();
				//System.out.println("==============================================="+esc);
				if(esc == 27) {
					//System.out.println("===============================================");
					return i;
				}
			}*/
			//System.out.println("uuuuuuuuuuuuuuuuiiiiiiiii");
			return -1;
		}// fim do else
	}//fim do metodo 
	
	private static int existeSimboloByte(byte simbolo,
			ArrayList<Simbolo> lSimbolo) {
		if( lSimbolo.isEmpty() == true ) {
			//System.out.println("aaaaaaaaaaaaaaaaaaiiiiiiiii");
			return -1;
		} else {
			
			for(int i = 0; i < lSimbolo.size(); i++) {
				//System.out.println( "simbolo =  "+lSimbolo.get(i).simbolo.hashCode() );
				//if( simbolo.equals( lSimbolo.get(i).simbolo ) == true ) {
				//if( simbolo.equals( lSimbolo.get(i).simbolo.toString() ) == true ) {
				if( simbolo == lSimbolo.get(i).simboloByte ) {
					//System.out.println( "simbolo =  "+lSimbolo.get(i).simbolo );
					return i;
				}
			}
			/*
			for( int i = 0; i < lSimbolo.size(); i++ ) {
				int esc = lSimbolo.get(i).simbolo.hashCode();
				//System.out.println("==============================================="+esc);
				if(esc == 27) {
					//System.out.println("===============================================");
					return i;
				}
			}*/
			//System.out.println("uuuuuuuuuuuuuuuuiiiiiiiii");
			return -1;
		}// fim do else
	}//fim do metodo existeSimboloByte
	
	private static int existeEscNoContexto(Character simbolo,
			ArrayList<Simbolo> lSimbolo) {
		if( lSimbolo.isEmpty() == true ) {
			//System.out.println("aaaaaaaaaaaaaaaaaaiiiiiiiii");
			return -1;
		} else {
			/*
			for(int i = 0; i < lSimbolo.size(); i++) {
				System.out.println( "simbolo =  "+lSimbolo.get(i).simbolo.hashCode() );
				//if( simbolo.equals( lSimbolo.get(i).simbolo ) == true ) {
				//if( simbolo.equals( lSimbolo.get(i).simbolo.toString() ) == true ) {
				if( simbolo == lSimbolo.get(i).simbolo ) {
					System.out.println( "simbolo =  "+lSimbolo.get(i).simbolo );
					return i;
				}
			}*/
			
			for( int i = 0; i < lSimbolo.size(); i++ ) {
				int esc = lSimbolo.get(i).simbolo.hashCode();
				//System.out.println("==============================================="+esc);
				if(esc == 27) {
					System.out.println("===============================================");
					return i;
				}
			}
			//System.out.println("uuuuuuuuuuuuuuuuiiiiiiiii");
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

	private static boolean contextoValido(int i, ArrayList<Character> lTexto, int indiceDeLeitura  ) {
		if( (indiceDeLeitura-i) < 0 ) {
			return false;
		}
		return true;
	}
	
	private static ArrayList<Simbolo> ordenaSimbolos(ArrayList<Simbolo> lSimbolo){
		
		//if( existeSimboloNoContexto((char) 27, lSimbolo) >= 0 ) { //tem esc nos simbolos
		if( existeSimboloByte((byte) 27, lSimbolo) >= 0 ) { //tem esc nos simbolos
			/*
			//printando a lSimbolo para debug
			for(int i = 0; i < lSimbolo.size(); i++) {
				System.out.print( lSimbolo.get(i).simbolo );
			}
			System.out.println();*/
			
			//lSimbolo.remove(lSimbolo.size() - 1); // exclui o esc
			//int indiceDoEscNosSimbolos = existeSimboloNoContexto((char) 27, lSimbolo);
			int indiceDoEscNosSimbolos = existeSimboloByte((byte) 27, lSimbolo);
			Simbolo escAux = lSimbolo.get( indiceDoEscNosSimbolos );
			lSimbolo.remove(indiceDoEscNosSimbolos); // exclui o esc para nao atraaplhar na ordenacao
			
			/*
			//printando a lSimbolo para debug
			for(int i = 0; i < lSimbolo.size(); i++) {
				System.out.print( lSimbolo.get(i).simbolo );
			}
			System.out.println();*/
			
			for(int i = 0; i < lSimbolo.size(); i++ ){
				Collections.sort(lSimbolo);
			}
			
			ArrayList<Simbolo> lSimboloAux = new ArrayList<Simbolo>();
			while( lSimbolo.isEmpty() == false ) {
				int contadorAux = 0;
				int indiceAux = 0;
				//for para encontrar o simbolo com maior contador
				for( int i = 0; i < lSimbolo.size(); i++ ) {
					if(lSimbolo.get(i).contador > contadorAux) {
						contadorAux = lSimbolo.get(i).contador;
						indiceAux = i;
					}
				}// final do for
				lSimboloAux.add( lSimbolo.get(indiceAux) ); 	//adiciono o simbolo com maior contador no finall
				lSimbolo.remove(indiceAux);		//removo o simbolo do lSimbolo
			}
			
			lSimbolo = lSimboloAux;
			
			lSimbolo.add( escAux );	//adicionando o esc no final de lSimbolo
			
			/*
			//printando a lSimbolo para debug
			for(int i = 0; i < lSimbolo.size(); i++) {
				System.out.print( lSimbolo.get(i).simbolo );
			}
			System.out.println();*/
			
			return lSimbolo;
		} else { //nao tem esc nos simbolos
			for(int i = 0; i < lSimbolo.size(); i++ ){
				Collections.sort(lSimbolo);
			}
			
			ArrayList<Simbolo> lSimboloAux = new ArrayList<Simbolo>();
			while( lSimbolo.isEmpty() == false ) {
				int contadorAux = 0;
				int indiceAux = 0;
				//for para encontrar o simbolo com maior contador
				for( int i = 0; i < lSimbolo.size(); i++ ) {
					if(lSimbolo.get(i).contador > contadorAux) {
						contadorAux = lSimbolo.get(i).contador;
						indiceAux = i;
					}
				}// final do for
				lSimboloAux.add( lSimbolo.get(indiceAux) ); 	//adiciono o simbolo com maior contador no finall
				lSimbolo.remove(indiceAux);		//removo o simbolo do lSimbolo
			}
			
			lSimbolo = lSimboloAux;
			
			return lSimbolo;
		}
		

	}
}//fim da classe main
