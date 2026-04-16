

//This page was coded by Lacie Carey. 
package isa;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class TestItemForDVD extends Item {
    public TestItemForDVD(String title, String language, Member donor) {
        super(title, language, donor);
    }
}

public class DVDTest {

    private DVD dvd;
    private Member donor;

    @BeforeEach
    public void setUp() {
        donor = new Member("Donor", "Address", "donor@example.com", 5);

        dvd = new DVD( "Movie Title", "English", "Director Name", Arrays.asList("English", "Spanish"), donor);
    }

    
    //Constructor Checks
   
    @Test
    public void testConstructorStartsFieldsCorrectly() {
        assertEquals("Movie Title", dvd.getTitle());
        assertEquals("English", dvd.getLanguage());
        assertEquals("Director Name", dvd.getDirector()) ;
        assertEquals(2, dvd.getAudioLanguages().size());
    }

    @Test
    public void testAudioLanguagesListCantBeChanged() {
        List<String> langs = dvd.getAudioLanguages();
        assertThrows(UnsupportedOperationException.class, () -> {
            langs.add("French");
        });
    }

    
    // Audio Language Test.
    
    @Test
    public void testAddAudioLanguage() {
        dvd.addAudioLanguage("French");
        assertTrue(dvd.getAudioLanguages().contains("French"));
    }

    @Test
    public void testRemoveAudioLanguage() {
        dvd.removeAudioLanguage("Spanish");
        assertFalse(dvd.getAudioLanguages().contains("Spanish"));
    }

    
    //checking inherited item works.
    

    @Test
    public void testDvdIsAvailableWhenNotBorrowed() {
        assertTrue(dvd.isAvailable());
    }

    @Test
    public void testLoanToMarksAsNotAvailable() {
        Member borrower = new Member("Alice", "123 Road", "alice@example.com", 1);
        dvd.loanTo(borrower);
        assertFalse(dvd.isAvailable());
    }

    @Test
    public void testLoanToDoesNothingIfAlreadyOnLoan() {
        Member borrower1 = new Member("A", "Addr", "a@example.com", 1);
        Member borrower2 = new Member("B", "Addr", "b@example.com", 1);

        dvd.loanTo(borrower1);
        dvd.loanTo(borrower2); //should not overwrite.

        
        assertFalse(dvd.isAvailable());
    }

    @Test
    public void testReturnLoanMakesItemAvailableAgain() {
        Member borrower = new Member("Alice", "123 Road", "alice@example.com", 1);
        dvd.loanTo(borrower);

        dvd.returnLoan();
        assertTrue(dvd.isAvailable());
    }


    //toString Test.

    @Test
    public void testToStringFormat() {
        String result = dvd.toString();
        assertTrue(result.contains("DVD: Movie Title"));
        assertTrue(result.contains("Director Name"));
    }
}
