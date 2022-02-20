package uz.pdp.apppaycard.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.apppaycard.entity.template.AbsEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Outcome extends AbsEntity {

    private double commissionAmount;

}
