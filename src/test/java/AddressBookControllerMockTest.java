import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.example.AddressBookController;
import org.example.AddressBookInterface;
import org.example.BuddyInfoInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AddressBookController.class)
public class AddressBookControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressBookInterface addressBookRepository;

    @MockBean
    private BuddyInfoInterface buddyInfoRepository;

    @Test
    void testAddBuddyMock() throws Exception {
        String buddyJson = "{\"name\":\"Alice\",\"address\":\"Wonderland\",\"phoneNumber\":1234567890}";

        mockMvc.perform(post("/addressbooks/buddies")
                        .contentType("application/json")
                        .content(buddyJson))
                .andExpect(status().isOk());
    }
}
