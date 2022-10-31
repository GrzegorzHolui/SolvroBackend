package com.solvro.solvrobackend.controllers.setterdiscount;

import com.solvro.solvrobackend.controllers.MessagesExceptionMaker;
import com.solvro.solvrobackend.controllers.exceptions.ServiceResultException;
import com.solvro.solvrobackend.dto.ServiceResultDto;
import com.solvro.solvrobackend.service.BasketActions;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SetterDiscountController {
    BasketActions basketActions;

    @PostMapping("/addDiscount")
    public ResponseEntity<ServiceResultDto> add(@RequestBody SetterDiscountRequest setterDiscountRequest) throws ServiceResultException {
        ServiceResultDto serviceResultDto = basketActions.setDiscount(setterDiscountRequest.basketHash, setterDiscountRequest.discountCardHash);
//        ServiceResultDto serviceResultDto = basketActions.addItem(UUID.randomUUID(), UUID.randomUUID(), itemAdderRequestDto.getItemQuantity());
        if (!serviceResultDto.message().contains("everything_is_fine")) {
            throw new ServiceResultException(MessagesExceptionMaker.makeMessage(serviceResultDto));
        }
        return ResponseEntity.ok(serviceResultDto);
    }

}