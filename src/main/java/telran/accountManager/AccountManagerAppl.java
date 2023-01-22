package telran.accountManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PreDestroy;

@SpringBootApplication
public class AccountManagerAppl {
	static Logger log = LoggerFactory.getLogger(AccountManagerAppl.class );

	public static void main(String[] args) {
		log.debug("main - launched");
		SpringApplication.run(AccountManagerAppl.class, args);
		log.debug("ctx created");
	}

	@PreDestroy
	void preDestroy() {
		System.out.println("bye - shutdown has been performed");
	}
}
