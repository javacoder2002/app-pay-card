package uz.pdp.apppaycard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.apppaycard.entity.Outcome;

import java.util.List;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Integer> {

    List<Outcome> findAllByFromCardId(Integer fromCard_id);

}
