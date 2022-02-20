package uz.pdp.apppaycard.entity.template;

import lombok.Data;
import uz.pdp.apppaycard.entity.Card;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public class AbsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Card fromCard;

    @ManyToOne
    private Card toCard;

    private double amount;

    private Date date;

}
