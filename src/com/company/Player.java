package com.company;

import java.util.ArrayList;

public class Player {
    private Field map;
    private Field botMap;
    private boolean isFirstStep;

    public Player(){
        this.map = new Field();
        this.botMap = new Field();
    }
    public Player(Field map, Field botMap){
        this.map = map;
        this.botMap = botMap;
    }
    public Field getMap (){
        return map;
    }
    public Field getBotMap(){
        return botMap;
    }
    public void setMap(Field map){
        this.map = map;
    }
    public void setBotMap(Field botMap){
        this.botMap = botMap;
    }

 }
