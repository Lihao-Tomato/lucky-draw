package org.example.service;

import org.example.exception.AppException;
import org.example.mapper.SettingMapper;
import org.example.mapper.UserMapper;
import org.example.model.Setting;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-02-02
 * Time: 12:08
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SettingMapper settingMapper;

    private static final DateFormat DF = new SimpleDateFormat("yyyyMMdd");

    @Value("${user.head.local-path}")
    private String headLocalPath;

    @Value("${user.head.remote-path}")
    private String headRemotePath;

    public String saveHead(MultipartFile headFile) {
        Date now = new Date();
        String dirUri = "/"+DF.format(now);//格式：20210203
        //图片存放文件夹
        File dir = new File(headLocalPath+dirUri);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //保存在本地以天为单位的文件夹，保证文件唯一：随机字符串作为文件名，后缀保留
        //suffix:获取后缀
        String suffix = headFile.getOriginalFilename().
                substring(headFile.getOriginalFilename().lastIndexOf("."));
        String headName = UUID.randomUUID().toString()+suffix;
        String uri = dirUri+"/"+headName;
        try {
            headFile.transferTo(new File(headLocalPath+uri));
        } catch (IOException e) {
            throw new AppException("REG001","上传用户头像出错");
        }
        return headLocalPath+uri;
    }

    //事务处理：多个更新，有时候有部分查询+更新也需要
    @Transactional//可以手动指定隔离级别和传播特性
    public void register(User user) {
        //插入user数据
        //插入后自增主键后会按照useGeneratedKeys=true设置到对象属性
        int n = userMapper.insertSelective(user);

        //插入setting数据，登录后进入设置页面，添加奖项和抽奖人员的时候需要setting_id字段
        Setting setting = new Setting();
        setting.setUserId(user.getId());
        setting.setBatchNumber(8);//每次抽奖的数量：设置一个默认值（业务决定）
        settingMapper.insertSelective(setting);

    }

    public User queryByUsername(String username) {

        return userMapper.selectByUsername(username);
    }
}
