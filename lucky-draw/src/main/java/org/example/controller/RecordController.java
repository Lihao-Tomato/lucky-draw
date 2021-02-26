package org.example.controller;

import org.example.model.User;
import org.example.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LiHao
 * Date: 2021-02-02
 * Time: 12:12
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    //抽奖：某个奖项下抽奖，一次抽多个人（插入多条抽奖记录）
    @PostMapping("/add/{awardId}")
    private Object add(@RequestBody List<Integer> memberIds, @PathVariable Integer awardId){
        int n = recordService.add(memberIds, awardId);
        return null;
    }

    @GetMapping("/delete/member")
    private Object deleteByMember(Integer id){
        int n = recordService.deleteByMember(id);
        return null;
    }

    @GetMapping("/delete/award")
    private Object deleteByAward(Integer id){
        int n = recordService.deleteByAward(id);
        return null;
    }

    @GetMapping("/delete/setting")
    private Object deleteBySetting(HttpSession session){
        User user = (User) session.getAttribute("user");
        //获取userid--->关联setting_id---->关联member_id，award_id
        //---->删除关联record
        int n = recordService.deleteByUserId(user.getId());
        return null;
    }
}
