package uz.pdp.apppaycard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apppaycard.entity.Card;
import uz.pdp.apppaycard.entity.Income;
import uz.pdp.apppaycard.repository.CardRepository;
import uz.pdp.apppaycard.repository.IncomeRepository;
import uz.pdp.apppaycard.repository.OutcomeRepository;
import uz.pdp.apppaycard.security.JwtFilter;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    OutcomeRepository outcomeRepository;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    JwtFilter jwtFilter;

    @GetMapping("/incomeHistory/{number}")
    public HttpEntity<?> getIncomeHistory(@PathVariable String number){

        Optional<Card> optionalCard = cardRepository.findByNumber(number);
        if (optionalCard.isEmpty())
            return ResponseEntity.status(404).body("card not found!");

        Card card = optionalCard.get();

        List<Income> allByToCardId = incomeRepository.findAllByToCardId(card.getId());

        return ResponseEntity.ok(allByToCardId);

    }

}
