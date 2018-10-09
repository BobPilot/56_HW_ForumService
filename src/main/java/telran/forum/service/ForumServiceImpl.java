package telran.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telran.forum.dao.IForumRepository;
import telran.forum.dto.*;
import telran.forum.entity.Comment;
import telran.forum.entity.Post;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class ForumServiceImpl implements IForumService {

    @Autowired
    IForumRepository forumRepository;

    @Override
    public Post addNewPost(NewPostDto newPost) {
        return forumRepository.save(new Post(newPost.getTitle(), newPost.getContent(), newPost.getAuthor(), newPost.getTags()));
    }

    @Override
    public Post getPost(String id) {
        return forumRepository.findById(id).orElse(null);
    }

    @Override
    public List<Post> getAllPosts() {
        return forumRepository.findAll();
    }

    @Override
    public Post removePost(String id) {
        Post post = getPost(id);
        if(post != null){
            forumRepository.delete(post);
        }
        return post;
    }

    @Override
    public Post updatePost(PostUpdateDto updatePost) {

        Post post = getPost(updatePost.getId());
        if(post != null){
            post.setContent(updatePost.getContent());
            forumRepository.save(post);
        }
        return post; // We return old post, is it?
    }

    @Override
    public boolean addLike(String id) {
        Post post = getPost(id);
        if(post == null){
            return false;
        }
        post.addLike();
        forumRepository.save(post);
        return true;
    }

    @Override
    public Post addComment(String id, NewCommentDto newComment) {

        Post post = getPost(id);
        if(post != null){
            post.addComment(new Comment(newComment.getUser(), newComment.getMessage()));
            forumRepository.save(post);
        }
        return post;
    }

    @Override
    public Iterable<Post> findByTags(List<String> tags) {
        return forumRepository.findByTagsIn(tags);
    }

    @Override
    public Iterable<Post> findByAuthor(String author) {
        return forumRepository.findByAuthor(author);
    }

    @Override
    public Iterable<Post> findByDate(DatePeriodDto period) {

        try {

            LocalDate from = LocalDate.parse(period.getFrom());
            LocalDate to = LocalDate.parse(period.getTo());
            return forumRepository.findByDateCreatedBetween(from, to);

        } catch (DateTimeParseException e){
            return null;
        }
    }

}
