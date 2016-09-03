package addressbook;

import static org.junit.Assert.*;

import org.junit.Test;

public class ContactTest {
  @Test(expected = IllegalArgumentException.class)
  public void testCreateContact_Empty() {
    Contact c = new Contact.Builder().build();
    assertEquals(null, c.getName());
    assertEquals(null, c.getEmailAddress());
    assertEquals(null, c.getAddress());
    assertEquals(null, c.getPhoneNumber());
    assertEquals(null, c.getNote());
  }

  @Test
  public void testCreateContact_WithName() {
    Contact c = new Contact.Builder().withName("Tim").build();
    assertTrue("Tim".equals(c.getName()));
  }

  @Test
  public void testCreateContact_WithDuplicateName() {
    Contact c1 = new Contact.Builder().withName("Tim").build();
    Contact c2 = new Contact.Builder().withName("Tim").build();
    assertTrue("Tim".equals(c1.getName()));
    assertTrue("Tim".equals(c2.getName()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContact_AssignNameTwice() {
    Contact c = new Contact.Builder().withName("Tim").withName("John").build();
    assertFalse("Tim".equals(c.getName()) || "John".equals(c.getName()));
  }

  /**
   * [BUG]
   * Assignment of null to a name should be prevented and a null pointer
   * exception should be thrown. But it assigns null to a name.
   * Javadocs does say that minimal checking is involved while creating
   * contact like allowance of emoji, question marks, etc. but it doesn't say
   * anything about null values.
   */
  @Test(expected = NullPointerException.class)
  public void testCreateContact_WithNameAsNull() {
    Contact c = new Contact.Builder().withName(null).build();
    assertEquals(null, c.getName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContact_AssignAddressTwice() {
    Contact c = new Contact.Builder().withAddress("425").withAddress("426")
        .build();
    assertFalse("425".equals(c.getAddress()) || "426".equals(c.getAddress()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContact_AssignEmailTwice() {
    Contact c = new Contact.Builder().withEmail("a@b.com").withEmail("b@a.com")
        .build();
    assertFalse("a@b.com".equals(c.getEmailAddress()) ||
        "b@a.com".equals(c.getEmailAddress()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContact_AssignPhoneNumberTwice() {
    Contact c = new Contact.Builder().withPhoneNumber("123").
        withPhoneNumber("132").build();
    assertFalse("123".equals(c.getPhoneNumber()) ||
        "132".equals(c.getPhoneNumber()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContact_AssignNoteTwice() {
    Contact c = new Contact.Builder().withNote("123").withNote("132").build();
    assertFalse("123".equals(c.getNote()) || "132".equals(c.getNote()));
  }

  @Test
  public void testCreateContact_WithEmail() {
    Contact c = new Contact.Builder().withEmail("tim@email.com").build();
    assertTrue("tim@email.com".equals(c.getEmailAddress()));
  }

  public void testCreateContact_WithIncorrectEmail() {
    Contact c = new Contact.Builder().withEmail("tim.email.com").build();
    assertTrue("tim.email.com".equals(c.getEmailAddress()));
  }

  /**
   * [BUG]
   * Assignment of null to a email should be prevented and a null pointer
   * exception should be thrown. But it assigns null to an email.
   */
  @Test(expected = NullPointerException.class)
  public void testCreateContact_WithEmailAsNull() {
    Contact c = new Contact.Builder().withEmail(null).build();
    assertEquals(null, c.getEmailAddress());
  }

  @Test
  public void testCreateContact_WithAddress() {
    Contact c = new Contact.Builder().withAddress("425 Blvd.").build();
    assertTrue("425 Blvd.".equals(c.getAddress()));
  }

  /**
   * [BUG]
   * Assignment of null to an address should be prevented and a null pointer
   * exception should be thrown. But it assigns null to an address.
   * Javadocs does say that minimal checking is involved while creating
   * contact like allowance of emoji, question marks, etc. but it doesn't say
   * anything about null values.
   */
  @Test(expected = NullPointerException.class)
  public void testCreateContact_WithAddressAsNull() {
    Contact c = new Contact.Builder().withAddress(null).build();
    assertEquals(null, c.getAddress());
  }

  @Test
  public void testCreateContact_WithValidPhoneNumber() {
    Contact c = new Contact.Builder().withPhoneNumber("1234567890")
        .build();
    assertTrue("1234567890".equals(c.getPhoneNumber()));
  }

  @Test
  public void testCreateContact_WithSpecialCharacterPhoneNumber() {
    Contact c = new Contact.Builder().withPhoneNumber("+1 (123) 456-789")
        .build();
    assertTrue("+1 (123) 456-789".equals(c.getPhoneNumber()));
  }

  @Test
  public void testCreateContact_WithInvalidPhoneNumber() {
    Contact c = new Contact.Builder().withPhoneNumber("ABCD").build();
    assertTrue("ABCD".equals(c.getPhoneNumber()));
  }

  /**
   * [BUG]
   * Assignment of null to phone number should be prevented and a null
   * pointer exception should be thrown. But it assigns null to phone number.
   * Javadocs does say that minimal checking is involved while creating
   * contact like allowance of emoji, question marks, etc. but it doesn't say
   * anything about null values.
   */
  @Test(expected = NullPointerException.class)
  public void testCreateContact_WithPhoneNumberAsNull() {
    Contact c = new Contact.Builder().withPhoneNumber(null).build();
    assertEquals(null, c.getPhoneNumber());
  }

  @Test
  public void testCreateContact_WithNote() {
    Contact c = new Contact.Builder().withNote("ABCD").build();
    assertTrue("ABCD".equals(c.getNote()));
  }

  /**
   * [BUG]
   * Assignment of null to note should be prevented and a null pointer
   * exception should be thrown. But it assigns null to note.
   * Javadocs does say that minimal checking is involved while creating
   * contact like allowance of emoji, question marks, etc. but it doesn't say
   * anything about null values.
   */
  @Test(expected = NullPointerException.class)
  public void testCreateContact_WithNoteAsNull() {
    Contact c = new Contact.Builder().withNote(null).build();
    assertEquals(null, c.getNote());
  }

  @Test(expected = NullPointerException.class)
  public void testGetName_ContactWithoutName() {
    Contact c = new Contact.Builder().withNote("ABCD").build();
    assertEquals(null, c.getName());
  }

  @Test(expected = NullPointerException.class)
  public void testGetNote_ContactWithoutNote() {
    Contact c = new Contact.Builder().withName("ABCD").build();
    assertEquals(null, c.getNote());
  }

  @Test(expected = NullPointerException.class)
  public void testGetPhone_ContactWithoutPhone() {
    Contact c = new Contact.Builder().withNote("ABCD").build();
    assertEquals(null, c.getPhoneNumber());
  }

  @Test(expected = NullPointerException.class)
  public void testGetAddress_ContactWithoutAddress() {
    Contact c = new Contact.Builder().withNote("ABCD").build();
    assertEquals(null, c.getAddress());
  }

  @Test(expected = NullPointerException.class)
  public void testGetEmail_ContactWithoutEmail() {
    Contact c = new Contact.Builder().withNote("ABCD").build();
    assertEquals(null, c.getEmailAddress());
  }

  /**
   * [BUG]
   * Test failed as the equal() method is not overridden.
   * The equal method should have been overridden so that two contacts
   * could have been compared for equality.
   */
  @Test
  public void testEquals() {
    Contact c1 = new Contact.Builder().withName("Tim").build();
    Contact c2 = new Contact.Builder().withName("Tim").build();
    assertTrue(c1.equals(c2));
  }

  @Test
  public void testToString_ContactWithOnlyName() {
    Contact c = new Contact.Builder().withName("Tim").build();
    String toStringResult = c.toString();
    assertEquals("Tim\n", toStringResult);
  }

  @Test
  public void testToString_ContactWithoutName() {
    Contact c = new Contact.Builder().withPhoneNumber("1234")
        .withEmail("a@b.com").withAddress("425 Blvd.").withNote("abcd")
        .build();
    String toStringResult = c.toString();
    assertEquals("a@b.com\n1234\n425 Blvd.\nabcd\n", toStringResult);
  }

  @Test
  public void testToString_ContactWithAllAttributes() {
    Contact c = new Contact.Builder().withName("Tim").withPhoneNumber("1234")
        .withEmail("a@b.com").withAddress("425 Blvd.").withNote("abcd")
        .build();
    String toStringResult = c.toString();
    assertEquals("Tim\na@b.com\n1234\n425 Blvd.\nabcd\n", toStringResult);
  }
}