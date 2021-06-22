package org.ddd.event;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Michael
 */
public class EventTest {
    @Test
    public void newEventTest() {
        EventObject eventObject = new EventObject(this) {{
        }};

        Assert.assertEquals("source is not this", this, eventObject.source);
        Assert.assertNotNull("event id is not inited", eventObject.eventId);
    }


    @Test
    public void testMap() {
        Map<User, String> map = new HashMap<>(4);
        final User abc = new User("ABC", 18);
        final User zxy = new User("zxy", 18);
        map.put(abc, "HELLO");
        map.put(zxy, "WORLD");

        System.out.println(map.get(abc));

        abc.setName("zxy");
        System.out.println(map);
        System.out.println(map.get(abc));

        User tem1 = new User("t1", 12);
        User tem2 = new User("t2", 12);
        map.put(tem1, "t1");
        System.out.println("put t1:" + map);
        map.put(tem2, "t2");
        System.out.println("put t2:" + map);
    }
}

class User {
    String name;

    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
