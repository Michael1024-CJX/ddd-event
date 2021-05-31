package org.ddd.defined;

/**
 * @Author Michael
 * @Date 2021/5/26 13:51
 */
public interface Entity<T extends Entity, ID> {
    /**
     * 返回实体的唯一标识
     * @return
     */
    ID identity();

    /**
     * 对于实体而且，唯一标识相同就表示两个实体是同一个实体
     * 两个实体在不同的生命周期中的值可能不相同
     *
     * @param other
     * @return
     */
    default boolean sameIdentityAs(T other) {
        return other != null && other.identity().equals(this.identity());
    }
}