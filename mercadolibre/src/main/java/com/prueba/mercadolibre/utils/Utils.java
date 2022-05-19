package com.prueba.mercadolibre.utils;

import com.prueba.mercadolibre.dominio.Item;
import com.prueba.mercadolibre.dominio.ListItemsRR;
import com.prueba.mercadolibre.dominio.MercadolibreServiceResponse;

import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static Float calculateMinor (List<Float> prices) {
        Float minor = prices.get(0);
        Float minorAux =  prices.get(0);
        for (Float price: prices) {
            if(minor < price) {
                minor = price;
                if(minor > minorAux) {
                    minor = minorAux;
                }
            }
        }
        return minor;
    }

    public static Float calculateMayor (List<Float> prices) {
        Float mayor = 0f;
        for (Float price : prices) {
            if(mayor < price) {
                mayor = price;
            }
        }
        return mayor;
    }


    public static void sum_up_recursive(List<Item> listPrices, Float target, ArrayList<Item> partial, Map<Float, ArrayList<Item>> items) {
        Float s = 0f;
        for (Item item: partial) s += item.getPrice();
        if (s <= target)
            items.put(plusArrayValues(partial),partial);
            System.out.println((partial));
        if (s >= target)
            return;
        for(int i=0;i<listPrices.size();i++) {
            ArrayList<Item> remaining = new ArrayList<Item>();
            Item n = listPrices.get(i);
            for (int j=i+1; j<listPrices.size();j++)
                remaining.add(listPrices.get(j));
            ArrayList<Item> partial_rec = new ArrayList<Item>(partial);
            partial_rec.add(n);
            sum_up_recursive(remaining,target,partial_rec, items);
        }
    }

    public static ListItemsRR sum_up(List<Item> listItems, Float target) {
        Map<Float, ArrayList<Item>> items = new HashMap<Float, ArrayList<Item>>();
        sum_up_recursive(listItems,target,new ArrayList<Item>(), items);
        Float mayor = calculateMayor(new ArrayList<>(items.keySet()));
        List<Item> listItemsResult = items.get(mayor);

        ListItemsRR listItemsRR = new ListItemsRR();
        listItemsRR.setAmount(mayor);
        List<String>  listIdItems = new ArrayList<>();
        for (Item item : listItemsResult) {
            listIdItems.add(item.getIdItem());
        }
        listItemsRR.setItem_ids(listIdItems);
        return listItemsRR;
    }

    public static Map<String, Float> convertArrayListToMap (List<MercadolibreServiceResponse> listItems) {
          if(!Objects.isNull(listItems)) {
              return listItems.stream().collect(Collectors.toMap(MercadolibreServiceResponse::getSiteId, MercadolibreServiceResponse::getPrice));
          }
          return null;
    }
    public static List<Item> convertMapToArrayList (Map<String, Float> listItems) {
        List<Item> items = new ArrayList<>();
        if(!Objects.isNull(listItems)) {
            for (Map.Entry<String, Float> entry : listItems.entrySet()) {
                items.add(new Item(entry.getKey(), entry.getValue()));

            }
        }
        return items;
    }

    public static List<String> filterItems (List<String> listItems) {
        Set<String> removeDuplicatedItems = new HashSet<String>(listItems);
        listItems.clear();
        listItems.addAll(removeDuplicatedItems);
        return listItems;
    }

    public static HashMap<Integer, List<Item>> sortMap(Map<Integer, List<Item>> items)
    {
        HashMap<Integer, List<Item>> orderedItems
                = items.entrySet()
                .stream()
                .sorted((i1, i2)
                        -> i1.getKey().compareTo(
                        i2.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (z1, z2) -> z1, LinkedHashMap::new));
        return orderedItems;
    }

    public static Float plusArrayValues (List<Item> listPrices) {
        Float total = 0f;
        for (Item item: listPrices) {
            total += item.getPrice();
        }
        return total;
    }

    public static Float plusArrayPrices (List<Float> listPrices) {
        Float total = 0f;
        for (Float price: listPrices) {
            total += price;
        }
        return total;
    }


}
