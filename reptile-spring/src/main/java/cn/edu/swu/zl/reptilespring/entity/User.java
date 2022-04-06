package cn.edu.swu.zl.reptilespring.entity;

public class User {

    //Id
    private int id;
    //博主昵称
    private String nickname;
    //粉丝数
    private String follower_count;
    //总获赞数
    private String total_favorited;
    //主页链接
    private String user_link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(String follower_count) {
        this.follower_count = follower_count;
    }

    public String getTotal_favorited() {
        return total_favorited;
    }

    public void setTotal_favorited(String total_favorited) {
        this.total_favorited = total_favorited;
    }

    public String getUser_link() {
        return user_link;
    }

    public void setUser_link(String user_link) {
        this.user_link = user_link;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", follower_count='" + follower_count + '\'' +
                ", total_favorited='" + total_favorited + '\'' +
                ", user_link='" + user_link + '\'' +
                '}';
    }
}
