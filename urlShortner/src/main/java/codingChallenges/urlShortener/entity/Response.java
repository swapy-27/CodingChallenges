package codingChallenges.urlShortener.entity;

public class Response {

    private String key;
    private String long_url;
    private String short_url;


    public Response(String key, String long_url, String short_url) {
        this.key = key;
        this.long_url = long_url;
        this.short_url = short_url;
    }


    @Override
    public String toString() {
        return "Response{" +
                "key='" + key + '\'' +
                ", long_url='" + long_url + '\'' +
                ", short_url='" + short_url + '\'' +
                '}';
    }
}
