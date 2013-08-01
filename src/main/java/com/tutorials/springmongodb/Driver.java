package com.tutorials.springmongodb;

import com.tutorials.springmongodb.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class Driver {

    public static void main(String[] args){

        ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext.xml");
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        // query to search user
        Query searchUserQuery = new Query(Criteria.where("username").is("dragos"));


        testSaveAndGet(mongoOperation, searchUserQuery);
        testUpdateAndGet(mongoOperation, searchUserQuery);
        testDeleteAndGet(mongoOperation, searchUserQuery);
    }

    private static void testSaveAndGet(MongoOperations mongoOperation, Query searchUserQuery){
        User user = new User("dragos", "admin");

        // save
        mongoOperation.save(user);

        // now user object got the created id.
        System.out.println("1. user : " + user);

        // find the saved user again.
        User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
        System.out.println("2. find - savedUser : " + savedUser);

    }

    private static void testUpdateAndGet(MongoOperations mongoOperation, Query searchUserQuery){
        // update password
        mongoOperation.updateFirst(searchUserQuery,
                Update.update("password", "new password"),User.class);

        // find the updated user object
        User updatedUser = mongoOperation.findOne(searchUserQuery, User.class);

        System.out.println("3. updatedUser : " + updatedUser);
    }

    private static void testDeleteAndGet(MongoOperations mongoOperation, Query searchUserQuery){
        // delete
        mongoOperation.remove(searchUserQuery, User.class);

        // List, it should be empty now.
        List<User> listUser = mongoOperation.findAll(User.class);
        System.out.println("4. Number of user = " + listUser.size());

    }
}
