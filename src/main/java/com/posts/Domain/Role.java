package com.posts.Domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by mumarm45 on 05/08/2017.
 */
@Entity
public class Role {

    @Id
    @GeneratedValue
   private Long id;

    @Column(unique = true)
    @NotNull
   private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_authors", joinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "author_id", referencedColumnName = "id")
    })
    private List<Author> authors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
