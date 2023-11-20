package be.africshop.africshopbackend.commandeModule.entities;

import be.africshop.africshopbackend.utils.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "as_validation_commande")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ValidationCommand extends CommonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "entreprise_name")
    private String entrepriseName;

    @Column(name = "adress")
    private String adress;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "ville")
    private String ville;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_pays")
    private Country country;


}
