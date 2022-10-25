package com.solvro.solvrobackend.controllers.RemoverBasket;

import com.solvro.solvrobackend.controllers.MessagesExceptionMaker;
import com.solvro.solvrobackend.controllers.exceptions.ItemAdderException;
import com.solvro.solvrobackend.service.BasketActions;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class RemoveBasketController {

    BasketActions basketActions;

    @DeleteMapping("/deleteItem")
    public ResponseEntity<ServiceResultDto> remove(@RequestBody RemoveBasketRequest removeBasketRequest) throws ItemAdderException {
        ServiceResultDto serviceResultDto = basketActions.deleteItem(removeBasketRequest.getBasketId(), removeBasketRequest.getItemId());
//        ServiceResultDto serviceResultDto = basketActions.deleteItem(UUID.randomUUID(), UUID.randomUUID());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ItemAdderException(MessagesExceptionMaker.makeMessage(serviceResultDto));
        }

        return ResponseEntity.ok(serviceResultDto);
    }

}
