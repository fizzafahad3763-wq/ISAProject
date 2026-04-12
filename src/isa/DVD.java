/*
This Page was coded by Lacie Carey.
 */
package isa;

import java.util.List;


public abstract class DVD {
    private String title;
    private String language;
    private Member donor;
    private Member borrower;
    
    public DVD (String title, String language, Member donor){
        this.title = title;
        this.language = language;
        this.donor = donor;
        this.borrower = null;
    }
    
    public boolean isOnLoan(){
        return borrower != null;
    }
    
    public boolean lendTo(Member member){
        if (isOnLoan()) return false;
        borrower = member;
        return true;
    }
    
    public void returnItem(){
        borrower = null;
    }
    
}
