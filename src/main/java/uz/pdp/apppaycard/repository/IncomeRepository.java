package uz.pdp.apppaycard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.apppaycard.entity.Income;
import uz.pdp.apppaycard.projection.CustomIncome;

import java.util.List;

@RepositoryRestResource(
        path = "income",
        collectionResourceRel = "incomes",
        excerptProjection = CustomIncome.class
)
public interface IncomeRepository extends JpaRepository<Income, Integer> {

    List<Income> findAllByToCardId(Integer toCard_id);

}
