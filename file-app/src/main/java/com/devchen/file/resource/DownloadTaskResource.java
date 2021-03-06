package com.devchen.file.resource;


import com.devchen.file.resource.entity.UnionResponse;
import com.devchen.file.service.DownloadService;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/download-task-resource")
public class DownloadTaskResource {

    @Resource
    private DownloadService downloadService;


    private final static Logger logger = Logger.getLogger(DownloadTaskResource.class);

    @RequestMapping(value = "/submit-magnet-download-task-with-file-name", method = RequestMethod.POST)
    public UnionResponse submitMagnetDownLoadTaskWithFileName(@RequestParam("magentAddress") String magnet, @RequestParam("saveDir") String saveDir, @RequestParam("fileName") String fileName) {
        logger.info(String.format("get magnet download task %s %s %s",magnet, saveDir,fileName));
        if(StringUtils.isEmpty(magnet) || StringUtils.isEmpty(saveDir)) {
            UnionResponse response = new UnionResponse();
            response.setResCode("0000");
            response.setResMsg("fail");
            return response;
        }
        downloadService.acceptMagnetDownloadTask(magnet,saveDir,fileName);
        UnionResponse response = new UnionResponse();
        response.setResCode("0000");
        response.setResMsg("success");
        return response;
    }


    @RequestMapping(value = "/submit-magnet-download-task", method = RequestMethod.POST)
    public UnionResponse submitMagnetDownLoadTask(@RequestParam("magentAddress") String magnet, @RequestParam("saveDir") String saveDir) {
        logger.info(String.format("get magnet download task %s %s",magnet, saveDir));
        if(StringUtils.isEmpty(magnet) || StringUtils.isEmpty(saveDir)) {
            UnionResponse response = new UnionResponse();
            response.setResCode("0000");
            response.setResMsg("fail");
            return response;
        }
        downloadService.acceptMagnetDownloadTask(magnet,saveDir,saveDir+magnet);
        UnionResponse response = new UnionResponse();
        response.setResCode("0000");
        response.setResMsg("success");
        return response;
    }

    @RequestMapping(value = "/submit-remote-torrent-download-task", method = RequestMethod.POST)
    public UnionResponse submitRemoteTorrentDownLoadTask(@RequestParam("remoteTorrent") String remoteTorrent, @RequestParam("saveDir") String saveDir, @RequestParam("fileName") String fileName) {
        logger.info(String.format("get remote torrent download task %s %s",remoteTorrent, saveDir));
        if(StringUtils.isEmpty(remoteTorrent) || StringUtils.isEmpty(saveDir)) {
            UnionResponse response = new UnionResponse();
            response.setResCode("0000");
            response.setResMsg("fail");
            return response;
        }
        downloadService.acceptRemoteTorrentDownloadTask(remoteTorrent, saveDir, fileName);
        UnionResponse response = new UnionResponse();
        response.setResCode("0000");
        response.setResMsg("success");
        return response;
    }
}
