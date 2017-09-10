package com.posts.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mumarm45 on 31/07/2017.
 */
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;


    private String title;
    @Column(columnDefinition = "Text")
    private String body;

    @ManyToOne
    private Author author;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
