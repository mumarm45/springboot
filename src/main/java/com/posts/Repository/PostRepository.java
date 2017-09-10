package com.posts.Repository;

import com.posts.Domain.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mumarm45 on 31/07/2017.
 */
@Repository
public interface PostRepository extends CrudRepository<Post,Long> {
    Post findFirstByOrderByLastUpdatedDateDesc();

    public List<Post> findAllByOrderByLastUpdatedDateDesc();
}
