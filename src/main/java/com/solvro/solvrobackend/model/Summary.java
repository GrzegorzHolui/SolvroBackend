package com.solvro.solvrobackend.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
//@Builder
@ToString

public class Summary {

    private DeliveryType deliveryType;
}
