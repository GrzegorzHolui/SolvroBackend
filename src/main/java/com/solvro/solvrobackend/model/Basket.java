package com.solvro.solvrobackend.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "baskets")
public class Basket  {
    @Id
    private UUID basketId;
    private String basketHash;
    private List<BasketItem> itemList;
    private SummaryInfo summaryInfo;

    public Basket(List<BasketItem> itemList, String basketHash, SummaryInfo summaryInfo) {
        this.basketId = UUID.randomUUID();
        this.itemList = itemList;
        this.basketHash = basketHash;
        this.summaryInfo = summaryInfo;
    }

    public Basket(List<BasketItem> itemList, String basketHash) {
        this.basketId = UUID.randomUUID();
        this.itemList = itemList;
        this.basketHash = basketHash;
        this.summaryInfo = new SummaryInfo();
    }

    public Basket(UUID basketId, String basketHash, List<BasketItem> itemList) {
        this.basketId = basketId;
        this.basketHash = basketHash;
        this.itemList = itemList;
        this.summaryInfo = new SummaryInfo();
    }


}