/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isa;

/**
 *
 * @author saniafarooq
 */
import java.util.ArrayList;


public class Collection {
    
    private ArrayList<Item> items;
    //Constructor
    public Collection() {
        items = new ArrayList<>();
    }
    
    //Add Book
    public void  addBook(String title, String author, Member donator, String language, String isbn){
    Book book = new Book(title, author, donator, language, isbn);
    items.add(book);
    if (donator !=null){
    donator.addDonation(book);
}
    
}
// Add DVD
public void addDVD(String title, String director, Member donator, String language, String [] audioLanguages){
DVD dvd = new DVD(title, director, donator, language, audioLanguages);
items.add(dvd);
if(donator != null)
{
donator.addDonation(dvd);
}}

//Search Items by title
public ArrayList<Item> searchItems(String searchTerm){
ArrayList<Item> result = new ArrayList<>();
 for(Item item : items){
if(item.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
{
result.add(item);
}
}
return result;
}
//Single item by Title 
public Item getItem(String title){
for(Item item : items){
if(item.getTitle().equalsIgnoreCase(title)){
return item;
}
}
return null;
}
//Remove Item
public void removeItem(Item item){
items.remove(item);
}}