package br.com.celula.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;

public class SaveFile {

	
    public void criaArquivo(byte[] bytes, String caminho) throws IOException {
    	
         FileOutputStream fos = new FileOutputStream(caminho);
         fos.write(bytes); 
         fos.flush();
         fos.close();
       
     }      

    @SuppressWarnings("resource")
	public String criaArquivoTemp(FileUploadEvent e) throws IOException {
    	
    	InputStream fileContent=null;
    	OutputStream out=null;
    	ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();  
    	String caminho = sc.getRealPath("/resources/tmp"); 
    	String nomeArq = System.currentTimeMillis()+"_"+ e.getFile().getFileName();
		File folder = new File(caminho);
        if (!folder.exists())
             folder.mkdirs();		
        out= new FileOutputStream(new File(caminho+File.separator+nomeArq));
        fileContent= e.getFile().getInputstream();
        int read=0;
        final byte[] bytes= new byte[1024];
        while((read= fileContent.read(bytes))!=-1){
           out.write(bytes,0,read); 
        }
		return nomeArq;
    }
    
    public static void gravaArquivoWeb(byte[] bytes, String arquivo) {
        FileOutputStream fos; 
        try {
            fos = new FileOutputStream(arquivo);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }     

}
