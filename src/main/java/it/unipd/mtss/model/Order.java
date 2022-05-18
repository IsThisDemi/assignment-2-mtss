////////////////////////////////////////////////////////////////////
// [RICCARDO] [MARTINELLO] [2009086]
// [SAMUELE] [RIZZATO] [1226307]
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.model;

import java.util.List;
import java.time.LocalTime;

public class Order {
    List<EItem> listaElementi; 
    User utente;
    LocalTime orarioOrdine;
    double price; 
    
    public Order(List<EItem> listaElementi, 
            User utente,
            LocalTime orarioOrdine,
            double price) {
        if(listaElementi.isEmpty()) {
            throw new IllegalArgumentException("Elenco vuoto");
        }
        if(utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        if(orarioOrdine == null) {
            throw new IllegalArgumentException("Orario non valido");
        }
        this.listaElementi = listaElementi; 
        this.utente = utente;
        this.orarioOrdine = orarioOrdine;
        this.price = price;
    }
    
    public double getPrice() {
        return price; 
    }
    
    public LocalTime getOrarioOrdine() {
        return orarioOrdine;
    }
    
    public User getUser() {
        return utente;
    }
    
    public List<EItem> getListaElementi(){
        return listaElementi;
    }
    
    public void setPrice(double newPrice) {
        price = newPrice;
    }
}