package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LeaveDetailsController {
    public static final String SUCCESS = "leaves applied";
    public static final String ALREADY_PUBLIC_HOLIDAY = "Public holiday or weekend";
    public static final String NO_AVAILABLE_LEAVES = "No Leaves available";


    @Autowired
    LeaveDetailsService leaveDetailsService;

    @Autowired
    PersonService personService;

    @Autowired
    LeaveCountService leaveCountService;

    @PostMapping("/applyForLeave")
    public Map<Boolean, String> applyLeave(@RequestParam Integer personId, @RequestBody LeaveDetails leaveDetails) throws ParseException {
        int daysExclude = 0;
        Map<Boolean, String> output = new HashMap<>();
        Person currentPerson = personService.getPersonById(personId);
        LeaveCount leaveCount = currentPerson.getLeaveCount();
        int numberOfAppliedDays = Math.toIntExact(ChronoUnit.DAYS.between(leaveDetails.getLeaveFrom().toInstant(), leaveDetails.getLeaveTo().toInstant())) + 1;
        Map<Boolean, Integer> publicHolidayMap = leaveCountService.isPublicHolidayOrWeekend(leaveDetails);
        Map.Entry<Boolean, Integer> entry = publicHolidayMap.entrySet().iterator().next();

        if (entry.getKey().equals(true) && entry.getValue().equals(0)) {
            output.put(false, ALREADY_PUBLIC_HOLIDAY);
            return output;
        } else if (entry.getKey().equals(true) && entry.getValue() > 0) {
            daysExclude = entry.getValue();
        }
        if (!leaveCountService.isEligibleForLeaves(numberOfAppliedDays, leaveCount, daysExclude)) {
            output.put(false, NO_AVAILABLE_LEAVES);
            return output;
        }
        Person person = personService.getPersonById(personId);
        leaveDetails.setNumberOfLeavesApplied(numberOfAppliedDays - daysExclude);
        leaveDetailsService.addLeave(person, leaveDetails);
        output.put(true, SUCCESS);
        return output;
    }

    @GetMapping("/getLeaveDetailsHistory")
    public List<LeaveDetails> getLeaveDetailsHistory(@RequestParam Integer personId) {
        Person currentPerson = personService.getPersonById(personId);
        return leaveDetailsService.getLeaveDetailsHistory(currentPerson);
    }


    @PostMapping("/approveLeaveRequest")
    public LeaveDetails approveLeaveRequest(@RequestParam String username, @RequestParam String status, @RequestBody LeaveDetails leaveDetails) {
        Person person = personService.getPersonByUsername(username);
        LeaveCount leaveCount = person.getLeaveCount();
        int numberOfAppliedDays = leaveDetails.getNumberOfLeavesApplied();
        if (leaveCount.getAvailableLeaves() > 0) {
            leaveCountService.updateLeaveCount(person, numberOfAppliedDays, leaveCount);
            return leaveDetailsService.updateLeaveStatusAndComment(status, leaveDetails);
        }
        leaveDetailsService.updateLeaveStatusAndComment("rejected", leaveDetails);
        return new LeaveDetails();
    }

    @PostMapping("/rejectLeaveRequest")
    public LeaveDetails rejectLeaveRequest(@RequestParam String status, @RequestBody LeaveDetails leaveDetails) {
        return leaveDetailsService.updateLeaveStatusAndComment(status, leaveDetails);
    }

}
