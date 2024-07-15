package lviv.team.bullying.bot.BullyingBot.core.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@AllArgsConstructor
@Getter
@Document(collection = "bulling")
public class BullingRecord {
    @Field(targetType = FieldType.OBJECT_ID)
    String id;
    long chatId;
    String userTag;
    long date;
}
