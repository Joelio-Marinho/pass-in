package rocketseat.com.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocketseat.com.passin.domain.checkIn.CheckIn;


public interface CheckInRepository extends JpaRepository<CheckIn,Integer> {
}
