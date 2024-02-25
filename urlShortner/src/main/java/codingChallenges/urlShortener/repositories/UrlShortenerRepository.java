package codingChallenges.urlShortener.repositories;

import codingChallenges.urlShortener.entity.URL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortenerRepository extends CrudRepository<URL, Long> {

     URL findByUniqueKey(String uniqueKey);
     URL  findByOriginalUrl(String originalUrl);

}
