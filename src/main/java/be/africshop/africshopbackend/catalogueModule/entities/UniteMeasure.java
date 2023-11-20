package be.africshop.africshopbackend.catalogueModule.entities;

import be.africshop.africshopbackend.utils.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Table(name = "as_unite_mesure")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UniteMeasure extends CommonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbole", length = 4, nullable = false)
    private String symbole;

    @Column(name = "libelle", length = 80, nullable = false)
    private String libelle;


}
