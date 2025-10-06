import org.example.AddressBook;
import org.example.BuddyInfo;
import org.example.ProgramApplication;  // your main class
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ProgramApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressBookIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void testCreateAddressBook() {
        ResponseEntity<AddressBook> response = restTemplate.postForEntity(
                getBaseUrl() + "/addressbooks/Create", null, AddressBook.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isGreaterThan(0);
    }

    @Test
    void testAddBuddy() {
        // Step 1: create an AddressBook
        AddressBook ab = restTemplate.postForObject(
                getBaseUrl() + "/addressbooks/Create", null, AddressBook.class);
        assertThat(ab).isNotNull();

        // Step 2: add a BuddyInfo (same JSON as your curl command)
        BuddyInfo buddy = new BuddyInfo("Alice", "Wonderland", 123456789);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BuddyInfo> request = new HttpEntity<>(buddy, headers);

        ResponseEntity<AddressBook> response = restTemplate.postForEntity(
                getBaseUrl() + "/addressbooks/buddies", request, AddressBook.class);

        // Step 3: verify the buddy was added
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getBuddies()).isNotEmpty();
        assertThat(response.getBody().getBuddies().get(0).getName()).isEqualTo("Alice");
    }
}
