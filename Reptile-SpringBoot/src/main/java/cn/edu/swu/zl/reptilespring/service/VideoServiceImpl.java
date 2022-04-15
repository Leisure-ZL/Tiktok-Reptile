package cn.edu.swu.zl.reptilespring.service;

import cn.edu.swu.zl.reptilespring.dao.VideoDao;
import cn.edu.swu.zl.reptilespring.entity.Video;
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
    public List<Video> getVideoRankByLike() {
        List<Video> list = videoDao.getUserAll();
        Collections.sort(list, new Comparator<Video>() {
            @Override
            public int compare(Video o1, Video o2) {
                long res = DataUtil.stringToNum(o1.getLike_num()) - DataUtil.stringToNum(o2.getLike_num());
                if(res == 0){
                    return 0;
                }
                return res < 0 ? 1 : -1;
            }
        });
        return list;
    }

    @Override
    public List<Video> getVideoRankByComment() {
        List<Video> list = videoDao.getUserAll();
        Collections.sort(list, new Comparator<Video>() {
            @Override
            public int compare(Video o1, Video o2) {
                long res = DataUtil.stringToNum(o1.getComment_num()) - DataUtil.stringToNum(o2.getComment_num());
                if(res == 0){
                    return 0;
                }
                return res < 0 ? 1 : -1;
            }
        });
        return list;
    }

    @Override
    public List<Video> getVideoRankByCollect() {
        List<Video> list = videoDao.getUserAll();
        Collections.sort(list, new Comparator<Video>() {
            @Override
            public int compare(Video o1, Video o2) {
                long res = DataUtil.stringToNum(o1.getCollect_num()) - DataUtil.stringToNum(o2.getCollect_num());
                if(res == 0){
                    return 0;
                }
                return res < 0 ? 1 : -1;
            }
        });
        return list;
    }



}
