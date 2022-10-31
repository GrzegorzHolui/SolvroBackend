package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.controllers.RequestsDto.SetterDiscountRequest;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import com.solvro.solvrobackend.exceptions.ServiceResultException;
import com.solvro.solvrobackend.service.BasketActions;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SetterDiscountController {
    BasketActions basketActions;

    @PutMapping("/addDiscount")
    public ResponseEntity<ServiceResultDto> add(@RequestBody SetterDiscountRequest setterDiscountRequest) throws ServiceResultException {
        ServiceResultDto serviceResultDto = basketActions.setDiscount(setterDiscountRequest.getBasketHash(), setterDiscountRequest.getDiscountCardHash());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}