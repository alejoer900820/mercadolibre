package com.prueba.mercadolibre.dominio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MercadolibreServiceResponse {

    private Integer code;
    private String title;
    private float price;
    private String siteId;

}
