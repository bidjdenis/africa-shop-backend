package be.africshop.africshopbackend.commandeModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest implements Serializable {

    private double quantity;

}
