package be.africshop.africshopbackend.catalogueModule.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "file_atachement")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileAttachement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageName;
    private String path;
    private String url;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;


}
