package be.africshop.africshopbackend.commandeModule.repository;

import be.africshop.africshopbackend.commandeModule.entities.Command;
import be.africshop.africshopbackend.utils.DataStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandRepository extends JpaRepository<Command, Long> {

    Optional<Command> findCommandByDataStatusIsNotAndId(DataStatus dataStatus, Long id);

    List<Command> getAllCommandesByDataStatusIsNot(DataStatus dataStatus);

}
