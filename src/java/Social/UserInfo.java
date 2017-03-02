/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Social;

/**
 *
 * @author SONY
 */
//Java Class mimicking static class behavior
public class UserInfo {

    private static String FullName;
    private static String UserName;
    private static String Phone;
    private static String Email;
    private static String password;
    private static String img;

    private static int TotalTweets;
    private static int TotalReTweets;
    private static int TotalFollowers;
    private static int TotalFollowing;

    public static String getFullName() {
        return FullName;
    }

    public static void setFullName(String FullName) {
        UserInfo.FullName = FullName;
    }

    public static String getUserName() {
        return UserName;
    }

    public static void setUserName(String UserName) {
        UserInfo.UserName = UserName;
    }

    public static String getPhone() {
        return Phone;
    }

    public static void setPhone(String Phone) {
        UserInfo.Phone = Phone;
    }

    public static String getEmail() {
        return Email;
    }

    public static void setEmail(String Email) {
        UserInfo.Email = Email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserInfo.password = password;
    }

    public static int getTotalTweets() {
        return TotalTweets;
    }

    public static void setTotalTweets(int TotalTweets) {
        UserInfo.TotalTweets = TotalTweets;
    }

    public static int getTotalReTweets() {
        return TotalReTweets;
    }

    public static void setTotalReTweets(int TotalReTweets) {
        UserInfo.TotalReTweets = TotalReTweets;
    }

    public static int getTotalFollowers() {
        return TotalFollowers;
    }

    public static void setTotalFollowers(int TotalFollowers) {
        UserInfo.TotalFollowers = TotalFollowers;
    }

    public static int getTotalFollowing() {
        return TotalFollowing;
    }

    public static void setTotalFollowing(int TotalFollowing) {
        UserInfo.TotalFollowing = TotalFollowing;
    }

    public static String getImg() {
        return img;
    }

    public static void setImg(String img) {
        UserInfo.img = img;
    }
}
