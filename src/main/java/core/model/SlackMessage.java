package core.model;

import lombok.Data;

@Data
public class SlackMessage {
    private final String text;

    public SlackMessage(String text) {
        this.text = text;
    }
}
