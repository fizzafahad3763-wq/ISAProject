
package isa;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 This page was coded by Lacie Carey.
 */

class TestItem extends Item {
    public TestItem(String title, String language, Member donor) {
        super(title, language, donor);
    }
}


public class MemberTest {
    
    private Member member;
    private Member donor;
    private TestItem item1;
    private TestItem item2;
    
    @BeforeEach
    public void setUp(){
        member = new Member("Alice", "1 Road", "alice@example.com", 0);
        donor = new Member("Donor", "2 Street", "donor@example.com", 0);

        item1 = new TestItem("Item One", "English", donor);
        item2 = new TestItem("Item Two", "French", donor);

    }
    

    //Construcor Tests.

    @Test
    public void testConstructorInitialisesFieldsRight() {
        assertEquals("Alice", member.getName());
        assertEquals("1 Road", member.getPostalAddress());
        assertEquals("alice@example.com", member.getEmail());
        assertEquals(0, member.getDonatedQty());
        assertEquals(0, member.borrowingQty());

    }

    @Test
    public void testAddDonationIncreasesDonatedQty() {
        member.addDonation(item1);
        assertEquals(1, member.getDonatedQty());
        assertEquals(1, member.getDonatedItems().size());
        assertTrue(member.getDonatedItems().contains(item1));
    }

    @Test
    public void testGetDonatedItemsIsUnmodifiable() {
        member.addDonation(item1);
        List<Item> donated = member.getDonatedItems();
        assertThrows(UnsupportedOperationException.class, () -> donated.add(item2));
    }

    // -------------------------
    // Borrowing Tests
    // -------------------------
    @Test
    public void testLendAddsItemToBorrowingList() {
        member.lend(item1);
        assertEquals(1, member.borrowingQty());
        assertTrue(member.getLoanItems().contains(item1));
    }

    @Test
    public void testReturnItemRemovesItemFromBorrowingList() {
        member.lend(item1);
        member.returnItem(item1);
        assertEquals(0, member.borrowingQty());
    }

    @Test
    public void testGetLoanItemsIsUnmodifiable() {
        member.lend(item1);
        List<Item> loans = member.getLoanItems();
        assertThrows(UnsupportedOperationException.class, () -> loans.add(item2));
    }
    

    @Test
    public void testCanBorrowMoreWhenUnderLimit() {
        member.addDonation(item1); // donatedQty = 1 → max borrow = 1
        assertTrue(member.canBorrowMore());
    }

    @Test
    public void testCannotBorrowMoreWhenAtLimit() {
        member.addDonation(item1); // donatedQty = 1
        member.lend(item2);        // now borrowing 1
        assertFalse(member.canBorrowMore());
    }

    @Test
    public void testBorrowLimitIsFiveWhenDonatedQtyIsHigh() {
        Member m = new Member("Bob", "3 Lane", "bob@example.com", 10);
        for (int i = 0; i < 5; i++) {
            m.lend(new TestItem("Item" + i, "Lang", donor));
        }
        assertFalse(m.canBorrowMore());
    }

    @Test
    public void testMembersEqualIfEmailsMatch() {
        Member m1 = new Member("X", "A", "same@mail.com", 0);
        Member m2 = new Member("Y", "B", "same@mail.com", 0);

        assertEquals(m1, m2);
    }

    @Test
    public void testMembersNotEqualIfEmailsDifferent() {
        Member m1 = new Member("X", "A", "x@mail.com", 0);
        Member m2 = new Member("Y", "B", "y@mail.com", 0);

        assertNotEquals(m1, m2);
    }
}


