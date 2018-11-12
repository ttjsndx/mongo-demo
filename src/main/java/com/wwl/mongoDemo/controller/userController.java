package com.wwl.mongoDemo.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import org.bson.BsonDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.bson.conversions.Bson;

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
//        CommandResult commandResult =
//                mongoTemplate.getDb().doEval("db.user.aggregate([{$group:{_id:'$name',count:{$sum:'$age'}}}])");
        BasicDBObject bson = new BasicDBObject();
        bson.put("$eval","db.user.aggregate([{$group:{_id:'$name',count:{$sum:'$age'}}}])");
        Object object =  mongoTemplate.getDb().runCommand(bson);

        System.out.println(object);
        return "success";
    }
}
