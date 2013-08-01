package com.tutorials.springmongodb.geospatial;

import com.tutorials.springmongodb.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.geo.Box;
import org.springframework.data.mongodb.core.geo.Circle;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("venueFinder")
public class VenueFinderImpl implements VenueFinder {

    private final MongoOperations template;

    @Autowired
    public VenueFinderImpl(@Qualifier("mongoTemplate") MongoOperations template){
        this.template = template;
    }

    @Override
    public List<Venue> findVenuesWithinSphere(double x, double y, double maxDistance) {
        Circle circle = new Circle(x, y, maxDistance);
        return template.find(new Query(Criteria.where("location").withinSphere(circle)), Venue.class);
    }

    @Override
    public List<Venue> findVenuesWithinBox(double lowerLeftX, double lowerLeftY, double upperRightX, double upperRightY) {
        Box box = new Box(new Point(lowerLeftX, lowerLeftY), new Point(upperRightX, upperRightY));
        return template.find(new Query(Criteria.where("location").within(box)), Venue.class);
    }

    @Override
    public List<Venue> findVenuesNearPoint(double x, double y) {
       return template.find(new Query(Criteria.where("location").near(new Point(x,y))), Venue.class);
    }

    @Override
    public List<Venue> findVenuesNearPointUsingSphericalCoordinates(double x, double y, double maxDistance) {
        return template.find(new Query(Criteria.where("location").nearSphere(new Point(x,y)).maxDistance(maxDistance)), Venue.class);
    }
}
