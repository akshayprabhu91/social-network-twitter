/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Social;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author USJUUDG
 */
@Named(value = "tweetManagerBean")
@SessionScoped
@ManagedBean
public class tweetManagerBean implements Serializable {

    //public static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    //public static String ORASTR = "%m/%d/%Y %H:%i:%s";
    //public static final SimpleDateFormat DATABASE = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private String tweetStr;
    private String searchStr;
    private String reply;
    private int totalTweets;
    private int totalReTweets;
    ArrayList<SearchObject> searchResult = new ArrayList<>();
    ArrayList<TweetObject> tweets = new ArrayList<>();

    //Getting the current user
    private String userName = UserInfo.getUserName();
    private String fullName = UserInfo.getFullName();
    //private String userName;

    //Database
    final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/prabhua6510";
    final String DB_USER = "prabhua6510";
    final String DB_PASS = "1441868";
    //final String DB_URL = "jdbc:mysql://localhost:3306/twitter";
    //final String DB_USER = "root";
    //final String DB_PASS = "admin123";
    Connection conn = null;
    Statement stat = null;
    ResultSet rs = null;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReply() {
        return reply;
    }

//    public static String getORASTR() {
//        return ORASTR;
//    }
//
//    public static void setORASTR(String ORASTR) {
//        tweetManagerBean.ORASTR = ORASTR;
//    }
    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public ArrayList<SearchObject> getSearchResult() {
        return searchResult;
    }

    public String getTweetStr() {
        return tweetStr;
    }

    public void setTweetStr(String tweetStr) {
        this.tweetStr = tweetStr;
    }

    public void setTotalTweets(int totalTweets) {
        this.totalTweets = totalTweets;
    }

    public void setTotalReTweets(int totalReTweets) {
        this.totalReTweets = totalReTweets;
    }

    public void setSearchResult(ArrayList<SearchObject> searchResult) {
        this.searchResult = searchResult;
    }

    public ArrayList<TweetObject> getTweets() {
        return tweets;
    }

    public void setTweets(ArrayList<TweetObject> tweets) {
        this.tweets = tweets;
    }

    /**
     * Creates a new instance of tweetManagerBean
     */
    public tweetManagerBean() {
    }

    public int fetchTweetCount() {
        //clear searches
        /* searchResult.clear();
        ArrayList<String> followinglist = new ArrayList<>();
        String query = "select * from following where username='" + userName + "'";
        try {
            Database.driverLoad();
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            while (rs.next()) {
                String str1 = rs.getString("followingusername");
                followinglist.add(str1);
            }
        } catch (SQLException ex) {
            //System.out.println("failed");
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return getTweetCount(followinglist); */

        int tempTweets = Database.QueryReturnsOneInt("select count(tweet) from tweets where username = '" + userName + "'");
        int tempReTweets = Database.QueryReturnsOneInt("select count(tweet) from retweets where ReTweetedBy = '" + userName + "'");

        //set the total tweets and retweets
        UserInfo.setTotalTweets(tempTweets);
        UserInfo.setTotalReTweets(tempReTweets);

        this.setTotalTweets(tempTweets);
        this.setTotalReTweets(tempReTweets);

        return (totalTweets + totalReTweets);
    }

    /* public int getTweetCount(ArrayList<String> users) {
        int count = 0;
        //ArrayList<String> tlist = new ArrayList<>();
        try {
            Database.driverLoad();
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stat = conn.createStatement();
            StringBuffer queryUser = new StringBuffer();
            int i = 0;
            if (users != null && !users.isEmpty()) {
                queryUser.append("('");
                for (String user : users) {
                    i++;
                    queryUser.append(user);
                    if (i != users.size()) {
                        queryUser.append("','");
                    } else {
                        queryUser.append("')");
                    }
                }
                String query = "select count(*) from tweets where username in " + queryUser.toString();
                rs = stat.executeQuery(query);
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return count;
    } */
    public int getFollowingCount() {
        return Database.QueryReturnsOneInt("select count(followingusername) from following where username ='" + userName + "'");
    }

    public int getFollowersCount() {
        return Database.QueryReturnsOneInt("select count(User) from myfollowers where MyUserName ='" + userName + "'");
    }

    public void addTweet() {
        Database.InsertUpdate("insert into tweets values('" + userName + "','" + tweetStr + "', now())");

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("homepage.xhtml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void displayTweets() {
        ArrayList<String> followinglist = new ArrayList<>();
        String query = "select * from following where username='" + userName + "'";
        try {
            Database.driverLoad();
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            while (rs.next()) {
                String str1 = rs.getString("followingusername");
                followinglist.add(str1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        displayTweets(followinglist);
    }

    public ArrayList<TweetObject> displayTweets(ArrayList<String> users) {
        tweets.clear();
        try {
            Database.driverLoad();
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stat = conn.createStatement();
            StringBuffer queryUser = new StringBuffer();
            int i = 0;
            if (users != null && !users.isEmpty()) {
                queryUser.append("('");
                for (String user : users) {
                    i++;
                    queryUser.append(user);
                    if (i != users.size()) {
                        queryUser.append("','");
                    } else {
                        queryUser.append("', '" + userName + "')");
                    }
                }
                StringBuffer query = new StringBuffer("select * from tweets where username in " + queryUser.toString());
                query.append(" order by timestamp desc");
                rs = stat.executeQuery(query.toString());
                while (rs.next()) {
                    if (Database.IsRowPresent("select * from retweets where TweetBy = '" + rs.getString(1) + "' and Tweet = '" + rs.getString(2) + "' and ReTweetedBy = '" + userName + "'")) {
                        tweets.add(new TweetObject(fullName, userName, rs.getString(2), fetchReTweetPeriod(rs.getString(1), rs.getString(2)), fetchCountOfLikes(userName, rs.getString(2)), displayLike(userName, rs.getString(2)), "Originally Tweeted by @" + rs.getString(1)));
                        tweets.add(new TweetObject(fetchFullName(rs.getString(1)), rs.getString(1), rs.getString(2), rs.getTimestamp(3), fetchCountOfLikes(rs.getString(1), rs.getString(2)), displayLike(rs.getString(1), rs.getString(2)), ""));
                    } else {
                        tweets.add(new TweetObject(fetchFullName(rs.getString(1)), rs.getString(1), rs.getString(2), rs.getTimestamp(3), fetchCountOfLikes(rs.getString(1), rs.getString(2)), displayLike(rs.getString(1), rs.getString(2)), ""));
                    }
                }
            } else {
                rs = stat.executeQuery("select * from tweets where username = '" + userName + "'");
                while (rs.next()) {
                    if (Database.IsRowPresent("select * from retweets where TweetBy = '" + rs.getString(1) + "' and Tweet = '" + rs.getString(2) + "' and ReTweetedBy = '" + userName + "'")) {
                        tweets.add(new TweetObject(fullName, userName, rs.getString(2), fetchReTweetPeriod(rs.getString(1), rs.getString(2)), fetchCountOfLikes(userName, rs.getString(2)), displayLike(userName, rs.getString(2)), "Originally Tweeted by @" + rs.getString(1)));
                        tweets.add(new TweetObject(fetchFullName(rs.getString(1)), rs.getString(1), rs.getString(2), rs.getTimestamp(3), fetchCountOfLikes(rs.getString(1), rs.getString(2)), displayLike(rs.getString(1), rs.getString(2)), ""));
                    } else {
                        tweets.add(new TweetObject(fetchFullName(rs.getString(1)), rs.getString(1), rs.getString(2), rs.getTimestamp(3), fetchCountOfLikes(rs.getString(1), rs.getString(2)), displayLike(rs.getString(1), rs.getString(2)), ""));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        //sort tweets arraylist based on timestamp
        for (int i = 0; i < tweets.size(); i++) {
            for (int j = (tweets.size() - 1); j >= 0; j--) {
                TweetObject temp = null;
                if ((tweets.get(i).getPeriod()).before(tweets.get(j).getPeriod())) {
                    //swap the objects
                    temp = tweets.get(i);
                    tweets.set(i, tweets.get(j));
                    tweets.set(j, temp);
                }
                if (i == j) {
                    break;
                }
            }
        }
        return tweets;
    }

    public String searchUsers() {
        try {
            Database.driverLoad();
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stat = conn.createStatement();

            String query = "select * from allusers where (UserName like '%" + searchStr + "%' or FullName like '%" + searchStr + "%') and (UserName != '" + userName + "')";
            rs = stat.executeQuery(query);

            //clearing the list first
            searchResult.clear();

            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    searchResult.add(new SearchObject(rs.getString(1), rs.getString(2), displayFollow(rs.getString(2))));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                //rs.close();
                stat.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("searchResults.xhtml");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

        return "searchResults.xhtml";
    }

    public void follow(String followinguser) {
        Database.InsertUpdate("insert into following values('" + userName + "','" + followinguser + "')");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("homepage.xhtml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Map.Entry<String, Long>> calculateTrends() {
        //read all tweets one by one
        // ArrayList<Tweet> allTweets = displayTweets();
        ArrayList<String> allTweets = Database.SelectQuery_ArrayListString("select tweet from tweets");
        List<String> tags = new ArrayList<>();
        List<String> hashtags = new ArrayList<>();
        //store all hashtags in arraylist
        for (String tweet : allTweets) {
            Pattern pattern = Pattern.compile("#\\w+");
            Matcher matcher = pattern.matcher(tweet);
            while (matcher.find()) {
                tags.add(matcher.group());
            }
        }

        //iterate arraylist and count common hashtags
        Set<String> unique = new HashSet<>(tags);
        for (String key : unique) {
            hashtags.add(key + ": " + Collections.frequency(tags, key) + " tweets");
        }

        Map<String, Long> map = tags.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        List<Map.Entry<String, Long>> result = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toList());

        return result;
    }

//    public int countLikes(String tweetedByUser, String tweetStr) {
//        return Database.QueryReturnsOneInt("select likes from tweets where username='" + tweetedByUser + "' and tweet='" + tweetStr + "'");
//    }
//    public void like(String tweetedByUser, String tweetStr) {
//        int temp = Database.QueryReturnsOneInt("select likes from tweets where username='" + tweetedByUser + "' and tweet='" + tweetStr + "'");
//        Database.InsertUpdate("update tweets set likes='" + (++temp) + "' where username='" + tweetedByUser + "' and tweet='" + tweetStr + "'");
//
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("homepage.xhtml");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//    public void reply(String tweetorig, String reply) {
//        int l = 0;
//        Database.InsertUpdate("insert into tweet_replies values('" + tweetorig + "','" + reply + "','" + userName + "', now()))");
//
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("homepage.xhtml");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//    public ArrayList<String> fetchReplies() {
//        ArrayList<String> tlist = new ArrayList<>();
//        try {
//            Database.driverLoad();
//            stat = conn.createStatement();
//            int i = 0;
//            String query = "select * from tweet_replies where tweet = '" + tweetStr + "' order by timestamp desc";
//            rs = stat.executeQuery(query);
//            while (rs.next()) {
//                String str1 = rs.getString("reply");
//                tlist.add(str1);
//            }
//        } catch (SQLException ex) {
//            System.out.println("failed");
//            ex.printStackTrace();
//        } finally {
//            try {
//                rs.close();
//                stat.close();
//                conn.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return tlist;
//    }
    public String fetchFullName(String user) {
        return Database.QueryReturnsOneString("select FullName from allusers where UserName = '" + user + "'");
    }

    public String displayFollow(String user) {
        if (!Database.IsRowPresent("select * from following where username = '" + userName + "' and followingusername = '" + user + "'")) {
            return "FOLLOW";
        }
        return "UNFOLLOW";
    }

    public String followOrUnfollow(String val, String followingUserName) {
        if ("FOLLOW".equals(val)) {
            Database.InsertUpdate("insert into following values('" + userName + "', '" + followingUserName + "')");
            Database.InsertUpdate("insert into myfollowers values('" + userName + "', '" + followingUserName + "')");
        } else if ("UNFOLLOW".equals(val)) {
            Database.InsertUpdate("delete from following where username = '" + userName + "' and followingusername = '" + followingUserName + "'");
            Database.InsertUpdate("delete from myfollowers where User = '" + userName + "' and MyUserName = '" + followingUserName + "'");
        }
        //Need to update the searchResult array list
        searchUsers();
        //calling the same page
        return "searchResults.xhtml";
    }

    public String displayLike(String user, String twt) {
        if (!Database.IsRowPresent("select * from likes where TweetBy = '" + user + "' and Tweet = '" + twt + "' and LikedBy = '" + userName + "'")) {
            return "Like";
        }
        return "Unlike";
    }

    public String likeOrUnlike(String val, String user, String twt) {
        if ("Like".equals(val)) {
            Database.InsertUpdate("insert into likes values('" + user + "', '" + twt + "', '" + userName + "')");
        } else if ("Unlike".equals(val)) {
            Database.InsertUpdate("delete from likes where TweetBy = '" + user + "' and Tweet = '" + twt + "' and LikedBy = '" + userName + "'");
        }
        //Need to update the displayTweets array list
        //displayTweets();
        //calling the same page
        return "homepage.xhtml";
    }

    public int fetchCountOfLikes(String user, String twt) {
        return Database.QueryReturnsOneInt("select count(*) from likes where TweetBy = '" + user + "' and Tweet ='" + twt + "'");
    }

    public ArrayList<TweetObject> fetchTweets() {
        displayTweets();
        return tweets;
    }

    public String reTweet(String user, String twt) {

        Database.InsertUpdate("insert into retweets values('" + user + "', '" + twt + "', '" + userName + "', now())");
        return "homepage.xhtml";
    }

    public Timestamp fetchReTweetPeriod(String twtBy, String twt) {
        return Database.QueryReturnsTimestamp("select datetime from retweets where TweetBy = '" + twtBy + "' and Tweet = '" + twt + "'");
    }
}
