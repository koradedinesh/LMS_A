package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public Person getPersonByUsernameAndPassword(Integer personId, String password) {
        List<Person> personList = personRepository.findAll();
        Person person = personList.stream()
                .filter(user -> user.getPersonId().equals(personId) && user.getPassword().equals(password)).findAny().get();
        return person;
    }

    public Person getPersonById(Integer personId) {
        Optional<Person> person = personRepository.findById(personId);
        return person.get();
    }

    public Person getPersonByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
