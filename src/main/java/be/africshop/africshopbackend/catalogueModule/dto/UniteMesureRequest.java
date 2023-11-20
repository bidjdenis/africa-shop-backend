package be.africshop.africshopbackend.catalogueModule.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UniteMesureRequest implements Serializable {

    private String symbole;

    private String libelle;

}
