package cn.marioquer.dockerplatform.controller;

import cn.marioquer.dockerplatform.service.ImageService;
import cn.marioquer.dockerplatform.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImageController {

    @Autowired
    ImageService imageService;

    /**
     * @param serverId
     * @return ImageVO的List
     */
    @PostMapping("getImageList")
    public List<ImageVO> getImageList(int serverId) {
        return imageService.getImageList(serverId);
    }

    /**
     * 从Dockerfile路径构建镜像
     * @param dockerfileUrl
     * @param serverId
     * @return
     */
    @PostMapping("buildImageByDockerfile")
    public String buildImageByDockerfile(String dockerfileUrl, int serverId) {
        return imageService.buildImageByDockerfile(dockerfileUrl, serverId);
    }
}
