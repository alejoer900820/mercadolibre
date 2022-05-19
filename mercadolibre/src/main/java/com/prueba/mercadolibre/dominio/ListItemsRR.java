package com.prueba.mercadolibre.dominio;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListItemsRR {

  private List<String> item_ids;
  private Float amount;

}
