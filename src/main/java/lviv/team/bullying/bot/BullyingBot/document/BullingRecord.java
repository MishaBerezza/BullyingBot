package lviv.team.bullying.bot.BullyingBot.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Document
public class BullingRecord {
    String id;
    long chatId;
    String userTag;
    long date;
}
