package core.model;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class Paragraph {
    private String type = "paragraph";
    private List<Text> content;

    public Paragraph(String commentText) {
        this.content = Collections.singletonList(new Text(commentText));
    }
}
