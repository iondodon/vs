package org.example.vs.booking


import org.example.vs.booking.business.usecase.vehicle.GetAllVehiclesUseCase
import org.example.vs.booking.business.usecase.vehicle.GetAvailableForHireUseCase
import org.example.vs.booking.business.usecase.vehicle.GetCostOfHiringUseCase
import org.example.vs.booking.controller.response.AllVehiclesResponse
import org.example.vs.booking.domain.entity.Vehicle
import org.example.vs.booking.presenter.VehiclePresenter
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import spock.lang.Specification
import spock.lang.Subject

import static java.util.Collections.singletonList
import static java.util.UUID.randomUUID

@ExtendWith(MockitoExtension.class)
class VehiclePresenterSpec extends Specification {
    private GetAllVehiclesUseCase getAllVehiclesUseCase = Mock()
    private GetAvailableForHireUseCase getAvailableForHireUseCase = Mock()
    private GetCostOfHiringUseCase getCostOfHiringUseCase = Mock()
    @Subject
    private VehiclePresenter vehiclePresenter = new VehiclePresenter(
            getAllVehiclesUseCase,
            getAvailableForHireUseCase,
            getCostOfHiringUseCase
    )

    def "Test return correct vehicles"() {
        given: "There is a vehicle"
        Vehicle vehicle = new Vehicle()
        vehicle.setUuid(randomUUID())
        List<Vehicle> vehicles = singletonList(vehicle)

        when: "vehicles are return from the use case"
        getAllVehiclesUseCase.exec() >> vehicles

        and: "I get all vehicles from the presenter"
        AllVehiclesResponse allVehicles = vehiclePresenter.getAllVehicles()

        then: "only one vehicle is returned"
        allVehicles.getVehicles().size() == 1
        and: "the UUID is correct"
        allVehicles.getVehicles().get(0).getUuid() == vehicle.getUuid()
    }
}
