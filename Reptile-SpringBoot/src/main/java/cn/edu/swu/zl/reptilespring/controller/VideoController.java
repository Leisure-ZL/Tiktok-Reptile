package cn.edu.swu.zl.reptilespring.controller;

import cn.edu.swu.zl.reptilespring.base.BaseResponse;
import cn.edu.swu.zl.reptilespring.entity.Video;
import cn.edu.swu.zl.reptilespring.entity.VideoRaw;
import cn.edu.swu.zl.reptilespring.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public BaseResponse<List<Video>> getLikeInc(@RequestParam("size") int size){
        BaseResponse<List<Video>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(videoService.getVideoByLikeInc(size));

        return resp;
    }

    @ResponseBody
    @RequestMapping("/video/inc_comment")
    public BaseResponse<List<Video>> getCommentInc(@RequestParam("size") int size){
        BaseResponse<List<Video>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(videoService.getVideoByCommentInc(size));

        return resp;
    }

    @ResponseBody
    @RequestMapping("/video/inc_collect")
    public BaseResponse<List<Video>> getCollectInc(@RequestParam("size") int size){
        BaseResponse<List<Video>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(videoService.getVideoByCollectInc(size));

        return resp;
    }

    @ResponseBody
    @RequestMapping("/video/like")
    public BaseResponse<List<Video>> getLike(@RequestParam("size") int size){
        BaseResponse<List<Video>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(videoService.getVideoByLike(size));

        return resp;
    }

    @ResponseBody
    @RequestMapping("/video/comment")
    public BaseResponse<List<Video>> getComment(@RequestParam("size") int size){
        BaseResponse<List<Video>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(videoService.getVideoByComment(size));

        return resp;
    }

    @ResponseBody
    @RequestMapping("/video/collect")
    public BaseResponse<List<Video>> getCollect(@RequestParam("size") int size){
        BaseResponse<List<Video>> resp = new BaseResponse<>();
        resp.setCode(200);
        resp.setMsg("OK");
        resp.setData(videoService.getVideoByCollect(size));

        return resp;
    }


}
