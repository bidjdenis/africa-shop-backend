package be.africshop.africshopbackend.commandeModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationCommandRequest implements Serializable {

    private String firstName;

    private String lastName;

    private String entrepriseName;

    private String adress;

    private String codePostal;

    private String ville;

    private String telephone;

    private String email;

    private Long idPays;

}
