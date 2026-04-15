/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package isa;

/**
 *
 * @author saniafarooq
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    /**
     * @param args the command line arguments
     */
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final String INPUT_FILE = "input.dat";
    
    public static void main(String[] args) {
        
        Collection systemCollection = new Collection();
        loadStartupData(systemCollection);
        boolean isRunning = true;
        while(isRunning)
        {
            displayMenu();
            String menuOption = scanner.nextLine().trim();
            
            switch (menuOption)
            {
case "1":
                    searchItems(systemCollection);
                    break;
case "2":
                    addItem(systemCollection);
                    break;
case "3":
                    updateItem(systemCollection);
                    break;
case "4":
                    removeItem(systemCollection);
                    break;
case "5":
                    addMember(systemCollection);
                    break;
case "6":
                    updateMember(systemCollection);
                    break;
case "7":
                    removeMember(systemCollection);
                    break;
case "8":
                    lendItem(systemCollection);
                    break;
case "9":
                    returnItem(systemCollection);
                    break;
case "10":
                    searchMembers(systemCollection);
                    break;
case "11":
                    saveToFile(systemCollection);
                    break;
case "12":
                    isRunning = false;
                    System.out.println("Program Closed");
                    break;
default:
                    System.out.println("Invalid Option..");
                        
            }
        }
    }
    private static void displayMenu()
    {
        System.out.println("\n ---------Item Sharing System--------");
        System.out.println("1 Search Items");
        System.out.println("2 Add Item");
        System.out.println("3 Update Item");
        System.out.println("4 Remove Item");
        System.out.println("5 Add member");
        System.out.println("6 Update Member");
        System.out.println("7 Remove Member");
        System.out.println("8 Lend Item to Member");
        System.out.println("9 Return Item to Collection");
        System.out.println("10 Search members");
        System.out.println("11 Save to File");
        System.out.println("12 Exit");
        System.out.println("Choose Option..");
    }
    
    private static void loadStartupData(Collection systemCollection)
    {
            try
            {
                loadFromFile(INPUT_FILE, systemCollection);
                System.out.println("Data loaded from.." + INPUT_FILE);
            }
            catch (IOException e)
            {
                System.out.println("Could not load startup file");
                System.out.println("Starting with empty system..");
            }
    }
    
    private static void loadFromFile(String fileName, Collection systemCollection) throws IOException
    {
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
        String currentLine;
        Member currentDonor = null;
        
        while((currentLine = fileReader.readLine()) != null)
        {
            if(currentLine.trim().isEmpty())
            {
                continue;
            }
            String[] fileParts = currentLine.split("\\|");
            if (fileParts[0].equalsIgnoreCase("Member"))
            {
                String memberName = fileParts[1].trim();
                String memberAddress = fileParts[2].trim();
                String memberEmail = fileParts[3].trim();
                int donatedCount = 0;
                
                if(fileParts.length > 4)
                {
                    try
                    {
                        donatedCount = Integer.parseInt(fileParts[4].trim());
      
                    }
                    catch(NumberFormatException e)
                    {
                        donatedCount = 0;
                    }
                }
                Member newMember = new Member(memberName, memberAddress,memberEmail, donatedCount);
                systemCollection.addMember(newMember);
                currentDonor = newMember;
            }
            else if (fileParts[0].equalsIgnoreCase("Book"))
            {
                String bookTitle = fileParts[1].trim();
                String bookAuthor = fileParts[2].trim();
                String bookIsbn = fileParts[3].trim();
                String bookLanguage = fileParts[4].trim();
                String borrowerEmail = "";
                if(fileParts.length > 5)
                {
                    borrowerEmail = fileParts[5].trim();
                }
                Book newBook = new Book(bookTitle, bookAuthor, currentDonor, bookLanguage,bookIsbn);
                systemCollection.getItems().add(newBook);
                
                if(currentDonor != null) 
                {
                    currentDonor.donateItem();
                }
                if(!borrowerEmail.isEmpty())
                {
                    Member borrower = systemCollection.getMemberByEmail(borrowerEmail);
                    if(borrower != null)
                    {
                        borrower.borrowItem(newBook);
                        newBook.loanTo(borrower);
                    }
                }
                      
            }
            else if(fileParts[0].equalsIgnoreCase("DVD"))
            {
                String dvdTitle = fileParts[1].trim();
                String caseLanguage = fileParts[2].trim();
                String dvdDirector = fileParts[3].trim();
                String[] audioLanguages = fileParts[4].trim().split(",");
                String borrowerEmail ="";
                
                for (int i=0; i < audioLanguages.length; i++)
                {
                    audioLanguages[i] = audioLanguages[i].trim();
                    
                }
                
                if(fileParts.length >5)
                {
                    borrowerEmail = fileParts[5].trim();
                }
                
                DVD newDvd = new DVD(dvdTitle, dvdDirector, currentDonor,caseLanguage, audioLanguages);
                systemCollection.getItems().add(newDvd);
                
                if(currentDonor != null)
                {
                    currentDonor.donateItem();
                }
                
                if(!borrowerEmail.isEmpty())
                {
                    Member borrower = systemCollection.getMemberByEmail(borrowerEmail);
                    if(borrower != null)
                    {
                        borrower.borrowItem(newDvd);
                        newDvd.loanTo(borrower);
                    }
                    
                }
                
            }
        }
        
        fileReader.close();
    }
    
    private static void searchItems(Collection systemCollection) 
    {
        System.out.print("Enter Title Search..");
        String searchTerm = scanner.nextLine();
        
        ArrayList<Item> matchedItems = systemCollection.searchItems(searchTerm);
        if(matchedItems.isEmpty())
        {
            System.out.println("No Item Foundd..");
            return;
        }
        
        for(int i = 0; i < matchedItems.size(); i++)
        {
            System.out.println((i+1) + "." + matchedItems.get(i).getTitle());
        }
        System.out.print("Choose item number to view details or press 12 to cancel..");
        int selectedNumber = readInteger();
        
        if(selectedNumber <= 0 || selectedNumber > matchedItems.size())
        {
            return;
        }
        
        Item selectedItem = matchedItems.get(selectedNumber - 1);
        displayItemDetails(selectedItem);
    }
    private static void displayItemDetails(Item selectedItem)
    {
        System.out.println("\n------- Item Details..--------");
        System.out.println("Title" + selectedItem.getTitle());
        System.out.println("Language " + selectedItem.getLanguage());
        
        
        if(selectedItem instanceof Book)
        {
            Book selectedBook = (Book) selectedItem;
            System.out.println("Type Book");
            System.out.println("Author " + selectedBook.getAuthor());
            System.out.println("ISBN" + selectedBook.getIsbn());
            
        }
        
        else if(selectedItem instanceof DVD) 
        {
            DVD selectedDvd = (DVD) selectedItem;
            System.out.println("Type DVD");
            System.out.println("Director" + selectedDvd.getDirector());
            System.out.println("Audio Languages" + String.join(",", selectedDvd.getAudioLanguages()));
            
        }
        
        if(selectedItem.getDonator() != null)
        {
            System.out.println("Donated by " + selectedItem.getDonator().getName());
            
        }
        
        else {
            System.out.println("Donated by No longer in System..");
            
        }
        
        if(selectedItem.isAvailable())
        {
            System.out.println("Status Available");
            
        }
        else 
        {
            System.out.println("Status on loan to " + selectedItem.getBorrower().getName());
            
        }
        
    }
    
    private static void addItem(Collection systemCollection)
    {
        System.out.println("1: Book");
        System.out.println("2: DVD");
        System.out.println("Choose item type");
        String itemTypeOption = scanner.nextLine().trim();
        
        Member donatingMember = chooseMember(systemCollection);
        if(donatingMember == null)
        {
            System.out.println("No member selected..");
            return;
        }
        
        if(itemTypeOption.equals("1"))
        {
            System.out.print("Enter Title");
            String bookTitle = scanner.nextLine();
            
            System.out.print("Enter language");
            String bookLanguage = scanner.nextLine();
            
            System.out.print("Enter author");
            String bookAuthor = scanner.nextLine();
            
            System.out.print("Enter ISBN");
            String bookIsbn = scanner.nextLine();
            
            
            systemCollection.addBook(bookTitle, bookLanguage, bookAuthor, bookIsbn, donatingMember);
            System.out.println("Book added..");
            
        }
        
        else if(itemTypeOption.equals("2"))
        {
            System.out.print("Enter Title");
            String dvdTitle = scanner.nextLine();
            
            System.out.print("Enter Language on case:");
            String caseLanguage = scanner.nextLine();
            
            System.out.print("Enter Director..");
            String dvdDirector = scanner.nextLine();
            
            System.out.print("Enter audio languages separated by comma");
            String audioInput = scanner.nextLine();
            String[] audioLanguages = audioInput.split(",");
            
            for(int i = 0; i < audioLanguages.length; i++)
            {
                audioLanguages[i] = audioLanguages[i].trim();
            }
            
            systemCollection.addDVD(dvdTitle, caseLanguage, dvdDirector, donatingMember, audioLanguages);
            System.out.println("DVD Added");
        }
        
        else 
        {
            System.out.println("Invalid type");
        }
    }
    
    private static void updateItem(Collection systemCollection)
    {
        Item selectedItem = chooseItem(systemCollection);
        if(selectedItem == null)
        {
            return;
        }
        
        System.out.print("New title");
        String newTitle = scanner.nextLine();
        if(!newTitle.isEmpty())
        {
            selectedItem.setTitle(newTitle);
        }
        
        System.out.print("New Language");
        String newLanguage = scanner.nextLine();
        if(!newLanguage.isEmpty())
            
        {
         selectedItem.setLanguage(newLanguage);
        }
            if(selectedItem instanceof Book)
            {
                Book selectedBook = (Book) selectedItem;
                
                System.out.print("New author");
                String newAuthor = scanner.nextLine();
                if(!newAuthor.isEmpty())
                {
                    selectedBook.setAuthor(newAuthor);
                }
                
                System.out.print("New ISBN.");
                String newIsbn = scanner.nextLine();
                if(!newIsbn.isEmpty())
                {
                    selectedBook.setIsbn(newIsbn);
                }
            }
            
            else if (selectedItem instanceof DVD)
            {
                DVD selectedDvd = (DVD) selectedItem;
                
                System.out.print("New Director");
                String newDirector = scanner.nextLine();
                if(!newDirector.isEmpty())
                {
                    selectedDvd.setDirector(newDirector);
                }
                
                System.out.print("New audio languages");
                String newAudioInput = scanner.nextLine();
                if(!newAudioInput.isEmpty())
                {
                    String[] newAudioLanguages = newAudioInput.split(",");
                    for(int i=0; i < newAudioLanguages.length; i++)
                    {
                        newAudioLanguages[i] = newAudioLanguages[i].trim();
                    }
                    
                    selectedDvd.setAudioLanguages(newAudioLanguages);
                }
            }
        System.out.println("Item Updated..");
    }
    
    private static void removeItem(Collection systemCollection)
    {
        Item selectedItem = chooseItem(systemCollection);
        if(selectedItem == null)
        {
            return;
        }
        
        systemCollection.removeItem(selectedItem);
        System.out.println("Item Removedd..");
        
        
    }
    
    private static void addMember(Collection systemCollection)
    {
        System.out.print("Enter member Name");
        String memberName = scanner.nextLine();
        
        System.out.print("Enter Postal Address");
        String memberAddress = scanner.nextLine();
        
        System.out.print("Enter Email Address");
        String memberEmail = scanner.nextLine();
        
        Member newMember = new Member(memberName, memberAddress, memberEmail, 0);
        systemCollection.addMember(newMember);
        
        System.out.println("Member Added..");
    }
    
    private static void updateMember(Collection systemCollection)
    {
        Member selectedMember = chooseMember(systemCollection);
        if(selectedMember == null)
        {
            return;
        }
        
        System.out.print("New Namee");
        String newName = scanner.nextLine();
        if(!newName.isEmpty())
        {
            selectedMember.setName(newName);
        }
        
        System.out.print("New Postal address");
        String newAddress = scanner.nextLine();
        if(!newAddress.isEmpty())
        {
            selectedMember.setPostalAddress(newAddress);
        }
        
        System.out.print("New Email..");
        String newEmail = scanner.nextLine();
        if(!newEmail.isEmpty())
        {
            selectedMember.setEmail(newEmail);
        }
        System.out.println("Member updated");
    }
    
    private static void removeMember(Collection systemCollection)
    {
        Member selectedMember = chooseMember(systemCollection);
        if(selectedMember == null)
        {
            return;
        }
        
        for(Item currentItem : systemCollection.getItems())
        {
            if(currentItem.getDonator() != null && currentItem.getDonator().equals(selectedMember))
            {
                currentItem.clearDonator();
            }
        }
        
        systemCollection.removeMember(selectedMember);
        System.out.println("Member removed");
    }
    
    private static void searchMembers(Collection systemCollection)
    {
        System.out.print("Enter member name for search");
        String searchTerm = scanner.nextLine();
        
        ArrayList<Member> matchedMembers = systemCollection.searchMembers(searchTerm);
        if(matchedMembers.isEmpty())
        {
            System.out.println("No Mmebers Found");
            return;
        }
        
        for (int i=0; i < matchedMembers.size(); i++)
        {
            System.out.println((i +1) + "." + matchedMembers.get(i).getName());
            
        }
        
        System.out.print("Chose member name to view details..");
        int selectedNumber = readInteger();
        
        if(selectedNumber <=0 || selectedNumber > matchedMembers.size())
        {
            return;
        }
        
        Member selectedMember = matchedMembers.get(selectedNumber - 1);
        displayMemberDetails(selectedMember);
               
    }
    
    private static void displayMemberDetails(Member selectedMember)
    {
        System.out.println("\n-------Member Details --------");
        System.out.println("Name" + selectedMember.getName());
        System.out.println("Postal Address" + selectedMember.getPostalAddress());
        System.out.println("Email" + selectedMember.getEmail());
        System.out.println("item Donated" + selectedMember.getItemsDonated());
        System.out.println("Currently Borrwoing" + selectedMember.getBorrowedItems().size());
        
        if(selectedMember.getBorrowedItems().isEmpty())
        {
            System.out.println("Borrowed Items: None");
            
        }
        else 
        {
            System.out.println("Borrowed Items");
            for(Item borrowedItem : selectedMember.getBorrowedItems())
            {
                System.out.println("-" + borrowedItem.getTitle());
                      
            }
            
            
        }
        
}
        private static void lendItem(Collection systemCollection)
        {
            Item selectedItem = chooseItem(systemCollection);
            if(selectedItem == null)
            {
                return;
            }
            
            if(!selectedItem.isAvailable())
            {
                System.out.println("This item is already on Loan");
                return;
                
            }
            
            Member selectedMember = chooseMember(systemCollection);
            if(selectedMember == null)
            {
                return;
            }
            
            if(!selectedMember.canBorrowMore())
            {
                System.out.println("This member has reached borrowing limit");
                return;
            }
            
            boolean borrowSuccess = selectedMember.borrowItem(selectedItem);
            if(borrowSuccess)
            {
                selectedItem.loanTo(selectedMember);
                System.out.println("Item Lent Successfully");
                
            }
            else 
            {
                System.out.println("Could not lend item");
            }
        }
        
        private static void returnItem(Collection systemCollection)
        {
            Item selectedItem = chooseItem(systemCollection);
            if(selectedItem == null)
            {
                return;
            }
            
            if(selectedItem.isAvailable())
            {
                System.out.println("Item is already in collection");
                return;
            }
            
            Member currentBorrower = selectedItem.getBorrower();
            currentBorrower.returnItem(selectedItem);
            selectedItem.returned();
            
            System.out.println("Item Returned");
        }
        
        private static void saveToFile(Collection systemCollection)
        {
            System.out.print("Enter File Name to Save");
            String outputFileName = scanner.nextLine();
            
            try
            {
                PrintWriter fileWriter = new PrintWriter(new FileWriter(outputFileName));
                
                for(Item currentItem : systemCollection.getItems())
                {
                    if(currentItem.getDonator() == null)
                    {
                        writeItemLine(fileWriter, currentItem);
                    }
                }
                
                for(Member currentMember : systemCollection.getMembers())
                {
                    fileWriter.println("Member|" + currentMember.getName() + "|" + currentMember.getPostalAddress() + "|" + currentMember.getEmail() + "|" + currentMember.getItemsDonated());
                    
                    for(Item currentItem : systemCollection.getItems())
                    {
                        if(currentItem.getDonator() != null && currentItem.getDonator().equals(currentMember))
                        {
                            writeItemLine(fileWriter, currentItem);
                        }
                    }
                }
                fileWriter.close();
                System.out.println("Data saved successfully");
            }
            catch (IOException e)
            {
                System.out.println("Error saving File");
            }
        }
        private static void writeItemLine(PrintWriter fileWriter, Item currentItem)
        {
            String borrowerEmail = "";
            
            if(!currentItem.isAvailable())
            {
                borrowerEmail = currentItem.getBorrower().getEmail();
            }
            
            if(currentItem instanceof Book)
            {
                Book currentBook = (Book) currentItem;
                fileWriter.println("Book|" + currentBook.getTitle()+ "|" + currentBook.getAuthor() + "|" +currentBook.getIsbn()
                +"|" + currentBook.getLanguage()+ "|" + borrowerEmail);
                
                
            }
            
            else if (currentItem instanceof DVD)
            {
                DVD currentDvd = (DVD) currentItem;
                fileWriter.println("DVD|" + currentDvd.getTitle() + "|" + currentDvd.getLanguage() + "|" + currentDvd.getDirector()
                + "|" + String.join(",", currentDvd.getAudioLanguages()) + "|" + borrowerEmail);
                
            }
        }
        
        private static Item chooseItem(Collection systemCollection)
        {
            System.out.print("Enter item Title search");
            String searchTerm = scanner.nextLine();
            
            ArrayList<Item> matchedItems = systemCollection.searchItems(searchTerm);
            
            if(matchedItems.isEmpty())
            {
                System.out.println("No matching items");
                return null;
            }
            for (int i=0; i< matchedItems.size(); i++)
            {
                System.out.println((i +1) + "." + matchedItems.get(i).getTitle());
            }
            
            System.out.print("Choose item number");
            int selectedNumber = readInteger();
            
            if(selectedNumber <=0 || selectedNumber > matchedItems.size())
            {
                System.out.println("Invalid Section");
                return null;
            }
            
            return matchedItems.get(selectedNumber-1);
        }
        
        private static Member chooseMember(Collection systemCollection)
        {
            System.out.print("Enter member name to search");
            String searchTerm = scanner.nextLine();
            
            ArrayList<Member> matchedMembers = systemCollection.searchMembers(searchTerm);
            if(matchedMembers.isEmpty())
            {
                System.out.println("No matching member");
                return null;
            }
            
            for (int i=0; i < matchedMembers.size(); i++)
            {
                System.out.println((i+1) + "." + matchedMembers.get(i).getName()+ "-" + matchedMembers.get(i).getEmail());
                
            }
            System.out.print("Chosse member number");
            int selectedNumber = readInteger();
            
            if(selectedNumber <=0 || selectedNumber > matchedMembers.size())
            {
                System.out.println("Invalid selection");
                return null;
            }
            
            return matchedMembers.get(selectedNumber-1);

        }
        
        private static int readInteger() 
        {
            try 
            {
                return Integer.parseInt(scanner.nextLine().trim());
                
                        
            }
            catch(NumberFormatException e )
            {
                return -1;
            }
                    
        }
     
    }

