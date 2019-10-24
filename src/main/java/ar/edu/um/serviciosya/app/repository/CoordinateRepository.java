package ar.edu.um.serviciosya.app.repository;

import ar.edu.um.serviciosya.app.domain.Coordinate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Coordinate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {

}
