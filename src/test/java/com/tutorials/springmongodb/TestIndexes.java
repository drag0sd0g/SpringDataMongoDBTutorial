package com.tutorials.springmongodb;

import com.tutorials.springmongodb.model.User;
import org.junit.Test;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.query.Order;

import java.util.List;

import static com.mongodb.util.MyAsserts.assertEquals;

public class TestIndexes extends AbstractMongoDBTest {

    public TestIndexes() throws Exception {
        super();
    }

    @Test
    public void testCreatingIndex() throws Exception{
        mongoTemplate.indexOps(User.class).ensureIndex(new Index().on("name", Order.ASCENDING));

        List<IndexInfo> indexInfoList = mongoTemplate.indexOps(User.class).getIndexInfo();

        for(IndexInfo indexInfo : indexInfoList){
            LOG.info(indexInfo.getName());
        }

        assertEquals(indexInfoList.size(), 2);
    }

    @Test
    public void testDroppingIndexes() throws Exception{
        mongoTemplate.indexOps(User.class).ensureIndex(new Index().on("username", Order.ASCENDING));
        mongoTemplate.indexOps(User.class).ensureIndex(new Index().on("password", Order.ASCENDING));

        List<IndexInfo> indexInfoList = mongoTemplate.indexOps(User.class).getIndexInfo();
        assertEquals(indexInfoList.size(), 3);

        mongoTemplate.indexOps(User.class).dropAllIndexes();

        indexInfoList = mongoTemplate.indexOps(User.class).getIndexInfo();

        assertEquals(indexInfoList.size(), 1);
    }


}
