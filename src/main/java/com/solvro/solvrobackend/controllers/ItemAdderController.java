package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.controllers.RequestsDto.ItemAdderRequestDto;
import com.solvro.solvrobackend.dto.ServiceCrudResultDto;
import com.solvro.solvrobackend.exceptions.ServiceResultException;
import com.solvro.solvrobackend.service.BasketActions;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ItemAdderController {

    BasketActions basketActions;

    @PostMapping("/addItem")
    public ResponseEntity<ServiceCrudResultDto> add(@RequestBody ItemAdderRequestDto itemAdderRequestDto) throws ServiceResultException {
        ServiceCrudResultDto serviceResultDto = basketActions.addItem(itemAdderRequestDto.getBasketHash(), itemAdderRequestDto.getItemHash(), itemAdderRequestDto.getItemQuantity());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto.message()));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}
