package accountManagment;

import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import accountManagment.controller.AccountManagmentController;
import jakarta.annotation.PreDestroy;

@SpringBootApplication
public class AccountManagmentAppl {
	private static final String SHUTDOWN = "bb";
	static Logger log = LoggerFactory.getLogger(AccountManagmentController.class);

	public static void main(String[] args) {
		log.debug("main - launched");
		ConfigurableApplicationContext ctx = SpringApplication.run(AccountManagmentController.class, args);
		log.debug("ctx created");
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("To stop server type " + SHUTDOWN);
			String line = scanner.nextLine();
			if (line.equals(SHUTDOWN)) {
				break;
			}
		}
		ctx.close();
	}

	@PreDestroy
	void preDestroy() {
		System.out.println("bye - shutdown has been performed");
	}
}
