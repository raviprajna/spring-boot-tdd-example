package ravi.labs.model;

import org.junit.Before;
import org.junit.Test;
import ravi.labs.TestData;
import ravi.labs.exception.PersonConstraintVoilationException;
import ravi.labs.exception.PersonNotFoundException;
import ravi.labs.service.PersonDataService;

import java.util.List;

import static junit.framework.TestCase.*;

public class PersonTest {

    PersonDataService personDataService = new PersonDataService();

    @Before
    public void setUp() throws Exception {
        TestData.setUpTestData();
    }

    @Test
    public void shouldAssignIdWhenCreated() {
        Person classUnderTest = new Person("John", "Smith");

        assertNotNull(classUnderTest.getId());
    }

    @Test
    public void idsShouldBeDifferent() {
        Person classUnderTest1 = new Person("John", "Smith");
        Person classUnderTest2 = new Person("Harry", "Brown");

        assertFalse(classUnderTest1.getId().equals(classUnderTest2.getId()));
    }


    @Test
    public void shouldBeDifferent() {
        Person classUnderTest1 = new Person("John", "Smith");
        Person classUnderTest2 = new Person("John", "Smith");

        assertNotSame(classUnderTest1, classUnderTest2);
    }

    @Test( expected = PersonNotFoundException.class)
    public void findPerson_NotFoundCase() throws PersonNotFoundException {
        personDataService.findPerson("XXXX", "YYYY");
    }

    @Test
    public void findPersonByFirstNameAndLastName() throws PersonNotFoundException {
        final Person expectedPerson = new Person("Ravi", "Prajna");
        final Person person = personDataService.findPerson(expectedPerson.getLastName(),
                expectedPerson.getFirstName());
        assertEquals(expectedPerson.getFirstName()+expectedPerson.getLastName(),
                person.getFirstName()+person.getLastName());
    }

    @Test
    public void findAllPersonByLastName() throws PersonNotFoundException {
        List<Person> expectedPersons = TestData.getPersonsByLastNamePrajna();

        final List<Person> persons = personDataService.findAllPersonBySurname("Prajna");
        assertEquals(expectedPersons.size(), persons.size());
        // Add more asserts
    }

    @Test( expected = PersonConstraintVoilationException.class)
    public void addDuplicatePerson_ExpectDuplicateException() throws PersonNotFoundException, PersonConstraintVoilationException {
        final Person person = personDataService.findPerson("Prajna", "Ravi");
        personDataService.addPerson(person);
    }

    @Test( expected = PersonConstraintVoilationException.class)
    public void addPersonWithEmptyNamesToRaiseEception() throws PersonNotFoundException, PersonConstraintVoilationException {
        Person person = new Person("", "");
        personDataService.addPerson(person);
    }

    @Test( expected = PersonConstraintVoilationException.class)
    public void addPersonWithNullValuesForNamesToRaiseEception() throws PersonNotFoundException, PersonConstraintVoilationException {
        Person person = new Person(null, null);
        personDataService.addPerson(person);
    }

    @Test
    public void addNewPerson() throws PersonNotFoundException, PersonConstraintVoilationException {
        final Person person = new Person("Kumar", "Kiran");
        final Person expectedPerson = personDataService.addPerson(person);
        assertEquals(person.getFirstName() + person.getLastName(),
                expectedPerson.getFirstName()+expectedPerson.getLastName());
    }

}
