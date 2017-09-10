package com.posts.Repository;

import com.posts.Domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mumarm45 on 31/07/2017.
 */
@Repository
public interface AuthorRepository extends PagingAndSortingRepository<Author,Long> {

    List<Author> findByFirstNameContaining(@Param("title") String title);
    Author findFirstByEmail(String email);


}
