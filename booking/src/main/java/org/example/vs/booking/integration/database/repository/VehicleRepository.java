package org.example.vs.booking.integration.database.repository;

import org.example.vs.booking.domain.entity.Vehicle;
import org.example.vs.booking.domain.entity.BookingDate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("""
                select v
                from Vehicle v
                left join fetch v.bookings b
                left join fetch b.bookedDates bd
                where bd.bdate is null or bd.bdate<>:bdate
            """)
    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = "category"
    )
    List<Vehicle> getAvailableForHireOnDate(LocalDate bdate);

    @Override
    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = "category"
    )
    List<Vehicle> findAll();

    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = "category"
    )
    Optional<Vehicle> findByUuid(UUID uuid);

    @Query("""
                select bd
                from Vehicle v
                join v.bookings b
                join b.bookedDates bd
                where v.uuid=:vehicleUuid and bd.bdate>=:from and bd.bdate<=:to
            """)
    List<BookingDate> findBookedDatesForVehicleByPeriod(UUID vehicleUuid, LocalDate from, LocalDate to);
}
