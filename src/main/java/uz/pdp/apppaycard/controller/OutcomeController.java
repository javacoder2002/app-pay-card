package uz.pdp.apppaycard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apppaycard.entity.Card;
import uz.pdp.apppaycard.entity.Outcome;
import uz.pdp.apppaycard.payload.OutcomeDto;
import uz.pdp.apppaycard.repository.CardRepository;
import uz.pdp.apppaycard.repository.IncomeRepository;
import uz.pdp.apppaycard.repository.OutcomeRepository;
import uz.pdp.apppaycard.security.JwtFilter;
import uz.pdp.apppaycard.security.JwtProvider;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/outcome")
public class OutcomeController {

    @Autowired
    OutcomeRepository outcomeRepository;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    JwtProvider jwtProvider;

    @GetMapping("/outcomeHistory/{number}")
    public HttpEntity<?> getOutcomeHistory(@PathVariable String number) {
        Optional<Card> optionalCard = cardRepository.findByNumber(number);
        if (optionalCard.isEmpty())
            return ResponseEntity.status(404).body("card not found!");

        Card card = optionalCard.get();

        List<Outcome> allByFromCardId = outcomeRepository.findAllByFromCardId(card.getId());

        return ResponseEntity.ok(allByFromCardId);
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody OutcomeDto outcomeDto) {
        Optional<Card> optionalFromCard = cardRepository.findById(outcomeDto.getFromCardId());
        if (optionalFromCard.isEmpty())
            return ResponseEntity.status(404).body("FromCard not found!");

        Optional<Card> optionalToCard = cardRepository.findById(outcomeDto.getToCardId());
        if (optionalToCard.isEmpty())
            return ResponseEntity.status(404).body("ToCard not found!");

        Card fromCard = optionalFromCard.get();
        Card toCard = optionalToCard.get();

        double money = outcomeDto.getAmount() + outcomeDto.getCommissionAmount();

        if (fromCard.getBalance() < money)
            return ResponseEntity.status(400).body("not enough money!");


        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!fromCard.getUsername().equals(user.getUsername()))
            return ResponseEntity.status(404).body("Card is not appropriate");

        Outcome outcome = new Outcome();
        outcome.setFromCard(fromCard);
        outcome.setToCard(toCard);
        outcome.setAmount(outcomeDto.getAmount());
        outcome.setCommissionAmount(outcomeDto.getCommissionAmount());
        outcome.setDate(new Date());

        Outcome save = outcomeRepository.save(outcome);

        fromCard.setBalance(fromCard.getBalance() - money);
        cardRepository.save(fromCard);

        toCard.setBalance(toCard.getBalance() + money);
        cardRepository.save(toCard);

        return ResponseEntity.ok(save);
    }
}
