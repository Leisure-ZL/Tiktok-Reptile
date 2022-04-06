package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.entity.User;
import cn.edu.swu.zl.reptilespring.entity.Video;

import java.util.List;

public interface VideoService {

    public List<Video> getVideoRankByLike();

    public List<Video> getVideoRankByComment();

    public List<Video> getVideoRankByCollect();


}
