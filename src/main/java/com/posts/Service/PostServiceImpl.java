package com.posts.Service;

import com.posts.Domain.Post;
import com.posts.Repository.AuthorRepository;
import com.posts.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mumarm45 on 31/07/2017.
 */
@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private AuthorRepository authorRepository;

    @Autowired
    PostServiceImpl(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Iterable<Post> list() {
        return postRepository.findAllByOrderByLastUpdatedDateDesc();
    }

    @Override
    public Post getOne(Long id) {
        return postRepository.findOne(id);
    }

    @Override
    public Post create(Post post) {
        //authorRepository.save(post.getAuthor());
        return postRepository.save(post);
    }

    @Override
    public Post update( Post updatePost) {
        return  postRepository.save(updatePost);

    }

    @Override
    public void delete(Long id) {
        postRepository.delete(id);
    }
}
