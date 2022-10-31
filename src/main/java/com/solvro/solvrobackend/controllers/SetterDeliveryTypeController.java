package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.controllers.RequestsDto.SetterDeliveryTypeRequestDto;
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
public class SetterDeliveryTypeController {
    BasketActions basketActions;

    @PutMapping("/putDeliveryType")
    public ResponseEntity<ServiceResultDto> add(@RequestBody SetterDeliveryTypeRequestDto setterDeliveryTypeRequestDto) {
        ServiceResultDto serviceResultDto =
                basketActions.setDeliveryType(setterDeliveryTypeRequestDto.getBasketHash(),
                        setterDeliveryTypeRequestDto.getDeliveryType());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto));
        }
        return ResponseEntity.ok(serviceResultDto);
    }
}
