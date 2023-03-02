package org.example.vs.booking.integration.database.repository;

import org.example.vs.booking.domain.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
