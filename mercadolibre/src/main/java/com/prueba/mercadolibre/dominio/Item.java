package com.prueba.mercadolibre.dominio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private String idItem;
    private Float price;

    public Item(String idItem, Float price) {
        this.idItem = idItem;
        this.price = price;
    }
}
