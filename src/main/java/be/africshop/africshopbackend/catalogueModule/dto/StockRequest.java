package be.africshop.africshopbackend.catalogueModule.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data
@Builder
public class StockRequest implements Serializable {

    private Double quantity;

    private Long idTypeStock;

    private Long idProduct;

}
