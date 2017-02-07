package edu.nyu.pqs.ps1;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Library for implementing Address Book. Users of the library can : 
 * <ul>
 * <li> Create an empty Address Book </li>
 * <li> Add an entry to Address Book which can contain name (required), postal address,
 *   phone number, email, address and note </li>
 * <li> User can remove an entry from the Address Book </li>
 * <li> User can search contacts by any attribute </li>
 * <li> User can save the Address Book to a file and can read Address Book from a file </li>
 * </ul>  
 * @author Anuj Bora
 */
public class AddressBook {
  private List<Contact> contacts;
	
  public AddressBook() {
    contacts = new ArrayList<Contact>();	
  }
	
  /**
   * Adds a Contact to the Address Book. Returns false for a null argument
   * @param contact contact to be added in Address Book
   * @return boolean true if contact successfully added to Address Book
   */
  public boolean addContact(Contact contact) {
    if (contact == null) {
      return false;
    }
    return contacts.add(contact);
  }
	
  /**
   * Removes contact from the Address Book of the given UUID
   * boolean false will be returned if either Address book is empty or
   * contact isn't found matching to given UUID
   * @param id the UUID of the contact to be deleted
   * @return boolean true if contact of given UUID found and deleted
   */
  public boolean removeContact(UUID id) {
    for (int i = 0; i < contacts.size(); i++) {
      if (contacts.get(i).getid().equals(id)) {
        contacts.remove(i);
        return true;
      }
    }
    return false;
  }
	
  /**
   * Imports contacts from an external JSON file. The new contacts will be added to 
   * existing Address Book maintaining previous contacts in the Address Book.
   * boolean false will be returned in case of exception in handling file
   * so that the exception can be handled at client side
   * @param path String The system-dependent filename.
   * @return boolean true if contacts successfully imported
   */
  public boolean importContacts(String path) {
    Gson gson = new Gson();
    try {
    	BufferedReader br = new BufferedReader(new FileReader(path));
      String line;
      // Read JSON file line by line and convert each line's data to Contact object
      while ((line = br.readLine()) != null) {
        Contact c = gson.fromJson(line, Contact.class);
        contacts.add(c);
      }
    } catch (IOException FileAccessFailed) {
      return false;
    }
    return true;
  }
	
  /**
   * Exports contacts of the Address Book in JSON using GSON library.
   * The path provided in argument should include name of the file.
   * If the file of the given name doesn't exists, it will be created.
   * boolean false will be returned in case of exception so that the 
   * exception can be handled on client side.
   * @param path String The system-dependent filename.
   * @return boolean true if contacts successfully exported
   */
  public boolean exportContacts(String path) {
    Gson gson = new Gson();    
    try {
    	FileWriter writer = new FileWriter(path);
      for (Contact c : contacts) {
        // Output one object per line in the external file
      	gson.toJson(c, writer);
        writer.write("\n");
      }
      writer.close();
    } catch (IOException FileAccessFailed) {
      return false;
    }
    return true;
  }
	
  /**
   * Returns list of contacts that matches provided keyword. 
   * Matching is case-insensitive.
   * It won't match phone numbers if the keyword consists of phone number
   * having hyphens (-) or round brackets as phone numbers are stored by
   * filtering out hyphens and round brackets.
   * @param keyword String the keyword to search
   * @return List containing matched contacts. Returns Empty list if zero contacts matched.
   */
  public List<Contact> searchContacts(String keyword) {
    List<Contact> matchedContacts = new ArrayList<Contact>();
    keyword = keyword.toLowerCase();
    for (Contact c : contacts) {
      // Convert raw data to lower case and compare with the keyword
      if (c.rawData().toLowerCase().contains(keyword)) {
        matchedContacts.add(c);
      }
    }
    return matchedContacts;
  }

  @Override
  public String toString() {
    return "AddressBook [contacts=" + contacts + "]";
  }
}
