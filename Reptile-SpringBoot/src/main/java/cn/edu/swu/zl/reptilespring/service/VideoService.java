package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.entity.Video;
import cn.edu.swu.zl.reptilespring.entity.VideoRaw;

import java.util.List;

public interface VideoService {

    List<VideoRaw> getAll();

    List<Video> getVideoByLikeInc(int size);

    List<Video> getVideoByLike(int size);

    List<Video> getVideoByComment(int size);

    List<Video> getVideoByCollect(int size);

    List<Video> getVideoByCommentInc(int size);

    List<Video> getVideoByCollectInc(int size);




}
