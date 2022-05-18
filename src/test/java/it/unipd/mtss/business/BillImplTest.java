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
        itemsOrdered.add(new EItem( ItemType.Keyboard, "Drevo Key", 45.00));
        
        assertEquals(245, testBill.getOrderPrice(itemsOrdered,user), 0.0);
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
    
}