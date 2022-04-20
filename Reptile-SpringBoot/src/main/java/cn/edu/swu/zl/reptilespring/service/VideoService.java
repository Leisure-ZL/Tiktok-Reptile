package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.entity.Video;
import cn.edu.swu.zl.reptilespring.entity.VideoRaw;

import java.util.List;

public interface VideoService {

    List<VideoRaw> getAll();

    List<Video> getVideoRankByIncremental();

    List<VideoRaw> getVideoRankByLike();

    List<VideoRaw> getVideoRankByComment();

    List<VideoRaw> getVideoRankByCollect();


}
