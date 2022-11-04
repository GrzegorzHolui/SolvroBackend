package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.controllers.RequestsDto.RemoveItemRequestDto;
import com.solvro.solvrobackend.dto.ServiceCrudResultDto;
import com.solvro.solvrobackend.exceptions.ServiceResultException;
import com.solvro.solvrobackend.service.BasketActions;
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
    public ResponseEntity<ServiceCrudResultDto> remove(@RequestBody RemoveItemRequestDto removeItemRequestDto) {
        ServiceCrudResultDto serviceResultDto = basketActions.deleteItem(removeItemRequestDto.getBasketHash(), removeItemRequestDto.getItemHash());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto.message()));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}
