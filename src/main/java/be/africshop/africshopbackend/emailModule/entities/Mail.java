package be.africshop.africshopbackend.emailModule.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "as_mails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Insert receiver mail")
    @Email(message = "Mail not valid", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-].[A-Za-z]{2,}$")
    @Column(name = "mail_to", nullable = false)
    private String mailTo;

    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Insert receiver mail")
    @Email(message = "Mail not valid", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-].[A-Za-z]{2,}$")
    @Column(name = "mail_from", nullable = false)
    private String mailFrom;

    @Column(name = "subject")
    private String subject;

    @Column(name = "text", nullable = false)
    private String text;

    private String attachment;


}
