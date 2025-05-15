package core.model;

import lombok.Data;

@Data
public class CommentPayload {
    private Body body;

    public CommentPayload(String commentText) {
        this.body = new Body(commentText);
    }
}