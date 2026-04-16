/*
This Page was coded by Lacie Carey.
 */
package isa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Member {
    
    private String name;
    private String address;
    private String email;
    private int donatedQty;
    private List<Item> donatedItems;
    private List<Item> borrowing;
    
    public Member(String name, String address, String email, int donatedQty) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.donatedQty = donatedQty;
        this.donatedItems = new ArrayList<>();
        this.borrowing = new ArrayList<>();
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getPostalAddress(){
        return address;
    }
    
    public void setPostalAddress(String postalAddress) {
        this.address = address;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getDonatedQty() {
        return donatedQty;
    }
    
    public void addDonation(Item item) {
        donatedItems.add(item);
        donatedQty++;
    }

    public List<Item> getDonatedItems() {
        return Collections.unmodifiableList(donatedItems);
    }

    
    
    public int borrowingQty() {
        return borrowing.size();
    }

    
    public List<Item> getLoanItems() {
        return Collections.unmodifiableList(borrowing);
    }

    
    public void lend(Item item) {
        borrowing.add(item);
    }

    public void returnItem(Item item) {
        borrowing.remove(item);
    }

    
    //borrowing limit logic.
    
    public boolean canBorrowMore() {
        int max = Math.min(5, donatedQty);
        return borrowing.size() < max;
    }

    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member other = (Member) o;
        return email != null && email.equalsIgnoreCase(other.email);
    }

    @Override
    public int hashCode() {
        return email == null ? 0 : email.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return name + " <" + email + ">";
    }
}

    
