package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.controllers.MessagesExceptionMaker;
import com.solvro.solvrobackend.controllers.RequestsDto.ItemAdderRequestDto;
import com.solvro.solvrobackend.exceptions.ServiceResultException;
import com.solvro.solvrobackend.service.BasketActions;
import com.solvro.solvrobackend.dto.ServiceResultDto;
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
    public ResponseEntity<ServiceResultDto> add(@RequestBody ItemAdderRequestDto itemAdderRequestDto) throws ServiceResultException {
        ServiceResultDto serviceResultDto = basketActions.addItem(itemAdderRequestDto.getBasketHash(), itemAdderRequestDto.getItemHash(), itemAdderRequestDto.getItemQuantity());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}
