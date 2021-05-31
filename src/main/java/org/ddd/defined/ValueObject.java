package org.ddd.defined;

import java.io.Serializable;

/**
 * @Author Michael
 * @Date 2021/5/26 14:04
 */
public interface ValueObject<T> extends Serializable {
    /**
     * 对于值对象而且，需要比较值对象的所有属性，如果属性都相同，则两个值对象相等
     * @param other
     * @return
     */
    boolean sameValueAs(T other);
}
