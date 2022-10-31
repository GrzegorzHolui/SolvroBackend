package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.controllers.RequestsDto.RemoveItemRequestDto;
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
public class RemoveItemController {

    BasketActions basketActions;

    @DeleteMapping("/deleteItem")
    public ResponseEntity<ServiceResultDto> remove(@RequestBody RemoveItemRequestDto removeItemRequestDto) {
        ServiceResultDto serviceResultDto = basketActions.deleteItem(removeItemRequestDto.getBasketHash(), removeItemRequestDto.getItemHash());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}
