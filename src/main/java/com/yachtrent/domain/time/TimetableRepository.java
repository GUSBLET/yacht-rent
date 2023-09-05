package com.yachtrent.domain.time;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
//    CharSequence findByTimeRange(Date startTime, Date finishTime);
}
