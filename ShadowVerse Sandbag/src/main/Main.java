package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		try {
			Robot robot = new Robot();
			Scanner scan = new Scanner(System.in);
			int counter = 1;
			int gamesThrown = 1;

			System.out.print("How many loops? ");
			int loops = scan.nextInt();
//			int loops = 100;
			scan.close();

			System.out.print("Click Shadow Verse within 5 seconds: ");
			for (int i = 1; i <= 5; i++) {
				delayTimer(1);
				System.out.print(i + " ");
			}

			// Clicks top left corner to make sure that the program is selected
			robot.mouseMove(40, 40);
			simulateClick();
			simulateClick();

			//////////////////////////////////////////////////
			// EACH CYCLE TAKES APPROXIMATELY 60-70 SECONDS //
			//////////////////////////////////////////////////

			// time start
			long startTime = System.nanoTime();
			for (int i = 0; i < loops; i++) {

				boolean repeatEndSequence = true;

				// Select New Deck
				System.out.println("\n[Selecting Deck]");
				robot.mouseMove(350, 330);
				simulateClick();

				// Click Menu OK
				System.out.println("\nClicking Menu 'OK' Button");
				robot.mouseMove(1180, 780);
				simulateClick();

				// In-Game OK Button - Load
				System.out.print("\nIn-Game 'OK' Button Loading: ");
				while (!robot.getPixelColor(1750, 480).equals(new Color(5, 54, 83))) {
					delayTimer(2);
					System.out.print(counter + " ");
					counter++;
					// In case of a connection time out
					if (robot.getPixelColor(1050, 950).equals(new Color(0, 32, 60))) {
						System.out.print("\n\nTIME OUT OCCURRED");
						robot.mouseMove(1050, 950);
						simulateClick();
						delayTimer(3);
					}
				}
				System.out.print("\n[Button Loaded]");
				counter = 1;

				// In-Game OK Button - Click
				robot.mouseMove(1750, 480);
				simulateClick();

				// Opponent Mulligan
				System.out.print("\n\nOpponent Mulliganing: ");
				while (robot.getPixelColor(970, 300).equals(new Color(57, 49, 35))) {
					delayTimer(2);
					System.out.print(counter + " ");
					counter++;
				}
				System.out.println("\n[Mulligan Complete]");
				counter = 1;

				while (repeatEndSequence) {
					// Menu Button - Load
					System.out.print("\nLoading 'Menu' Button: ");
					while (!robot.getPixelColor(70, 70).equals(new Color(98, 122, 123))) {
						delayTimer(2);
						System.out.print(counter + " ");
						counter++;
					}
					System.out.print("\n[Button Loaded]");
					counter = 1;

					// Menu Button - Click
					robot.mouseMove(70, 70);
					delayTimer(1);
					simulateClick();

					// Quit Button 1 - Click
					robot.mouseMove(970, 280);
					simulateClick();

					// Quit Button 2 - Click
					robot.mouseMove(970, 780);
					simulateClick();

					// Battle Again Button - Load
					System.out.print("\n\nLoading 'Battle Again' Button: ");
					while (!robot.getPixelColor(970, 970).equals(new Color(0, 105, 137))) {
						simulateClick(); // In case of demotion need to constantly click.
						System.out.print(counter + " ");
						counter++;

						repeatEndSequence = false;
						if (counter == 11) {
							repeatEndSequence = true;
							break;
						}
					}
					counter = 1;
				} // end of endSequence
				System.out.println("\n[Button Loaded]");

				// Battle Again Button - Click
				robot.mouseMove(970, 970);
				simulateClick();

				// Num of Games Thrown
				System.out.println("\n_________________________\n\nGames Thrown: " + gamesThrown);
				gamesThrown++;

				// Time Elapsed
				double secElapsed = (double) (System.nanoTime() - startTime) / 1_000_000_000.0;
				System.out.println("Time Elapsed: " + new DecimalFormat("#.##").format(secElapsed / 60) + " mins\n_________________________");
			} // end repeatEndSequence
			System.out.println("\n\nPROGRAM EXECUTION COMPLETE");
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Simulates a left mouse button click
	 */
	private static void simulateClick() {
		try {
			Robot r = new Robot();
			r.delay(1000);
			r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			r.delay(1000);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/**
	 * thread sleep in seconds
	 * 
	 * @param a - seconds to sleep
	 */
	private static void delayTimer(int a) {
		try {
			for (int i = 0; i < a; i++) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
