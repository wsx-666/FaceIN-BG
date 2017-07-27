package Bean.ResponseBean;

/**
 * Created by Dao on 2017/7/25.
 */
public class Results {
    private Float confidence;
    private String user_id;
    private String face_token;

    public Results() {
    }

    public Results(Float confidence, String user_id, String face_token) {
        this.confidence = confidence;
        this.user_id = user_id;
        this.face_token = face_token;
    }

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Results results = (Results) o;

        if (confidence != null ? !confidence.equals(results.confidence) : results.confidence != null) return false;
        if (user_id != null ? !user_id.equals(results.user_id) : results.user_id != null) return false;
        return face_token != null ? face_token.equals(results.face_token) : results.face_token == null;
    }

    @Override
    public int hashCode() {
        int result = confidence != null ? confidence.hashCode() : 0;
        result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
        result = 31 * result + (face_token != null ? face_token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Results{" +
                "confidence=" + confidence +
                ", user_id='" + user_id + '\'' +
                ", face_token='" + face_token + '\'' +
                '}';
    }
}
