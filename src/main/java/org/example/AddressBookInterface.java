package org.example;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Zeena Ford, 101229954
 * @version September 28 2025
 */


public interface AddressBookInterface extends CrudRepository<org.example.AddressBook, Integer> {
}