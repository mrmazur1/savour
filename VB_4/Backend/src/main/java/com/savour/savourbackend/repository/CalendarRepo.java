package com.savour.savourbackend.repository;

import com.savour.savourbackend.model.Calendar;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called calendarRepository
// CRUD refers Create, Read, Update, Delete

public interface CalendarRepo extends CrudRepository<Calendar, Integer> {
}
