package net.shan.practice.json;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类GameRuleDO.java的实现描述：TODO 类实现描述 游戏规则
 *
 * @author sam.js 2014年2月20日 下午7:52:28
 */
public class GameRuleDO {

    private Chance                chance;               // 游戏的可玩次数
    private Score                 score;                // 游戏的积分规则
    private OtherRule             other;                // 其他游戏规则
    private List<ProbabilityRule> probabilityRules;     // 游戏概率模型

    private int                   probabilityRuleSource; // 通过probabilityRules,将所有probability相加,得到source

    public List<ProbabilityRule> getProbabilityRules() {
        return probabilityRules;
    }

    public void setProbabilityRules(List<ProbabilityRule> probabilityRules) {
        this.probabilityRules = probabilityRules;
    }

    @JSONField(serialize = false)
    public int getProbabilityRuleSource() {
        return probabilityRuleSource;
    }

    @JSONField(deserialize = false)
    public void setProbabilityRuleSource(int probabilityRuleSource) {
        this.probabilityRuleSource = probabilityRuleSource;
    }

    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public OtherRule getOther() {
        return other;
    }

    public void setOther(OtherRule other) {
        this.other = other;
    }



    @JSONField(serialize = false)
    public GameRuleDO build(){
        ChanceDetail chanceDetail = new ChanceDetail();
        chanceDetail.setPc(3);
        chanceDetail.setMobile(3);
        chanceDetail.setAll(0);

        Chance chance = new Chance();
        chance.setEveryDay(chanceDetail);

        Factor mF = new Factor();
        mF.setBase(1.0);
        mF.setInviteFactor(2.0);
        mF.setInviteCondition(10);

        ScoreDetail m = new ScoreDetail();
        m.setDeduct(50);
        m.setReward(100);
        m.setMin(1);
        m.setMax(200);
        m.setFactor(mF);

        Factor pcF = new Factor();
        pcF.setBase(1.0);
        pcF.setInviteFactor(2.0);
        pcF.setInviteCondition(10);

        ScoreDetail pc = new ScoreDetail();
        pc.setDeduct(50);
        pc.setReward(100);
        pc.setMin(1);
        pc.setMax(200);
        pc.setFactor(pcF);

        Score score = new Score();
        score.setPc(pc);
        score.setMobile(m);

        Map<Integer, Integer> probability = new HashMap<Integer, Integer>();
        probability.put(50, 50);
        probability.put(100, 30);
        probability.put(200, 20);

        ProbabilityRule probability1 = new ProbabilityRule();
        probability1.setScore(50);
        probability1.setProbability(50);
        ProbabilityRule probability2 = new ProbabilityRule();
        probability2.setScore(100);
        probability2.setProbability(30);
        ProbabilityRule probability3 = new ProbabilityRule();
        probability3.setScore(200);
        probability3.setProbability(20);
        List<ProbabilityRule> probabilityRuleList = new ArrayList<ProbabilityRule>();
        probabilityRuleList.add(probability1);
        probabilityRuleList.add(probability2);
        probabilityRuleList.add(probability3);

        OtherRule otherRule = new OtherRule();
        otherRule.setMinTimeDuration(20);
        otherRule.setMaxTimeDuration(600);

        setChance(chance);
        setScore(score);
        setProbabilityRules(probabilityRuleList);
        setOther(otherRule);

        return this;
    }

    public static class Chance {

        private ChanceDetail everyDay = null; // 每日的可玩次数

        public ChanceDetail getEveryDay() {
            return everyDay;
        }

        public void setEveryDay(ChanceDetail everyDay) {
            this.everyDay = everyDay;
        }
    }

    public static class ChanceDetail {

        private Integer pc     = 0; // PC端可玩次数
        private Integer mobile = 0; // 移动端可玩次数
        private Integer all    = 0; // 全平台可玩次数

        public Integer getPc() {
            if (pc == null) {
                return 0;
            }
            return pc;
        }

        public void setPc(Integer pc) {
            this.pc = pc;
        }

        public Integer getMobile() {
            if (mobile == null) {
                return 0;
            }
            return mobile;
        }

        public void setMobile(Integer mobile) {
            this.mobile = mobile;
        }

        public Integer getAll() {
            if (all == null) {
                return 0;
            }
            return all;
        }

        public void setAll(Integer all) {
            this.all = all;
        }
    }

    public static class Score {

        private ScoreDetail pc     = null; // PC端积分规则
        private ScoreDetail mobile = null; // 移动端积分规则

        public ScoreDetail getPc() {
            return pc;
        }

        public void setPc(ScoreDetail pc) {
            this.pc = pc;
        }

        public ScoreDetail getMobile() {
            return mobile;
        }

        public void setMobile(ScoreDetail mobile) {
            this.mobile = mobile;
        }
    }

    public static class ScoreDetail {

        private Integer deduct = 0;   // 进入游戏需要消耗的积分
        private Integer reward = 0;   // 游戏奖励的基本积分，如果这个积分>0,以这个积分为准
        private Integer min    = 0;   // 奖励的最小积分
        private Integer max    = 0;   // 奖励的最大积分
        private Factor  factor = null; // 积分计算的系数

        public Integer getDeduct() {
            return deduct;
        }

        public void setDeduct(Integer deduct) {
            this.deduct = deduct;
        }

        public Integer getReward() {
            return reward;
        }

        public void setReward(Integer reward) {
            this.reward = reward;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Factor getFactor() {
            return factor;
        }

        public void setFactor(Factor factor) {
            this.factor = factor;
        }
    }

    public static class Factor {

        private Double  base            = 1.0; // 基本系数
        private Double  inviteFactor    = 2.0; // 邀请满足条件后的奖励系数
        private Integer inviteCondition = 10; // 需要满足的邀请条件

        public Double getBase() {
            return base;
        }

        public void setBase(Double base) {
            this.base = base;
        }

        public Double getInviteFactor() {
            return inviteFactor;
        }

        public void setInviteFactor(Double inviteFactor) {
            this.inviteFactor = inviteFactor;
        }

        public Integer getInviteCondition() {
            return inviteCondition;
        }

        public void setInviteCondition(Integer inviteCondition) {
            this.inviteCondition = inviteCondition;
        }
    }

    /**
     * 其他规则定义
     *
     * @author yuzhipeng 2014-2-22 下午11:23:39
     */
    public static class OtherRule {

        private Integer minTimeDuration; // 最短游戏时长

        private Integer maxTimeDuration; // 最长游戏时长

        public Integer getMinTimeDuration() {
            return minTimeDuration;
        }

        public void setMinTimeDuration(Integer minTimeDuration) {
            this.minTimeDuration = minTimeDuration;
        }

        public Integer getMaxTimeDuration() {
            return maxTimeDuration;
        }

        public void setMaxTimeDuration(Integer maxTimeDuration) {
            this.maxTimeDuration = maxTimeDuration;
        }
    }

    public static class ProbabilityRule {

        private Integer probability; // 命中的概率
        private Integer score;      // 积分

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Integer getProbability() {
            return probability;
        }

        public void setProbability(Integer probability) {
            this.probability = probability;
        }
    }
}



