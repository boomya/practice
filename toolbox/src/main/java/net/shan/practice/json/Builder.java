package net.shan.practice.json;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sam.js on 14-2-20.
 */
public class Builder {
    public static void main(String[] args){
        new Builder().test1();
    }

    private void test2(){
        Map<Game.Chance, Integer> map = new HashMap<Game.Chance, Integer>();
        map.put(Game.Chance.PC, 100);
        map.put(Game.Chance.ALL, 200);
        map.put(Game.Chance.MOBILE, 400);
        String jstr = JSON.toJSONString(map);

    }
    private void test1(){
        GameRuleDO rule = new GameRuleDO();
        rule.build();


        String jstr = JSON.toJSONString(rule);
//        String jstr = "{\"score\":{\"mobile\":{\"deduct\":50,\"factor\":{},\"max\":200,\"min\":1,\"reward\":100},\"pc\":{\"deduct\":50,\"factor\":{},\"max\":200,\"min\":1,\"reward\":100}}}";
//        String jstr = "{\"chance\":{\"everyDay\":{\"all\":0,\"mobile\":3,\"pc\":3}}";
//        jstr = "{\"chance\":{\"everyDay\":{\"all\":0,\"mobile\":3,\"pc\":3}},\"other\":{\"maxTimeDuration\":600,\"minTimeDuration\":20},\"probabilityRules\":[{\"probability\":50,\"score\":50},{\"probability\":30,\"score\":100},{\"probability\":20,\"score\":200}],\"score\":{\"mobile\":{\"deduct\":50,\"factor\":{\"base\":1,\"inviteCondition\":10,\"inviteFactor\":2},\"max\":200,\"min\":1,\"reward\":100},\"pc\":{\"deduct\":50,\"factor\":{\"base\":1,\"inviteCondition\":10,\"inviteFactor\":2},\"max\":200,\"min\":1,\"reward\":100}}}";
        System.out.println(jstr);
        GameRuleDO tmp = JSON.parseObject(jstr, GameRuleDO.class);

//        String everyDayStr = JSON.toJSONString(everyDay);
//        System.out.println(everyDayStr);
//        EveryDayChance tmp = JSON.parseObject(everyDayStr, EveryDayChance.class);

//        Group group = new Group();
//        group.setId(0L);
//        group.setName("admin");
//
//        User guestUser = new User();
//        guestUser.setId(2L);
//        guestUser.setName("guest");
//
//        User rootUser = new User();
//        rootUser.setId(3L);
//        rootUser.setName("root");
//
//        group.getUsers().add(guestUser);
//        group.getUsers().add(rootUser);
//
//        String jsonString = JSON.toJSONString(group);
//
//        System.out.println(jsonString);
//        Group group2 = JSON.parseObject(jsonString, Group.class);


    }

    public class User {
        private Long id;
        private String name;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
    public class Group {
        private Long id;
        private String name;
        private List<User> users = new ArrayList<User>();

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public List<User> getUsers() { return users; }
        public void setUsers(List<User> users) { this.users = users; }
    }
}
