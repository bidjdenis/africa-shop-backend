package be.africshop.africshopbackend.commandeModule.repository;

import be.africshop.africshopbackend.commandeModule.entities.ValidationCommand;
import be.africshop.africshopbackend.utils.DataStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ValidationCommandRepository extends JpaRepository<ValidationCommand, Long> {

    Optional<ValidationCommand> findValidationCommandByDataStatusIsNotAndId(DataStatus dataStatus, Long id);

    List<ValidationCommand> getAllValidationCommandsByDataStatusIsNot(DataStatus dataStatus);

}
