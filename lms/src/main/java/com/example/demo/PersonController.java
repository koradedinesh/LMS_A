package com.example.demo;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    LeaveCountService leaveCountService;


    @GetMapping("/getPersonByIdAndPassword")
    public Person getPersonByUsername(@RequestParam Integer personId, @RequestParam String password) {
        return personService.getPersonByUsernameAndPassword(personId, password);
    }

    @PostMapping("/savePerson")
    public void savePerson(@RequestBody @NotNull Person person) {
        Person currentPerson = personService.savePerson(person);
        leaveCountService.setInitialLeaveCount(currentPerson);
    }

    @GetMapping("/getPersonByUsername")
    public Person getPersonByUsername(@RequestParam String username) {
        return personService.getPersonByUsername(username);
    }

    @GetMapping("/getPendingLeaves")
    public List<Map<String, List<LeaveDetails>>> getPendingLeaves() {
        Map<String, List<LeaveDetails>> output = new HashMap<>();
        List<Map<String, List<LeaveDetails>>> pendingLeaveDetails = new ArrayList<>();
        List<Person> personList = personService.getAllPersons();
        for (Person person : personList) {
            List<LeaveDetails> filteredLeaveDetails;
            filteredLeaveDetails = person.getLeaveDetails().stream().filter(l -> l.getStatus().equals("submitted"))
                    .map(detail -> {
                        detail.setPerson(null);
                        return detail;
                    })
                    .collect(Collectors.toList());
            output.put(person.getUsername(), filteredLeaveDetails);
        }
        pendingLeaveDetails.add(output);
        return pendingLeaveDetails;
    }
}
