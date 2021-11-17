package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static Game myGame;
    public static void main(String[] args) {

	    /*
	    * Class:
	    * -Bot
	    * -Field
	    * -Player
	    * -Game
	    *
	    *
	    * Enums:
	    * -Cell(Empty, Ship, EmptyShoot, ShipShoot, ShipBreak)
	    * -Menu
	    * */

//		1.начать игру,2 выход

		myGame = new Game();
		while (true){
			Scanner sc = new Scanner(System.in);
			System.out.println("1. Начать игру.\n" +
								"2. Выход.");
			myGame.getPlayer().setMap(new Field());
			myGame.getBot().setMap(new Field ());
			myGame.getPlayer().setBotMap(new Field ());

			int choise = sc.nextInt();
			switch (choise){
				case 1:{
					System.out.println("1.Рандомно.\n" +
							           "2.Самому.\n");
					choise = sc.nextInt();
					switch (choise){
						case 1:{
							myGame.getBot().getMap().placementOfShipsRandom();
							System.out.println("Player");
							myGame.getPlayer().getMap().placementOfShipsRandom();
							myGame.getPlayer().getMap().paintField();
							myGame.getPlayer().getBotMap().paintField();
							myGame.game("Player", "Bot");
						}
						break;
						case 2:{
							myGame.getBot().getMap().placementOfShipsRandom();
							do {
								System.out.println("Player");
								myGame.getPlayer().getMap().setFieldByUser();
								myGame.getPlayer().getMap().paintField();
								myGame.getPlayer().getBotMap().paintField();
							}while (myGame.getPlayer().getMap().getShipsCoordinate().length < 10);
							System.out.println("Началась игра");
							myGame.game("Player", "Bot");
						}
					}
				}
				break;
				case 2:{
					return;
				}
			}
		}
    }
}
