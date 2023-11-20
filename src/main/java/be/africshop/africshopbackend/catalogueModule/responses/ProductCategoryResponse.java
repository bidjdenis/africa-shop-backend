package be.africshop.africshopbackend.catalogueModule.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import be.africshop.africshopbackend.utils.DataStatus;

import java.time.Instant;

@Data
public class ProductCategoryResponse {

    private Long id;

    private String libelle;

    private DataStatus dataStatus;

    private String codeAuto;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dateUpdated;

}
