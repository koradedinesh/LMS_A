package com.example.demo;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class LeaveCountServiceTest {

    @Test
    void isEligibleForLeaves() {
        LeaveCountService leaveCountService = new LeaveCountService();
        int numberOfDaysApplied = 3;
        int daysExcluded = 1; //means public holiday is included in appplied leaves
        LeaveCount leaveCount = new LeaveCount();
        leaveCount.setLeavesTaken(23);
        leaveCount.setAvailableLeaves(1);
        Boolean isEligibleForLeave = leaveCountService.isEligibleForLeaves(numberOfDaysApplied, leaveCount, daysExcluded);
        Assert.assertEquals(false, isEligibleForLeave);
    }
}