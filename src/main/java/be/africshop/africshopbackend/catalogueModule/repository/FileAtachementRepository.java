package be.africshop.africshopbackend.catalogueModule.repository;

import be.africshop.africshopbackend.catalogueModule.entities.FileAttachement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileAtachementRepository extends JpaRepository<FileAttachement, Long> {

    Optional<FileAttachement> findByImageName(String imageName);

    Optional<FileAttachement> findByProductIdAndImageName(Long productId, String imageName);

    List<FileAttachement> findByProductId(Long id);

}
