package lviv.team.bullying.bot.BullyingBot.entity;

public enum EntityTypes {
    MENTION("mention"),
    BOT_COMMAND("bot_command");

    private String type;

     EntityTypes(String type) {
        this.type = type;
    }

    public String getType() {
         return type;
    }
}
