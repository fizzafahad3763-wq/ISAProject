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
    private ArrayList<Member> members;
  
    public Collection() {
        items = new ArrayList<>();
        members = new ArrayList<>();
    }
    
   
    public void  addBook(String title, String language, String author, String isbn, Member donator)
    {
    Book book = new Book(title, author, donator, language, isbn);
    items.add(book);
    if (donator !=null){
    donator.donateItem();
}
    
}

public void addDVD(String title, String language, String director,Member donator, String [] audioLanguages)
{
DVD dvd = new DVD(title, director, donator, language, audioLanguages);
items.add(dvd);
if(donator != null)
{
donator.donateItem();
}
}


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

public Item getItem(String title)
{
for(Item item : items)
{
if(item.getTitle().equalsIgnoreCase(title)){
return item;
}
}
return null;
}

public void removeItem(Item item)
{
items.remove(item);
}

public void addMember(Member member)
{
    members.add(member);
}
public void removeMember(Member member)
{
    members.remove(member);
}
public ArrayList<Member> searchMembers(String searchTerm)
{
   ArrayList<Member> result = new ArrayList<>();
   
   for (Member member : members)
   {
       if (member.getName().toLowerCase().contains(searchTerm.toLowerCase()))
       {
           result.add(member);
       }
   }
   
   return result;
}
public Member getMemberByEmail(String email) 
{
    for (Member member : members)
    {
        if (member.getEmail().equalsIgnoreCase(email))
        {
            return member;
        }
    }
    return null;
}

public ArrayList<Item> getItems()
{
    return items;
}

public ArrayList<Member> getMembers()
{
    return members;
}
}