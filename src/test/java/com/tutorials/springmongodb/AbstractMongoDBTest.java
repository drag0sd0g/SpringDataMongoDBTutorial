package com.tutorials.springmongodb;

import com.mongodb.MongoClient;
import com.tutorials.springmongodb.model.User;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

public class AbstractMongoDBTest {

    protected final MongoTemplate mongoTemplate;

    private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private static final String DATABASE = "testdb";

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractMongoDBTest.class);

    @Before
    public void setUp() throws Exception{
        mongoTemplate.setApplicationContext(new GenericXmlApplicationContext("applicationContext.xml"));
        mongoTemplate.createCollection("testCollection");
        mongoTemplate.indexOps(User.class).dropAllIndexes();
        mongoTemplate.indexOps(User.class).resetIndexCache();

    }

    @After
    public void tearDown() throws Exception{
        mongoTemplate.dropCollection("testCollection");
    }

    public AbstractMongoDBTest() throws Exception{
        mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(HOST, PORT), DATABASE));
    }

}
