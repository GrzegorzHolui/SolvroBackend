package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.controllers.MessagesExceptionMaker;
import com.solvro.solvrobackend.controllers.RequestsDto.SetterQuantityRequestDto;
import com.solvro.solvrobackend.exceptions.ServiceResultException;
import com.solvro.solvrobackend.dto.ServiceResultDto;
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
    public ResponseEntity<ServiceResultDto> add(@RequestBody SetterQuantityRequestDto setterQuantityRequestDto) {
        ServiceResultDto serviceResultDto =
                basketActions.changeAmountOfProduct(setterQuantityRequestDto.getBasketHash(),
                        setterQuantityRequestDto.getItemHash(), setterQuantityRequestDto.getNewQuantity());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}