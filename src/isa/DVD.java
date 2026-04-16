/*
This Page was coded by Lacie Carey.
 */
package isa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class DVD extends Item {
    private String director;
    private String[] audioLanguages;
    
    public DVD (String title, String director, Member donatedBy, String language, String[] audioLanguages){
        super(title, language, donatedBy);
        this.director = director;
        this.audioLanguages = new String[audioLanguages.length];
        for (int i = 0; i < audioLanguages.length; i++) {
            this.audioLanguages[i] = audioLanguages[i];
}

    }
    
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String[] getAudioLanguages() {
        return Arrays.copyOf(audioLanguages, audioLanguages.length);
    }

    public void setAudioLanguages(String[] languages) {
        this.audioLanguages = Arrays.copyOf(languages, languages.length);
    }

    @Override
    public String toString() {
        return "DVD: " + getTitle() + " (" + director + ")";
    }
}

