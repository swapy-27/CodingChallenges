package codingChallenges.urlShortener.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "url_table")
@AllArgsConstructor
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

}
