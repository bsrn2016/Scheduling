/**
 * 
 */
package portfolio4_v10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Zakaria EI Boujattoui, Linus Städtler and Anh Phuc Hoang
 * @since 01.06.2016
 * @version 5.0
 */
public class Methods {
	private Scanner sc = new Scanner(System.in);
	private Scheduling s1 = new Scheduling();
	private ArrayList<Prozess> prozessList;

	public void start() {
		try {
			// user interface
			System.out.println("==========================================");
			System.out.println("============Prozess_Simulator=============");
			System.out.println("==========================================");
			System.out.println("Geben Sie die Anzahl der Prozesse ein,");
			int temp3 = readzahlen3();
			System.out.println("==========================================");
			System.out.println("Wählen Sie die Art der Prozessherstellung:" + '\n');
			System.out.println("0 = Prozesse selbst bestimmen");
			System.out.println("1 = Zufall");
			int temp2 = readZahlen2();
			System.out.println("==========================================");
			System.out.println("Wählen Sie ein Schedulingverfahren für die Prozesse:" + '\n');
			System.out.println("0 = Synchron (Alle Verfahren testen)");
			System.out.println("1 = SJF");
			System.out.println("2 = FCFS");
			System.out.println("3 = SRTF");
			System.out.println("4 = LRTF");
			int temp = readZahlen();
			System.out.println("==========================================");

			// choosing scheduling methods
			switch (temp) {
			// case 0 all scheduling methods
			case 0:
				ArrayList<Prozess> tempList = new ArrayList<>(prozessSpeicherung(temp2, temp3));
				s1.SJF(tempList);
				s1.FCFS(tempList);
				s1.SRTF(tempList);
				s1.LRTF(tempList);
				ask();
				break;

			// case 1: "Shortest Job First"
			case 1:
				s1.SJF(prozessSpeicherung(temp2, temp3));
				ask();
				break;

			// case 2: "First Come First Served"
			case 2:
				s1.FCFS(prozessSpeicherung(temp2, temp3));
				ask();
				break;

			// case 3: "Shortest Remaining Time First"
			case 3:
				s1.SRTF(prozessSpeicherung(temp2, temp3));
				ask();
				break;

			// case 4: "Longest Remaining Time First"
			case 4:
				s1.LRTF(prozessSpeicherung(temp2, temp3));
				ask();
				break;

			default:
				System.out.println("Fehler");
				ask();
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	// user input: chooses scheduling method
	public int readZahlen() {
		int temp = 5;
		do {
			try {
				System.out.println('\n' + "(Wählen Sie eine Zahl zwischen 0 und 4)" + '\n');
				temp = sc.nextInt(); // scans the following number
			} catch (Exception e) { // if user can't read or input something
									// besides an integer --> programm stops
				System.out.println("Fehler!");
				System.out.println("Bitte das Programm neu starten.");
				System.exit(0);
			}
		} while (temp > 4 || temp < 0); // will call the do part until the user
										// chooses a number between 0 and 4

		return temp;
	}

	// user input: decision between process generating methods
	public int readZahlen2() {
		int temp2 = 3;
		do {
			try {
				System.out.println('\n' + "(Wählen Sie 0 oder 1)" + '\n');
				temp2 = sc.nextInt(); // scans the following Number
			} catch (Exception e) { // if user can't read or input something
									// besides an integer --> programm stops
				System.out.println("Fehler!");
				System.out.println("Bitte das Programm neu starten.");
				System.exit(0);
			}
		} while (temp2 > 1 || temp2 < 0); // will call the do part until the
											// user chooses a number between 0
											// and 1
		return temp2;
	}

	// user input: amount of processes
	public int readzahlen3() {
		int temp3 = 0;
		do {
			try {
				System.out.println("Wählen Sie eine Zahl zwischen 2 und 10:" + '\n');
				temp3 = sc.nextInt();
			} // scans the following Number
			catch (Exception e) { // if user can't read or input something
									// besides an integer --> Programm stops
				System.out.println("Fehler!");
				System.out.println("Bitte das Programm neu starten.");
				System.exit(0);
			}
		} while (temp3 < 2 || temp3 > 10); // will call the do part until the
											// user chooses a number between 2
											// and 10
		return temp3;
	}

	// method to generate processes and saving them inside a container
	// takes the input from readZahlen2 and readZahlen3
	public ArrayList<Prozess> prozessSpeicherung(int temp2, int temp3) {

		// version 1: user choose the processes on their on
		if (temp2 == 0) {
			prozessList = new ArrayList<>(temp3);
			for (int i = 0; i < temp3; i++) {
				System.out.println("Daten für Prozess " + (i + 1) + ": ");
				String id = "";
				do {
					System.out.println("Name: (max 5 Zeichen) ");// get the name
																	// of the
																	// process
					id = sc.next();
				} while (id.length() > 5);
				int ankunftsZeit = 21;
				do {
					try {
						System.out.println("Ankunftszeit: (0-10)");
						ankunftsZeit = sc.nextInt(); // get the start time
					} catch (Exception e) {
						System.out.println("Fehler!");
						System.out.println("Bitte das Programm neu starten.");
						System.exit(0);
					}
				} while (ankunftsZeit > 10 || ankunftsZeit < 0); // start has to
																	// be
																	// between
																	// 20 and 0

				int rechenZeit = 0;
				do {
					try {
						System.out.println("Rechenzeit: (1-10)");
						rechenZeit = sc.nextInt(); // get cpu burst time
					} catch (InputMismatchException e) {
						System.out.println("Fehler!");
						System.out.println("Bitte das Programm neu starten.");
						System.exit(0);
					}

				} while (rechenZeit < 1 || rechenZeit > 10); // cpu burst time
																// has to be
																// between 1 and
																// 10

				System.out.println("===========================================================================");
				System.out.println("Prozess " + (i + 1) + ": ID = " + id + ", Ankunftszeit = " + ankunftsZeit
						+ ", Rechenzeit = " + rechenZeit);
				System.out.println("===========================================================================");
				// saving informations as a process and into a container
				Prozess tempProzess = new Prozess(ankunftsZeit, rechenZeit, id);
				prozessList.add(i, tempProzess);

			}

		}
		// version 2: random generated processes
		else {
			prozessList = new ArrayList<>(temp3);
			// for the size of prozessList a random generated process will be
			// made and added into the container
			for (int i = 0; i < temp3; i++) {
				Prozess p = new Prozess(randomZahl2(), randomZahl(), "RP" + (i + 1)); // process
																						// p
																						// uses
																						// the
																						// methods
																						// randomZahl2
																						// and
																						// randomZahl1
																						// to
																						// generate
																						// their
																						// start
																						// and
																						// cpu
																						// busrt
																						// time
				prozessList.add(i, p); // add process p to container
				System.out.println("Prozess " + (i + 1) + ".\n id: " + p.getId() + ", Ankunftszeit: "
						+ p.getAnkunftsZeit() + ", Rechenzeit: " + p.getRechenZeit());
			}

		}
		System.out.println("\n" + "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// container sorted by ankunftsZeit
		prozessList.sort(Comparator.comparing(Prozess::getAnkunftsZeit));
		return prozessList;
	}

	public int randomZahl() {
		int random = (int) Math.round(Math.random() * 5 + 1); // random number
																// between 1-6
		return random;
	}

	public int randomZahl2() {
		int random = (int) Math.round(Math.random() * 10); // random number
															// between 0-10
		return random;
	}

	public ArrayList<Prozess> getProzessList() {
		return prozessList;
	}

	public void setProzessList(ArrayList<Prozess> prozessList) {
		this.prozessList = prozessList;
	}

	// user input: stop or repeat the programm
	public void ask() {
		System.out.println("");
		System.out.println("Wählen sie den weiteren Vorgang (0 oder 1 eingeben)\n");
		System.out.println("0 = Programm wiederholen \n1 = Ende\n");
		int n = 2;
		try {
			n = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Fehler! System wird geschlossen.");
			System.exit(0);
		}
		// 0 = again
		if (n == 0) {
			start();
		}
		// anything else = close
		else {
			sc.close();
			System.out.println("Vielen Dank für die Ausführung, bis zum nächsten Mal!");
			System.exit(0);
		}

	}
}