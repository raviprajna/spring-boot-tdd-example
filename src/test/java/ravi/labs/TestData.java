package ravi.labs;

import ravi.labs.model.Person;
import ravi.labs.service.PersonDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestData {
    public static List<Person> getPersonsByLastNamePrajna() {
        return PersonDataService.PERSON_DATA.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase("Prajna"))
                .collect(Collectors.toList());
    }

    public static List<Person> getEmptyPersonList() {
        return PersonDataService.PERSON_DATA.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase("XXXX"))
                .collect(Collectors.toList());
    }

    public static void setUpTestData(){
        PersonDataService.PERSON_DATA = new ArrayList<>();
        PersonDataService.PERSON_DATA.add(new Person("Ravi", "Prajna"));
        PersonDataService.PERSON_DATA.add(new Person("Poorna", "Prajna"));
        PersonDataService.PERSON_DATA.add(new Person("Mary", "Smith"));
        PersonDataService.PERSON_DATA.add(new Person("Brian", "Archer"));
        PersonDataService.PERSON_DATA.add(new Person("Collin", "Brown"));

    }
}
