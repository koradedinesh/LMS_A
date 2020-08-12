package com.example.demo;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
class PersonServiceTest {
    @InjectMocks
    PersonService personService;

    @Mock
    PersonRepository personRepository;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getPersonByUsernameAndPassword() {
        Person person = new Person();
        person.setPersonId(123);
        person.setPassword("John123");
        Person person1 = new Person();
        person1.setPersonId(245);
        person1.setPassword("Bob123");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        Mockito.when(personRepository.findAll()).thenReturn(personList);
        Person output = personService.getPersonByUsernameAndPassword(123, "John123");
        Assert.assertEquals(Integer.valueOf(123), output.getPersonId());

    }
}