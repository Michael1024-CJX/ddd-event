package org.github.event.domain;

import java.util.UUID;

/**
 * @author chenjx
 */
public class Notice {
    private String noticeId;
    private String message;

    private Notice(String noticeId, String message) {
        this.noticeId = noticeId;
        this.message = message;
    }

    public static Notice create(String message){
        return new Notice(UUID.randomUUID().toString(), message);
    }

    public String getNoticeId() {
        return noticeId;
    }

    public String getMessage() {
        return message;
    }
}
