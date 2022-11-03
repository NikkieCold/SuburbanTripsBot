package ua.nikkie.SuburbanTripsBot.entities.services;

import static java.lang.String.format;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.nikkie.SuburbanTripsBot.entities.DriverTrip;
import ua.nikkie.SuburbanTripsBot.entities.enums.DriverTripDestination;
import ua.nikkie.SuburbanTripsBot.entities.repositories.DriverTripRepository;
import ua.nikkie.SuburbanTripsBot.exceptions.UnexpectedInput;

@Service
public class DriverTripService {

    private static final String MESSAGE_DATE_FORMAT = "%s.2022";
    private static final String MESSAGE_TIME_FORMAT = "%s:00";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d.MM.yyyy");

    DriverTripRepository driverTripRepository;
    BotUserService botUserService;

    public DriverTripService(DriverTripRepository driverTripRepository, BotUserService botUserService) {
        this.driverTripRepository = driverTripRepository;
        this.botUserService = botUserService;
    }

    public List<DriverTrip> getDriverTrips(Message message) {
        return driverTripRepository.findDriverTripsByDriver(botUserService.getBotUser(message));
    }

    public DriverTrip getUnfinishedTrip(Message message) {
        return getDriverTrips(message).stream()
            .filter(t -> !t.getIsFinished()).findAny().orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void setDestination(Message message) throws UnexpectedInput {
        DriverTrip driverTrip = driverTripRepository.save(new DriverTrip(botUserService.getBotUser(message)));
        driverTrip.setDestination(DriverTripDestination.parseMessage(message));
    }

    @Transactional
    public void setStopFrom(Message message) {
        getUnfinishedTrip(message).setStopFrom(message.getText());
    }

    @Transactional
    public void setStopTo(Message message) {
        getUnfinishedTrip(message).setStopTo(message.getText());
    }

    @Transactional
    public void setStopsThrough(Message message) {
        getUnfinishedTrip(message).setStopsThrough(message.getText());
    }

    @Transactional
    public void setComment(Message message) {
        getUnfinishedTrip(message).setComment(message.getText());
    }

    @Transactional
    public void setSeatsNumber(Message message) {
        getUnfinishedTrip(message).setSeatsNumber(message.getText());
    }

    @Transactional
    public void setDate(Message message) {
        String dateFromMessage = format(MESSAGE_DATE_FORMAT, message.getText());
        getUnfinishedTrip(message).setDate(LocalDate.parse(dateFromMessage, DATE_FORMATTER));
    }

    @Transactional
    public void setTimeRange(Message message) {
        getUnfinishedTrip(message).setTimeRange(message.getText());
    }

    @Transactional
    public void setTime(Message message) {
        String timeFromMessage = format(MESSAGE_TIME_FORMAT, message.getText());
        DriverTrip trip = getUnfinishedTrip(message);
        trip.setTime(LocalTime.parse(timeFromMessage));
        trip.setIsFinished(true);
    }
}
