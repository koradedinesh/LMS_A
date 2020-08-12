package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LeaveCountController {

    @Autowired
    LeaveCountService leaveCountService;

    @Autowired
    PersonService personService;

    @GetMapping("/getAvailableLeavesById")
    public Integer getAvailableLeaves(@RequestParam Integer personId) {
        Person person = personService.getPersonById(personId);
        return person.getLeaveCount().getAvailableLeaves();
    }

    @GetMapping("/getLeaveCountByPersonId")
    public LeaveCount getLeaveCountByPersonId(@RequestParam Integer personId) {
        Person person = personService.getPersonById(personId);
        return person.getLeaveCount();
    }
}
