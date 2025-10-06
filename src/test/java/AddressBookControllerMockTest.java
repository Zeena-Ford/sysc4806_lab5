import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.AddressBook;
import org.example.AddressBookController;
import org.example.AddressBookInterface;
import org.example.BuddyInfoInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@WebMvcTest(AddressBookController.class)
public class AddressBookControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressBookInterface addressBookRepo;

    @MockBean
    private BuddyInfoInterface buddyRepo;

    @Test
    void testCreateAddressBookMock() throws Exception {
        String bookJson = "{\"buddies\":[]}";

        // Simulate saving an AddressBook (the repo just returns what’s passed in)
        when(addressBookRepo.save(org.mockito.Mockito.any(AddressBook.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/addressbooks/Create")
                        .contentType("application/json")
                        .content(bookJson))
                .andExpect(status().isOk());
    }

    @Test
    void testAddBuddyMock() throws Exception {
        // Create a dummy AddressBook instance
        AddressBook mockBook = new AddressBook();

        // Simulate repo behavior: findById(1) returns this dummy book
        when(addressBookRepo.findById(1)).thenReturn(Optional.of(mockBook));

        // Simulate saving the AddressBook
        when(addressBookRepo.save(org.mockito.Mockito.any(AddressBook.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // JSON body for a BuddyInfo
        String buddyJson = "{\"name\":\"Alice\",\"address\":\"Wonderland\",\"phoneNumber\":1234567890}";

        mockMvc.perform(post("/addressbooks/buddies")
                        .contentType("application/json")
                        .content(buddyJson))
                .andExpect(status().isOk());
    }
}