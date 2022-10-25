package com.solvro.solvrobackend.controllers.adderContoller;

import com.solvro.solvrobackend.controllers.adderContoller.exception.ItemAdderException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.solvro.solvrobackend.service.BasketActions;
import com.solvro.solvrobackend.service.dto.ServiceResultDto;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class ItemAdderController {

    BasketActions basketActions;

    @PostMapping("/addItem")
    public ResponseEntity<ServiceResultDto> inputNumbers(@RequestBody ItemAdderRequestDto itemAdderRequestDto) throws ItemAdderException {
        ServiceResultDto serviceResultDto = basketActions.addItem(itemAdderRequestDto.getBasketId(), itemAdderRequestDto.getItemId(), itemAdderRequestDto.getItemQuantity());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ItemAdderException(MessagesExceptionMaker.makeMessage(serviceResultDto));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}
