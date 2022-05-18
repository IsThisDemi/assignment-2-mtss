package it.unipd.mtss.business;

import it.unipd.mtss.business.BillImpl;
import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BillImplTest {
    
    private List<EItem> itemsOrdered;
    private BillImpl testBill; 
    private User user; 
    
    @Before
    public void setup() {
        itemsOrdered = new ArrayList<EItem>();
        testBill = new BillImpl();
        user = new User("IsThisDemi","Riccardo","Martinello",LocalDate.of(2001, 2, 10));
    }
    
    @Test
    public void testCalcoloDelTotale(){
        
        itemsOrdered.add(new EItem( ItemType.Processor, "Pintel e700",110.00));
        itemsOrdered.add(new EItem( ItemType.Motherboard, "Azuz Pro", 60.00));
        itemsOrdered.add(new EItem( ItemType.Mouse, "Fazer Neo", 30.00));
        itemsOrdered.add(new EItem( ItemType.Mouse, "Fazer Neo", 30.00)); // aggiungo altro mouse per non interferire con il test di MouseUgualiTastiere
        itemsOrdered.add(new EItem( ItemType.Keyboard, "Drevo Key", 45.00));
        
        assertEquals(275, testBill.getOrderPrice(itemsOrdered,user), 0.0);
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
}