/*
This Page was coded by Lacie Carey.
 */
package isa;

import java.util.ArrayList;


public class Member {
    
    private String name;
    private String postalAddress;
    private String email;
    private int itemsDonated;
    private ArrayList<Item> borrowedItems;
    
    public int getMaxBorrowableItems(){
    return Math.min(5, itemsDonated);
    }
    
    public boolean canBorrowMore(){
    return borrowedItems.size() < getMaxBorrowableItems();
    }
    
    
}
