package com.solvro.solvrobackend.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
//@Builder
@AllArgsConstructor
@ToString
@Document(collection = "baskets")
public class Basket {
    @Id
    private UUID basketId;
    private String basketHash;
    private List<BasketItem> itemList;
    private Summary summary;

    private SummaryInfo summaryInfo;

    public Basket(List<BasketItem> itemList, String basketHash) {
        this.basketId = UUID.randomUUID();
        this.itemList = itemList;
        this.basketHash = basketHash;
        this.summary = new Summary();
        this.summaryInfo = new SummaryInfo();
    }

    public Basket(UUID basketId, String basketHash, List<BasketItem> itemList) {
        this.basketId = basketId;
        this.basketHash = basketHash;
        this.itemList = itemList;
        this.summary = new Summary();
        this.summaryInfo = new SummaryInfo();
    }


}