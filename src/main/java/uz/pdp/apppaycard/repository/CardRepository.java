package uz.pdp.apppaycard.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.apppaycard.entity.Card;
import uz.pdp.apppaycard.projection.CustomCard;

import java.util.Optional;

@RepositoryRestResource(
        path = "card",
        collectionResourceRel = "cards",
        excerptProjection = CustomCard.class
)
public interface CardRepository extends JpaRepository<Card, Integer> {

    Optional<Card> findByNumber(String number);

}
