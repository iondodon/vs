package org.example.vs.booking.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.vs.booking.domain.type.VehicleType;

import java.math.BigDecimal;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Setter
@Getter
@Entity
@Table(name = "vehicle_category")
public class VehicleCategory {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "category_id_generator")
    @SequenceGenerator(
            name = "category_id_generator",
            sequenceName = "category_id_generator",
            initialValue = 100
    )
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 10)
    private VehicleType category;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal pricePerDay;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass() ||
                id == null || ((VehicleCategory) o).getId() == null) {
            return false;
        }
        VehicleCategory category = (VehicleCategory) o;
        return id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}