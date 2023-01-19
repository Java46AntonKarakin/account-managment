package accountManagment;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import accountManagment.controller.accountManagerController;
import jakarta.annotation.PreDestroy;

public class AccountingManagerAppl {
	@Autowired
	accountManagerController controller;

	private static final String SHUTDOWN = "shutdown";

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(accountManagerController.class, args);
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
