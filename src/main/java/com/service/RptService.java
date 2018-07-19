package com.service;

import com.mapper.Mapper114;
import com.mapper.Mapper21;
import com.pojo.SendingVo;
import com.pojo.UserDayCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 15:23 2018/7/5
 */

@Service
public class RptService {


    @Autowired
    private Mapper21 mapper21;

    @Autowired
    private Mapper114 mapper114;

    public SendingVo findOne(String hisid) {
        return mapper21.findOne(hisid);
    }

    public int findHistory(long id) {
        return mapper21.findHistory(id);
    }

    public List<SendingVo> getHistory(int minHisid, int maxHisid) {
        return mapper114.getHistory(minHisid, maxHisid);
    }

    public void saveHistory(SendingVo vo) {
        mapper21.saveHistory(vo);
    }

    public void saveRpt(SendingVo vo) {
        mapper21.saveRpt(vo);
    }

    public SendingVo getRptcodeByRpt(long id) {
        return mapper114.getRptcodeByRpt(id);
    }

    public SendingVo getRptcodeByHistory(long id) {
        return mapper114.getRptcodeByHistory(id);
    }

    public SendingVo getRptContent(int pid) {
        return mapper114.getRptContent(pid);
    }

    public void batchInsertHistory(List<SendingVo> list) {
        mapper21.batchInsertHistory(list);
    }

    public void batchInsertRpt(List<SendingVo> list) {
        mapper21.batchInsertRpt(list);
    }

    public List<UserDayCount> getGhUserDayCount() {
        return mapper21.getGhUserDayCount();
    }

    public List<SendingVo> findHistory114(Long i) {
        return mapper114.findHistory114(i);
    }

    public SendingVo findSendHistory21(long hisid) {
        return mapper21.findSendHistory21(hisid);
    }

    public void updateHistory114(SendingVo vo) {
        mapper114.updateHistory114(vo);
    }

    public List<SendingVo> findWz() {
        return mapper114.findWz();
    }

    public SendingVo findWz21(SendingVo vo) {
        return mapper21.findWz21(vo);
    }

    public void updateGhHistory(SendingVo vo) {
        mapper114.updateGhHistory(vo);
    }

    public Integer findGhSucc(String uid) {
        return mapper114.findGhSucc(uid);
    }

    public Integer findGhFail(String uid) {
        return mapper114.findGhFail(uid);
    }

    public Integer findGhTotal(String uid) {
        return mapper114.findGhTotal(uid);
    }

    public Integer findGhSubFail(String uid) {
        return mapper114.findGhSubFail(uid);
    }

    public List<SendingVo> findFail() {
        return mapper114.findFail();
    }
}
