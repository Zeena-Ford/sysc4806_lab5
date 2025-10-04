package org.example;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;



/**
 * @author Zeena Ford, 101229954
 * @version September 28 2025
 */

@Entity
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<BuddyInfo> budLists = new ArrayList<>();

    public AddressBook() {}

    public void addBuddy(BuddyInfo buddy) {
        budLists.add(buddy);
    }

    public List<BuddyInfo> getBuddies() {
        return budLists;
    }

    public int getId() {
        return id;
    }

    public void removeBuddy(BuddyInfo buddy) {
        budLists.remove(buddy);
    }

    public List<BuddyInfo> getbudLists() {
        return budLists;
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();

        BuddyInfo buddy1 = new BuddyInfo("Zeena", "Greenland Street", 613123456);
        BuddyInfo buddy2 = new BuddyInfo("Alex", "Main Street", 5551234);

        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        //addressBook.printBuddies();
    }
}
