package lviv.team.bullying.bot.BullyingBot.response;

import lviv.team.bullying.bot.BullyingBot.document.BullingRecord;
import org.springframework.stereotype.Component;

@Component
public class ResponseParser {

    public String bullingRecordToText(BullingRecord bullingRecord) {
        StringBuilder text = new StringBuilder();
        text.append("This shit was recorded ")
                .append(bullingRecord.getUserTag())
                .append("\n")
                .append(" at ")
                .append(bullingRecord.getDate());

        return text.toString();
    }

}
