package cn.edu.swu.zl.reptilespring.controller;

import cn.edu.swu.zl.reptilespring.base.BaseResponse;
import cn.edu.swu.zl.reptilespring.entity.Video;
import cn.edu.swu.zl.reptilespring.entity.VideoRaw;
import cn.edu.swu.zl.reptilespring.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ResponseBody
    @RequestMapping("/video")
    public BaseResponse<List<VideoRaw>> getAll(){
        BaseResponse<List<VideoRaw>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(videoService.getAll());

        return resp;
    }


    @ResponseBody
    @RequestMapping("/video/inc_like")
    public BaseResponse<List<Video>> getLikeInc(){
        BaseResponse<List<Video>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(videoService.getVideoRankByIncremental());

        return resp;
    }

}
