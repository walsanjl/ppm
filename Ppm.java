/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Ppm 
{

	private static ArithEncoder codificadorAritmetico;
	private static ArithDecoder decodificadorAritmetico;
	
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
    	setupTabela();
    	
        String pathFileSource = getPathToFileSource();
        System.out.println("[Source] File path: " + pathFileSource);
        String pathFileTarget = getPathToFileTarget( pathFileSource );
        System.out.println("[Target] File path: " + pathFileTarget);
        
        montaTabela( pathFileSource, pathFileTarget );
        //Usando
        //codificadorAritmetico.encode(lowCount, highCount, totalCount);
        
        //E depois pra decodificar
        //int[3] counts;
        //removeSymbolFromStream(counts);
        //E depois eh so usar o counts 
    }

	private static void setupTabela()
	{
		try 
		{
			//Recebe arquivo onde sera salvo os codigos comprimidos
			codificadorAritmetico = new ArithEncoder(new FileOutputStream("res.ppm"));
			
			//Recebe o arquivo de onde sera extraido o codigo comprimido 
			decodificadorAritmetico = new ArithDecoder(new FileInputStream("res.ppm"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static String getPathToFileTarget( String pathFileSource ) 
	{
		//Pega a posicao do ultimo ponto da String 
		int pointPos = pathFileSource.lastIndexOf('.');
		
		//Se o caminho foi c:\teste.txt, a saida sera c:\teste.ppm
		return pathFileSource.substring(0, pointPos) + ".ppm";
	}

	private static void montaTabela(String fileSource, String fileTarget) 
	{
		
		
	}

	private static String getPathToFileSource() {
	
		JFileChooser janela = new JFileChooser();
		
		//Torna a janela visivel
		janela.setVisible(true);
		
		//Abre caixa de dialogo
		int valor = janela.showOpenDialog(null);
	
		//Se escolheu sim
		if(valor == JFileChooser.APPROVE_OPTION)
		{
			//Seleciona arquivo
			File file = janela.getSelectedFile();
			if(JOptionPane.showConfirmDialog(janela, "Arquivo " + file.getName() + " selecionado.", "Confirmação", JOptionPane.OK_OPTION) == JOptionPane.OK_OPTION)
			{
				return file.getPath();
			}
				
		}
		
		return null;
		
	}
    
}
