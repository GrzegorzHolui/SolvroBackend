package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.dto.ServiceResultDto;
import com.solvro.solvrobackend.exceptions.ServiceResultException;
import com.solvro.solvrobackend.service.BasketActions;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class GetterBasketInformationController {
    BasketActions basketActions;

    @GetMapping("/getBasketInformation")
    public ResponseEntity<ServiceResultDto> add(@RequestParam String basketHash) {
        ServiceResultDto serviceResultDto =
                basketActions.getInformationAboutBasket(basketHash);
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto));
        }
        return ResponseEntity.ok(serviceResultDto);
    }
}
