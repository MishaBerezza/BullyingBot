package lviv.team.bullying.bot.BullyingBot.response;

public enum DefaultResponses {
    UNKNOWN_COMMAND("Guys, I don't know this command"),
    COMMAND_NOT_FOUND("Command not found"),;


    final String text;


    DefaultResponses(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
