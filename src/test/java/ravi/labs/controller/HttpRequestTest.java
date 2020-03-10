package ravi.labs.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import ravi.labs.TestData;
import ravi.labs.exception.PersonNotFoundException;
import ravi.labs.model.Person;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        TestData.setUpTestData();
    }

    @Test
    public void shouldReturnPersonDetails() throws Exception {
        assertThat(
            this.restTemplate.getForObject(
                "http://localhost:" + port + "/person/smith/mary",
                String.class
            )
        ).contains("Mary");
    }

    @Test
    public void shouldReturnPersonNotFound() throws PersonNotFoundException {
        assertThat(
            this.restTemplate.getForObject(
                "http://localhost:" + port + "/person/yyyy/xxxx",
                String.class
            )
        ).contains("Not Found");
    }


    @Test
    public void shouldReturnAllPersonBySurnameFromService() throws Exception {
        List<Person> persons = TestData.getPersonsByLastNamePrajna();

        assertThat(
                this.restTemplate.getForObject(
                        "http://localhost:" + port + "/persons?lastName=Prajna",
                        List.class
                )
        ).size().isEqualTo(persons.size());

    }

    @Test
    public void shouldReturnEmptyPersonListFromService() throws Exception {
        assertThat(
                this.restTemplate.getForObject(
                        "http://localhost:" + port + "/persons?lastName=XXXX",
                        List.class
                )
        ).isEmpty();
    }

    @Test
    public void shouldAddPersonFromService() throws Exception {
        final Person request = new Person("FN", "LN");
        assertThat(
                this.restTemplate.postForObject(
                        "http://localhost:" + port + "/person",
                        request,
                        Person.class
                )
        ).toString().contains("FN");
    }

    @Test
    public void shouldThrowExceptionWhileAdddingPersonFromService() throws Exception {
        final Person request = new Person("", "");
        assertThat(
                this.restTemplate.postForObject(
                        "http://localhost:" + port + "/person",
                        request,
                        Person.class
                )
        ).toString().contains("Person with same first and last name exist or Person firsname and lastname both cannot be empty");
    // TODO : Check why error message is not getting printed in expected format
    }
}