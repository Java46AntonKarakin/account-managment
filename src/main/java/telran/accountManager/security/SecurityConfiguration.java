package telran.accountManager.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import telran.accountManager.controller.AccountManagerController;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	static Logger log = LoggerFactory.getLogger(AccountManagerController.class);
	@Value("${app.username.admin:admin}")
	String admin;
	@Value("${app.password.admin:${ADMIN_PASSWORD}}")
	String adminPassword;

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		log.debug("security configuration - enter");
		http.csrf().disable().authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.GET)
				.hasAnyRole("USER", "ADMIN").anyRequest().hasRole("ADMIN")).httpBasic();
		log.debug("http configuration completed");
		return http.build();
	}

	@Bean
	PasswordEncoder getPasswordEncoder() {
		log.debug("BCryptPasswordEncoder - enter");
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsManager userDetailsService(PasswordEncoder bCryptPasswordEncoder) {
		log.debug("userDetailsService - enter");
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(
				User.withUsername(admin).password(bCryptPasswordEncoder.encode(adminPassword)).roles("ADMIN").build());
		log.debug("manager.createUser - success");
		return manager;
	}
}
