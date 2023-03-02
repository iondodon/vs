package org.example.vs.booking.integration.database.repository;

import org.example.vs.booking.domain.entity.BookingDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Set;

public interface BookingDateRepository extends JpaRepository<BookingDate, Long> {
    @Query("""
                select bd
                from BookingDate bd
                where bd.bdate>=:from and bd.bdate<=:to
            """)
    Set<BookingDate> findAllInPeriodInclusive(LocalDate from, LocalDate to);
}
