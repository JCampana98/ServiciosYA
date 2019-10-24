package ar.edu.um.serviciosya.app.repository;

import ar.edu.um.serviciosya.app.domain.Offerer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Offerer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OffererRepository extends JpaRepository<Offerer, Long> {

}
