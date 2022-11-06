package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.controllers.requestsdto.AdderDiscountRequestDto;
import com.solvro.solvrobackend.dto.ServiceDiscountCardResultDto;
import com.solvro.solvrobackend.exceptions.ServiceResultException;
import com.solvro.solvrobackend.service.BasketActions;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AdderDiscountController {
    BasketActions basketActions;

    @PostMapping("/addDiscount")
    public ResponseEntity<ServiceDiscountCardResultDto> add(@RequestBody AdderDiscountRequestDto adderDiscountRequestDto) throws ServiceResultException {
        ServiceDiscountCardResultDto serviceResultDto = basketActions.addDiscount(adderDiscountRequestDto.getDiscountCardHash());
        if (!serviceResultDto.message().contains("everything is fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto.message()));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}