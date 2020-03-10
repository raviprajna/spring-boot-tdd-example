package ravi.labs.model;

import java.util.concurrent.atomic.AtomicLong;

public class Person {
    private static final AtomicLong counter = new AtomicLong();

    private Long id;
    private String firstName;
    private String lastName;

    // Static block to be used before constructors
    {
        counter.getAndIncrement();
    }
    private Person() {
        // empty
    }

    public Person(String firstName, String lastName) {
        this.id = counter.get();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
