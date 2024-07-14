package lviv.team.bullying.bot.BullyingBot.response;

import lviv.team.bullying.bot.BullyingBot.document.BullingRecord;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ResponseParser {

    public String bullingRecordToText(BullingRecord bullingRecord) {
        StringBuilder text = new StringBuilder();
        text.append("This shit was recorded ")
                .append(bullingRecord.getUserTag())
                .append("\n")
                .append(" at ")
                .append(getDateFromEpochSeconds(bullingRecord.getDate()));

        return text.toString();
    }

    private String getDateFromEpochSeconds(long epochSeconds) {
        Instant instant = Instant.ofEpochSecond(epochSeconds);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("Europe/Kyiv"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return zonedDateTime.format(formatter);
    }
}
