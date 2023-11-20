package be.africshop.africshopbackend.securityModule.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "auth_users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EnableJpaAuditing
public class AppUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50)
    private String firstname;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 20, max = 30)
    private String phone;

    @NotBlank
    @Size(min = 3, max = 60)
    @Column(unique = true)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 80)
    @Email
    private String email;

    public AppUser(Long id, String name, String username, String email, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    private Instant expireDate;

    @Column(name = "expired_at", columnDefinition = "boolean default true")
    private boolean expiredAt;

    @Column(name = "locked", columnDefinition = "boolean default true")
    private boolean locked;

    @Column(name = "email_verify", columnDefinition = "boolean default false")
    private boolean emailVerify;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<AppRole> appRoles = new ArrayList<>();


    @Column(name = "never_connected", columnDefinition = "boolean default true")
    private boolean neverConnected;

    @Column(name = "enable", columnDefinition = "boolean default true")
    private boolean enable;

    @Column(name = "est_connecter", columnDefinition = "boolean default false")
    private boolean estConnecter;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTimeConnected;

    private String photoProfileUrl;

    private String photoProfileReference;


}
