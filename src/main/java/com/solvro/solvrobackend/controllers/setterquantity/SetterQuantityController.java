package com.solvro.solvrobackend.controllers.setterquantity;

import com.solvro.solvrobackend.controllers.MessagesExceptionMaker;
import com.solvro.solvrobackend.controllers.exceptions.ItemAdderException;
import com.solvro.solvrobackend.controllers.itemAdder.ItemAdderRequestDto;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import com.solvro.solvrobackend.service.BasketActions;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SetterQuantityController {

    BasketActions basketActions;

    @PutMapping("/changeQuantity")
    public ResponseEntity<ServiceResultDto> add(@RequestBody SetterQuantityRequestDto setterQuantityRequestDto) {
        ServiceResultDto serviceResultDto = basketActions.changeAmountOfProduct(setterQuantityRequestDto.getBasketId(), setterQuantityRequestDto.getItemId(), setterQuantityRequestDto.getNewQuantity());
//        ServiceResultDto serviceResultDto = basketActions.addItem(UUID.randomUUID(), UUID.randomUUID(), itemAdderRequestDto.getItemQuantity());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ItemAdderException(MessagesExceptionMaker.makeMessage(serviceResultDto));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}