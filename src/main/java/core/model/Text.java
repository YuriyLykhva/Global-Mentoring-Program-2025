package core.model;

import lombok.Data;

@Data
public class Text {
    private String type = "text";
    private String text;

    public Text(String text) {
        this.text = text;
    }
}
