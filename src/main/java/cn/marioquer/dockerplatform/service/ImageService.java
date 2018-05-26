package cn.marioquer.dockerplatform.service;

import cn.marioquer.dockerplatform.vo.ImageVO;

import java.util.List;

public interface ImageService {

    public List<ImageVO> getImageList(int serverId);

    public String pullImage(String rep, String tag);

    public String buildImageByDockerfile(String dockerfileUrl, int serverId, String imageName);
}
