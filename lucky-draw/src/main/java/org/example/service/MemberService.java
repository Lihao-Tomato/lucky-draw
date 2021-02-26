package org.example.service;

import org.example.mapper.MemberMapper;
import org.example.mapper.SettingMapper;
import org.example.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-02-02
 * Time: 12:09
 */
@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private SettingMapper settingMapper;

    public List<Member> queryByMemberId(Integer id) {
        return memberMapper.selectByMemberId(id);
    }

    public int add(Member member, Integer userId) {
        Integer settingId = settingMapper.queryIdByUserId(userId);
        member.setSettingId(settingId);
        return memberMapper.insertSelective(member);
    }

    public int update(Member member) {
        return memberMapper.updateByPrimaryKeySelective(member);
    }

    public int delete(Integer id) {
        return memberMapper.deleteByPrimaryKey(id);
    }
}
