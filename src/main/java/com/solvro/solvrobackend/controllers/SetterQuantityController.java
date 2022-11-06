package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.controllers.requestsdto.SetterQuantityRequestDto;
import com.solvro.solvrobackend.dto.ServiceChangeQuantityResultDto;
import exceptions.ServiceResultException;
import com.solvro.solvrobackend.service.BasketActions;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SetterQuantityController {

    BasketActions basketActions;

    @PutMapping("/changeQuantity")
    public ResponseEntity<ServiceChangeQuantityResultDto> changeQuantity(@RequestBody SetterQuantityRequestDto setterQuantityRequestDto) {
        ServiceChangeQuantityResultDto serviceResultDto =
                basketActions.changeAmountOfProduct(
                        setterQuantityRequestDto.getItemHash(), setterQuantityRequestDto.getNewQuantity());
        if (!serviceResultDto.message().contains("everything is fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto.message()));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}