package ar.edu.um.serviciosya.app.repository;

import ar.edu.um.serviciosya.app.domain.Turn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Turn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {

}
