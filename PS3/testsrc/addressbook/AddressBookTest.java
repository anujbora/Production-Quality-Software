package addressbook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import addressbook.AddressBook.ContactAttribute;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AddressBookTest {
  private AddressBook addressbook;

  @Before
  public void testCreateAddressBook() {
    addressbook = new AddressBook();
  }

  @Test
  public void testCreateAddressBook_Empty() {
    assertEquals(0, addressbook.numberOfContacts());
  }

  @Test
  public void testCreateAddressBook_SingleContact() {
    Contact c = new Contact.Builder().withName("Tim").build();
    assertTrue(addressbook.addContact(c));
    assertEquals(1, addressbook.numberOfContacts());
  }

  /**
   * [BUG]
   * Address book should prevent addition of null in the address book by
   * throwing a Null Pointer Exception
   * But, it isn't prevented and null can be freely added in the address book
   */
  @Test(expected = NullPointerException.class)
  public void testCreateAddressBook_Null() {
    // addContact() returns true meaning null was added in address book
    assertTrue(addressbook.addContact(null));
    // address book size equals 1 meaning null was added in address book
    assertEquals(1, addressbook.numberOfContacts());
  }

  @Test
  public void testRemoveContact_Existing() {
    Contact c = new Contact.Builder().withName("Tim").build();
    assertTrue(addressbook.addContact(c));
    assertTrue(addressbook.removeContact(c));
  }

  @Test
  public void testRemoveContact_NotExisitng() {
    Contact c = new Contact.Builder().withName("Tim").build();
    assertFalse(addressbook.removeContact(c));
  }

  /**
   * [BUG]
   * A Null Pointer Exception should have been thrown when null is passed
   * as an argument to remove contact method. But in this case, it doesn't
   * throws a Null Pointer Exception.
   */
  @Test(expected = NullPointerException.class)
  public void testRemoveContact_Null() {
    assertFalse(addressbook.removeContact(null));
  }

  @Test(expected = NullPointerException.class)
  public void testSearch_Null() {
    Contact c = new Contact.Builder().withName("Tim").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.NAME, null);
    assertEquals(0, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithName_Exists() {
    Contact c = new Contact.Builder().withName("Tim").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.NAME, "Tim");
    assertEquals(1, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithName_NotExists() {
    Contact c = new Contact.Builder().withName("John").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.NAME, "Tim");
    assertEquals(0, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithName_NameIsNull() {
    Contact c = new Contact.Builder().withNote("Tim").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.NAME, "Tim");
    assertEquals(0, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithEmail_Exists() {
    Contact c = new Contact.Builder().withEmail("tim@email.com").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.EMAIL, "tim@email.com");
    assertEquals(1, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithEmail_NotExists() {
    Contact c = new Contact.Builder().withEmail("john@gmail.org").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.EMAIL, "tim@email.com");
    assertEquals(0, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithEmail_EmailIsNull() {
    Contact c = new Contact.Builder().withName("John").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.EMAIL, "John");
    assertEquals(0, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithAddress_Exists() {
    Contact c = new Contact.Builder().withAddress("425 Blvd.").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.ADDRESS, "425 Blvd.");
    assertEquals(1, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithAddress_NotExists() {
    Contact c = new Contact.Builder().withAddress("500 Avenue").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.ADDRESS, "425 Blvd.");
    assertEquals(0, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithAddress_AddressIsNull() {
    Contact c = new Contact.Builder().withEmail("john@gmail.org").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.ADDRESS, "john@gmail.org");
    assertEquals(0, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithPhoneNumber_Exists() {
    Contact c = new Contact.Builder().withPhoneNumber("123456").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.PHONE, "123456");
    assertEquals(1, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithPhoneNumber_NotExists() {
    Contact c = new Contact.Builder().withPhoneNumber("100").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.PHONE, "1234567890");
    assertEquals(0, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithPhoneNumber_PhoneNumberIsNull() {
    Contact c = new Contact.Builder().withEmail("john@gmail.org").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.PHONE, "john@gmail.org");
    assertEquals(0, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithNote_Exists() {
    Contact c = new Contact.Builder().withNote("ABCD").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.NOTE, "ABCD");
    assertEquals(1, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithNote_NotExists() {
    Contact c = new Contact.Builder().withNote("EFGH").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.NOTE, "ABCD");
    assertEquals(0, matchedContacts.size());
  }

  @Test
  public void testSearch_ContactWithNote_NoteIsNull() {
    Contact c = new Contact.Builder().withName("John").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.NOTE, "John");
    assertEquals(0, matchedContacts.size());
  }

  @Test
  public void testSearch_MultipleContacts_Exist() {
    Contact c = new Contact.Builder().withName("Tim").withAddress("425 Blvd.")
        .build();
    assertTrue(addressbook.addContact(c));
    c = new Contact.Builder().withName("Tim").withAddress("500 Blvd.").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.NAME, "Tim");
    assertEquals(2, matchedContacts.size());
  }

  @Test
  public void testSearch_CaseInsensitive() {
    Contact c = new Contact.Builder().withName("Tim").withAddress("425 Blvd.")
        .build();
    assertTrue(addressbook.addContact(c));
    c = new Contact.Builder().withName("Tim").withAddress("500 Blvd.").build();
    assertTrue(addressbook.addContact(c));
    ArrayList<Contact> matchedContacts =
        addressbook.search(ContactAttribute.NAME, "tim");
    assertEquals(2, matchedContacts.size());
  }

  @Test
  public void testSave_NonEmptyAddressBook()
      throws IOException, FileNotFoundException {
    Contact c = new Contact.Builder().withNote("ABCD").build();
    assertTrue(addressbook.addContact(c));
    c = new Contact.Builder().withName("Anuj").build();
    assertTrue(addressbook.addContact(c));
    c = new Contact.Builder().withAddress("425 Blvd.").build();
    assertTrue(addressbook.addContact(c));
    File temp = File.createTempFile("contacts", ".gson");
    addressbook.save(temp.toString());
  }

  @Test
  public void testSave_EmptyAddressBook()
      throws IOException, FileNotFoundException {
    File temp = File.createTempFile("contacts", ".gson");
    addressbook.save(temp.toString());
    assertTrue(new File(temp.toString()).exists());
  }

  @Test(expected = FileNotFoundException.class)
  public void testSave_EmptyAddressBook_InvalidPath()
      throws IOException, FileNotFoundException {
    addressbook.save("/");
  }

  @Test(expected = NullPointerException.class)
  public void testSave_EmptyAddressBook_null()
      throws IOException, FileNotFoundException {
    addressbook.save(null);
  }

  @Test(expected = NullPointerException.class)
  public void testLoadContacts_null() throws IOException {
    AddressBook addressbook = new AddressBook(null);
    assertEquals(addressbook.numberOfContacts(), 0);
  }

  @Test(expected = FileNotFoundException.class)
  public void testLoadContacts_InvalidPath()
      throws IOException, FileNotFoundException {
    AddressBook addressbook = new AddressBook("mfn");
    assertEquals(addressbook.numberOfContacts(), 0);
  }

  //The test creates multiple contacts, saves them and imports them to a new
  // address book and finally validates the size of the address book of
  // imported contacts
  @Test
  public void testLoadContacts_MultipleContacts_BySize()
      throws IOException, FileNotFoundException {
    Contact c = new Contact.Builder().withNote("ABCD").build();
    assertTrue(addressbook.addContact(c));
    c = new Contact.Builder().withName("Anuj").build();
    assertTrue(addressbook.addContact(c));
    c = new Contact.Builder().withAddress("425 Blvd.").build();
    assertTrue(addressbook.addContact(c));
    assertEquals(3, addressbook.numberOfContacts());
    File temp = File.createTempFile("contacts", ".gson");
    addressbook.save(temp.toString());
    AddressBook addressbook = new AddressBook(temp.toString());
    assertEquals(3, addressbook.numberOfContacts());
  }

  // The test creates multiple contacts, saves them and imports them to a new
  // address book and finally validates the data of all imported contacts
  @Test
  public void testLoadContacts_MultipleContacts_ByData()
      throws IOException, FileNotFoundException {
    Contact c = new Contact.Builder().withNote("ABCD").build();
    assertTrue(addressbook.addContact(c));
    c = new Contact.Builder().withName("Anuj").build();
    assertTrue(addressbook.addContact(c));
    c = new Contact.Builder().withAddress("Blvd.").build();
    assertTrue(addressbook.addContact(c));
    assertEquals(3, addressbook.numberOfContacts());
    File temp = File.createTempFile("contacts", ".gson");
    addressbook.save(temp.toString());
    AddressBook addressbook = new AddressBook(temp.toString());
    assertEquals(1, addressbook.search(ContactAttribute.NOTE, "ABCD").size());
    assertEquals(1, addressbook.search(ContactAttribute.NAME, "Anuj").size());
    assertEquals(1, addressbook.search(ContactAttribute.ADDRESS, "Blvd.")
        .size());
    assertEquals("ABCD", addressbook.search(ContactAttribute.NOTE, "ABCD")
        .get(0).getNote());
    assertEquals("Anuj", addressbook.search(ContactAttribute.NAME, "Anuj")
        .get(0).getName());
    assertEquals("Blvd.", addressbook.search(ContactAttribute.ADDRESS, "Blvd.")
        .get(0).getAddress());
  }

  @Test
  public void testToString_ZeroContacts() {
    String toStringOutput = addressbook.toString();
    assertEquals("", toStringOutput);
  }

  @Test
  public void testToString_SingleContact() {
    Contact c = new Contact.Builder().withName("Tim").withNote("ABCD").build();
    addressbook.addContact(c);
    String toStringOutput = addressbook.toString();
    assertEquals("Tim\nABCD\n\n", toStringOutput);
  }

  @Test
  public void testToString_MultipleContacts() {
    Contact c = new Contact.Builder().withName("Tim").withNote("ABCD").build();
    addressbook.addContact(c);
    c = new Contact.Builder().withName("John").withPhoneNumber("1234").build();
    addressbook.addContact(c);
    String toStringOutput = addressbook.toString();
    assertEquals("Tim\nABCD\n\nJohn\n1234\n\n", toStringOutput);
  }
}