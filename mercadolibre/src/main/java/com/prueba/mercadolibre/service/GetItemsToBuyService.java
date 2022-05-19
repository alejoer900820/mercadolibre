package com.prueba.mercadolibre.service;

import com.prueba.mercadolibre.dominio.ListItemsRR;
import com.prueba.mercadolibre.dominio.MercadolibreServiceResponse;
import com.prueba.mercadolibre.dominio.MessageResponse;
import com.prueba.mercadolibre.utils.Constants;
import com.prueba.mercadolibre.utils.Mapper;
import com.prueba.mercadolibre.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class GetItemsToBuyService {

    public ResponseEntity<?> getItemsToBuy (ListItemsRR listItems) {

        if(!Objects.isNull(listItems.getItem_ids())) {
            if(!listItems.getItem_ids().isEmpty()) {
                MercadolibreServiceResponse mercadolibreServiceResponse = new MercadolibreServiceResponse();
                RestTemplate restTemplate = new RestTemplate();
                List<MercadolibreServiceResponse> listReponseItems = new ArrayList<>();
                List<String> filteredItems = Utils.filterItems(listItems.getItem_ids());

                for (String id : filteredItems) {
                    String url = Constants.SERVICE_GET_PRICE_URL + id;
                    ArrayList result = restTemplate.getForObject(url, ArrayList.class);
                    mercadolibreServiceResponse = Mapper.mapToResponse(result);
                    if (mercadolibreServiceResponse.getPrice() < listItems.getAmount()) {
                        listReponseItems.add(mercadolibreServiceResponse);
                    }
                }
                if (listReponseItems.size() != 0) {
                    ListItemsRR listItemsRR = calculate(Utils.convertArrayListToMap(listReponseItems), listItems.getAmount());
                    if (!Objects.isNull(listItemsRR)) {
                        return new ResponseEntity<>(listItemsRR, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(new MessageResponse(Constants.ITEMS_PRICES_EXCEED_BONUS_AMOUNT, Constants.NOT_FOUND), HttpStatus.NOT_FOUND);
                    }
                } else {
                    return new ResponseEntity<>(new MessageResponse(Constants.ITEMS_PRICES_EXCEED_BONUS_AMOUNT, Constants.NOT_FOUND), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(new MessageResponse(Constants.THERE_ARE_NO_SELECTED_ITEMS,Constants.NOT_FOUND), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse(Constants.THERE_ARE_NO_SELECTED_ITEMS,Constants.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    ListItemsRR calculate(Map<String, Float> items, Float amount) {
        List<String> idItems = new ArrayList<>();
        List<Float> listPrices = new ArrayList<>(items.values());
        Float minor = Utils.calculateMinor(listPrices);
        if(minor < amount) {
            Float plusPrices = Utils.plusArrayPrices(listPrices);
            if(plusPrices == amount) {
                ListItemsRR listItemsRR = new ListItemsRR();
                listItemsRR.setAmount(amount);
                listItemsRR.setItem_ids(new ArrayList<>(items.keySet()));
                return listItemsRR;
            } else {
                return Utils.sum_up(Utils.convertMapToArrayList(items), amount);
            }
        } else {
            return null;
        }
    }
}
