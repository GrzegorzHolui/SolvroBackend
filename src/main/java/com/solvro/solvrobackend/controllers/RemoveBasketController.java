package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.controllers.MessagesExceptionMaker;
import com.solvro.solvrobackend.controllers.RequestsDto.RemoveBasketRequest;
import com.solvro.solvrobackend.exceptions.ServiceResultException;
import com.solvro.solvrobackend.service.BasketActions;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RemoveBasketController {

    BasketActions basketActions;

    @DeleteMapping("/deleteItem")
    public ResponseEntity<ServiceResultDto> remove(@RequestBody RemoveBasketRequest removeBasketRequest) {
        ServiceResultDto serviceResultDto = basketActions.deleteItem(removeBasketRequest.getBasketHash(), removeBasketRequest.getItemHash());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}
