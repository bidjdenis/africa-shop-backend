package be.africshop.africshopbackend.catalogueModule.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {

    private String libelle;

    private double price;

    private String currency;

    private String quatity;

    private String description;

    private Double valeurUniteMesure;

    private Long idProductCategory;

    private Long idUniteMesure;

    private Long idCountry;

    private String images;

    private String imageName;

}
