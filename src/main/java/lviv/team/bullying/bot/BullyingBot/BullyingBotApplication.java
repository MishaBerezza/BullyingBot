package lviv.team.bullying.bot.BullyingBot;

import lviv.team.bullying.bot.BullyingBot.repo.BullingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class BullyingBotApplication {

	@Autowired
	BullingRepo bullingRepo;

	public static void main(String[] args) {
		SpringApplication.run(BullyingBotApplication.class, args);
	}
}
