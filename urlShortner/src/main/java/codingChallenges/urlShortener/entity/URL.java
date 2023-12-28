package codingChallenges.urlShortener.entity;


import jakarta.persistence.*;
import lombok.Generated;

@Entity
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    private String uniqueKey;
    @Column
    private String originalUrl;


    public URL() {
    }

    public URL(String uniqueKey, String originalUrl) {
        this.originalUrl = originalUrl;
        this.uniqueKey = uniqueKey;
    }

}
