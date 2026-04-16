/*
This Page was coded by Lacie Carey.
 */
package isa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DVD extends Item {
    private String director;
    private List<String> audioLanguages;
    
    public DVD (String title, String caseLanguage, String director, List<String> audioLanguages, Member donor){
        super(title, caseLanguage, donor);
        this.director = director;
        this.audioLanguages = new ArrayList<>(audioLanguages);
    }
    
    public String getDirector(){
        return director;
    }
    
    public void setDirector(String director){
        this.director = director;
    }
    
    public List<String> getAudioLanguages(){
        return Collections.unmodifiableList(audioLanguages);
    }
    
    public void addAudioLanguage(String language){
        audioLanguages.add(language);
    }
    
    public void removeAudioLanguage(String language){
        audioLanguages.remove(language);
    }
    
    @Override
    public String toString(){
        return "DVD: " + getTitle() + " (" + director + ")";
    }
    
}
