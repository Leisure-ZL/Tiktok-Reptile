package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.entity.VideoRaw;

import java.util.List;

public interface VideoService {

    List<VideoRaw> getAll();

    List<VideoRaw> getVideoRankByLike();

    List<VideoRaw> getVideoRankByComment();

    List<VideoRaw> getVideoRankByCollect();


}
