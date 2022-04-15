package cn.edu.swu.zl.reptilespring.entity;

public class User extends UserRaw {
    private String head_img;
    private long follower_incremental;
    private long like_incremental;

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public long getFollower_incremental() {
        return follower_incremental;
    }

    public void setFollower_incremental(long follower_incremental) {
        this.follower_incremental = follower_incremental;
    }

    public long getLike_incremental() {
        return like_incremental;
    }

    public void setLike_incremental(long like_incremental) {
        this.like_incremental = like_incremental;
    }
}
