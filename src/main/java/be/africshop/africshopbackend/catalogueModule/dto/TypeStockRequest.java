package be.africshop.africshopbackend.catalogueModule.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class TypeStockRequest implements Serializable {

    private String libelle;

}
