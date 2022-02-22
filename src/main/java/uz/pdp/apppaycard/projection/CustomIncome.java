package uz.pdp.apppaycard.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.apppaycard.entity.Card;
import uz.pdp.apppaycard.entity.Income;

import javax.persistence.ManyToOne;
import java.util.Date;

@Projection(types = Income.class)
public interface CustomIncome {

    Integer getId();

    Card getFromCard();

    Card getToCard();

    double getAmount();

    Date getDate();

}
