package br.com.celula.bean;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
 
@ManagedBean(name="imagemMB")
public class ImagemMB {
     
    private List<String> images;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 0; i < 7; i++) 
            images.add("e" + i + ".jpg");
        
    }
 
    public List<String> getImages() {
        return images;
    }
}