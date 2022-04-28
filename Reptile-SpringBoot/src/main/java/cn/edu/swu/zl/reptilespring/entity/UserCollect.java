package cn.edu.swu.zl.reptilespring.entity;

public class UserCollect {

    private int id;
    private int userId;
    private int collectUserId;
    private String nickname;
    private String followerCount;
    private String headImg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCollectUserId() {
        return collectUserId;
    }

    public void setCollectUserId(int collectUserId) {
        this.collectUserId = collectUserId;
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

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    @Override
    public String toString() {
        return "UserCollect{" +
                "id=" + id +
                ", userId=" + userId +
                ", userCollectId=" + collectUserId +
                ", nickname='" + nickname + '\'' +
                ", followerCount='" + followerCount + '\'' +
                ", headImg='" + headImg + '\'' +
                '}';
    }
}
