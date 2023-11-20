package be.africshop.africshopbackend.commandeModule.response;

import be.africshop.africshopbackend.catalogueModule.entities.Product;
import be.africshop.africshopbackend.securityModule.entities.AppUser;
import be.africshop.africshopbackend.utils.DataStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse implements Serializable {

    private Long id;
    private double quantity;
    private Product product;
    private double price;
    private String currency;
    private String url;
    private DataStatus dataStatus;
    private String codeAuto;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dateCreated;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dateUpdated;

}
