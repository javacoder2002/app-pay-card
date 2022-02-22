package uz.pdp.apppaycard.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.apppaycard.entity.Card;

import java.util.Date;

@Projection(types = Card.class)
public interface CustomCard {

    Integer getId();

    String getUsername();

    String getNumber();

    double getBalance();

    Date getExpiredDate();

    boolean isActive();

}
