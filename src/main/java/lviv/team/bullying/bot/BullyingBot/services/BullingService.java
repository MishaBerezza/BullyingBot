package lviv.team.bullying.bot.BullyingBot.services;

import lviv.team.bullying.bot.BullyingBot.core.document.BullingRecord;
import lviv.team.bullying.bot.BullyingBot.repo.BullingRepo;
import lviv.team.bullying.bot.BullyingBot.response.BullingResponseParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BullingService {

    @Autowired
    BullingRepo bullingRepo;

    @Autowired
    BullingResponseParser bullingResponseParser;

    public List<String> saveBullingRecords(long chatId, List<MessageEntity> messageEntities) {

        List<BullingRecord> bullingRecords = messageEntities.stream()
                .map(messageEntity -> buildBullingRecord(chatId, messageEntity))
                .toList();

        List<BullingRecord> savedBullingRecords = bullingRepo.saveAll(bullingRecords);

        return savedBullingRecords.stream()
                .map(savedBullingRecord -> bullingResponseParser.buildSaveRecordText(savedBullingRecord))
                .toList();
    }

    public List<String> getAllBullingRecordsByChatId(long chatId) {
        Set<BullingRecord> bullingRecords = Set.copyOf(bullingRepo.findByChatId(chatId));

        if (bullingRecords.isEmpty()) {
            return bullingResponseParser.buildGetRecordsEmptyResulText();
        }

        return bullingResponseParser.buildGetRecordsText(bullingRecords);
    }

    public List<String> getBullingRecordsByUserTag(long chatId, List<MessageEntity> messageEntities) {
        Set<BullingRecord> bullingRecords = messageEntities.stream()
                .map(messageEntity ->
                        bullingRepo.findByChatIdAndUserTag(chatId, messageEntity.getText())
                )
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        if (bullingRecords.isEmpty()){
            return bullingResponseParser.buildGetRecordsEmptyResulText();
        }

        return bullingResponseParser.buildGetRecordsText(bullingRecords);
    }


    private BullingRecord buildBullingRecord(long chatId, MessageEntity messageEntity) {
        long nowEpochSeconds = Instant.now().getEpochSecond();
        String userMention = messageEntity.getText();

        return new BullingRecord(
                generateId(
                        String.valueOf(chatId),
                        userMention,
                        String.valueOf(nowEpochSeconds)
                ),
                chatId,
                userMention.trim(),
                nowEpochSeconds
        );
    }

    private String generateId(String chatId, String mention, String date) {
        return String.join("-", String.valueOf(chatId), mention, String.valueOf(date));
    }
}
