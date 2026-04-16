
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
    private Member borrower;

    @BeforeEach
    public void setUp() {
        donor = new Member("Donor", "Address", "donor@example.com", 3);
        borrower = new Member("Borrower", "Addr", "borrower@example.com", 3);

        String[] audio = {"English", "French"};
        dvd = new DVD("Movie Title", "Director Name", donor, "English", audio);

    }

    
    //Constructor Checks
   
    @Test
    public void testConstructorStartsFieldsCorrectly() {
        assertEquals("Movie Title", dvd.getTitle());
        assertEquals("Director Name", dvd.getDirector());
        assertEquals("English", dvd.getLanguage());
        assertEquals(donor, dvd.getDonator());

        String[] langs = dvd.getAudioLanguages();
        assertArrayEquals(new String[]{"English", "French"}, langs);

    }

        // Director tests
    @Test
    public void testSetDirectorUpdatesDirector() {
        dvd.setDirector("New Director");
        assertEquals("New Director", dvd.getDirector());
    }

    // Audio languages tests
    @Test
    public void testSetAudioLanguagesReplacesArray() {
        String[] newLangs = {"Mandarin", "Cantonese"};
        dvd.setAudioLanguages(newLangs);

        assertArrayEquals(new String[]{"Mandarin", "Cantonese"}, dvd.getAudioLanguages());
    }

    @Test
    public void testGetAudioLanguagesReturnsCopy() {
        String[] langs = dvd.getAudioLanguages();
        langs[0] = "CHANGED";

        // Original should NOT change
        assertEquals("English", dvd.getAudioLanguages()[0]);
    }

    // Loan tests
    @Test
    public void testLoanToMarksAsUnavailable() {
        assertTrue(dvd.isAvailable());

        dvd.loanTo(borrower);

        assertFalse(dvd.isAvailable());
        assertEquals(borrower, dvd.getBorrower());
    }


    @Test
    public void testReturnLoanMarksAsAvailable() {
        dvd.loanTo(borrower);
        dvd.returnLoan();

        assertTrue(dvd.isAvailable());
        assertNull(dvd.getBorrower());
    }

    // toString test
    @Test
    public void testToStringFormat() {
        String result = dvd.toString();
        assertTrue(result.contains("DVD: Movie Title: "));
        assertTrue(result.contains("Director Name: "));
    }
}
