package com.example.demo;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class LeaveDetailsServiceTest {

    @InjectMocks
    LeaveDetailsService leaveDetailsService;

    @Mock
    LeaveDetailsRepository leaveDetailsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void updateLeaveStatusAndComment() {
        LeaveDetails leaveDetails = new LeaveDetails();
        leaveDetails.setStatus("submitted");
        leaveDetails.setRequestId(2);
        leaveDetails.setComment("Happy Holiday");
        String status = "approve";
        Mockito.when(leaveDetailsRepository.findById(Mockito.any())).thenReturn(Optional.of(leaveDetails));
        LeaveDetails updatedLeaveDetails = leaveDetailsService.updateLeaveStatusAndComment(status, leaveDetails);
        Assert.assertEquals("approved", updatedLeaveDetails.getStatus());
        Assert.assertEquals("Happy Holiday", updatedLeaveDetails.getComment());
    }
}