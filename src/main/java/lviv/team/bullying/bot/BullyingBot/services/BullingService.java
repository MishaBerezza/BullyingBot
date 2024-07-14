package lviv.team.bullying.bot.BullyingBot.services;

import lviv.team.bullying.bot.BullyingBot.document.BullingRecord;
import lviv.team.bullying.bot.BullyingBot.repo.BullingRepo;
import lviv.team.bullying.bot.BullyingBot.response.ResponseParser;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.time.Instant;
import java.util.List;

@Service
public class BullingService {

    @Autowired
    BullingRepo bullingRepo;

    @Autowired
    ResponseParser responseParser;

    public List<String> saveBullingRecords(long chatId, List<MessageEntity> messageEntities) {

        List<BullingRecord> bullingRecords = messageEntities.stream()
                .map(messageEntity -> buildBullingRecord(chatId, messageEntity))
                .toList();

        List<BullingRecord> savedBullingRecords = bullingRepo.saveAll(bullingRecords);

        return savedBullingRecords.stream()
                .map(savedBullingRecord -> responseParser.bullingRecordToText(savedBullingRecord))
                .toList();
    }

//    public String getBullingRecords(long chatId, List<MessageEntity> messageEntities) {
//
//        List<BullingRecord> bullingRecords = messageEntities.stream().map(messageEntity -> buildBullingRecord(chatId, messageEntity)).toList();
//
//    }


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

    private int generateId(String chatId, String mention, String date) {
        return 1;
    }
}
