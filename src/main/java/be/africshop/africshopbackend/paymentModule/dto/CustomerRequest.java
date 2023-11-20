package be.africshop.africshopbackend.paymentModule.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerRequest implements Serializable {

    private String coustomerId;

    private String name;

    private String email;



}
