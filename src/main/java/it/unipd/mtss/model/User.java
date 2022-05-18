////////////////////////////////////////////////////////////////////
// [RICCARDO] [MARTINELLO] [2009086]
// [SAMUELE] [RIZZATO] [1226307]
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.model;

import java.time.LocalDate;

public class User {
    String username, Nome, Cognome;
    LocalDate dataNascita;

    public User(String username, 
            String Nome, 
            String Cognome, 
            LocalDate dataNascita) {
        if(username == null) {
            throw new IllegalArgumentException("Nickname non valido");
        }
        if(Nome == null) {
            throw new IllegalArgumentException("Nome non valido");
        }
        if(Cognome == null) {
            throw new IllegalArgumentException("Cognome non valido");
        }
        if(dataNascita == null) {
            throw new IllegalArgumentException("Data di nascita non valida");
        }
        this.username = username;
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.dataNascita = dataNascita;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getName() {
        return Nome;
    }
    
    public String getSurname() {
        return Cognome;
    }
    
    public int getAge() {
        return LocalDate.now().getYear()-dataNascita.getYear();
    }
}