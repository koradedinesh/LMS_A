package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class LeaveDetailsService {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    public static final String SUBMITTED = "submitted";
    public static final String APPROVED = "approved";
    public static final String REJECTED = "rejected";

    @Autowired
    LeaveDetailsRepository leaveDetailsRepository;


    public void addLeave(Person person, LeaveDetails leaveDetails) throws ParseException {
        leaveDetails.setPerson(person);
        leaveDetails.setSubmittedOn(leaveDetails.getSubmittedOn());
        leaveDetails.setLeaveTo(leaveDetails.getLeaveTo());
        leaveDetails.setLeaveFrom(leaveDetails.getLeaveFrom());
        leaveDetails.setStatus(SUBMITTED);
        leaveDetailsRepository.save(leaveDetails);
    }

    public List<LeaveDetails> getLeaveDetailsHistory(Person person) {
        return person.getLeaveDetails();
    }

    public LeaveDetails updateLeaveStatusAndComment(String status, LeaveDetails leaveDetails) {
        LeaveDetails currentLeaveDetails = leaveDetailsRepository.findById(leaveDetails.getRequestId()).orElse(null);
        if (null != currentLeaveDetails) {
            if (status.equalsIgnoreCase("approve")) {
                currentLeaveDetails.setStatus(APPROVED);
            } else {
                currentLeaveDetails.setStatus(REJECTED);
            }
            currentLeaveDetails.setComment((leaveDetails.getComment() == null ? "" : leaveDetails.getComment()));
            leaveDetailsRepository.save(currentLeaveDetails);
            return currentLeaveDetails;
        }
        //return new means leave details found for personID
        return new LeaveDetails();
    }
}
