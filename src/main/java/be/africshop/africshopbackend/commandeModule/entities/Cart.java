package be.africshop.africshopbackend.commandeModule.entities;

import be.africshop.africshopbackend.catalogueModule.entities.Product;
import be.africshop.africshopbackend.utils.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Table(name = "as_cart")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cart extends CommonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

}
