package com.wwl.mongoDemo.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ScriptOperations;
import org.springframework.data.mongodb.core.script.ExecutableMongoScript;
import org.springframework.data.mongodb.core.script.NamedMongoScript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class userController {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * mongo测试
     * @return
     */
    @RequestMapping(value = "/test")
    public String test(){
        String command = "db.user.aggregate([{$group:{_id:'$name',count:{$sum:'$age'}}}])";
        BasicDBObject bson = new BasicDBObject();
        bson.put("$eval",command);

        Object object1 = mongoTemplate.getDb().doEval(command);
        Object object2 =  mongoTemplate.getDb().command(bson);
        Object object3 = mongoTemplate.getCollection("$cmd").findOne(bson);

        ScriptOperations operations = mongoTemplate.scriptOps();
        ExecutableMongoScript script = new ExecutableMongoScript(command);
        Object object4 = operations.execute(script);

        /**
         * call是调用system.js集合中方法的方法，传入参数是sysytem.js表中数据的主键值，
         * 可在mongo shell中天插入或者使用如下代码插入。
         * 插入一次后可直接使用
         */
//        ScriptOperations operations = mongoTemplate.scriptOps();
//        String command = "function(){return db.user.aggregate([{$group:{_id:'$name',count:{$sum:'$age'}}}])}";
//        NamedMongoScript namedMongoScript = new NamedMongoScript("user2",script);
//        operations.register(namedMongoScript);
        Object object5 = operations.call("user2");

        return "success";
    }
}
