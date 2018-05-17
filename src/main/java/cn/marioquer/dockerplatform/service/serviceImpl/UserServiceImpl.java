package cn.marioquer.dockerplatform.service.serviceImpl;

import cn.marioquer.dockerplatform.utils.enums.ErrorMessage;
import cn.marioquer.dockerplatform.vo.UserLoginVO;
import cn.marioquer.dockerplatform.dao.UserDao;
import cn.marioquer.dockerplatform.entity.UserEntity;
import cn.marioquer.dockerplatform.service.UserService;
import cn.marioquer.dockerplatform.utils.PasswordEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserLoginVO login(String username, String password) {
        UserEntity user = userDao.findByUsername(username);
        UserLoginVO userLoginVO = new UserLoginVO();
        if (user == null) {
            userLoginVO.setLogin_status(ErrorMessage.NOT_FOUND);
        } else {
            String salt = user.getSalt();
            String encryptedPassword = user.getPassword();
            boolean result = false;
            try {
                result = PasswordEncryption.authenticate(password, encryptedPassword, salt);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            if (result) {
                userLoginVO.setId(user.getId());
                userLoginVO.setUsername(user.getUsername());
                userLoginVO.setLogin_status(ErrorMessage.SUCCESS);
            } else {
                userLoginVO.setLogin_status(ErrorMessage.WRG_PSW);
            }
        }
        return userLoginVO;
    }

    @Override
    public String signUp(String username, String password) {
        if (userDao.existsByUsername(username)) {
            return ErrorMessage.EXISTS;
        } else {
            UserEntity user = new UserEntity();
            user.setUsername(username);
            try {
                String salt = PasswordEncryption.generateSalt();
                String encryptedPassword = PasswordEncryption.getEncryptedPassword(password, salt);
                user.setSalt(salt);
                user.setPassword(encryptedPassword);
                userDao.saveAndFlush(user);
                return ErrorMessage.SUCCESS;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return ErrorMessage.FAIL;
    }
}
