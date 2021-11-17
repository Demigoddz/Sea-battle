package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Bot bot;
    private Player player;
    private Point [] points;


    public Game() {
        this.bot = new Bot();
        this.player = new Player();
    }

    public Game(Bot bot, Player player) {
        this.bot = bot;
        this.player = player;
    }

    public Bot getBot() {
        return bot;
    }

    public Player getPlayer() {
        return player;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void game(String playerName, String botName) {
        Scanner sc = new Scanner(System.in);
        int x = - 1;
        int y = - 1;
        boolean isFirstStepPlayer = true;
        while (true) {
            if (isFirstStepPlayer) {
                System.out.println(playerName + " ходит");
                do {
                    System.out.println("Введите кординату x");
                    x = sc.nextInt();
                    System.out.println("Введите кординату y");
                    y = sc.nextInt();
                } while (x < 1 || x > 10 || y < 1 || y > 10 || getPlayer().getBotMap().getField().get(x).get(y) != Cell.Empty);
                if (bot.getMap().isShipShot(x, y) || getPlayer().getBotMap().isShipShot(x, y)) {
                    bot.getMap().getField().get(x).set(y, Cell.ShipShoot);
                    getPlayer().getBotMap().getField().get(x).set(y, Cell.ShipShoot);
                    if (bot.getMap().isShipBreak(x, y) || getPlayer().getBotMap().isShipBreak(x, y)) {
                        bot.getMap().makeShipBreak(x, y, bot.getMap().getShipsCoordinate());
                        player.getMap().makeShipBreak(x, y, player.getMap().getShipsCoordinate());
                        getPlayer().getBotMap().makeShipBreak(x, y, bot.getMap().getShipsCoordinate());

                    }
                }
                else if (bot.getMap().isEmptyShoot(x, y) && getPlayer().getBotMap().isEmptyShoot(x, y)) {
                    bot.getMap().getField().get(x).set(y, Cell.EmptyShoot);
                    getPlayer().getBotMap().getField().get(x).set(y, Cell.EmptyShoot);
                }
                System.out.println("Player");
                getPlayer().getMap().paintField();
                getPlayer().getBotMap().paintField();
//                System.out.println("Bot");
//                getBot().getMap().paintField();
//                getBot().getPlayerMap().paintField();
                if (getBot().getMap().isLose()){
                    System.out.println("Win Player");
                    System.out.println("Lose Bot");
                    return;
                }
            }
            else {
                Point point = bot.stepBot(player);
                x = point.getX();
                y = point.getY();
                if (player.getMap().isShipShot(x, y)) {
                    player.getMap().getField().get(x).set(y, Cell.ShipShoot);
                    if (player.getMap().isShipBreak(x, y)){
                        player.getMap().makeShipBreak(x, y, player.getMap().getShipsCoordinate());
                        bot.setPoints(new Point[0]);
                    }
                }
                else if (player.getMap().isEmptyShoot(x, y)){
                    player.getMap ().getField().get(x).set(y, Cell.EmptyShoot);
                }
                System.out.println("Player");
                getPlayer().getMap().paintField();
                getPlayer().getBotMap().paintField();
//                System.out.println("Bot");
//                getBot().getMap().paintField();
//                getBot().getPlayerMap().paintField();
                if (getPlayer().getMap().isLose()){
                    System.out.println("Win Bot");
                    System.out.println("Lose Player");
                    return;
                }
            }
            if (getPlayer().getBotMap().getField().get(x).get(y) == Cell.EmptyShoot || player.getMap().getField().get(x).get(y) == Cell.EmptyShoot){
                isFirstStepPlayer = !isFirstStepPlayer;
            }
        }
    }
}
