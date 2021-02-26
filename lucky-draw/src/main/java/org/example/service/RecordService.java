package org.example.service;

import org.example.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-02-02
 * Time: 12:08
 */
@Service
public class RecordService {

    @Autowired
    private RecordMapper recordMapper;

    public int add(List<Integer> memberIds, Integer awardId) {
        return recordMapper.batchInsert(memberIds,awardId);
    }

    public int deleteByMember(Integer memberId) {
        return recordMapper.deleteByMemberId(memberId);
    }

    public int deleteByAward(Integer awardId) {
        return recordMapper.deleteByAwardId(awardId);
    }

    public int deleteByUserId(Integer id) {
        return recordMapper.deleteByUserId(id);
    }
}
