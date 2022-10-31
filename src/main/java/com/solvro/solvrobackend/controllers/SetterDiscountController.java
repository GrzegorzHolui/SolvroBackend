package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.controllers.RequestsDto.SetterDiscountRequestDto;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import com.solvro.solvrobackend.exceptions.ServiceResultException;
import com.solvro.solvrobackend.service.BasketActions;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SetterDiscountController {
    BasketActions basketActions;

    @PostMapping("/addDiscount")
    public ResponseEntity<ServiceResultDto> add(@RequestBody SetterDiscountRequestDto setterDiscountRequestDto) throws ServiceResultException {
        ServiceResultDto serviceResultDto = basketActions.setDiscount(setterDiscountRequestDto.getBasketHash(), setterDiscountRequestDto.getDiscountCardHash());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}