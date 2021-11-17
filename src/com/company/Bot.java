package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Bot {
    private Field map;
    private Field playerMap;
    private Point[] points;
    private Point[] points2;
    private boolean isLastShootWasInShip = false;

    public Bot() {
        this.map = new Field();
        this.playerMap = new Field();
        points = new Point[0];
        points2 = new Point[0];
    }

    public Bot(Field map, Field playerMap) {
        this.map = map;
        this.playerMap = playerMap;
        points = new Point[0];
    }

    public Field getMap() {
        return map;
    }

    public Field getPlayerMap() {
        return playerMap;
    }

    public void setMap(Field map) {
        this.map = map;
    }

    public void setPlayerMap(Field playerMap) {
        this.playerMap = playerMap;
    }
    public void setPoints(Point[] points){
        this.points = points;
    }

    public Point stepBot(Player player) {
        int x = 0;
        int y = 0;
        int index = 0;
        Point point = new Point();
        if (points.length >= 1) {
//            if (player.getMap().getField().get(x).get(y) == Cell.ShipShoot){
//                points = deletePoint(points,new Point(x, y));
//            }
            do {
                index = (int) (Math.random() * (points.length - 0) + 0);
                x = points[index].getX();
                y = points[index].getY();
            } while (playerMap.getField().get(x).get(y) != Cell.Empty);
            point = points[index];
            if (player.getMap().isShipShot(x, y)) {
                playerMap.getField().get(x).set(y, Cell.ShipShoot);
                points = deletePoint(points, new Point(x, y));
                isLastShootWasInShip = true;
                if (player.getMap().isShipShot(x, y + 1)) {
                    points = deletePoint(points, new Point(x + 1, y));
                    points = deletePoint(points, new Point(x - 1, y));
                    points = deletePoint(points, new Point(x + 1, y - 1));
                    points = deletePoint(points, new Point(x - 1, y - 1));

                    points = addPoint(points, new Point(x, y + 1));
                    points2 = addPoint(points2, new Point(x + 1, y + 2));
                    points2 = addPoint(points2, new Point(x + 1, y + 3));
                    points2 = addPoint(points2, new Point(x - 1, y + 2));
                    points2 = addPoint(points2, new Point(x - 1, y + 3));
                    for (int i = 0; i < points.length; i++) {
                        for (int j = 0; j < points2.length; j++) {
                            if (points[i].getY() == points2[j].getY() && points[i].getX() == points2[j].getX()) {
                                points = deletePoint(points, points[i]);

                                break;
                            }
                        }
                    }
                }
                if (player.getMap().isShipShot(x, y - 1)) {
                    points = deletePoint(points, new Point(x + 1, y));
                    points = deletePoint(points, new Point(x - 1, y));
                    points = deletePoint(points, new Point(x + 1, y + 1));
                    points = deletePoint(points, new Point(x - 1, y + 1));

                    points = addPoint(points, new Point(x, y - 1));
                    points2 = addPoint(points2, new Point(x + 1, y - 2));
                    points2 = addPoint(points2, new Point(x + 1, y - 3));
                    points2 = addPoint(points2, new Point(x - 1, y - 2));
                    points2 = addPoint(points2, new Point(x - 1, y - 3));
                    for (int i = 0; i < points.length; i++) {
                        for (int j = 0; j < points2.length; j++) {
                            if (points[i].getY() == points2[j].getY() && points[i].getX() == points2[j].getX()) {
                                points = deletePoint(points, points[i]);
                                break;
                            }
                        }
                    }
                }
                else {
                    if (player.getMap().isShipShot(x + 1, y)) {
                        points = deletePoint(points, new Point(x, y + 1));
                        points = deletePoint(points, new Point(x, y - 1));
                        points = deletePoint(points, new Point(x - 1, y + 1));
                        points = deletePoint(points, new Point(x - 1, y - 1));

                        points = addPoint(points, new Point(x + 1, y));
                        points2 = addPoint(points2, new Point(x + 2, y + 1));
                        points2 = addPoint(points2, new Point(x + 3, y + 1));
                        points2 = addPoint(points2, new Point(x + 2, y - 1));
                        points2 = addPoint(points2, new Point(x + 3, y - 1));
                        for (int i = 0; i < points.length; i++) {
                            for (int j = 0; j < points2.length; j++) {
                                if (points[i].getY() == points2[j].getY() && points[i].getX() == points2[j].getX()) {
                                    points = deletePoint(points, points[i]);
                                    break;
                                }
                            }
                        }
                    }
                    if (player.getMap().isShipShot(x - 1, y)) {
                        points = deletePoint(points, new Point(x, y + 1));
                        points = deletePoint(points, new Point(x, y - 1));
                        points = deletePoint(points, new Point(x + 1, y + 1));
                        points = deletePoint(points, new Point(x + 1, y - 1));

                        points = addPoint(points, new Point(x - 1, y));
                        points2 = addPoint(points2, new Point(x - 2, y + 1));
                        points2 = addPoint(points2, new Point(x - 3, y + 1));
                        points2 = addPoint(points2, new Point(x - 2, y - 1));
                        points2 = addPoint(points2, new Point(x - 3, y - 1));
                        for (int i = 0; i < points.length; i++) {
                            for (int j = 0; j < points2.length; j++) {
                                if (points[i].getY() == points2[j].getY() && points[i].getX() == points2[j].getX()) {
                                    points = deletePoint(points, points[i]);
                                    break;
                                }
                            }
                        }
                    }
                }
                if (player.getMap().isShipBreak(x, y)) {
                    isLastShootWasInShip = false;
                    points = new Point[0];
                    return point;
                }
            }
            else {
                if (player.getMap().isEmptyShoot(x, y)) {
                    points = deletePoint(points, new Point(x, y));
                    return point;
                }
            }
        }
        else {
            do {
                x = (int) (Math.random() * (11 - 1) + 1);
                y = (int) (Math.random() * (11 - 1) + 1);
            } while (playerMap.getField().get(x).get(y) != Cell.Empty);
            point = new Point(x, y);
            if (player.getMap().isShipShot(x, y)) {
                isLastShootWasInShip = true;
                if (player.getMap().isShipBreak(x, y)) {
                    isLastShootWasInShip = false;
                    return point;
                }
                else {
                    if (player.getMap().getField().get(x + 1).get(y) == Cell.Empty || player.getMap().getField().get(x + 1).get(y) == Cell.Ship) {
                        points = addPoint(points, new Point(x + 1, y));
                    }
                    if (player.getMap().getField().get(x - 1).get(y) == Cell.Empty || player.getMap().getField().get(x - 1).get(y) == Cell.Ship) {
                        points = addPoint(points, new Point(x - 1, y));
                    }
                    if (player.getMap().getField().get(x).get(y - 1) == Cell.Empty || player.getMap().getField().get(x).get(y - 1) == Cell.Ship) {
                        points = addPoint(points, new Point(x, y - 1));
                    }
                    if (player.getMap().getField().get(x).get(y + 1) == Cell.Empty || player.getMap().getField().get(x).get(y + 1) == Cell.Ship) {
                        points = addPoint(points, new Point(x, y + 1));
                    }
                    return point;
                }
            }
            return point;
        }
        return point;
    }

    public Point[] addPoint(Point[] points, Point point) {
        for (int i = 0; i < points.length; i++) {
            if (point.getX() == points[i].getX() && point.getY() == points[i].getY()) return points;
        }
        Point[] array = new Point[points.length + 1];
        for (int i = 0; i < points.length; i++) {
            array[i] = points[i];
        }
        array[array.length - 1] = point;
        points = array;
        return points;
    }

    public Point[] deletePoint(Point[] points, Point point) {
        boolean isFind = false;
        for (int i = 0; i < points.length; i++) {
            if (points[i].getY() == point.getY() && points[i].getX() == point.getX())
                isFind = true;
        }
        if (isFind == false) return points;

        int idx = 0;
        Point[] array = new Point[points.length - 1];
        for (int i = 0; i < points.length; i++) {
            if (point.getX() != points[i].getX() || point.getY() != points[i].getY()) {
                array[idx] = points[i];
                idx++;
            }
        }
        return array;
    }

    public Point[] deleteNull(Point[] points) {
        if (points.length == 0) return points;
        int idx = 0;
        int count = 0;
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                count++;
            }
        }
        Point[] array = new Point[points.length - count];
        for (int i = 0; i < points.length; i++) {
            if (array[i] != null) {
                array[idx] = points[i];
                idx++;
            }
        }
        return array;
    }
}
