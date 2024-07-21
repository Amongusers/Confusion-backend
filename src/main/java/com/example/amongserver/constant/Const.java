package com.example.amongserver.constant;
/*
Константы url путей, использующие для опредения endPoint
(как завещая Никита, рукава)
По хорошему, тут нужно навести порядок, но всё работает
*/
public class Const {

    // Префтикс в LINK_API_V1
    public static final String LINK_API = "/api";
    // Префтикс во всех топиках
    public static final String TOPIC = "/topic";
    // Постфикс в LINK_CHAT и GEOPOS_TOPIC (как будто так быть не должно, я не помню почему
    // было сделано таким образом)
    public static final String CHAT = "chat";
    // Постфикс в COORDINATES_TOPIC
    public static final String COORDINATES = "coordinates";
    // Постфикс в USER_TOPIC
    public static final String USER = "user";
    // Постфикс в GEMA_TOPIC
    public static final String GAME = "game";
    // Постфикс в VOTE_TOPIC
    public static final String VOTE = "vote";
    // Постфикс в LINK_CHAT
    public static final String LINK_API_V1 = LINK_API + "/" + "v1";
    // Подключение WebSockets, используется в WebSocketConfig
    public static final String LINK_CHAT = LINK_API_V1 + "/" + CHAT;
    // Url для GeoPositionController
    public static final String GEOPOS_TOPIC = TOPIC + "/" + CHAT;
    // Url для GameCoordinatesController
    public static final String COORDINATES_TOPIC = TOPIC + "/" + COORDINATES;
    // Url для UserController
    public static final String USER_TOPIC = TOPIC + "/" + USER;
    // Url для GameStateController
    public static final String GEMA_TOPIC = TOPIC + "/" + GAME;
    // Url для VoteController
    public static final String VOTE_TOPIC = TOPIC + "/" + VOTE;

}
