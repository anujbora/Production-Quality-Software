package edu.nyu.pqs.ps1;

/**
 * Example implementation of the Address Book.
 * Creates one contact and adds it to the address book
 * @author Anuj Bora
 */
public class Implementation {
  public static void main(String[] args) {
    Contact contact = new Contact.Builder("Anuj").withphoneNumber("1234567890").build();
    AddressBook book = new AddressBook();
    book.addContact(contact);
  }
}