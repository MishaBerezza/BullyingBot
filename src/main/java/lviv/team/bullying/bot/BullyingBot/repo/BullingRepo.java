package lviv.team.bullying.bot.BullyingBot.repo;

import lviv.team.bullying.bot.BullyingBot.core.document.BullingRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BullingRepo extends MongoRepository<BullingRecord, Integer> {

    @Override
    <S extends BullingRecord> List<S> saveAll(Iterable<S> entities);

    @Query(value = "{ 'chatId' : ?0 }")
    List<BullingRecord> findByChatId(long chatId);

}
