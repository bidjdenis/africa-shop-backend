package be.africshop.africshopbackend.commandeModule.repository;

import be.africshop.africshopbackend.commandeModule.entities.Country;
import be.africshop.africshopbackend.utils.DataStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findCountryByDataStatusIsNotAndId(DataStatus dataStatus, Long id);

    List<Country> getAllCountryByDataStatusIsNot(DataStatus dataStatus);

    @Query("select c from Country c where c.dataStatus <> :x and lower(c.libelle) like %:y% ")
    List<Country> findByDataStatusIsNotAndLibelleIgnoreCase(@Param("x") DataStatus dataStatus, @Param("y") String name);



}
