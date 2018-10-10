package telran.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import telran.forum.api.Link;
import telran.forum.dto.*;
import telran.forum.entity.Post;
import telran.forum.service.IForumService;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(Link.FORUM)
public class ForumController {

    @Autowired
    IForumService forumService;

    //  localhost:8080/forum
    @GetMapping
    public List<Post> getAllPosts(){
        return forumService.getAllPosts();
    }

    //  localhost:8080/forum/post
    @PostMapping(Link.POST)
    public Post addNewPost(@RequestBody NewPostDto newPost) {
        return forumService.addNewPost(newPost);
    }

    //  localhost:8080/forum/post/ID
    @GetMapping(Link.POST + "/{id}")
    public Post getPost(@PathVariable String id) {
        return forumService.getPost(id);
    }

    //  localhost:8080/forum/post/ID
    @DeleteMapping(Link.POST + "/{id}")
    public Post removePost(@PathVariable String id) {
        return forumService.removePost(id);
    }

    //  localhost:8080/forum/post
    @PutMapping(Link.POST + "/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody PostUpdateDto updatePost) {
        return forumService.updatePost(updatePost);
    }

    //  localhost:8080/forum/post/ID/like
    @PutMapping(Link.POST + "/{id}"  + Link.LIKE)
    public boolean addLike(@PathVariable String id) {
        return forumService.addLike(id);
    }

    //  localhost:8080/forum/post/ID
    @PostMapping(Link.POST + "/{id}")
    public Post addComment(@PathVariable String id, @RequestBody NewCommentDto newComment) {
        return forumService.addComment(id, newComment);
    }

    // localhost:8080/forum/search/tags/History,Day
    @GetMapping(Link.SEARCH + Link.TAGS + "/{query}")
    public Iterable<Post> findByTags(@PathVariable String query) {
        return forumService.findByTags(Arrays.asList(query.split(",")));
    }

    //  localhost:8080/forum/search/author/Name
    @GetMapping(Link.SEARCH + Link.AUTHOR + "/{auth}")
    public Iterable<Post> findByAuthor(@PathVariable String auth) {
        return forumService.findByAuthor(auth);
    }

    // localhost:8080/forum/search/date/2018-10-09x2018-10-10
    @GetMapping(Link.SEARCH + Link.DATE + "/{period}")
    public Iterable<Post> findByDate(@PathVariable String period, HttpServletResponse response) {

        Iterable<Post> list = null;

        if(period.length() == 21){
            list = forumService.findByDate(new DatePeriodDto(period.substring(0, 10), period.substring(11)));
        }
        if(list == null){
            response.setStatus(400);
        }

        return list;
    }

}
