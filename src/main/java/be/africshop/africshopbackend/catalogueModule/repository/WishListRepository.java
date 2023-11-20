package be.africshop.africshopbackend.catalogueModule.repository;

import be.africshop.africshopbackend.catalogueModule.entities.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findByProductId(Long id);

}
