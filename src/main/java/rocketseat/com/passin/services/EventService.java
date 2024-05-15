package rocketseat.com.passin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rocketseat.com.passin.DTO.event.EventIdDTO;
import rocketseat.com.passin.DTO.event.EventRequestDTO;
import rocketseat.com.passin.DTO.event.EventResponseDTO;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.event.Event;
import rocketseat.com.passin.repositories.AttendeeRepository;
import rocketseat.com.passin.repositories.EventReposiry;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventReposiry eventReposiry;

    private final AttendeeRepository attendeeRepository;

    public EventResponseDTO getEventDetail(String eventId){

        Event  event =  this.eventReposiry.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
        List<Attendee> attendeeList = this.attendeeRepository.findByEventId(eventId);
        return new EventResponseDTO(event,attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO){
        Event event = new Event();

        event.setTitle(eventDTO.title());
        event.setDetails(eventDTO.details());
        event.setMaximumAttendees(eventDTO.maximumAttendees());
        event.setSlug(this.createSlug(eventDTO.title()));

        this.eventReposiry.save(event);

        return new EventIdDTO(event.getId());

    }

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);

        return normalized
                .replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]","")
                .replaceAll("[^\\w\\s]","")
                .replaceAll("\\s+","-")
                .toLowerCase();
    }
}
