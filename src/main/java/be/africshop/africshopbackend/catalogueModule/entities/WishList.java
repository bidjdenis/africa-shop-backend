package be.africshop.africshopbackend.catalogueModule.entities;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "wish_list")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WishList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;
}
