package com.nns.springboot.sched.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nns.springboot.sched.entity.SpamTrend;

@Repository
public interface SpamTrendRepository extends JpaRepository<SpamTrend, Long>{

} 
