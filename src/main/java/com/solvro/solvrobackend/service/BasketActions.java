package service;

import service.dto.ServiceResultDto;

import java.util.UUID;

public interface BasketActions {
    ServiceResultDto addItem(UUID basketId, UUID itemId, int itemQuantity);

    ServiceResultDto deleteItem(UUID basketId, UUID itemId);
}
