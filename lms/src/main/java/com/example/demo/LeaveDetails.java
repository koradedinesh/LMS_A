package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "leave_details")
public class LeaveDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer requestId;

    @Column(name = "leave_from")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date leaveFrom;

    @Column(name = "leave_to")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date leaveTo;

    @Column(name = "submitted_on")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date submittedOn;
    private String reason;
    private String status;
    private String comment;

    @Column(name = "leaves_applied")
    private int numberOfLeavesApplied;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public LeaveDetails() {
    }

    public LeaveDetails(int requestId, Date leaveFrom, Date leaveTo, Date submittedOn, String reason, String status, String comment, int numberOfLeavesApplied, Person person) {
        this.requestId = requestId;
        this.leaveFrom = leaveFrom;
        this.leaveTo = leaveTo;
        this.submittedOn = submittedOn;
        this.reason = reason;
        this.status = status;
        this.comment = comment;
        this.numberOfLeavesApplied = numberOfLeavesApplied;
        this.person = person;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Date getLeaveFrom() {
        return leaveFrom;
    }

    public void setLeaveFrom(Date leaveFrom) {
        this.leaveFrom = leaveFrom;
    }

    public Date getLeaveTo() {
        return leaveTo;
    }

    public void setLeaveTo(Date leaveTo) {
        this.leaveTo = leaveTo;
    }

    public Date getSubmittedOn() {
        return submittedOn;
    }

    public void setSubmittedOn(Date submittedOn) {
        this.submittedOn = submittedOn;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNumberOfLeavesApplied() {
        return numberOfLeavesApplied;
    }

    public void setNumberOfLeavesApplied(int numberOfLeavesApplied) {
        this.numberOfLeavesApplied = numberOfLeavesApplied;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
