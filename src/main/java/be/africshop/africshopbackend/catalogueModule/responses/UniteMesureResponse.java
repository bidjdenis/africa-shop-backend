package be.africshop.africshopbackend.catalogueModule.responses;

import be.africshop.africshopbackend.utils.DataStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;


@Data
public class UniteMesureResponse {

    private Long id;

    private String symbole;

    private String libelle;

    private DataStatus dataStatus;

    private String codeAuto;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dateUpdated;


}
