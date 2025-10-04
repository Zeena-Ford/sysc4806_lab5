package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AddressBookThymeController {

    private final org.example.AddressBookInterface addressBookRepo;

    public AddressBookThymeController(org.example.AddressBookInterface addressBookRepo) {
        this.addressBookRepo = addressBookRepo;
    }

    @GetMapping("/addressbooks/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        AddressBook book = addressBookRepo.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("AddressBook not found"));
        model.addAttribute("book", book);
        model.addAttribute("buddies", book.getBuddies());
        return "addressbooks";
    }
}