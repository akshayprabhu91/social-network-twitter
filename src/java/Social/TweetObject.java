package Social;


import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SONY
 */
public class TweetObject {

    private String FullName;
    private String UserName;
    private String Tweet;
    private Timestamp Period;
    private int NumOfLikes;
    private String LikeOrUnlike;
    private String isRetweeted;

    public TweetObject(String FullName, String UserName, String Tweet, Timestamp Period, int NumOfLikes, String LikeOrUnlike, String isRetweeted) {
        this.FullName = FullName;
        this.UserName = UserName;
        this.Tweet = Tweet;
        this.Period = Period;
        this.NumOfLikes = NumOfLikes;
        this.LikeOrUnlike = LikeOrUnlike;
        this.isRetweeted = isRetweeted;
    }
    
    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getTweet() {
        return Tweet;
    }

    public void setTweet(String Tweet) {
        this.Tweet = Tweet;
    }

    public Timestamp getPeriod() {
        return Period;
    }

    public void setPeriod(Timestamp Period) {
        this.Period = Period;
    }

    public int getNumOfLikes() {
        return NumOfLikes;
    }

    public void setNumOfLikes(int NumOfLikes) {
        this.NumOfLikes = NumOfLikes;
    }

    public String getLikeOrUnlike() {
        return LikeOrUnlike;
    }

    public void setLikeOrUnlike(String LikeOrUnlike) {
        this.LikeOrUnlike = LikeOrUnlike;
    }

    public String getIsRetweeted() {
        return isRetweeted;
    }

    public void setIsRetweeted(String isRetweeted) {
        this.isRetweeted = isRetweeted;
    }

    
}
