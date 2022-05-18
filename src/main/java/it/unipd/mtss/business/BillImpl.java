////////////////////////////////////////////////////////////////////
// [RICCARDO] [MARTINELLO] [2009086]
// [SAMUELE] [RIZZATO] [1226307]
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.Order;
import it.unipd.mtss.model.User;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;

public class BillImpl implements Bill{
    
    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user) throws BillException {
        double total = 0;
        int processori = 0;
        int mouse = 0;
        int tastiere = 0;

        EItem ProcessoreMenoCostoso = null;
        EItem MouseMenoCostoso=null; 
        EItem TastieraMenoCostosa = null;
        EItem MouseOTastieraMenoCostosa = null;

        if(itemsOrdered == null) {
            throw new BillException("Lista nulla");
        }

        if(itemsOrdered.isEmpty()) {
            throw new BillException("Lista ordini vuota");
        }

        if (itemsOrdered.size() > 30) {
            throw new BillException("Limite ordine superato");
        }
        
        //Calcolo prezzo totale
        for (EItem item : itemsOrdered) {
            total += item.getPrice();
            if (item.getType() == ItemType.Processor) {
                processori++;

                if ((ProcessoreMenoCostoso == null) || (ProcessoreMenoCostoso.getPrice() > item.getPrice())) {
                    ProcessoreMenoCostoso = item;
                }
            }

            if (item.getType() == ItemType.Mouse) {
                mouse++;

                if ((MouseMenoCostoso == null) || (MouseMenoCostoso.getPrice() > item.getPrice())) {
                    MouseMenoCostoso = item;
                }
            }

            if (item.getType() == ItemType.Keyboard) {
                tastiere++;

                if ((TastieraMenoCostosa == null) || (TastieraMenoCostosa.getPrice() > item.getPrice())) {
                    TastieraMenoCostosa = item;
                }
            }

            if (item.getType() == ItemType.Keyboard || item.getType() == ItemType.Mouse){
                if ((MouseOTastieraMenoCostosa == null) || (MouseOTastieraMenoCostosa.getPrice() > item.getPrice())) {
                    MouseOTastieraMenoCostosa = item;
                }
            }
        }

        // sconto se più di 5 processori
        if (processori > 5) {
            total -= ProcessoreMenoCostoso.getPrice() * 0.5;
        }
        
        //sconto se più di 10 mouse
        if (mouse > 10) {
            total -= MouseMenoCostoso.getPrice() * 1;
        }

        //Sconto numero mouse uguale numero testiere
        if (tastiere == mouse && tastiere != 0) {
            total -= MouseOTastieraMenoCostosa.getPrice() * 1;
        }

        //Sconto del 10% se il totale supera i 1000 euro
        if(total > 1000){
            total -= total * 0.1;
        }     

        //Commissione di 2 euro se il prezzo è inferiore a 10 euro
        if(total < 10){
            total += 2;
        }
        
        return total;
    }

    public List<Order> getFreeOrders(List<Order> ordini) throws BillException {
        
        List<Order> ordiniGratis = new ArrayList<Order>();
        
        for (int i = 0; i < ordini.size(); i++) {
            
            if(ordini.get(i).getUser().getAge()<18 && //se minorenne
             ordini.get(i).getOrarioOrdine().isAfter(LocalTime.of(18,00,00,00)) && //dopo le 18
             ordini.get(i).getOrarioOrdine().isAfter(LocalTime.of(18,00,00,00))){ //prima delle 19
                
                ordiniGratis.add(ordini.get(i)); //ordine in regalo
            }
        }

        if(ordiniGratis.size() > 9){
            
            for(int i=0; i<10; i++) {
              //numero a caso tra 1 e numero ordini
              int randomIndex = (int)(ordiniGratis.size() * Math.random());
              if(ordiniGratis.get(randomIndex).getPrice() == 0) {
                  i--;
              }
              else {
              ordiniGratis.get(randomIndex).setPrice(0);
              }
            }
        }
        else {
            throw new BillException("Ordini insufficienti per regali");
        }
        
        return ordiniGratis;
    }
}