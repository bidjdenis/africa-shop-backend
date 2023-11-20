package be.africshop.africshopbackend.emailModule.response;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class MailResponse implements Serializable {
    private String mailTo;

    private String mailFrom;

    private String name;

    private String subject;

    private String text;
    private Instant dateCreate;
}
