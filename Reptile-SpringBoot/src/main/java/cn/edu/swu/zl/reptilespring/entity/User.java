package cn.edu.swu.zl.reptilespring.entity;

public class User extends UserRaw {
    private String headImg;
    private long followerIncremental;
    private long likeIncremental;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public long getFollowerIncremental() {
        return followerIncremental;
    }

    public void setFollowerIncremental(long followerIncremental) {
        this.followerIncremental = followerIncremental;
    }

    public long getLikeIncremental() {
        return likeIncremental;
    }

    public void setLikeIncremental(long likeIncremental) {
        this.likeIncremental = likeIncremental;
    }
}
