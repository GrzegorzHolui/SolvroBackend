package com.solvro.solvrobackend.controllers.adderBasket;

import com.solvro.solvrobackend.Repository.BasketRepository;
import com.solvro.solvrobackend.dto.BasketResultDto;
import com.solvro.solvrobackend.model.Basket;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
public class BasketAdderController {

     BasketRepository basketRepository;

    @PostMapping("/addBasket")
    public ResponseEntity<BasketResultDto> addBasket() {
        Basket basket = new Basket(new ArrayList<>(),"testHash");
        basketRepository.save(basket);
        return ResponseEntity.ok(new BasketResultDto("new Basket has been added", basket.getBasketId()));
    }

}
