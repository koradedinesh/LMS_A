package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveCountRepository extends JpaRepository<LeaveCount, Integer> {
}
