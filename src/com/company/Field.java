package com.company;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Field {
    private ArrayList<ArrayList<Cell>> field;
    private int[] ship = {1, 2, 3, 4};
    private Point[][] shipsCoordinate;
    //создание поля


    public Field() {//создание поля 12 на 12
        field = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < 12; i++) {
            ArrayList<Cell> temp = new ArrayList<Cell>();
            for (int j = 0; j < 12; j++) {
                temp.add(Cell.Empty);
            }
            field.add(temp);
        }// стенки поля от 1 до 10 место пустое
        for (int i = 0; i < field.size(); i++) {
            this.field.get(0).set(i, Cell.BORDER);
        }
        for (int i = 0; i < field.size(); i++) {
            this.field.get(i).set(0, Cell.BORDER);
        }
        for (int i = 0; i < field.size(); i++) {
            this.field.get(11).set(i, Cell.BORDER);
        }
        for (int i = 0; i < field.size(); i++) {
            this.field.get(i).set(11, Cell.BORDER);
        }
        shipsCoordinate = new Point[0][];
    }

    private void addPoint(Point[] points) {
        Point[][] array = new Point[shipsCoordinate.length + 1][];
        for (int i = 0; i < shipsCoordinate.length; i++) {
            array[i] = new Point[shipsCoordinate[i].length];
            for (int j = 0; j < shipsCoordinate[i].length; j++) {
                array[i][j] = shipsCoordinate[i][j];
            }
        }
        array[array.length - 1] = points;
        shipsCoordinate = array;
    }

    public Point[][] getShipsCoordinate() {
        return shipsCoordinate;
    }

    public void setField(ArrayList<ArrayList<Cell>> fields) {
        field = fields;
    }

    public ArrayList<ArrayList<Cell>> getField() {
        return field;
    }

    public void paintField() { //    отрисовка поля
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.get(i).size(); j++) {
                if (field.get(i).get(j) == Cell.Empty) {
                    System.out.print(" ");
                }
                if (field.get(i).get(j) == Cell.Ship) {
                    System.out.print("+");
                }
                if (field.get(i).get(j) == Cell.ShipShoot) {
                    System.out.print("-");
                }
                if (field.get(i).get(j) == Cell.ShipBreak) {
                    System.out.print("*");
                }
                if (field.get(i).get(j) == Cell.EmptyShoot) {
                    System.out.print("•");
                }
                if (field.get(i).get(j) == Cell.BORDER) {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }

    public void setFieldByUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1.Однопалубный кораблик \n" +
                "2.Двухпалубный кораблик \n" +
                "3.Трехпалубный кораблик \n" +
                "4.Четырехпалубный кораблик ");
        int shipId = sc.nextInt();
        System.out.println("Напишите кординате по кординате X:");
        int x = sc.nextInt();
        System.out.println("Напишите кординате по кординате Y:");
        int y = sc.nextInt();
        if (x >= 1 && x <= 10 && y >= 1 && y <= 10) {
            int direction = -1;
            if (shipId == 2 || shipId == 3 || shipId == 4) {
                System.out.println("Выберите напрвление:");
                System.out.println("1.Горизонталдь \n" +
                        "2.Вертикаль");
                direction = sc.nextInt();
            }
            if (shipId == 1 && ship[3] > 0) {
                if (placementOfShips(x, y, shipId, direction)) {
                    ship[3]--;
                    System.out.println("Кораблик добавлен");
                } else System.out.println("Ошибка");
            } else if (shipId == 2 && ship[2] > 0) {
                if (placementOfShips(x, y, shipId, direction)) {
                    ship[2]--;
                    System.out.println("Кораблик добавлен");
                } else System.out.println("Ошибка");
            } else if (shipId == 3 && ship[1] > 0) {
                if (placementOfShips(x, y, shipId, direction)) {
                    ship[1]--;
                    System.out.println("Кораблик добавлен");
                } else System.out.println("Ошибка");
            } else if (shipId == 4 && ship[0] > 0) {
                if (placementOfShips(x, y, shipId, direction)) {
                    ship[0]--;
                    System.out.println("Кораблик добавлен");
                } else System.out.println("Ошибка");
            } else System.out.println("Ошибка");
        } else System.out.println("Неверные индекса");
    }

    public void placementOfShipsRandom() {
        int x = 0, y = 0;
        int direction = -1;
        while (ship[0] > 0 || ship[1] > 0 || ship[2] > 0 || ship[3] > 0) {
            x = (int) (Math.random() * (11 - 1) + 1);
            y = (int) (Math.random() * (11 - 1) + 1);
            int shipId = (int) (Math.random() * (5 - 1) + 1);
            if (shipId >= 2 && shipId <= 4) direction = (int) (Math.random() * (3 - 1) + 1);
            if (shipId == 4 && ship[0] > 0) {
                if (placementOfShips(x, y, shipId, direction)) {
                    ship[0]--;
                }
            } else if (shipId == 3 && ship[1] > 0) {
                if (placementOfShips(x, y, shipId, direction)) {
                    ship[1]--;
                }
            } else if (shipId == 2 && ship[2] > 0) {
                if (placementOfShips(x, y, shipId, direction)) {
                    ship[2]--;
                }
            } else if (shipId == 1 && ship[3] > 0) {
                if (placementOfShips(x, y, shipId, direction)) {
                    ship[3]--;
                }
            }
        }
    }

    //расположение корабликов
    public boolean placementOfShips(int x, int y, int shipId, int direction) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            switch (shipId) {
                case 1: {
                    if ((x - 1 >= 0 && x + 1 <= 11 && y - 1 >= 0 && y + 1 <= 11) &&
                            (field.get(x).get(y) == Cell.Empty) && // проверка спавна начало кораблика
                            (field.get(x + 1).get(y) == Cell.Empty || field.get(x + 1).get(y) == Cell.BORDER) &&
                            (field.get(x - 1).get(y) == Cell.Empty || field.get(x - 1).get(y) == Cell.BORDER) &&
                            (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) == Cell.BORDER) &&
                            (field.get(x).get(y - 1) == Cell.Empty || field.get(x).get(y - 1) == Cell.BORDER) &&
                            (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                            (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                            (field.get(x - 1).get(y + 1) == Cell.Empty || field.get(x - 1).get(y + 1) == Cell.BORDER) &&
                            (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER)) {
                        field.get(x).set(y, Cell.Ship);
                        addPoint(new Point[]{new Point(x, y)});
                        return true;
                    }
                    return false;
                }
                case 2: {
                    if (direction == 1) { //проверки на горизонталь
                        if ((x - 1 >= 0 && x + 1 <= 11 && y - 1 >= 0 && y + 2 <= 11) &&
                                (field.get(x).get(y + 1) == Cell.Empty) && //проверка на спавн с лево на право
                                field.get(x).get(y) == Cell.Empty &&
                                (field.get(x).get(y + 2) == Cell.Empty || field.get(x).get(y + 2) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 2) == Cell.Empty || field.get(x + 1).get(y + 2) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y) == Cell.Empty || field.get(x + 1).get(y) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x).get(y - 1) == Cell.Empty || field.get(x).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 2) == Cell.Empty || field.get(x - 1).get(y + 2) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 1) == Cell.Empty || field.get(x - 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y) == Cell.Empty || field.get(x - 1).get(y) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER)) {
                            field.get(x).set(y, Cell.Ship);
                            field.get(x).set(y + 1, Cell.Ship);
                            addPoint(new Point[]{new Point(x, y), new Point(x, y + 1)});
                            return true;
                        } else if ((x - 1 <= 11 && x + 1 >= 0 && y + 1 <= 11 && y - 2 >= 0) &&
                                (field.get(x).get(y - 1) == Cell.Empty) && //проверка на спавн с право на лево
                                field.get(x).get(y) == Cell.Empty &&
                                (field.get(x).get(y - 2) == Cell.Empty || field.get(x).get(y - 2) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 2) == Cell.Empty || field.get(x - 1).get(y - 2) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y) == Cell.Empty || field.get(x - 1).get(y) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 1) == Cell.Empty || field.get(x - 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 2) == Cell.Empty || field.get(x + 1).get(y - 2) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y) == Cell.Empty || field.get(x + 1).get(y) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) == Cell.BORDER)) {
                            field.get(x).set(y, Cell.Ship);
                            field.get(x).set(y - 1, Cell.Ship);
                            addPoint(new Point[]{new Point(x, y), new Point(x, y - 1)});
                            return true;
                        } else return false;
                    } else if (direction == 2) { //проверки на вертикаль
                        if ((x + 2 <= 11 && x - 1 >= 0 && y + 1 <= 11 && y - 1 >= 0) &&
                                (field.get(x + 1).get(y) == Cell.Empty) && // проверка спавна тела кораблика по вертикале c низу в верх
                                field.get(x).get(y) == Cell.Empty &&
                                (field.get(x + 2).get(y) == Cell.Empty || field.get(x + 2).get(y) == Cell.BORDER) &&
                                (field.get(x + 2).get(y + 1) == Cell.Empty || field.get(x + 2).get(y + 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 1) == Cell.Empty || field.get(x - 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y) == Cell.Empty || field.get(x - 1).get(y) == Cell.BORDER) &&
                                (field.get(x + 2).get(y - 1) == Cell.Empty || field.get(x + 2).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x).get(y - 1) == Cell.Empty || field.get(x).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER)) {
                            field.get(x).set(y, Cell.Ship);
                            field.get(x + 1).set(y, Cell.Ship);
                            addPoint(new Point[]{new Point(x, y), new Point(x + 1, y)});
                            return true;
                        } else if ((x - 1 <= 11 && x - 2 >= 0 && y + 1 <= 11 && y - 1 >= 0) &&
                                (field.get(x - 1).get(y) == Cell.Empty) && //спавн кораблика по верртикале c верху в низ
                                field.get(x).get(y) == Cell.Empty &&
                                (field.get(x + 1).get(y) == Cell.Empty || field.get(x + 1).get(y) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 1) == Cell.Empty || field.get(x - 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 2).get(y + 1) == Cell.Empty || field.get(x - 2).get(y + 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x).get(y - 1) == Cell.Empty || field.get(x).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 2).get(y - 1) == Cell.Empty || field.get(x - 2).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 2).get(y) == Cell.Empty || field.get(x - 2).get(y) == Cell.BORDER)) {
                            field.get(x).set(y, Cell.Ship);
                            field.get(x - 1).set(y, Cell.Ship);
                            addPoint(new Point[]{new Point(x, y), new Point(x - 1, y)});
                            return true;
                        } else return false;
                    } else return false;
                }
                case 3: {
                    if (direction == 1) {
                        if ((y - 1 <= 11 && y + 3 >= 0 && x + 1 <= 11 && x - 1 >= 0) &&
                                (field.get(x).get(y + 1) == Cell.Empty) &&
                                field.get(x).get(y) == Cell.Empty &&
                                (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 1) == Cell.Empty || field.get(x - 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y) == Cell.Empty || field.get(x + 1).get(y) == Cell.BORDER) &&
                                (field.get(x - 1).get(y) == Cell.Empty || field.get(x - 1).get(y) == Cell.BORDER) &&
                                (field.get(x).get(y + 2) == Cell.Empty) &&
                                (field.get(x + 1).get(y + 2) == Cell.Empty || field.get(x + 1).get(y + 2) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 2) == Cell.Empty || field.get(x - 1).get(y + 2) == Cell.BORDER) &&
                                (field.get(x).get(y - 1) == Cell.Empty || field.get(x).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 3) == Cell.Empty || field.get(x + 1).get(y + 3) == Cell.BORDER) &&
                                (field.get(x).get(y + 3) == Cell.Empty || field.get(x).get(y + 3) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 3) == Cell.Empty || field.get(x - 1).get(y + 3) == Cell.BORDER)) {// с лево на право
                            field.get(x).set(y, Cell.Ship);
                            field.get(x).set(y + 1, Cell.Ship);
                            field.get(x).set(y + 2, Cell.Ship);
                            addPoint(new Point[]{new Point(x, y), new Point(x, y + 1), new Point(x, y + 2)});
                            return true;
                        } else if ((y + 1 <= 11 && y - 3 >= 0 && x + 1 <= 11 && x - 1 >= 0) &&
                                (field.get(x).get(y - 1) == Cell.Empty) &&
                                field.get(x).get(y) == Cell.Empty &&
                                (field.get(x + 1).get(y - 2) == Cell.Empty || field.get(x + 1).get(y - 2) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y) == Cell.Empty || field.get(x + 1).get(y) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y - 2) == Cell.Empty) &&
                                (field.get(x - 1).get(y - 2) == Cell.Empty || field.get(x - 1).get(y - 2) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y) == Cell.Empty || field.get(x - 1).get(y) == Cell.BORDER) &&
                                (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y - 3) == Cell.Empty || field.get(x).get(y - 3) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 3) == Cell.Empty || field.get(x + 1).get(y - 3) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 3) == Cell.Empty || field.get(x - 1).get(y - 3) == Cell.BORDER)) { //с право на лево
                            field.get(x).set(y, Cell.Ship);
                            field.get(x).set(y - 1, Cell.Ship);
                            field.get(x).set(y - 2, Cell.Ship);
                            addPoint(new Point[]{new Point(x, y), new Point(x, y - 1), new Point(x, y - 2)});
                            return true;
                        } else return false;
                    } else if (direction == 2) {
                        if ((x + 3 <= 11 && x - 1 >= 0 && y + 1 <= 11 && y - 1 >= 0) &&
                                (field.get(x + 1).get(y) == Cell.Empty) && // с низу в верх
                                field.get(x).get(y) == Cell.Empty &&
                                (field.get(x + 2).get(y) == Cell.Empty) &&
                                (field.get(x + 2).get(y + 1) == Cell.Empty || field.get(x + 2).get(y + 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 1) == Cell.Empty || field.get(x - 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y) == Cell.Empty || field.get(x - 1).get(y) == Cell.BORDER) &&
                                (field.get(x + 2).get(y - 1) == Cell.Empty || field.get(x + 2).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x).get(y - 1) == Cell.Empty || field.get(x).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 3).get(y) == Cell.Empty || field.get(x + 3).get(y) == Cell.BORDER) &&
                                (field.get(x + 3).get(y - 1) == Cell.Empty || field.get(x + 3).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 3).get(y + 1) == Cell.Empty || field.get(x + 3).get(y + 1) == Cell.BORDER)) {
                            field.get(x).set(y, Cell.Ship);
                            field.get(x + 1).set(y, Cell.Ship);
                            field.get(x + 2).set(y, Cell.Ship);
                            addPoint(new Point[]{new Point(x, y), new Point(x + 1, y), new Point(x + 2, y)});
                            return true;
                        } else if ((x - 3 >= 0 && x + 1 <= 11 && y - 1 >= 0 && y + 1 <= 11) &&
                                (field.get(x - 1).get(y) == Cell.Empty) && //с верху в низ
                                field.get(x).get(y) == Cell.Empty &&
                                (field.get(x + 1).get(y) == Cell.Empty || field.get(x + 1).get(y) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 1) == Cell.Empty || field.get(x - 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 2).get(y + 1) == Cell.Empty || field.get(x - 2).get(y + 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x).get(y - 1) == Cell.Empty || field.get(x).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 2).get(y - 1) == Cell.Empty || field.get(x - 2).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 2).get(y) == Cell.Empty) &&
                                (field.get(x - 3).get(y) == Cell.Empty || field.get(x - 3).get(y) == Cell.BORDER) &&
                                (field.get(x - 3).get(y + 1) == Cell.Empty || field.get(x - 3).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 3).get(y - 1) == Cell.Empty || field.get(x - 3).get(y - 1) == Cell.BORDER)) {
                            field.get(x).set(y, Cell.Ship);
                            field.get(x - 1).set(y, Cell.Ship);
                            field.get(x - 2).set(y, Cell.Ship);
                            addPoint(new Point[]{new Point(x, y), new Point(x - 1, y), new Point(x - 2, y)});
                            return true;
                        } else return false;
                    } else return false;

                }
                case 4: {
                    if (direction == 1) {
                        if ((y + 4 <= 11 && y - 1 >= 0 && x + 1 <= 11 && x - 1 >= 0) &&
                                (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) != Cell.BORDER) &&
                                field.get(x).get(y) == Cell.Empty &&
                                field.get(x).get(y + 1) == Cell.Empty &&
                                (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 1) == Cell.Empty || field.get(x - 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y) == Cell.Empty || field.get(x + 1).get(y) == Cell.BORDER) &&
                                (field.get(x - 1).get(y) == Cell.Empty || field.get(x - 1).get(y) == Cell.BORDER) &&
                                field.get(x).get(y + 2) == Cell.Empty &&
                                (field.get(x + 1).get(y + 2) == Cell.Empty || field.get(x + 1).get(y + 2) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 2) == Cell.Empty || field.get(x - 1).get(y + 2) == Cell.BORDER) &&
                                (field.get(x).get(y - 1) == Cell.Empty || field.get(x).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 3) == Cell.Empty || field.get(x + 1).get(y + 3) == Cell.BORDER) &&
                                field.get(x).get(y + 3) == Cell.Empty &&
                                (field.get(x - 1).get(y + 3) == Cell.Empty || field.get(x - 1).get(y + 3) == Cell.BORDER) &&
                                (field.get(x).get(y + 4) == Cell.Empty || field.get(x).get(y + 4) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 4) == Cell.Empty || field.get(x + 1).get(y + 4) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 4) == Cell.Empty || field.get(x - 1).get(y + 4) == Cell.BORDER)) {// с лево на право
                            field.get(x).set(y, Cell.Ship);
                            field.get(x).set(y + 1, Cell.Ship);
                            field.get(x).set(y + 2, Cell.Ship);
                            field.get(x).set(y + 3, Cell.Ship);
                            addPoint(new Point[]{new Point(x, y), new Point(x, y + 1), new Point(x, y + 2), new
                                    Point(x, y + 3)});
                            return true;
                        } else if ((y + 1 <= 11 && y - 4 >= 0 && x + 1 <= 11 && x - 1 >= 0) &&
                                (field.get(x).get(y - 1) == Cell.Empty) &&
                                (field.get(x + 1).get(y - 2) == Cell.Empty || field.get(x + 1).get(y - 2) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y) == Cell.Empty || field.get(x + 1).get(y) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y) == Cell.Empty) &&
                                (field.get(x).get(y - 2) == Cell.Empty) &&
                                (field.get(x - 1).get(y + 1) == Cell.Empty || field.get(x - 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 2) == Cell.Empty || field.get(x - 1).get(y - 2) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y) == Cell.Empty || field.get(x - 1).get(y) == Cell.BORDER) &&
                                (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y - 3) == Cell.Empty) &&
                                (field.get(x + 1).get(y - 3) == Cell.Empty || field.get(x + 1).get(y - 3) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 3) == Cell.Empty || field.get(x - 1).get(y - 3) == Cell.BORDER) &&
                                (field.get(x).get(y - 4) == Cell.Empty) &&
                                (field.get(x - 1).get(y - 4) == Cell.Empty || field.get(x - 1).get(y - 4) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 4) == Cell.Empty || field.get(x + 1).get(y - 4) == Cell.BORDER)) {// с право на лево
                            field.get(x).set(y, Cell.Ship);
                            field.get(x).set(y - 1, Cell.Ship);
                            field.get(x).set(y - 2, Cell.Ship);
                            field.get(x).set(y - 3, Cell.Ship);
                            addPoint(new Point[]{new Point(x, y), new Point(x, y - 1), new Point(x, y - 2), new
                                    Point(x, y - 3)});
                            return true;
                        } else return false;
                    } else if (direction == 2) {
                        if ((x + 4 <= 11 && x - 1 >= 0 && y + 1 <= 11 && y - 1 >= 0) &&
                                (field.get(x + 1).get(y) == Cell.Empty) &&
                                (field.get(x).get(y) == Cell.Empty) &&
                                (field.get(x + 2).get(y + 1) == Cell.Empty || field.get(x + 2).get(y + 1) == Cell.BORDER) &&
                                (field.get(x + 2).get(y) == Cell.Empty) &&
                                (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 1) == Cell.Empty || field.get(x - 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y) == Cell.Empty || field.get(x - 1).get(y) == Cell.BORDER) &&
                                (field.get(x + 2).get(y - 1) == Cell.Empty || field.get(x + 2).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x).get(y - 1) == Cell.Empty || field.get(x).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 3).get(y) == Cell.Empty) &&
                                (field.get(x + 3).get(y - 1) == Cell.Empty || field.get(x + 3).get(y - 1) == Cell.BORDER) &&
                                (field.get(x + 3).get(y + 1) == Cell.Empty || field.get(x + 3).get(y + 1) == Cell.BORDER) &&
                                (field.get(x + 4).get(y) == Cell.Empty || field.get(x + 4).get(y) == Cell.BORDER) &&
                                (field.get(x + 4).get(y - 1) == Cell.Empty || field.get(x + 4).get(y - 1) == Cell.BORDER) &&// с верху в низ
                                (field.get(x + 4).get(y + 1) == Cell.Empty || field.get(x + 4).get(y + 1) == Cell.BORDER)) {
                            field.get(x).set(y, Cell.Ship);
                            field.get(x + 1).set(y, Cell.Ship);
                            field.get(x + 2).set(y, Cell.Ship);
                            field.get(x + 3).set(y, Cell.Ship);
                            addPoint(new Point[]{new Point(x, y), new Point(x  + 1, y), new Point(x + 2, y), new
                                    Point(x + 3, y)});
                            return true;
                        } else if ((x - 4 <= 11 && x + 1 >= 0 && y + 1 <= 11 && y - 1 >= 0) &&
                                (field.get(x - 1).get(y) == Cell.Empty) &&
                                (field.get(x).get(y) == Cell.Empty) &&
                                (field.get(x + 1).get(y) == Cell.Empty || field.get(x + 1).get(y) == Cell.BORDER) &&
                                (field.get(x + 1).get(y + 1) == Cell.Empty || field.get(x + 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x).get(y + 1) == Cell.Empty || field.get(x).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y + 1) == Cell.Empty || field.get(x - 1).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 2).get(y + 1) == Cell.Empty || field.get(x - 2).get(y + 1) == Cell.BORDER) &&
                                (field.get(x + 1).get(y - 1) == Cell.Empty || field.get(x + 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x).get(y - 1) == Cell.Empty || field.get(x).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 1).get(y - 1) == Cell.Empty || field.get(x - 1).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 2).get(y - 1) == Cell.Empty || field.get(x - 2).get(y - 1) == Cell.BORDER) &&
                                (field.get(x - 2).get(y) == Cell.Empty) &&
                                (field.get(x - 3).get(y) == Cell.Empty || field.get(x - 3).get(y) == Cell.BORDER) &&
                                (field.get(x - 3).get(y + 1) == Cell.Empty || field.get(x - 3).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 3).get(y) == Cell.Empty) &&
                                (field.get(x - 4).get(y) == Cell.Empty || field.get(x - 4).get(y) == Cell.BORDER) &&
                                (field.get(x - 4).get(y + 1) == Cell.Empty || field.get(x - 4).get(y + 1) == Cell.BORDER) &&
                                (field.get(x - 4).get(y - 1) == Cell.Empty || field.get(x - 4).get(y - 1) == Cell.BORDER)) {// с низу в верх
                            field.get(x).set(y, Cell.Ship);
                            field.get(x - 1).set(y, Cell.Ship);
                            field.get(x - 2).set(y, Cell.Ship);
                            field.get(x - 3).set(y, Cell.Ship);
                            addPoint(new Point[]{new Point(x, y), new Point(x - 1, y), new Point(x - 2, y), new
                                    Point(x - 3, y)});
                            return true;
                        } else return false;
                    } else return false;
                }
                default: {
                    return false;
                }
            }
        }
    }

    public boolean isShipShot(int x, int y) {
        if (getField().get(x).get(y) == Cell.Ship) {
            return true;
        }
        return false;
    }

    public boolean isEmptyShoot(int x, int y) {
        if (getField().get(x).get(y) == Cell.Empty){
            return true;
        }
        return false;
    }

    public boolean isShipBreak(int x, int y) {
        for (int i = 0; i < shipsCoordinate.length; i++) {
            for (int j = 0; j < shipsCoordinate[i].length; j++) {
                if (shipsCoordinate[i][j].getX() == x && shipsCoordinate[i][j].getY() == y){
                    for (int k = 0; k < shipsCoordinate[i].length; k++) {
                        if (field.get(shipsCoordinate[i][k].getX()).get(shipsCoordinate[i][k].getY()) != Cell.ShipShoot) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void makeShipBreak(int x, int y, Point[][] shipsCoordinate) {
        for (int i = 0; i < shipsCoordinate.length; i++) {
            for (int j = 0; j < shipsCoordinate[i].length; j++) {
                if (shipsCoordinate[i][j].getX() == x && shipsCoordinate[i][j].getY() == y) {
                    for (int k = 0; k < shipsCoordinate[i].length; k++) {
                        field.get(shipsCoordinate[i][k].getX()).set(shipsCoordinate[i][k].getY(), Cell.ShipBreak);
                    }
                    for (int k = 0; k < shipsCoordinate[i].length; k++) {
                       if (true) {
                            if (field.get(shipsCoordinate[i][k].getX() + 1).get(shipsCoordinate[i][k].getY() + 1) == Cell.Empty) {
                                field.get(shipsCoordinate[i][k].getX() + 1).set(shipsCoordinate[i][k].getY() + 1, Cell.EmptyShoot);
                            }
                            if (field.get(shipsCoordinate[i][k].getX() - 1).get(shipsCoordinate[i][k].getY() - 1) == Cell.Empty) {
                                field.get(shipsCoordinate[i][k].getX() - 1).set(shipsCoordinate[i][k].getY() - 1, Cell.EmptyShoot);
                            }
                            if (field.get(shipsCoordinate[i][k].getX() + 1).get(shipsCoordinate[i][k].getY() - 1) == Cell.Empty) {
                                field.get(shipsCoordinate[i][k].getX() + 1).set(shipsCoordinate[i][k].getY() - 1, Cell.EmptyShoot);
                            }
                            if (field.get(shipsCoordinate[i][k].getX() - 1).get(shipsCoordinate[i][k].getY() + 1) == Cell.Empty) {
                                field.get(shipsCoordinate[i][k].getX() - 1).set(shipsCoordinate[i][k].getY() + 1, Cell.EmptyShoot);
                            }
                            if (field.get(shipsCoordinate[i][k].getX() - 1).get(shipsCoordinate[i][k].getY() + 1) == Cell.Empty) {
                                field.get(shipsCoordinate[i][k].getX() - 1).set(shipsCoordinate[i][k].getY() + 1, Cell.EmptyShoot);
                            }
                            if (field.get(shipsCoordinate[i][k].getX() + 1).get(shipsCoordinate[i][k].getY()) == Cell.Empty) {
                                field.get(shipsCoordinate[i][k].getX() + 1).set(shipsCoordinate[i][k].getY(), Cell.EmptyShoot);
                            }
                           if (field.get(shipsCoordinate[i][k].getX() - 1).get(shipsCoordinate[i][k].getY()) == Cell.Empty) {
                               field.get(shipsCoordinate[i][k].getX() - 1).set(shipsCoordinate[i][k].getY(), Cell.EmptyShoot);
                           }
                            if (field.get(shipsCoordinate[i][k].getX()).get(shipsCoordinate[i][k].getY() + 1) == Cell.Empty) {
                                field.get(shipsCoordinate[i][k].getX()).set(shipsCoordinate[i][k].getY() + 1, Cell.EmptyShoot);
                            }
                            if (field.get(shipsCoordinate[i][k].getX()).get(shipsCoordinate[i][k].getY() - 1) == Cell.Empty) {
                                field.get(shipsCoordinate[i][k].getX()).set(shipsCoordinate[i][k].getY() - 1, Cell.EmptyShoot);
                            }
                       }
                    }
                }
            }
        }
    }
    public boolean isLose(){
        for (int i = 0;i < field.size(); i++){
            for (int j = 0;j < field.get(i).size(); j++){
                if (field.get(i).get(j) == Cell.Ship){
                    return false;
                }
            }
        }
        return true;
    }
}

