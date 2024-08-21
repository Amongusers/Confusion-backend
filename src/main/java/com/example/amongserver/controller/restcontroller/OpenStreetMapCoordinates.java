package com.example.amongserver.controller.restcontroller;


import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.web.bind.annotation.*;

import com.example.amongserver.dto.GameCoordinatesDto;
import com.example.amongserver.dto.OSMRequest;
import com.example.amongserver.dto.OSMResponse;

import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.map.data.BoundingBox;
import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Relation;
import de.westnordost.osmapi.map.data.Way;
import de.westnordost.osmapi.map.handler.MapDataHandler;
import de.westnordost.osmapi.overpass.OverpassMapDataApi;
/*
Rest контроллер
Получание всех координатат на клиент в начале игры
*/
@RestController
@RequestMapping("/placeShip")
@RequiredArgsConstructor
public class OpenStreetMapCoordinates {

    // a piece of park "50 years of October"
    private static String BBOX = "[bbox:55.684815,37.498758,55.686050,37.501324];";
    private static String WAY_FOOTWAY = "way[highway=footway];";
    private static String WAY_DESIGNATED = "way[foot=designated];";
    private static String WAY_PATH = "way[highway=path];";
    private static String WAY_SIDEWALK = "way[highway=sidewalk];";

    @PostMapping()
    public OSMResponse getMarksInBBox(@RequestBody OSMRequest osmRequest){
        
        // Setup bbox
        String bbox = GetBboxString(osmRequest);

        // Get nodes from OSM
        OsmConnection connection = new OsmConnection("https://maps.mail.ru/osm/tools/overpass/api/", "my user agent");
	    OverpassMapDataApi overpass = new OverpassMapDataApi(connection);

        String queryString = bbox + "((" + WAY_FOOTWAY + WAY_DESIGNATED + WAY_PATH + WAY_SIDEWALK + ");>;);out body;";
        System.out.println("queryString = " + queryString);

        final List<Node> listOfNodes = new ArrayList<Node>();
        overpass.queryElements(queryString, new MapDataHandler()
		{
			@Override 
            public void handle(BoundingBox bounds) {

            }
			@Override 
            public void handle(Way way) {
				
			}
            @Override
            public void handle(Node node) {
                listOfNodes.add(node);
            }
            @Override
            public void handle(Relation relation) {
                
            }
		});
		
        System.out.println("OpenStreetMapController | Nodes = " + listOfNodes.size());

        // Generate random tasks list
        List<GameCoordinatesDto> tasksList = new ArrayList<>();
        int maxTasks = 10;
        double MIN_DISTANCE_BETWEEN_TASKS_METRES = 50.0;

        List<GameCoordinatesDto> tasksShortList = new ArrayList<>();
        List<GameCoordinatesDto> tasksMediumList = new ArrayList<>();
        List<GameCoordinatesDto> tasksLongList = new ArrayList<>();

        while (listOfNodes.size() > 0 && tasksList.size() < maxTasks) {
            int randomIndex = new Random().nextInt(listOfNodes.size());

            LatLon nodePos = listOfNodes.get(randomIndex).getPosition();

            double nodeDistanceFromSpaceShip = calculateDistanceMetres(
                osmRequest.getLatitudeSpaceShip(),
                osmRequest.getLongitudeSpaceShip(), 
                nodePos.getLatitude(),
                nodePos.getLongitude());

            if(nodeDistanceFromSpaceShip > osmRequest.getRadius()
            ) {
                listOfNodes.remove(randomIndex);
                continue;
            }
            

            boolean isSmallDistance = false;
            for (GameCoordinatesDto task : tasksList) {
                if(calculateDistanceMetres(
                    task.getLatitude(), 
                    task.getLongitude(), 
                    nodePos.getLatitude(), 
                    nodePos.getLongitude()) < MIN_DISTANCE_BETWEEN_TASKS_METRES
                ) {
                    listOfNodes.remove(randomIndex);
                    isSmallDistance = true;
                    break;
                }
            }
            if(isSmallDistance){
                continue;
            }

            GameCoordinatesDto task = new GameCoordinatesDto(
                nodePos.getLatitude(), 
                nodePos.getLongitude(), 
                false);

            // Возьмем за радиус = 600 м, тогда:
            // - "short" дистанция = от 0 до 200 м
            // - "medium" дистанция = от 200 до 400 м
            // - "long" дистанция = от 401 до 600 м
            if(nodeDistanceFromSpaceShip <= osmRequest.getRadius() / 3){
                tasksShortList.add(task);
            }
            else if(nodeDistanceFromSpaceShip <= osmRequest.getRadius() * 2 / 3){
                tasksMediumList.add(task);
            }
            else{
                tasksLongList.add(task);
            }


            tasksList.add(task);
        }

        System.out.println("OpenStreetMapController | Short tasks = " + tasksShortList.size());
        System.out.println("OpenStreetMapController | Medium tasks = " + tasksMediumList.size());
        System.out.println("OpenStreetMapController | Long tasks = " + tasksLongList.size());

        return new OSMResponse(
            tasksList.toArray(), 
            new int[]{tasksShortList.size(), tasksMediumList.size(), tasksLongList.size()},
            tasksList.size() == 10
        );
    }

    private String GetBboxString(OSMRequest osmRequest){
        double ONE_DEGREE_M = 111320;

        double deltaLatitude = osmRequest.getRadius() / ONE_DEGREE_M;
        double deltaLongitude = osmRequest.getRadius() / (ONE_DEGREE_M * Math.cos(Math.PI * osmRequest.getLatitudeSpaceShip() / 180));

        double south = osmRequest.getLatitudeSpaceShip() - deltaLatitude;
        double west = osmRequest.getLongitudeSpaceShip() - deltaLongitude;
        double north = osmRequest.getLatitudeSpaceShip() + deltaLatitude;
        double east = osmRequest.getLongitudeSpaceShip() + deltaLongitude;

        return "[bbox:" + String.format(Locale.US, "%.6f", south) + ","
         + String.format(Locale.US, "%.6f", west) + ","
          + String.format(Locale.US, "%.6f", north) + ","
           + String.format(Locale.US, "%.6f", east) + "];";
    }

    double calculateDistanceMetres(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);
    
        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        double distance = Math.sqrt(x * x + y * y) * 6371;
    
        return distance*1000;
    }
}
