package com.solvro.solvrobackend.controllers.requestsdto;
import com.solvro.solvrobackend.model.DeliveryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SetterDeliveryTypeRequestDto {
    DeliveryType deliveryType;
}