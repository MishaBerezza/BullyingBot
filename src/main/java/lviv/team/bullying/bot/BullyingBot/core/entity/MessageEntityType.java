package lviv.team.bullying.bot.BullyingBot.core.entity;

public enum MessageEntityType {
    MENTION("mention"),
    BOT_COMMAND("bot_command");

    private String type;

     MessageEntityType(String type) {
        this.type = type;
    }

    public String getType() {
         return type;
    }
}
