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
public class SearchObject {
    private String FullName;
    private String UserName;
    private String followOrUnfollow;

    public SearchObject(String FullName, String UserName, String followOrUnfollow) {
        this.FullName = FullName;
        this.UserName = UserName;
        this.followOrUnfollow = followOrUnfollow;
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

    public String getFollowOrUnfollow() {
        return followOrUnfollow;
    }

    public void setFollowOrUnfollow(String followOrUnfollow) {
        this.followOrUnfollow = followOrUnfollow;
    }
}
