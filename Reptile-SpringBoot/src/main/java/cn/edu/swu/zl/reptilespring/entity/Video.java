package cn.edu.swu.zl.reptilespring.entity;

public class Video {

    private int id;
    private String video_name;
    private String like_num;
    private String comment_num;
    private String collect_num;
    private String url;
    private String user_name;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getLike_num() {
        return like_num;
    }

    public void setLike_num(String like_num) {
        this.like_num = like_num;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(String collect_num) {
        this.collect_num = collect_num;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", video_name='" + video_name + '\'' +
                ", like_num='" + like_num + '\'' +
                ", comment_num='" + comment_num + '\'' +
                ", collect_num='" + collect_num + '\'' +
                ", url='" + url + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
