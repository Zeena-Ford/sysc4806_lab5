import org.example.AddressBook;
import org.example.BuddyInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AddressBookIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateAddressBook() {
        ResponseEntity<AddressBook> response = restTemplate.postForEntity(
                "/addressbooks/Create", null, AddressBook.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isGreaterThan(0);
    }

    @Test
    void testAddBuddy() {
        // Create AddressBook first
        AddressBook ab = restTemplate.postForObject("/addressbooks/Create", null, AddressBook.class);

        // Add BuddyInfo
        BuddyInfo buddy = new BuddyInfo("Alice", "Wonderland", 1234);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BuddyInfo> request = new HttpEntity<>(buddy, headers);

        ResponseEntity<AddressBook> response = restTemplate.postForEntity(
                "/addressbooks/buddies", request, AddressBook.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getBuddies()).isNotEmpty();
        assertThat(response.getBody().getBuddies().get(0).getName()).isEqualTo("Alice");
    }
}
