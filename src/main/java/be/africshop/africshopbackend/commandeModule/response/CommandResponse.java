package be.africshop.africshopbackend.commandeModule.response;

import be.africshop.africshopbackend.catalogueModule.entities.Product;
import be.africshop.africshopbackend.securityModule.entities.AppUser;
import be.africshop.africshopbackend.utils.DataStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandResponse implements Serializable {

    private Long id;

    private String numeroCommand;

    private double totalPrice;

    private double quantity;

    private boolean shoppingCart;

    private Product product;

    private AppUser appUser;

    private DataStatus dataStatus;

    private String codeAuto;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dateUpdated;

}
