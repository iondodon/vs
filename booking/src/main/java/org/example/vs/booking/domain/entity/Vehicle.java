package org.example.vs.booking.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.vs.booking.business.constants.RegularExpressions;
import org.example.vs.booking.domain.type.FuelType;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Setter
@Getter
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "vehicle_id_generator")
    @SequenceGenerator(
            name = "vehicle_id_generator",
            sequenceName = "vehicle_id_generator",
            initialValue = 100
    )
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private UUID uuid;

    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true, length = 10)
    @Pattern(regexp = RegularExpressions.REGISTRATION_NUMBER_REG_EXP)
    private String registrationNumber;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 20)
    private String make;

    @NotNull
    @Column(nullable = false, length = 10)
    private String model;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private FuelType fuelType;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_category_id", referencedColumnName = "id", nullable = false)
    private VehicleCategory category;

    @OneToMany(mappedBy = "vehicle")
    private Set<Booking> bookings;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass() ||
                id == null || ((Vehicle) o).getId() == null) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return id.equals(vehicle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
