/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isa;

/**
 *
 * @author saniafarooq
 */
public abstract class Item {
    
    private String title;
    private String language;
    private Member donatedBy;
    private Member onLoanTo;

    public Item(String title, String language, Member donatedBy) {
        this.title = title;
        this.language = language;
        this.donatedBy = donatedBy;
        this.onLoanTo = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Member getDonator() {
        return donatedBy;
    }
    
    public void clearDonator() {
        this.donatedBy = null;
    }
      
    public void loanTo(Member borrower) {
        if (isAvailable()) {
            this.onLoanTo = borrower;
        
        } else{
                    System.out.println("Item is currenlty being loaned");
        }
    }
    
    public boolean isAvailable() {
        return onLoanTo == null;
    }

    public void returnLoan() {
        if (onLoanTo != null) {
            onLoanTo.returnItem(this);
            this.onLoanTo = null;
        }
    }
}
