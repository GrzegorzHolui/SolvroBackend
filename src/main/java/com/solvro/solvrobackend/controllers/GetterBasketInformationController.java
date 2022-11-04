package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.dto.ServiceSummaryResultDto;
import com.solvro.solvrobackend.exceptions.ServiceResultException;
import com.solvro.solvrobackend.service.BasketActions;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class GetterBasketInformationController {
    BasketActions basketActions;

    @GetMapping("/getBasketInformation")
    public ResponseEntity<ServiceSummaryResultDto> add(@RequestBody String basketHash) {
        ServiceSummaryResultDto serviceSummaryResultDto =
                basketActions.getInformationAboutBasket(basketHash);
        if (!serviceSummaryResultDto.message().contains("everything_is_fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceSummaryResultDto.message()));
        }
        return ResponseEntity.ok(serviceSummaryResultDto);
    }
}
