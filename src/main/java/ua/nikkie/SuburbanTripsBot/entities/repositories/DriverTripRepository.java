package ua.nikkie.SuburbanTripsBot.entities.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nikkie.SuburbanTripsBot.entities.BotUser;
import ua.nikkie.SuburbanTripsBot.entities.DriverTrip;

@Repository
public interface DriverTripRepository extends JpaRepository<DriverTrip, Long> {
    List<DriverTrip> findDriverTripsByDriver(BotUser botUser);
}
