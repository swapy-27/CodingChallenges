package codingChallenges.urlShortener.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;


@Entity
@Table(name = "url_table")
@AllArgsConstructor
@Getter
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String uniqueKey;
    @Column
    private String originalUrl;


    public URL() {
    }

    public URL(String uniqueKey, String originalUrl) {
        this.uniqueKey=uniqueKey;
        this.originalUrl = originalUrl;
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
