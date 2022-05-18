////////////////////////////////////////////////////////////////////
// [RICCARDO] [MARTINELLO] [2009086]
// [SAMUELE] [RIZZATO] [1226307]
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EItemTest {

    private EItem Processor;
    private EItem Motherboard;
    private EItem Mouse;
    private EItem Keyboard;

    @Before
    public void setup() {
        Processor = new EItem( ItemType.Processor, "Pintel e700",110.00);
        Motherboard = new EItem(ItemType.Motherboard, "Azuz Pro", 60.00);
        Mouse = new EItem( ItemType.Mouse, "Fazer Neo", 30.00);
        Keyboard = new EItem( ItemType.Keyboard, "Drevo Key", 45.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTipologiaElementoNullo() {
        new EItem(null, "Pintel e700", 110.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNomeElementoNullo() {
        new EItem(ItemType.Processor, null, 110);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrezzoElementoNegativo() {
        new EItem(ItemType.Processor, "Pintel e700", -3.00);
    }

    @Test
    public void testGetName() {
        assertEquals("Pintel e700", Processor.getName());
        assertEquals("Azuz Pro", Motherboard.getName());
        assertEquals("Fazer Neo", Mouse.getName());
        assertEquals("Drevo Key", Keyboard.getName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(110.00, Processor.getPrice(), 0.0);
        assertEquals(60.00, Motherboard.getPrice(), 0.0);
        assertEquals(30.00, Mouse.getPrice(), 0.0);
        assertEquals(45.00, Keyboard.getPrice(), 0.0);
    }

    @Test
    public void testGetType() {
        assertEquals(ItemType.Processor, Processor.getType());
        assertEquals(ItemType.Motherboard, Motherboard.getType());
        assertEquals(ItemType.Mouse, Mouse.getType());
        assertEquals(ItemType.Keyboard, Keyboard.getType());
    }
}