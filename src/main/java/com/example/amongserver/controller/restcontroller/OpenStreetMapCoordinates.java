package com.example.amongserver.controller.restcontroller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.*;

import com.example.amongserver.dto.GameCoordinatesDto;
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
@RequestMapping("/osm")
@RequiredArgsConstructor
public class OpenStreetMapCoordinates {

    // a piece of park "50 years of October"
    private static String BBOX = "[bbox:55.684815,37.498758,55.686050,37.501324];";

    @GetMapping()
    public List<GameCoordinatesDto> getMarksInBox(){
        // Get nodes from OSM
        OsmConnection connection = new OsmConnection("https://maps.mail.ru/osm/tools/overpass/api/", "my user agent"); // 
	    OverpassMapDataApi overpass = new OverpassMapDataApi(connection);

        final List<Node> listOfNodes = new ArrayList<Node>();
        overpass.queryElements(BBOX + "(way[highway=footway];>;);out body;", new MapDataHandler()
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
		
        System.out.println("nodes = " + listOfNodes.size());

        // Generate random tasks list
        List<GameCoordinatesDto> tasksList = new ArrayList<>();
        int maxTasks = 10;

        while (tasksList.size() < maxTasks) {
            int randomIndex = new Random().nextInt(listOfNodes.size());
            System.out.println("randomIndex = " + randomIndex);

            LatLon nodePos = listOfNodes.get(randomIndex).getPosition();
            tasksList.add(new GameCoordinatesDto(
                nodePos.getLatitude(), 
                nodePos.getLongitude(), 
                false));

            listOfNodes.remove(randomIndex);
        }

        return tasksList;
    }
}
