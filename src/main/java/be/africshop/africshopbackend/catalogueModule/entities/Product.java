package be.africshop.africshopbackend.catalogueModule.entities;


import be.africshop.africshopbackend.commandeModule.entities.Country;
import be.africshop.africshopbackend.utils.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "as_produit")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends CommonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle", length = 80, nullable = false)
    private String libelle;

    @Column(name = "price", length = 100, nullable = false)
    private double price;

    @Column(name = "currency", length = 10, nullable = false)
    private String currency;

    @Column(name = "quantity", length = 10, nullable = false)
    private String quatity;

    @Column(name = "description")
    private String description;

    @Column(name = "publish", length = 80, nullable = false)
    private Boolean publish;

    @Column(name = "valeur_unite_mesure", length = 80)
    private Double valeurUniteMesure;

    @Column(name = "tag", length = 50)
    private String tag;

    @Column(name = "is_cart", nullable = false)
    private boolean cart;

    @ManyToOne
    @JoinColumn(name = "id_categorie_produit")
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumn(name = "id_unite_mesure")
    private UniteMeasure uniteMeasure;

    @ManyToOne
    @JoinColumn(name = "id_country")
    private Country country;

    @Column(name = "featured", nullable = false)
    private boolean featured;

    @Column(name = "trending", nullable = false)
    private boolean trending;

    @Column(name = "image_name")
    private String imageName;

}
