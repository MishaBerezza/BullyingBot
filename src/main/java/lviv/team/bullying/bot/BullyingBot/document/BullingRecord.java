package lviv.team.bullying.bot.BullyingBot.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Document(collection = "bulling")
public class BullingRecord {
    @Id
    int id;
    long chatId;
    String userTag;
    long date;
}
