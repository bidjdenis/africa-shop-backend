package be.africshop.africshopbackend.catalogueModule.repository;

import be.africshop.africshopbackend.catalogueModule.entities.UniteMeasure;
import be.africshop.africshopbackend.utils.DataStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UniteMesureRepository extends JpaRepository<UniteMeasure, Long> {

    Optional<UniteMeasure> findUniteMesureByDataStatusIsNotAndId(DataStatus dataStatus, Long id);
    List<UniteMeasure> getAllUniteMesuresByDataStatusIsNot(DataStatus dataStatus);

    @Query("select u from UniteMeasure u where u.dataStatus <> :x and lower(u.libelle) like %:y% or lower(u.symbole) like  %:y%")
    List<UniteMeasure> findByDataStatusIsNotAndLibelleIgnoreCase(@Param("x") DataStatus dataStatus, @Param("y") String name);


}
