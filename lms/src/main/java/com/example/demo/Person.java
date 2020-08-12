package com.example.demo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "person_id")
    private Integer personId;
    private String username;
    private String password;
    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<LeaveDetails> leaveDetails;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
    private LeaveCount leaveCount;

    public Person() {
    }

    public Person(Integer personId, String username, String password, String role, List<LeaveDetails> leaveDetails, LeaveCount leaveCount) {
        this.personId = personId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.leaveDetails = leaveDetails;
        this.leaveCount = leaveCount;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<LeaveDetails> getLeaveDetails() {
        return leaveDetails;
    }

    public void setLeaveDetails(List<LeaveDetails> leaveDetails) {
        this.leaveDetails = leaveDetails;
    }

    public LeaveCount getLeaveCount() {
        return leaveCount;
    }

    public void setLeaveCount(LeaveCount leaveCount) {
        this.leaveCount = leaveCount;
    }
}
