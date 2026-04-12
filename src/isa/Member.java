/*
This Page was coded by Lacie Carey.
 */
package isa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Member {
    
    private String name;
    private String postalAddress;
    private String email;
    private int itemsDonated;
    private List<Item> borrowedItems;
    
    public Member(String name, String postalAddress, String email, int itemsDonated) {
        this.name = name;
        this.postalAddress = postalAddress;
        this.email = email;
        this.itemsDonated = itemsDonated;
        this.borrowedItems = new ArrayList<>();
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getPostalAddress(){
        return postalAddress;
    }
    
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getItmesDonated() {
        return itemsDonated;
    }
    
    public List<Item> getBorrowedItems() {
        return Collections.unmodifiableList(borrowedItems);
    }
    
    
    
    
    public int getMaxBorrowableItems(){
        return Math.min(5, itemsDonated);
    }
    
    public boolean canBorrowMore(){
        return borrowedItems.size() < getMaxBorrowableItems();
    }
    
    public void donateItem() {
        itemsDonated++;
    }
    
    public boolean borrowItem(Item item){
        if(!canBorrowMore()){
            return false;
        }
        borrowedItems.add(item);
        return true;       
    }
    
    public boolean returnItem(Item item) {
        return borrowedItems.remove(item);
    }
}