package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
@Table(name = "leave_count")
public class LeaveCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "count_id")
    private Integer countId;

    @Column(name = "leaves_taken")
    private Integer leavesTaken;

    @Column(name = "available_leaves")
    private Integer availableLeaves;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public LeaveCount() {
    }

    public LeaveCount(Integer countId, Integer leavesTaken, Integer availableLeaves, Person person) {
        this.countId = countId;
        this.leavesTaken = leavesTaken;
        this.availableLeaves = availableLeaves;
        this.person = person;
    }

    public Integer getCountId() {
        return countId;
    }

    public void setCountId(Integer countId) {
        this.countId = countId;
    }

    public Integer getLeavesTaken() {
        return leavesTaken;
    }

    public void setLeavesTaken(Integer leavesTaken) {
        this.leavesTaken = leavesTaken;
    }

    public Integer getAvailableLeaves() {
        return availableLeaves;
    }

    public void setAvailableLeaves(Integer availableLeaves) {
        this.availableLeaves = availableLeaves;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
