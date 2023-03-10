package org.example.vs.booking.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Setter
@Getter
@Entity
@Table(name = "booking")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "booking_id_generator")
    @SequenceGenerator(
            name = "booking_id_generator",
            sequenceName = "booking_id_generator",
            initialValue = 100
    )
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private UUID uuid;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "bookings_bookingdates",
            joinColumns = @JoinColumn(name = "booking_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bookingdate_id", nullable = false)
    )
    @Builder.Default
    private Set<BookingDate> bookedDates = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass() ||
                id == null || ((Booking) o).getId() == null) {
            return false;
        }
        Booking booking = (Booking) o;
        return id.equals(booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
