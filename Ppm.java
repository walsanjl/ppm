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
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
    	Object[] options = {"Comprimir", "Descomprimir", "Cancelar"};
    	
    	int resultado = JOptionPane.showOptionDialog(null,"Você deseja comprimir ou descomprimir?",
                                                          "PPM Compressor/Descompressor",
                                                          JOptionPane.YES_NO_CANCEL_OPTION,
                                                          JOptionPane.PLAIN_MESSAGE,
                                                          null,
                                                          options,
                                                          null
                                                    );
    	if(resultado == JOptionPane.YES_OPTION || resultado == JOptionPane.NO_OPTION)
    	{
    		
    		String pathFileSource = getPathToFileSource();
    		System.out.println("[Source] File path: " + pathFileSource);


    		if(resultado == JOptionPane.YES_OPTION)
    		{
                String pathFileTarget = getPathToFileTarget( pathFileSource );
                System.out.println("[Target] File path: " + pathFileTarget);
    			
                //Criacao do codificador aritmetico
        		ArithEncoder codificadorAritmetico;
				try {
					codificadorAritmetico = new ArithEncoder(new FileOutputStream(pathFileTarget));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        

        		//Codigo para o PPM Compressor
				//Deve vir aqui
    			
    		}
    		else
    		{
    			//Criacao do decodificador aritmetico
    			ArithDecoder decodificadorAritmetico;
				try {
					decodificadorAritmetico = new ArithDecoder(new FileInputStream(pathFileSource));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    			//Codigo para o PPM Descompressor
				//Deve vir aqui
    			
    		}
    		
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(null, "O programa está sendo encerrado!");
    	}


    }	
    
    private static String getPathToFileTarget( String pathFileSource ) 
	{
		//Pega a posicao do ultimo ponto da String 
		int pointPos = pathFileSource.lastIndexOf('.');
		
		//Se o caminho foi c:\teste.txt, a saida sera c:\teste.ppm
		return pathFileSource.substring(0, pointPos) + ".ppm";
	}
    
	private static String getPathToFileSource() 
	{
	
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
