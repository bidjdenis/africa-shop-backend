package be.africshop.africshopbackend.commandeModule.entities;

import be.africshop.africshopbackend.catalogueModule.entities.Product;
import be.africshop.africshopbackend.securityModule.entities.AppUser;
import be.africshop.africshopbackend.utils.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "as_commande")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Command extends CommonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_commande", length = 80, nullable = false)
    private String numeroCommande;

    @Column(name = "total_price", length = 20, nullable = false)
    private double totalPrice;

    @Column(name = "quantity", length = 20, nullable = false)
    private double quantity;

    @Column(name = "shopping_cart", length = 20, nullable = false)
    private boolean shoppingCart;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private AppUser appUser;


}
