package org.example.service;

import org.example.mapper.SettingMapper;
import org.example.model.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-02-02
 * Time: 12:08
 */
@Service
public class SettingService {

    @Autowired
    private SettingMapper settingMapper;

    public Setting queryByUserId(Integer id) {
        return settingMapper.selectByUserId(id);
    }

    public int update(Integer batchNumber, Integer userId) {
        return settingMapper.update(batchNumber, userId);
    }
}
