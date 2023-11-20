package be.africshop.africshopbackend.catalogueModule.repository;

import be.africshop.africshopbackend.utils.DataStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import be.africshop.africshopbackend.catalogueModule.entities.ProductCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    Optional<ProductCategory> findProductCategoryByDataStatusIsNotAndId(DataStatus dataStatus, Long id);

   List<ProductCategory> findProductCategoryByDataStatusIsNotOrderByLibelle(DataStatus dataStatus);

    @Query("select p from ProductCategory p where p.dataStatus <> :x and lower(p.libelle) like %:y%  ")
    List<ProductCategory> findByDataStatusIsNotAndLibelleIgnoreCase(@Param("x") DataStatus dataStatus, @Param("y")  String name);
}
