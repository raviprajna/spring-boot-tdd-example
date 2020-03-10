package ravi.labs.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ravi.labs.exception.PersonConstraintVoilationException;
import ravi.labs.exception.PersonNotFoundException;
import ravi.labs.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class PersonDataService {
    public static List<Person> PERSON_DATA = new ArrayList<>();

    // Static block for adding reference data
    {
        PERSON_DATA.add(new Person("Ravi", "Prajna"));
        PERSON_DATA.add(new Person("Poorna", "Prajna"));
        PERSON_DATA.add(new Person("Mary", "Smith"));
        PERSON_DATA.add(new Person("Brian", "Archer"));
        PERSON_DATA.add(new Person("Collin", "Brown"));
    }

    public Person findPerson(String lastName, String firstName) throws PersonNotFoundException {
        final Predicate<Person> personFirstNameAndLastNamePredicate =
                p -> p.getFirstName().equalsIgnoreCase(firstName)
                    && p.getLastName().equalsIgnoreCase(lastName);
        final List<Person> persons = filterPersonByPredicate(personFirstNameAndLastNamePredicate);

        if(persons.isEmpty())
            throw new PersonNotFoundException("Could not find Person by firstName : " +firstName + " , and lastName : "+ lastName );
        else
            return persons.get(0);
    }

    public List<Person> findAllPersonBySurname(String lastName) {
        final Predicate<Person> personLastNamePredicate = p -> p.getLastName().equalsIgnoreCase(lastName);
        final List<Person> persons = filterPersonByPredicate(personLastNamePredicate);

        return persons;
    }

    private List<Person> filterPersonByPredicate(Predicate<Person> personPredicate) {
        return PERSON_DATA.stream()
                .filter(personPredicate)
                .collect(Collectors.toList());
    }

    public Person addPerson(Person person) throws PersonConstraintVoilationException {
        final Predicate<Person> personFirstNameAndLastNamePredicate =
                p -> p.getFirstName().equalsIgnoreCase(person.getFirstName())
                        && p.getLastName().equalsIgnoreCase(person.getLastName());
        final List<Person> persons = filterPersonByPredicate(personFirstNameAndLastNamePredicate);
        if(!persons.isEmpty()){
            throw new PersonConstraintVoilationException("Person with same first and last name exist");
        }
        if(StringUtils.isEmpty(person.getLastName()) && StringUtils.isEmpty(person.getFirstName()) ){
            throw new PersonConstraintVoilationException("Person first and last name cannot be empty");
        }
        Person newPerson = new Person(person.getFirstName(), person.getLastName());
        PERSON_DATA.add(newPerson);
        return newPerson;
    }
}
