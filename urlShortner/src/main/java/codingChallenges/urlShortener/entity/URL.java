package codingChallenges.urlShortener.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Entity
@Table(name = "url_table")
@AllArgsConstructor
@Getter
public class URL {
    @Id
    private Long id;

    @Column
    private String uniqueKey;
    @Column
    private String originalUrl;


    public URL() {
    }

//    public URL(Long id , String uniqueKey, String originalUrl) {
//        this.originalUrl = originalUrl;
//        this.uniqueKey = uniqueKey;
//        this.id=id;
//    }


    @Override
    public String toString() {
        return "URL{" +
                "id=" + id +
                ", uniqueKey='" + uniqueKey + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                '}';
    }
}
