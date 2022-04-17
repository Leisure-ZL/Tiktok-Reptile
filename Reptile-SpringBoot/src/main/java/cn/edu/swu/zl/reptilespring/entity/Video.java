package cn.edu.swu.zl.reptilespring.entity;

public class Video extends VideoRaw{

    private long likeIncremental;
    private long commentIncremental;
    private long collectIncremental;

    public long getLikeIncremental() {
        return likeIncremental;
    }

    public void setLikeIncremental(long likeIncremental) {
        this.likeIncremental = likeIncremental;
    }

    public long getCommentIncremental() {
        return commentIncremental;
    }

    public void setCommentIncremental(long commentIncremental) {
        this.commentIncremental = commentIncremental;
    }

    public long getCollectIncremental() {
        return collectIncremental;
    }

    public void setCollectIncremental(long collectIncremental) {
        this.collectIncremental = collectIncremental;
    }
}
