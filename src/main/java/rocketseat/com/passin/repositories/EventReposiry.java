package rocketseat.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocketseat.com.passin.domain.event.Event;


public interface EventReposiry extends JpaRepository<Event,String> {
}
