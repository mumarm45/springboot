package com.posts.Service;

import com.posts.Domain.Post;
import com.posts.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mumarm45 on 31/07/2017.
 */

public interface PostService {

    Iterable<Post> list();
    Post getOne(Long id);
    Post create(Post post);
    Post update(Post post);
    void delete(Long id);

}
