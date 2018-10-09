package telran.forum.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;
import telran.forum.entity.Post;

@Repository
public interface IForumRepository extends MongoRepository<Post, String> {
	Iterable<Post> findByTagsIn(List<String> tags);
	
	Iterable<Post> findByAuthor(String author);
	
	Iterable<Post> findByDateCreatedBetween(LocalDate from, LocalDate to);
	
	Stream<Post> findAllBy();

}
