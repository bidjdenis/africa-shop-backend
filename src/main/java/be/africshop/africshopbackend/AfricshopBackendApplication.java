package be.africshop.africshopbackend;

import be.africshop.africshopbackend.securityModule.entities.AppRole;
import be.africshop.africshopbackend.securityModule.entities.AppUser;
import be.africshop.africshopbackend.securityModule.repository.AppRoleRepository;
import be.africshop.africshopbackend.securityModule.repository.AppUserRepository;
import be.africshop.africshopbackend.securityModule.utils.enums.RoleName;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class AfricshopBackendApplication {

 	private final AppRoleRepository roleRepository;

	private final AppUserRepository userRepository;

	public AfricshopBackendApplication(AppRoleRepository roleRepository, AppUserRepository userRepository) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AfricshopBackendApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			List<AppRole> roles = List.of(
					AppRole.builder().name(String.valueOf(RoleName.ROLE_USER)).build(),
					AppRole.builder().name(String.valueOf(RoleName.ROLE_ADMIN)).build(),
                    AppRole.builder().name(String.valueOf(RoleName.ROLE_SUPER_ADMIN)).build()
			);
			if (roleRepository.findAll().size() < 2) {
				roleRepository.saveAll(roles);
			}
			if (userRepository.findAll().size() < 2) {
			 AppUser appUser =userRepository.save(new AppUser(1L,"Denis", "admin", "bidjdenis1122@gmail.com", passwordEncoder().encode("1022")));
			 this.setListRoles(appUser);
			}
		};
	}

	@SneakyThrows
	private void setListRoles(AppUser user) {
		user.getAppRoles().clear();
		roleRepository.findAll().stream()
				.peek(appRole -> roleRepository.findByName(appRole.getName()).orElseThrow())
				.forEachOrdered(appRole -> user.getAppRoles().add(appRole));
		userRepository.save(user);
	}

}
