package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/addressbooks")
public class AddressBookController {

    @Autowired
    private org.example.AddressBookInterface addressBookRepo;
    private org.example.BuddyInfoInterface buddyRepo;

    public AddressBookController(org.example.AddressBookInterface addressBookRepo, org.example.BuddyInfoInterface buddyRepo) {
        this.addressBookRepo = addressBookRepo;
        this.buddyRepo = buddyRepo;
    }

    // Create an AddressBook
    @PostMapping("/Create")
    public AddressBook createAddressBook(@RequestBody AddressBook book) {
        return addressBookRepo.save(book);
    }


    @GetMapping
    public Iterable<AddressBook> getAll() {
        return addressBookRepo.findAll();
    }

    @PostMapping("/buddies")
    public BuddyInfo addBuddy(@RequestBody BuddyInfo buddy) {
        // Always add to AddressBook with id 1 (or whatever you want)
        AddressBook book = addressBookRepo.findById(1).orElseThrow();
        book.addBuddy(buddy);
        addressBookRepo.save(book);
        return buddy;
    }

    //@PostMapping("/Create")
    //public AddressBook createAddressBooks(@RequestBody AddressBook book) {
    //   return addressBookRepo.save(book); // return the saved book
    //}


    @DeleteMapping("/{bookId}/buddies/{buddyId}")
    public AddressBook removeBuddy(@PathVariable int bookId, @PathVariable Long buddyId) {
        AddressBook book = addressBookRepo.findById(Math.toIntExact(bookId))
                .orElseThrow(() -> new RuntimeException("AddressBook not found"));
        BuddyInfo buddy = buddyRepo.findById(buddyId)
                .orElseThrow(() -> new RuntimeException("Buddy not found"));
        book.removeBuddy(buddy);
        buddyRepo.delete(buddy);
        return addressBookRepo.save(book);
    }
}