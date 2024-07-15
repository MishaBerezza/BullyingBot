package lviv.team.bullying.bot.BullyingBot.response;

public enum DefaultResponses {
    UNKNOWN_COMMAND("Guys, I don't know this command"),
    COMMAND_NOT_FOUND("Command not found"),
    INVALID_COMMAND_CONTENT("Invalid command content");


    final String text;


    DefaultResponses(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
