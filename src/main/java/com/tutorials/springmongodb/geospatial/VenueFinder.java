package com.tutorials.springmongodb.geospatial;

import com.tutorials.springmongodb.model.Venue;

import java.util.List;

public interface VenueFinder {

    List<Venue> findVenuesWithinSphere(double x, double y, double maxDistance);
    List<Venue> findVenuesWithinBox(double lowerLeftX, double lowerLeftY, double upperRightX, double upperRightY);
    List<Venue> findVenuesNearPoint(double x, double y);
    List<Venue> findVenuesNearPointUsingSphericalCoordinates(double x, double y, double maxDistance);
}
