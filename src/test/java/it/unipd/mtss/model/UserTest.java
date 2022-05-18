////////////////////////////////////////////////////////////////////
// [RICCARDO] [MARTINELLO] [2009086]
// [SAMUELE] [RIZZATO] [1226307]
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import org.junit.Test;

public class UserTest {

    private User Demi = new User("IsThisDemi", "Riccardo", "Martinello", LocalDate.of(2001, 2, 10));

    @Test(expected = IllegalArgumentException.class)
    public void testUsernameNullo() {
        new User(null,"Riccardo","Martinello",LocalDate.of(2001, 2, 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNomeNullo() {
        new User("IsThisDemi",null,"Martinello",LocalDate.of(2001, 2, 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCognomeNullo() {
        new User("IsThisDemi","Riccardo",null,LocalDate.of(2001, 2, 10));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDataNulla() {
        new User("IsThisDemi","Riccardo","Martinello",null);
    }

    @Test
    public void testGetAge() {
        assertEquals(Demi.getAge(), 21);
    }

    @Test
    public void testGetName() {
        assertEquals(Demi.getName(), "Riccardo");
    }

    @Test
    public void testGetSurname() {
        assertEquals(Demi.getSurname(), "Martinello");
    }

    @Test
    public void testGetUsername() {
        assertEquals(Demi.getUsername(), "IsThisDemi");
    }
}