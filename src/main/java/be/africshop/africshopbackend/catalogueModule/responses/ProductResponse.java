package be.africshop.africshopbackend.catalogueModule.responses;

import be.africshop.africshopbackend.catalogueModule.entities.ProductCategory;
import be.africshop.africshopbackend.catalogueModule.entities.UniteMeasure;
import be.africshop.africshopbackend.commandeModule.entities.Country;
import be.africshop.africshopbackend.utils.DataStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ProductResponse {

    private Long id;

    private String libelle;

    private double price;

    private String currency;

    private String quatity;

    private String description;

    private Boolean publish;

    private Double valeurUniteMesure;

    private String tag;

    private ProductCategory productCategory;

    private UniteMeasure uniteMeasure;

    private Country country;

    private String image;

    private boolean cart;

    private DataStatus dataStatus;

    private String codeAuto;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dateUpdated;

}
