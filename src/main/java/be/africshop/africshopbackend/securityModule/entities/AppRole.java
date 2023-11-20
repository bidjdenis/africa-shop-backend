package be.africshop.africshopbackend.securityModule.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Builder
@Table(name = "auth_roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppRole  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(name = "name")
    private String name;

    @CreationTimestamp
    private Date creatAt;
    @UpdateTimestamp
    private Date updateAt;


}
