package com.example.amongserver.constant;

public class Const {


    public static final String LINK_API = "/api";

    public static final String TOPIC = "/topic";
    public static final String CHAT = "chat";
    public static final String COORDINATES = "coordinates";
    public static final String USER = "user";
    public static final String GAME = "game";
    public static final String VOTE = "vote";
    public static final String LINK_API_V1 = LINK_API + "/" + "v1";
    public static final String LINK_CHAT = LINK_API_V1 + "/" + CHAT;

    public static final String GEOPOS_TOPIC = TOPIC + "/" + CHAT;

    public static final String COORDINATES_TOPIC = TOPIC + "/" + COORDINATES;
    public static final String USER_TOPIC = TOPIC + "/" + USER;
    public static final String GEMA_TOPIC = TOPIC + "/" + GAME;
    public static final String VOTE_TOPIC = TOPIC + "/" + VOTE;

}
