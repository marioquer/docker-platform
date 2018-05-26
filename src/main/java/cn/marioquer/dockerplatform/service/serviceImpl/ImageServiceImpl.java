package cn.marioquer.dockerplatform.service.serviceImpl;

import cn.marioquer.dockerplatform.entity.ServerEntity;
import cn.marioquer.dockerplatform.service.ImageService;
import cn.marioquer.dockerplatform.service.ServerService;
import cn.marioquer.dockerplatform.utils.SSHHelper;
import cn.marioquer.dockerplatform.utils.SSHInfo;
import cn.marioquer.dockerplatform.utils.enums.ErrorMessage;
import cn.marioquer.dockerplatform.utils.enums.ShellScript;
import cn.marioquer.dockerplatform.utils.enums.UtilString;
import cn.marioquer.dockerplatform.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ServerService serverService;

    @Override
    public List<ImageVO> getImageList(int serverId) {
        ServerEntity server = serverService.getServerEntity(serverId).get();
        SSHHelper sshHelper = new SSHHelper(new SSHInfo(server.getIp(), server.getUname(), server.getPassword()));
        sshHelper.connect();
        String[] imageInfo = sshHelper.exec(ShellScript.IMAGE_LS).split(UtilString.REG_PATTERN_RETURN);
        List<ImageVO> imageList = new ArrayList<>();
        for (String line : imageInfo) {
            String[] infoDetail = line.split(UtilString.REG_PATTERN_N_SPACE);
            if (line.startsWith("REPOSITORY")) {
                continue;
            } else if (infoDetail.length == 5) {
                ImageVO imageVO = new ImageVO();
                imageVO.setRepository(infoDetail[0]);
                imageVO.setTag(infoDetail[1]);
                imageVO.setId(infoDetail[2]);
                imageVO.setCreated(infoDetail[3]);
                imageVO.setSize(infoDetail[4]);
                imageList.add(imageVO);
            }
        }
        sshHelper.close();
        return imageList;
    }

    /**
     * 从HUB拉取镜像
     *
     * @param rep
     * @param tag
     * @return
     */
    @Override
    public String pullImage(String rep, String tag) {
        return null;
    }

    /**
     * 从本地dockerfile构建image
     *
     * @param dockerfileUrl dockerfile所在目录
     * @param serverId
     * @return
     */
    @Override
    public String buildImageByDockerfile(String dockerfileUrl, int serverId, String imageName) {
        ServerEntity server = serverService.getServerEntity(serverId).get();
        SSHHelper sshHelper = new SSHHelper(new SSHInfo(server.getIp(), server.getUname(), server.getPassword()));
        sshHelper.connect();
        if (dockerfileUrl.endsWith("Dockerfile")) {
            dockerfileUrl = dockerfileUrl.substring(0, dockerfileUrl.length() - 11);
        }
        //进入Dockerfile所在目录
        sshHelper.exec("cd " + dockerfileUrl);
        //执行build
        String script = ShellScript.IMAGE_BUILD + " -t " + imageName + " .";
        String[] imageInfo = sshHelper.exec(script).split(UtilString.REG_PATTERN_RETURN);
        if (imageInfo[imageInfo.length - 1].contains("Successfully tagged") && imageInfo[imageInfo.length - 2].contains("Successfully build")) {
            return ErrorMessage.SUCCESS;
        } else if (imageInfo[0].contains("unable to prepare context:")) {
            return ErrorMessage.NOT_FOUND;
        } else {
            return ErrorMessage.FAIL;
        }
    }
}
