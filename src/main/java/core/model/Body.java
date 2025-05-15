package core.model;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class Body {
    private String type = "doc";
    private int version = 1;
    private List<Paragraph> content;

    public Body(String commentText) {
        this.content = Collections.singletonList(new Paragraph(commentText));
    }
}
