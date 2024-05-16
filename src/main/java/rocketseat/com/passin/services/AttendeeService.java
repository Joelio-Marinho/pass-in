package rocketseat.com.passin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import rocketseat.com.passin.DTO.attendee.AttendeeBadgeResponseDTO;
import rocketseat.com.passin.DTO.attendee.AttendeeDetails;
import rocketseat.com.passin.DTO.attendee.AttendeesListResponseDTO;
import rocketseat.com.passin.DTO.attendee.AttendeeBadgeDTO;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.attendee.exception.AttendeeAlreadyExistException;
import rocketseat.com.passin.domain.attendee.exception.AttendeeNotFoundException;
import rocketseat.com.passin.domain.checkIn.CheckIn;
import rocketseat.com.passin.repositories.AttendeeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;

    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        return this.attendeeRepository.findByEventId(eventId);
    }


    public AttendeesListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map( attendee -> {
           Optional<CheckIn>  checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;

            return new AttendeeDetails(attendee.getId(),
                    attendee.getName(),
                    attendee.getEmail(),
                    attendee.getCreatedAt(),
                    checkedInAt);
        }).toList();
        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public Attendee registerAttendee( Attendee attendee){
        this.attendeeRepository.save(attendee);
        return attendee;
    }

    public void verifyAttendeeSubscription(String email,String eventId){
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId,email);
        if(isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Attendee is already registered");
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        Attendee attendee = getAttendee(attendeeId);
        var uri = uriComponentsBuilder.path("attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri();
        AttendeeBadgeDTO badgeDTO = new AttendeeBadgeDTO(  attendee.getName(), attendee.getEmail(),uri.toString(),attendee.getEvent().getId());
        return new AttendeeBadgeResponseDTO(badgeDTO);
    }

    private  Attendee getAttendee(String attendeeId){
        return this.attendeeRepository.findById(attendeeId).orElseThrow(()-> new AttendeeNotFoundException("Attendee not foud with ID: " + attendeeId));
    }

    public void checkInAttendee(String attendeeId){
        Attendee attendee = getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
    }
}
