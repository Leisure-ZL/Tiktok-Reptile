package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.dao.VideoDao;
import cn.edu.swu.zl.reptilespring.entity.Video;
import cn.edu.swu.zl.reptilespring.entity.VideoRaw;
import cn.edu.swu.zl.reptilespring.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoDao videoDao;


    @Override
    public List<VideoRaw> getAll() {
        return videoDao.getVideoRawAll();
    }

    @Override
    public List<Video> getVideoByLikeInc(int size) {
        return videoDao.getVideoByLikeInc(size);
    }

    @Override
    public List<Video> getVideoByLike(int size) {
        return videoDao.getVideoByLike(size);
    }

    @Override
    public List<Video> getVideoByComment(int size) {
        return videoDao.getVideoByComment(size);
    }

    @Override
    public List<Video> getVideoByCollect(int size) {
        return videoDao.getVideoByCollect(size);
    }

    @Override
    public List<Video> getVideoByCommentInc(int size) {
        return videoDao.getVideoByCommentInc(size);
    }

    @Override
    public List<Video> getVideoByCollectInc(int size) {
        return videoDao.getVideoByCollectInc(size);
    }


}
