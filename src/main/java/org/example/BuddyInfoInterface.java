package org.example;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Zeena Ford, 101229954
 * @version September 20 2025
 */

public interface BuddyInfoInterface extends CrudRepository<org.example.BuddyInfo, Long> {
    org.example.BuddyInfo findByName(String name);
}