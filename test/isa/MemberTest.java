
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
    
    @BeforeEach
    public void setUp(){
        donor = new Member("Donor", "Address", "donor@example.com", 5);
        member = new Member ("Alice", "123 Road", "alice@example.com", 0);
    }
    

    //Construcor Tests.

    @Test
    public void testConstructorInitialisesFieldsRight() {
        assertEquals("Alice", member.getName());
        assertEquals("123 Road", member.getPostalAddress());
        assertEquals("alice@example.com", member.getEmail());
        assertEquals(0, member.getItemsDonated());
        assertTrue(member.getBorrowedItems().isEmpty());
    }

    //Borrowing tests.
     
    @Test
    public void testMemberWithNoDonationsCantBorrow() {
        TestItem item = new TestItem("Book A", "English", donor);
        
        assertFalse(member.canBorrowMore());
        assertFalse(member.borrowItem(item));
        assertEquals(0, member.getBorrowedItems().size());
    }
    
    @Test
    public void testMemberWithOneDonationCanBorrowOnlyOneItem(){
        member.donateItem();
        TestItem item1 = new TestItem("Book A", "English", donor);
        TestItem item2 = new TestItem("Book B", "English", donor);
        
        assertTrue(member.borrowItem(item1));
        assertFalse(member.borrowItem(item2));
        assertEquals(1, member.getBorrowedItems().size());
    }
    
    @Test
    public void testMemberWithThreeDonationsCanBorrowUpToThreeItems(){
        member.donateItem();
        member.donateItem();
        member.donateItem();
        
        TestItem a = new TestItem("A", "English", donor);
        TestItem b = new TestItem("B", "English", donor);
        TestItem c = new TestItem("C", "English", donor);
        TestItem d = new TestItem("D", "English", donor);
        
        assertTrue(member.borrowItem(a));
        assertTrue(member.borrowItem(b));
        assertTrue(member.borrowItem(c));
        assertFalse(member.borrowItem(d)); //Limit
        
        assertEquals(3, member.getBorrowedItems().size());
    }
    
    @Test
    public void testDonateLimitIsAlwaysFive(){
        for (int i = 0; i <10; i++){
            member.donateItem();
        }
        assertEquals(5, member.getMaxBorrowableItems());
    }
    
    //donateItem() checks.
    
    @Test
    public void testDonateItemDonationCountWorks() {
        assertEquals(0, member.getItemsDonated());
        member.donateItem();
        assertEquals(1, member.getItemsDonated());
    }
    
    @Test
    public void testDonateItemIncreasesBorrowableItem(){
        assertEquals(0, member.getMaxBorrowableItems());
        member.donateItem();
        assertEquals(1, member.getMaxBorrowableItems());
    }
    
    //returnItem() checks.
    
    @Test
    public void testReturnItemRemovesItemFromBorrowedList() {
        member.donateItem();
        TestItem item = new TestItem("Book A", "English", donor);
        member.borrowItem(item);
        assertEquals(1, member.getBorrowedItems().size());

        assertTrue(member.returnItem(item));
        assertEquals(0, member.getBorrowedItems().size());
    }

    @Test
    public void testReturnItemFailsIfItemNotBorrowed() {
        TestItem item = new TestItem("Book A", "English", donor);
        assertFalse(member.returnItem(item));
    }
    
    //update field checks.

    @Test
    public void testSetNameWillUpdateName() {
        member.setName("Bob");
        assertEquals("Bob", member.getName());
    }

    @Test
    public void testSetEmailWillUpdateEmail() {
        member.setEmail("new@example.com");
        assertEquals("new@example.com", member.getEmail());
    }

    @Test
    public void testSetPostalAddressWillUpdateAddress() {
        member.setPostalAddress("New Address");
        assertEquals("New Address", member.getPostalAddress());
    }

    //defensive checks.
    @Test
    public void testBorrowedItemsListIsUnmodifiable() {
        List<Item> list = member.getBorrowedItems();
        assertThrows(UnsupportedOperationException.class, () -> {
            list.add(new TestItem("X", "English", donor));
        });
    }

    //equality email checks.
    
    @Test
    public void testMembersWithSameEmailAreEqual() {
        Member m2 = new Member("Other", "Addr", "alice@example.com", 1);
        assertEquals(member, m2);
    }

    @Test
    public void testMembersWithDifferentEmailsAreNotEqual() {
        Member m2 = new Member("Other", "Addr", "different@example.com", 1);
        
        assertNotEquals(member, m2);
    }

    @Test
    public void testEmailComparisonIsCaseInsensitive() {
        Member m2 = new Member("Other", "Addr", "ALICE@example.com", 1);
        assertEquals(member, m2);
    }

}
