package be.africshop.africshopbackend.emailModule.repository;


import be.africshop.africshopbackend.emailModule.entities.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail, Long> {
}
