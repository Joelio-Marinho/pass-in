package rocketseat.com.passin.DTO.event;

public record EventRequestDTO(
        String title,
        String details,
        Integer maximumAttendees
) {
}
