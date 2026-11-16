package data_access;

public class TTCgtfsDataAccessObject {
}

/*
package data_access;

import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;
import com.google.transit.realtime.GtfsRealtime.VehiclePosition;
import entity.Route;
import entity.Vehicle; // Your own entity class

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TtcGtfsDataAccessObject implements VehicleDataAccessInterface {

    private static final String VEHICLES_URL = "https://bustime.ttc.ca/gtfsrt/vehicles";
    private final Map<String, Route> staticRouteMap = new HashMap<>();

    public TtcGtfsDataAccessObject() {
        loadStaticRoutes(); // Reads routes.txt from src/main/resources/ once on startup
    }

    // The parsing snippet goes inside a method like this:
    @Override
    public List<Vehicle> getLiveVehiclesForRoute(String targetRouteId) {
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            URL url = new URI(VEHICLES_URL).toURL();
            FeedMessage feed = FeedMessage.parseFrom(url.openStream());

            for (FeedEntity entity : feed.getEntityList()) {
                if (entity.hasVehicle()) {
                    VehiclePosition vp = entity.getVehicle();
                    String routeId = vp.getTrip().getRouteId();

                    if (routeId.equals(targetRouteId)) {
                        float lat = vp.getPosition().getLatitude();
                        float lon = vp.getPosition().getLongitude();
                        Route route = staticRouteMap.get(routeId);

                        vehicles.add(new Vehicle(route, lat, lon));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch TTC live vehicle feed", e);
        }
        return vehicles;
    }

    private void loadStaticRoutes() {
        // Parse src/main/resources/routes.txt into staticRouteMap
    }
}
 */