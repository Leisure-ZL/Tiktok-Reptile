package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.dao.VideoDao;
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
    public List<VideoRaw> getVideoRankByLike() {
        List<VideoRaw> list = videoDao.getVideoRawAll();
        Collections.sort(list, new Comparator<VideoRaw>() {
            @Override
            public int compare(VideoRaw o1, VideoRaw o2) {
                long res = DataUtil.stringToNum(o1.getLikeNum()) - DataUtil.stringToNum(o2.getLikeNum());
                if(res == 0){
                    return 0;
                }
                return res < 0 ? 1 : -1;
            }
        });
        return list;
    }

    @Override
    public List<VideoRaw> getVideoRankByComment() {
        List<VideoRaw> list = videoDao.getVideoRawAll();
        Collections.sort(list, new Comparator<VideoRaw>() {
            @Override
            public int compare(VideoRaw o1, VideoRaw o2) {
                long res = DataUtil.stringToNum(o1.getCommentNum()) - DataUtil.stringToNum(o2.getCommentNum());
                if(res == 0){
                    return 0;
                }
                return res < 0 ? 1 : -1;
            }
        });
        return list;
    }

    @Override
    public List<VideoRaw> getVideoRankByCollect() {
        List<VideoRaw> list = videoDao.getVideoRawAll();
        Collections.sort(list, new Comparator<VideoRaw>() {
            @Override
            public int compare(VideoRaw o1, VideoRaw o2) {
                long res = DataUtil.stringToNum(o1.getCollectNum()) - DataUtil.stringToNum(o2.getCollectNum());
                if(res == 0){
                    return 0;
                }
                return res < 0 ? 1 : -1;
            }
        });
        return list;
    }



}
