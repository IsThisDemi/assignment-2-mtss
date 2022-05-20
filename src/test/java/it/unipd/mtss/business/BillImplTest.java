////////////////////////////////////////////////////////////////////
// [RICCARDO] [MARTINELLO] [2009086]
// [SAMUELE] [RIZZATO] [1226307]
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.Order;
import it.unipd.mtss.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BillImplTest {

    private List<EItem> itemsOrdered;
    private BillImpl testBill; 
    private LocalTime oreSeiMezza;
    private User user; 

    @Before
    public void setup() {
        itemsOrdered = new ArrayList<EItem>();
        testBill = new BillImpl();
        oreSeiMezza = LocalTime.of(18,30,00,00);
        user = new User("IsThisDemi","Riccardo","Martinello",LocalDate.of(2001, 2, 10));
    }
    
    @Test
    public void testCalcoloDelTotale(){
        
        itemsOrdered.add(new EItem( ItemType.Processor, "Pintel e700",110.00));
        itemsOrdered.add(new EItem( ItemType.Motherboard, "Azuz Pro", 60.00));
        itemsOrdered.add(new EItem( ItemType.Mouse, "Fazer Neo", 5.00));
        itemsOrdered.add(new EItem( ItemType.Mouse, "Fazer Neo", 30.00)); // aggiungo altro mouse per non interferire con il test di MouseUgualiTastiere
        itemsOrdered.add(new EItem( ItemType.Keyboard, "Drevo Key", 45.00));
        
        assertEquals(250, testBill.getOrderPrice(itemsOrdered,user), 0.0);
    }
    
    @Test(expected=BillException.class)
    public void testCalcoloDelTotaleConListaOrdiniVuota() {
        
        testBill.getOrderPrice(itemsOrdered, user);
    }
    
    @Test(expected=BillException.class)
    public void testCalcoloDelTotaleConListaOrdiniNulla() {
        itemsOrdered = null;
        testBill.getOrderPrice(itemsOrdered, user);
    }

    @Test
    public void testTotaleConScontoSulMenoCaroSePiùDiCinqueProcessori() {
        
        for(int i=0; i<6; i++) {
            itemsOrdered.add(new EItem( ItemType.Processor, "Pintel e700",110.00));
        }       
        assertEquals(605.00, testBill.getOrderPrice(itemsOrdered,user), 0.0);
    }
    
    @Test
    public void testTotaleConScontoSulMenoCaroSePiùDiDieciMouse() {
        
        for(int i=0; i<11; i++) {
            itemsOrdered.add(new EItem( ItemType.Mouse, "Fazer Neo",30.00));
        }       
        assertEquals(300.00, testBill.getOrderPrice(itemsOrdered,user), 0.0);
    }

    @Test
    public void testTotaleConScontoSulMenoCaroSeNumeroTastiereUgualeNumeroMouse() {
        
        for(int i=0; i<3; i++) {
            itemsOrdered.add(new EItem( ItemType.Mouse, "Fazer Neo",30.00));
        }       
        for(int i=0; i<3; i++) {
            itemsOrdered.add(new EItem( ItemType.Keyboard, "Drevo Key",45.00));
        }
        assertEquals(195.00, testBill.getOrderPrice(itemsOrdered,user), 0.0);
    }

    @Test
    public void testTotaleConScontoSeTotaleSuperioreA1000Euro(){
        for(int i=0; i<20; i++) {
            itemsOrdered.add(new EItem( ItemType.Motherboard, "Azuz Pro",60.00));
        }    
        assertEquals(1080.00, testBill.getOrderPrice(itemsOrdered,user), 0.0);
    }

    @Test(expected=BillException.class)
    public void testOltreTrentaElementiPerOrdine() {
        
        for(int i=0; i<32; i++) {
            itemsOrdered.add(new EItem( ItemType.Motherboard, "Azuz Pro",60.00));
        }
        
        testBill.getOrderPrice(itemsOrdered, user);
    }

    @Test
    public void testCommissione2EuroSeInferiore10Euro(){
        itemsOrdered.add(new EItem( ItemType.Mouse, "Fazer Neo",5.00)); 
        assertEquals(7.00, testBill.getOrderPrice(itemsOrdered,user), 0.0);
    }

    @Test
    public void testOrdiniRegalatiAdUtentiMinorenni() {
        
        //lista con tutti gli ordini
        List<Order> ordini = new ArrayList<Order>();
        //itemsOrdered -> lista elementi in un ordine, ne creo una uguale per tutti gli ordini
        itemsOrdered.add(new EItem( ItemType.Processor, "Pintel e700",110.00));
        itemsOrdered.add(new EItem( ItemType.Motherboard, "Azuz Pro",60.00));
        //11 utenti minorenni
        String[] nomi =new String[]{"Sokka", "Lune", "Possum", "Fede",
                        "Pandolf", "Infant", "Martin", "Tosca", "Passu", "Toffy", "David"};
        User user = null;
        //inserisco 11 ordini
        for (int i = 0; i < 11; i++) {
            //nuovo utente minorenne
            user = new User(nomi[i]+"_test",nomi[i],"prova",LocalDate.of(2010, 5, 12));;
            //aggiungo il suo ordine
            ordini.add(new Order(itemsOrdered, user,  oreSeiMezza, testBill.getOrderPrice(itemsOrdered, user)));
        }
        
        List<Order> ordiniGratis = testBill.getFreeOrders(ordini);
        int numeroOrdiniRegalati = 0;

        for (Order ord : ordiniGratis) {
            if(ord.getPrice() == 0) {
                numeroOrdiniRegalati++;
            }
        }
        //controllo che siano stati regalati 10 ordini di minorenni nell'orario statbilito
        assertEquals(10,numeroOrdiniRegalati); 
    }

    //se non vengono effettuate almeno 10 ordini non vengono regalati ordini
    @Test(expected=BillException.class)
    public void testOrdiniRegalatiSeMenoDi10ordini() { 
        
        //lista con tutti gli ordini
        List<Order> ordini = new ArrayList<Order>();
        //itemsOrdered -> lista elementi in un ordine, ne creo una uguale per tutti gli ordini
        itemsOrdered.add(new EItem( ItemType.Processor, "Pintel e700",110.00));
        //3 utenti minorenni
        String[] nomi =new String[]{"Lune", "Sokka", "Passu"};
        User user = null;
        //inserisco 3 ordini
        for (int i = 0; i < 3; i++) {
            //nuovo utente minorenne
            user = new User(nomi[i]+"_test",nomi[i],"prova",LocalDate.of(2010, 5, 12));;
            //aggiungo il suo ordine
            ordini.add(new Order(itemsOrdered, user,  oreSeiMezza, testBill.getOrderPrice(itemsOrdered, user)));
        }
        
        testBill.getFreeOrders(ordini);
    }
}
