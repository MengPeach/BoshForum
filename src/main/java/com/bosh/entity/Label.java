/**
 * 
 */
package com.bosh.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * @author wangmt
 * @date 2018年9月20日
 */
@Entity
@Table(name = "quark_label")
public class Label implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7733436820721631122L;

	@Id
    @GeneratedValue
    private Integer id;

    //标签名称
    @Column(nullable = false, unique = true)
    private String name;

    //主题数量
    @Column(name = "posts_count")
    private Integer postsCount = 0;

    //详情
    private String details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
