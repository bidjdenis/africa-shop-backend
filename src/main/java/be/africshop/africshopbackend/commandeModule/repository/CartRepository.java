package be.africshop.africshopbackend.commandeModule.repository;

import be.africshop.africshopbackend.commandeModule.entities.Cart;
import be.africshop.africshopbackend.commandeModule.entities.Country;
import be.africshop.africshopbackend.utils.DataStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByDataStatusIsNot(DataStatus dataStatus);

    Optional<Cart> findByDataStatusIsNotAndId(DataStatus dataStatus, Long id);
    Optional<Cart> findByDataStatusIsNotAndProductId(DataStatus dataStatus, Long idProduct);
}
