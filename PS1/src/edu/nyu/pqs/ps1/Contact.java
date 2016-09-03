package edu.nyu.pqs.ps1;

import java.util.UUID;

/**
 * Represents a Contact in the Address Book. It consists of :
 * <ul>
 * <li> name (required) </li> 
 * <li> id (automatically generated for each Contact object and unique for each object) </li>
 * <li> postalAddress (optional; empty if not provided) </li>
 * <li> phoneNumber (optional; empty if not provided) </li>
 * <li> email (optional; empty if not provided) </li>
 * <li> address (optional; empty if not provided) </li>
 * <li> note (optional; empty if not provided) </li>
 * </ul>
 * @author Anuj Bora
 */
public class Contact {
  private final String name;
  private final UUID id;
  private final String postalAddress; 
  private final String phoneNumber;
  private final String email;
  private final String address;
  private final String note;
	
  /**
   * Build the contact using Builder Pattern. Builder Pattern simulates the optional
   * parameters : postal address, phone number, email, address and note.
   * The parameterized constructor automatically assigns a unique ID i.e. UUID to 
   * each new contact.
   * @author Anuj Bora
   */
  public static class Builder {
    private final String name;
    private final UUID id;
    private String postalAddress = "";
    private String phoneNumber = "";
    private String email = "";
    private String address = "";
    private String note = "";

    public Builder(String name) {
      if (name == null) {
        throw new IllegalArgumentException("Name cannot be null");   
      } else {
        this.name = name;
        this.id = UUID.randomUUID();
      }
    }        

    /**
     * Sets postal address in the builder
     * @param postalAddress String the postal address of contact
     * @return current instance of the Builder
     * @throws IllegalArgumentException if the argument is null
     */
    public Builder withpostalAddress(String postalAddress) {
      if (postalAddress == null) {
        throw new IllegalArgumentException("Postal Address cannot be null");
      } else {
        this.postalAddress = postalAddress;
      }
      return this;
    }
		
    /**
     * Sets phone number in the builder. If the phone number provided in argument
     * is of type (XXX) XXX-XXXX; it will be converted and stored as XXXXXXXXXX.
     * Hyphens, spaces and round brackets will be filtered out.
     * <b> No other validations will be performed except a check for null value. </b>
     * @param phoneNumber String the phone number of contact
     * @return current instance of the Builder
     * @throws IllegalArgumentException if the argument is null
     */
    public Builder withphoneNumber(String phoneNumber) {
      if (phoneNumber == null) {
        throw new IllegalArgumentException("Phone Number cannot be null");
      } else {
        // Regular expression removes hyphens and round brackets from phone number
        this.phoneNumber = phoneNumber.replaceAll("[\\s\\-()]", "");
        return this;	
      }
    }
		
    /**
     * Sets email in the builder. <b> No validations are performed except 
     * a check for null value. </b> 
     * @param email String the email of the contact
     * @return current instance of the Builder
     * @throws IllegalArgumentException if the argument is null
     */
    public Builder withEmail(String email) {
      if (email == null) {
        throw new IllegalArgumentException("Email Address cannot be null");
      } else {
        this.email = email;
        return this;		
      }
    }
		
    /**
     * Sets address in the builder. <b> No validations are performed except
     * a check for null value. </b>
     * @param address String the address of the contact
     * @return current instance of the Builder
     * @throws IllegalArgumentException if the argument is null
     */
    public Builder withAddress(String address) {
      if (address == null) {
        throw new IllegalArgumentException("Address cannot be null");
      } else {
        this.address = address;
        return this;
      }	
    }
		
    /**
     * Sets note in the builder. <b> No validations are performed except
     * a check for null value. </b>
     * @param note String the note of the contact
     * @return Builder current instance of the Builder
     * @throws IllegalArgumentException if the argument is null
     */
    public Builder withNote(String note) {
      if (note == null) {
        throw new IllegalArgumentException("Note cannot be null");
      } else {
        this.note = note;
        return this;
      }		
    }
		
    /**
     * Creates a new Contact object using Builder
     * @return Contact object with same values as Builder object
     */
    public Contact build() {
      return new Contact(this);
    }
  }
	
  private Contact(Builder builder) {
    name = builder.name;
    id = builder.id;
    postalAddress = builder.postalAddress;
    phoneNumber = builder.phoneNumber;
    email = builder.email;
    address = builder.address;
    note = builder.note;
  }

  public String getName() {
    return name;
  }
	
  public UUID getid() {
    return id;
  }
	
  public String getpostalAddress() {
    return postalAddress;
  }

  public String getphoneNumber() {
    return phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public String getAddress() {
    return address;
  }

  public String getNote() {
    return note;
  }
	
  /**
   * Returns all the data of the object delimited by a space
   * @return String having all the values of all the attributes of the object
   */
  public String rawData() {
    // Append all the data separated by white space and return
    return name + " " + postalAddress + " " + phoneNumber + " " + email + 
      " " + address + " " + note;
  }
  
  /**
   * Method automatically generated using Eclipse
   * Calculates the hash code of the object using values of all the attributes
   * @return integer hash code of the object
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((note == null) ? 0 : note.hashCode());
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    result = prime * result + ((postalAddress == null) ? 0 : postalAddress.hashCode());
    return result;
  }
  
  /**
   * Method automatically generated using Eclipse
   * The method checks if both the objects have equal values for all the attributes
   * @return boolean true if all the attributes of both objects match
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Contact other = (Contact) obj;
    // Check whether every attributes value matches for both objects
    if (!address.equals(other.getAddress())) {
      return false;
    }
    if (!email.equals(other.getEmail())) {
      return false;
    }
    if (!name.equals(other.getName())) {
      return false;
    }
    if (!note.equals(other.getNote())) {
      return false;
    }
    if (!phoneNumber.equals(other.getphoneNumber())) {
      return false;
    }
    if (!postalAddress.equals(other.getpostalAddress())) {
      return false;
    }
    return true;
  }
 
  @Override
  public String toString() {
    return "Contact [name=" + name + ", id=" + id + ", postalAddress=" + 
      postalAddress + ", phoneNumber=" + phoneNumber + ", email=" + email 
      + ", address=" + address + ", note=" + note + "]";
  }
}