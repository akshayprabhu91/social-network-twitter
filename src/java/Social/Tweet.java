/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Social;

import java.sql.Timestamp;

/**
 *
 * @author SONY
 */
public class Tweet {

    private String username;
    private String tweet;
    private Timestamp period;
    private int likes;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Timestamp getPeriod() {
        return period;
    }

    public void setPeriod(Timestamp period) {
        this.period = period;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Tweet(String username, String tweet, Timestamp period, int likes) {
        this.username = username;
        this.tweet = tweet;
        this.period = period;
        this.likes = likes;
    }
}
