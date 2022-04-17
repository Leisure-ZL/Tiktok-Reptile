package cn.edu.swu.zl.reptilespring.entity;

public class UserRaw {

    //Id
    private int id;
    //博主昵称
    private String nickname;
    //粉丝数
    private String followerCount;
    //总获赞数
    private String likeCount;
    //主页链接
    private String link;

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

    public String getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(String followerCount) {
        this.followerCount = followerCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", follower_count='" + followerCount + '\'' +
                ", total_favorited='" + likeCount + '\'' +
                ", user_link='" + link + '\'' +
                '}';
    }
}
