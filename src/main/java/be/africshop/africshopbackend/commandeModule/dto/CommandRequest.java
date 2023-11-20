package be.africshop.africshopbackend.commandeModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandRequest implements Serializable {

    private String numeroCommande;

    private double quantity;

    private BigDecimal totalPrice;

    private Long idProduit;

    private Long idClient;

}
