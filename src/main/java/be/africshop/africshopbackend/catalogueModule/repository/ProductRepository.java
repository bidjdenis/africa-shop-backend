package be.africshop.africshopbackend.catalogueModule.repository;

import be.africshop.africshopbackend.catalogueModule.entities.Product;
import be.africshop.africshopbackend.utils.DataStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductByDataStatusIsNotAndId(DataStatus dataStatus, Long id);

    List<Product> getAllProductByDataStatusIsNotAndProductCategoryIdAndPublishOrderByLibelleAsc(DataStatus dataStatus, Long idProductCategory, boolean publish);

    List<Product> getAllProductsByDataStatusIsNotAndPublishOrderByLibelleAsc(DataStatus dataStatus, boolean publish);
    List<Product> getAllProductsByDataStatusIsNotOrderByLibelleAsc(DataStatus dataStatus);


    List<Product> getAllByDataStatusIsNotAndUniteMeasureIdAndPublishOrderByLibelleAsc(DataStatus dataStatus, Long idUniteMeasure, boolean publish);

    List<Product> getAllByDataStatusIsNotAndProductCategoryIdAndUniteMeasureIdAndPublishOrderByLibelleAsc(DataStatus dataStatus, Long idProductCategory, Long idUniteMeasure, boolean publish);

    List<Product> findByDataStatusIsNotAndCart(DataStatus dataStatus, boolean cart);
    List<Product> findByDataStatusIsNotAndFeaturedAndPublishOrderByLibelleAsc(DataStatus dataStatus, boolean featured, boolean publish);
    List<Product> findByDataStatusIsNotAndTrendingAndPublishOrderByLibelleAsc(DataStatus dataStatus, boolean tending, boolean publish);

    @Query("select p from Product p where p.dataStatus <> :x and lower(p.libelle) like %:y% or lower(p.description) like %:y% ")
    List<Product> findByDataStatusIsNotAndLibelle(@Param("x") DataStatus dataStatus, @Param("y") String libelle);

    List<Product> findByDataStatusIsNotAndProductCategoryIdAndCountryIdAndPublishOrderByLibelleAsc(DataStatus dataStatus, Long idCategory, Long IdCountry, boolean publish);
    List<Product> findByDataStatusIsNotAndCountryIdAndPublishOrderByLibelleAsc(DataStatus dataStatus, Long IdCountry, boolean publish);

}
