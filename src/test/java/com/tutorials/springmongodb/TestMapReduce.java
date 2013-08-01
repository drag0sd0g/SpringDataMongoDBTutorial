package com.tutorials.springmongodb;

import com.tutorials.springmongodb.model.MRModel;
import com.tutorials.springmongodb.model.ValueObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;

import static junit.framework.Assert.assertEquals;

public class TestMapReduce extends AbstractMongoDBTest {

    public TestMapReduce() throws Exception {
        super();
    }

    @Before
    @Override
    public void setUp() throws Exception{
        super.setUp();
        mongoTemplate.insert(new MRModel(new String[] {"a", "b"} ));
        mongoTemplate.insert(new MRModel(new String[] {"b", "c"} ));
        mongoTemplate.insert(new MRModel(new String[] {"c", "d"} ));
    }

    @After
    @Override
    public void tearDown() throws Exception{
        super.tearDown();
        mongoTemplate.remove(new MRModel(new String[]{"a", "b"}));
        mongoTemplate.remove(new MRModel(new String[]{"b", "c"}));
        mongoTemplate.remove(new MRModel(new String[]{"c", "d"}));
    }

    @Test
    public void doTest() throws Exception{
        MapReduceResults<ValueObject> results = mongoTemplate.mapReduce("mapreduce",
                getClass().getClassLoader().getResource("map.js").toString(),
                getClass().getClassLoader().getResource("reduce.js").toString(),
                ValueObject.class);
        int count=0;
        for (ValueObject valueObject : results) {
            LOG.info(valueObject.toString());
            ++count;
        }

        LOG.info("Raw results: {}", results.getRawResults().toString());

        assertEquals(4, count);
    }
}
