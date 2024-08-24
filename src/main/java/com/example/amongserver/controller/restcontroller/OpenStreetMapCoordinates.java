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

    private static String WAY_FOOTWAY = "way[highway=footway];";
    private static String WAY_DESIGNATED = "way[foot=designated];";
    private static String WAY_PATH = "way[highway=path];";
    private static String WAY_SIDEWALK = "way[highway=sidewalk];";

    private static int TASKS_COUNT = 10;
    private static double MIN_DISTANCE_BETWEEN_TASKS_METRES = 50.0;

    // Нужный процент меток разного расстояния
    private static float SHORT_TASKS_PERCENT = 0.3f;
    private static float MEDUIM_TASKS_PERCENT = 0.5f;
    private static float LONG_TASKS_PERCENT = 0.2f;

    @PostMapping()
    public OSMResponse getMarksInBBox(@RequestBody OSMRequest osmRequest) {

        // Генерируем прямоугольник, из которого будем брать точки
        String bbox = getBboxString(osmRequest);

        // Отправляем запрос и получаем точки от OSM
        List<List<Node>> listOfNodes = getNodes(
                bbox,
                osmRequest.getLatitudeSpaceShip(),
                osmRequest.getLongitudeSpaceShip(),
                osmRequest.getRadius());

        // Генерируем метки
        int shortTasksCount = (int) (TASKS_COUNT * SHORT_TASKS_PERCENT);
        int mediumTasksCount = (int) (TASKS_COUNT * MEDUIM_TASKS_PERCENT);
        int longTasksCount = (int) (TASKS_COUNT * LONG_TASKS_PERCENT);
        List<List<GameCoordinatesDto>> listOfTasks = generateAllTasks(
                listOfNodes,
                osmRequest,
                shortTasksCount,
                mediumTasksCount,
                longTasksCount);

        System.out.println("OpenStreetMapController | -------------------------");
        System.out.println("OpenStreetMapController | Short tasks = " + listOfTasks.get(0).size());
        System.out.println("OpenStreetMapController | Medium tasks = " + listOfTasks.get(1).size());
        System.out.println("OpenStreetMapController | Long tasks = " + listOfTasks.get(2).size());

        // Отдаем клиенту
        //
        // надо разделить на два:
        // - один будет выдавать список меток заданий при старте игры
        // - второй будет выдавать true/false, если можно поставить корабль на этом
        // месте
        return new OSMResponse(
                listOfTasks.toArray(),
                listOfTasks.get(0).size() == shortTasksCount
                        && listOfTasks.get(1).size() == mediumTasksCount
                        && listOfTasks.get(2).size() == longTasksCount);
    }

    double calculateDistanceMetres(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        double distance = Math.sqrt(x * x + y * y) * 6371;

        return distance * 1000;
    }

    private String getBboxString(OSMRequest osmRequest) {
        double ONE_DEGREE_M = 111320;

        double deltaLatitude = osmRequest.getRadius() / ONE_DEGREE_M;
        double deltaLongitude = osmRequest.getRadius()
                / (ONE_DEGREE_M * Math.cos(Math.PI * osmRequest.getLatitudeSpaceShip() / 180));

        double south = osmRequest.getLatitudeSpaceShip() - deltaLatitude;
        double west = osmRequest.getLongitudeSpaceShip() - deltaLongitude;
        double north = osmRequest.getLatitudeSpaceShip() + deltaLatitude;
        double east = osmRequest.getLongitudeSpaceShip() + deltaLongitude;

        return "[bbox:" + String.format(Locale.US, "%.6f", south) + ","
                + String.format(Locale.US, "%.6f", west) + ","
                + String.format(Locale.US, "%.6f", north) + ","
                + String.format(Locale.US, "%.6f", east) + "];";
    }

    private List<List<Node>> getNodes(String bbox, double latitudeSpaceShip, double longitudeSpaceShip, int radius) {
        OsmConnection connection = new OsmConnection("https://maps.mail.ru/osm/tools/overpass/api/", "my user agent");
        OverpassMapDataApi overpass = new OverpassMapDataApi(connection);

        String queryString = bbox + "((" + WAY_FOOTWAY + WAY_DESIGNATED + WAY_PATH + WAY_SIDEWALK + ");>;);out body;";
        System.out.println("queryString = " + queryString);

        final List<List<Node>> listOfNodes = new ArrayList<List<Node>>();

        for (int i = 0; i < 3; i++) {
            listOfNodes.add(new ArrayList<Node>());
        }

        overpass.queryElements(queryString, new MapDataHandler() {
            @Override
            public void handle(BoundingBox bounds) {

            }

            @Override
            public void handle(Way way) {

            }

            @Override
            public void handle(Node node) {
                double nodeDistance = calculateDistanceMetres(
                        node.getPosition().getLatitude(),
                        node.getPosition().getLongitude(),
                        latitudeSpaceShip,
                        longitudeSpaceShip);

                // Возьмем за радиус = 600 м, тогда:
                // - "short" дистанция = от 0 до 200 м
                // - "medium" дистанция = от 200 до 400 м
                // - "long" дистанция = от 401 до 600 м
                if (nodeDistance < radius / 3.0) {
                    listOfNodes.get(0).add(node);
                } else if (nodeDistance < radius * 2 / 3.0) {
                    listOfNodes.get(1).add(node);
                } else {
                    listOfNodes.get(2).add(node);
                }
            }

            @Override
            public void handle(Relation relation) {

            }
        });

        System.out.println("OpenStreetMapController | Short nodes = " + listOfNodes.get(0).size());
        System.out.println("OpenStreetMapController | Medium nodes = " + listOfNodes.get(1).size());
        System.out.println("OpenStreetMapController | Long nodes = " + listOfNodes.get(2).size());
        return listOfNodes;
    }

    List<List<GameCoordinatesDto>> generateAllTasks(
            List<List<Node>> listOfNodes,
            OSMRequest osmRequest,
            int shortTasksCount,
            int mediumTasksCount,
            int longTasksCount) {
        List<List<GameCoordinatesDto>> tasksList = new ArrayList<>();

        // Генерируем и сортируем метки в зависимости от удаленности
        List<GameCoordinatesDto> shortTasks = generateTasks(listOfNodes.get(0), shortTasksCount, osmRequest);
        List<GameCoordinatesDto> mediumTasks = generateTasks(listOfNodes.get(1), mediumTasksCount, osmRequest);
        List<GameCoordinatesDto> longTasks = generateTasks(listOfNodes.get(2), longTasksCount, osmRequest);

        tasksList.add(shortTasks);
        tasksList.add(mediumTasks);
        tasksList.add(longTasks);

        return tasksList;
    }

    private List<GameCoordinatesDto> generateTasks(List<Node> listOfNodes, int maxTasks, OSMRequest osmRequest) {

        List<GameCoordinatesDto> tasksList = new ArrayList<>();

        while (listOfNodes.size() > 0 && tasksList.size() < maxTasks) {

            // Берем рандомный индекс
            int randomIndex = new Random().nextInt(listOfNodes.size());

            LatLon nodePos = listOfNodes.get(randomIndex).getPosition();

            // Смотрим дистанцию этой точки от корабля
            double nodeDistanceFromSpaceShip = calculateDistanceMetres(
                    osmRequest.getLatitudeSpaceShip(),
                    osmRequest.getLongitudeSpaceShip(),
                    nodePos.getLatitude(),
                    nodePos.getLongitude());

            // Удаляем эту точку, если она выходит за радиус
            if (nodeDistanceFromSpaceShip > osmRequest.getRadius()) {
                listOfNodes.remove(randomIndex);
                continue;
            }

            // Проверяем, не находится ли эта точка слишком близко к любой другой, которые
            // уже есть у нас
            boolean isSmallDistance = false;
            for (GameCoordinatesDto task : tasksList) {
                if (calculateDistanceMetres(
                        task.getLatitude(),
                        task.getLongitude(),
                        nodePos.getLatitude(),
                        nodePos.getLongitude()) < MIN_DISTANCE_BETWEEN_TASKS_METRES) {
                    listOfNodes.remove(randomIndex);
                    isSmallDistance = true;
                    break;
                }
            }
            if (isSmallDistance) {
                continue;
            }

            // Победа - все условия соблюдены
            GameCoordinatesDto task = new GameCoordinatesDto(
                    nodePos.getLatitude(),
                    nodePos.getLongitude(),
                    false);

            tasksList.add(task);
        }

        return tasksList;
    }
}
