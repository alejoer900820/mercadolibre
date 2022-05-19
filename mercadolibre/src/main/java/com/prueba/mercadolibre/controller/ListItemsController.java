package com.prueba.mercadolibre.controller;

import com.prueba.mercadolibre.dominio.ListItemsRR;
import com.prueba.mercadolibre.service.GetItemsToBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
public class ListItemsController {

    @Autowired
    GetItemsToBuyService getItemsToBuyService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getItemsToBuy(@RequestBody ListItemsRR listItems) {
        return getItemsToBuyService.getItemsToBuy(listItems);
    }

}
