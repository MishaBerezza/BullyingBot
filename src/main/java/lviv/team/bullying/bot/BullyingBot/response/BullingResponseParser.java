package lviv.team.bullying.bot.BullyingBot.response;

import lviv.team.bullying.bot.BullyingBot.core.document.BullingRecord;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BullingResponseParser {

    public String buildSaveRecordText(BullingRecord bullingRecord) {
        StringBuilder text = new StringBuilder();

        text.append(bullingRecord.getUserTag());
        text.append("\n");
        text.append("This shit was recorded ");
        text.append(" at ");
        text.append(getDateFromEpochSeconds(bullingRecord.getDate()));

        return text.toString();
    }

    public List<String> buildGetRecordsText(Set<BullingRecord> bullingRecords) {
        Map<String, List<BullingRecord>> userToRecords = groupByUser(bullingRecords);


        return userToRecords.entrySet()
                .stream()
                .map(entry -> buildTextForGetRecords(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<String> buildGetRecordsEmptyResulText() {
        String text = "I don't find any records";

        return List.of(text);
    }

    public String buildTextForGetRecords(String userTag, List<BullingRecord> bullingRecords) {
        StringBuilder text = new StringBuilder();

        text.append(userTag);
        text.append("\n");
        text.append("This shit has " + bullingRecords.size() + " bulling records.");
        text.append("\n");


        bullingRecords.forEach(bullingRecord -> {
            String textDate = getDateFromEpochSeconds(bullingRecord.getDate());

            text.append(textDate).append("\n");
        });

        return text.toString();
    }

    public Map<String, List<BullingRecord>> groupByUser(Set<BullingRecord> bullingRecords) {
        return bullingRecords.stream().collect(Collectors.groupingBy(BullingRecord::getUserTag));

    }

    private String getDateFromEpochSeconds(long epochSeconds) {
        Instant instant = Instant.ofEpochSecond(epochSeconds);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("Europe/Kyiv"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        return zonedDateTime.format(formatter);
    }
}
