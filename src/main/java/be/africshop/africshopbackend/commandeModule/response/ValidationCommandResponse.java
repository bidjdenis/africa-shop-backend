package be.africshop.africshopbackend.commandeModule.response;

import be.africshop.africshopbackend.commandeModule.entities.Country;
import be.africshop.africshopbackend.utils.DataStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationCommandResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String entrepriseName;

    private String adress;

    private String codePostal;

    private String ville;

    private String telephone;

    private String email;

    private Country country;

    private DataStatus dataStatus;

    private String codeAuto;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dateUpdated;
}
