package com.prueba.mercadolibre.utils;

import com.prueba.mercadolibre.builder.GenericBuilder;
import com.prueba.mercadolibre.dominio.MercadolibreServiceResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Mapper {

    public static MercadolibreServiceResponse mapToResponse (ArrayList result) {
        MercadolibreServiceResponse getItemsByIdResponse = new MercadolibreServiceResponse();
        LinkedHashMap<String, LinkedHashMap> data = (LinkedHashMap<String, LinkedHashMap>) result.get(0);
        Object code = data.get("code");
        LinkedHashMap body = data.get("body");

        Map<String, Float> items = new HashMap<>();
        items.put(String.valueOf(body.get("id")), Float.parseFloat(String.valueOf(body.get("price"))));

        getItemsByIdResponse = GenericBuilder.of(
                MercadolibreServiceResponse::new).with(MercadolibreServiceResponse::setCode, (Integer) code)
                .with(MercadolibreServiceResponse::setTitle, String.valueOf(body.get("title")))
                .with(MercadolibreServiceResponse::setPrice, Float.parseFloat(String.valueOf(body.get("price"))))
                .with(MercadolibreServiceResponse::setSiteId, String.valueOf(body.get("id"))).build();

        return getItemsByIdResponse;
    }

}
