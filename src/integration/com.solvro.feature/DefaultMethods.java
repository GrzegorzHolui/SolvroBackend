package com.solvro.feature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvro.solvrobackend.controllers.requestsdto.AdderDiscountRequestDto;
import com.solvro.solvrobackend.controllers.requestsdto.ItemAdderRequestDto;
import com.solvro.solvrobackend.controllers.requestsdto.RemoveItemRequestDto;
import com.solvro.solvrobackend.controllers.requestsdto.SetterDeliveryTypeRequestDto;
import com.solvro.solvrobackend.controllers.requestsdto.SetterQuantityRequestDto;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public interface DefaultMethods {
    default MvcResult postAddBasket(MockMvc mockMvc) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/addBasket")).andReturn();
    }

    default MvcResult postAddItem(MockMvc mockMvc, ItemAdderRequestDto itemAdderRequestDto) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/addItem")
                .content(new ObjectMapper().writeValueAsString(itemAdderRequestDto))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    default MvcResult deleteItem(MockMvc mockMvc, RemoveItemRequestDto itemToRemove) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete("/deleteItem")
                .content(new ObjectMapper().writeValueAsString(itemToRemove))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    default MvcResult changeQuantity(MockMvc mockMvc, SetterQuantityRequestDto setterQuantityRequestDto) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put("/changeQuantity")
                .content(new ObjectMapper().writeValueAsString(setterQuantityRequestDto))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    default MvcResult setDeliveryType(MockMvc mockMvc, SetterDeliveryTypeRequestDto setterDeliveryTypeRequestDto) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put("/putDeliveryType")
                .content(new ObjectMapper().writeValueAsString(setterDeliveryTypeRequestDto))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    default MvcResult addDiscount(MockMvc mockMvc, AdderDiscountRequestDto adderDiscountRequestDto) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/addDiscount")
                .content(new ObjectMapper().writeValueAsString(adderDiscountRequestDto))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    default MvcResult getBasketInformation(MockMvc mockMvc) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/getBasketInformation")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
    }


}