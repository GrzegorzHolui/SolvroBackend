package com.solvro.solvrobackend.controllers;

import com.solvro.solvrobackend.controllers.requestsdto.RemoveItemRequestDto;
import com.solvro.solvrobackend.dto.ServiceCrudResultDto;
import exceptions.ServiceResultException;
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
        ServiceCrudResultDto serviceResultDto = basketActions.deleteItem(removeItemRequestDto.getItemHash());
        if (!serviceResultDto.message().contains("everything is fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto.message()));
        }
        return ResponseEntity.ok(serviceResultDto);
    }
}