package org.github.event.domain;

import java.util.*;

/**
 * @author chenjx
 */
public class Customer {
    private CustomerId id;
    private String name;
    private Map<String, Notice> notices;

    public Customer(CustomerId id, String name) {
        this.id = id;
        this.name = name;
        this.notices = new LinkedHashMap<>();
    }

    public void notice(String message){
        final Notice notice = Notice.create(message);
        notices.put(notice.getNoticeId(), notice);
    }

    public void readNotice(String noticeId){
        notices.remove(noticeId);
    }

    public CustomerId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Map<String, Notice> notices() {
        return notices;
    }
}
