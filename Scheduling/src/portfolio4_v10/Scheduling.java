package portfolio4_v10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Zakaria EI Boujattoui, Linus Städtler and Anh Phuc Hoang
 * @since 01.06.2016
 * @version 5.0
 */
public class Scheduling {

	// Shortest Remaining Time First
	//
	public void SJF(ArrayList<Prozess> sizes) {
		ArrayList<Prozess> tempProzess = new ArrayList<>(sizes);
		// check if the first numbers have the same ankunftsZeit
		int tempCounter = 1;
		while (tempProzess.get(0).getAnkunftsZeit() == tempProzess.get(tempCounter).getAnkunftsZeit()) {
			tempCounter++;
			if (tempCounter == tempProzess.size()) {
				break;
			}
		}
		// if yes
		if (tempCounter > 1) {
			ArrayList<Prozess> rePosition = new ArrayList<>();
			for (int i = 0; i < tempCounter; i++) {
				rePosition.add(i, tempProzess.get(i)); // temporary container
														// for those numbers
			}
			rePosition.sort(Comparator.comparing(Prozess::getRechenZeit)); // sort
																			// by
																			// RechenZeit
			for (int i = 0; i < rePosition.size(); i++) {
				tempProzess.set(i, rePosition.get(i)); // re add them into the
														// main list
			}
		}
		// calculation for terminierung
		int[] terminierung = new int[tempProzess.size()];
		terminierung[0] = tempProzess.get(0).getAnkunftsZeit() + tempProzess.get(0).getRechenZeit(); // terminierung
																										// for
																										// the
																										// first
																										// Process
		for (int j = 1; j < tempProzess.size(); j++) {
			int temp = 0;
			while (terminierung[j - 1] >= tempProzess.get(j + temp).getAnkunftsZeit()) { // check
																							// the
																							// terminierung
																							// of
																							// the
																							// previous
																							// process
																							// with
																							// the
																							// ankunftsZeit
																							// of
																							// the
																							// j
																							// +
																							// temp
				temp++; // if the ankunftsZeit is smaller than the one of the
						// previous process, well check the next process's
						// ankuftsZeit
				if (temp + j == tempProzess.size()) {
					break; // until the last process
				}
			}
			if (temp != 0) { // if there are processes which start before the
								// previous one finished..
				ArrayList<Prozess> tempTemp = new ArrayList<>(temp);
				for (int i = 0; i < temp; i++) {
					tempTemp.add(i, tempProzess.get(i + j)); // ..we add them
																// into a
																// temporary
																// container
				}
				tempTemp.sort(Comparator.comparing(Prozess::getRechenZeit)); // ..and
																				// sort
																				// them
																				// by
																				// rechenZeit
				for (int i = 0; i < temp; i++) {
					tempProzess.remove(i + j);
					tempProzess.add(i + j, tempTemp.get(i)); // ..re-add them
				}
				// calculation for the next terminierung
				terminierung[j] = terminierung[j - 1] + tempProzess.get(j).getRechenZeit();
			} else {
				terminierung[j] = tempProzess.get(j).getAnkunftsZeit() + tempProzess.get(j).getRechenZeit();
			}
		}
		// print
		System.out.println();
		System.out.println("Shortest Job First:");
		int length = terminierung[terminierung.length - 1];
		for (int i = 0; i < length; i++) {
			if (i < 10) {
				System.out.print("\t" + "0" + i);
			} else {
				System.out.print("\t" + i);
			}
		}

		int temp = tempProzess.get(0).getAnkunftsZeit();
		int freeTime = 0;
		for (int i = 0; i < tempProzess.size(); i++) {
			System.out.print("\n" + tempProzess.get(i).getId() + "\t"); // print
																		// name
																		// of
																		// the
																		// process
			if (tempProzess.get(i).getAnkunftsZeit() > temp) { // if the
																// ankunftsZeit
																// of the next
																// process is
																// later than
																// temp
				freeTime = tempProzess.get(i).getAnkunftsZeit() - temp; // equals
																		// freeTime
				temp = temp + freeTime;
				tempLength(temp); // calls tempLength
			} else if (tempProzess.get(i).getAnkunftsZeit() <= temp) { // ankunftsZeit
																		// of
																		// the
																		// next
																		// process
																		// is
																		// earlier
																		// or
																		// equal
																		// temp
				int tempSave = tempProzess.get(i).getAnkunftsZeit();
				tempLength(temp);
				tempProzess.get(i).setAnkunftsZeit(tempSave);
				int waitZeit = temp - tempProzess.get(i).getAnkunftsZeit();
				tempProzess.get(i).setWarteZeit(waitZeit);
			}
			for (int j = 0; j < tempProzess.get(i).getRechenZeit(); j++) { // print
																			// of
																			// Burst
																			// Time
				System.out.print("*" + "\t");
			}
			temp += tempProzess.get(i).getRechenZeit(); // if done CPU Burst
														// Time
														// will be added to temp
		}
		printData(tempProzess, 0);

	}

	// First Come First Served
	//
	public void FCFS(ArrayList<Prozess> sizes) {
		int[] terminierung = new int[sizes.size()];
		int[] ankunftsZeit = new int[sizes.size()];
		for (int i = 0; i < ankunftsZeit.length; i++) {
			ankunftsZeit[i] = sizes.get(i).getAnkunftsZeit();
		}
		int[] rechenZeit = new int[sizes.size()];
		for (int i = 0; i < rechenZeit.length; i++) {
			rechenZeit[i] = sizes.get(i).getRechenZeit();
		}
		// calculation of terminierung
		terminierung[0] = ankunftsZeit[0] + rechenZeit[0];
		for (int k = 1; k < sizes.size(); k++) {
			if (ankunftsZeit[k] <= terminierung[k - 1]) {
				terminierung[k] = terminierung[k - 1] + rechenZeit[k];
			} else {
				terminierung[k] = ankunftsZeit[k] + rechenZeit[k];
			}
		}
		// print count of terminierung
		System.out.println();
		System.out.println("First Come First Served");
		int length = terminierung[terminierung.length - 1];
		for (int i = 0; i < length; i++) {
			if (i < 10) {
				System.out.print("\t" + "0" + i);
			} else {
				System.out.print("\t" + i);
			}
		}
		// FCFS print
		int temp = ankunftsZeit[0];
		int freeTime = 0;
		for (int i = 0; i < sizes.size(); i++) {
			System.out.print("\n" + sizes.get(i).getId() + "\t");
			if (ankunftsZeit[i] > temp) {
				freeTime = ankunftsZeit[i] - temp;
				temp = temp + freeTime; // space between processes + temp
				tempLength(temp);
			} else if (ankunftsZeit[i] <= temp) { // start time of the next one
													// will be the same as temp
				int tempSave = ankunftsZeit[i];
				ankunftsZeit[i] = temp;
				tempLength(ankunftsZeit[i]);
				ankunftsZeit[i] = tempSave;
				int waitZeit = temp - ankunftsZeit[i]; // wait time
				sizes.get(i).setWarteZeit(waitZeit);
			}
			for (int j = 0; j < rechenZeit[i]; j++) { // print
				System.out.print("*" + "\t");
			}
			temp += rechenZeit[i]; // if done temp + RechenZeit
		}
		printData(sizes, 1);
	}

	// Shortest Remaining Time First
	public void SRTF(ArrayList<Prozess> sizes) {
		ArrayList<Prozess> tempProzess = new ArrayList<>(sizes);
		ArrayList<Integer> ankunftsZeit = new ArrayList<>();
		ArrayList<Integer> pruefPunkt = new ArrayList<>();
		ArrayList<Prozess> bereiteProzesse = new ArrayList<>();
		int tempCounter = 1;
		// warm up session
		for (int i = 0; i < tempProzess.size(); i++) {
			ankunftsZeit.add(i, tempProzess.get(i).getAnkunftsZeit());
		}
		for (int i = 0; i < ankunftsZeit.size() - 1; i++) { // separate
															// ankunftsZeit
			if (ankunftsZeit.get(i) == ankunftsZeit.get(i + 1)) {
				ankunftsZeit.set(i, null);
			}
		}
		for (int i = 0; i < ankunftsZeit.size(); i++) { // save ankunftsZeit
			if (ankunftsZeit.get(i) != null) {
				pruefPunkt.add(ankunftsZeit.get(i));
			}
		}
		// calculation of terminierung
		int[] terminierung = new int[tempProzess.size()];
		terminierung[0] = tempProzess.get(0).getAnkunftsZeit() + tempProzess.get(0).getRechenZeit();
		for (int k = 1; k < sizes.size(); k++) {
			if (tempProzess.get(k).getAnkunftsZeit() <= terminierung[k - 1]) {
				terminierung[k] = terminierung[k - 1] + tempProzess.get(k).getRechenZeit();
			} else {
				terminierung[k] = tempProzess.get(k).getAnkunftsZeit() + tempProzess.get(k).getRechenZeit();
			}
		}

		pruefPunkt.add(terminierung[terminierung.length - 1]);
		pruefPunkt.remove(0);
		// get all the processes that have the same ankunftsZeit as the one at
		// position 0
		while (tempProzess.get(0).getAnkunftsZeit() == tempProzess.get(tempCounter).getAnkunftsZeit()) {
			tempCounter++;
			if (tempCounter == tempProzess.size()) {
				break;
			}
		}
		// range marker: none of the processes will start before that
		for (int i = 0; i < tempProzess.size(); i++) {
			for (int j = 0; j < terminierung[terminierung.length - 1]; j++) {
				boolean[] mark = new boolean[terminierung[terminierung.length - 1]];
				mark[j] = false;
				tempProzess.get(i).setMarks(mark);
			}
		}
		// line 203 -> sort by rechenZeit if there are any values
		if (tempCounter > 1) {
			ArrayList<Prozess> rePosition = new ArrayList<>();

			for (int i = 0; i < tempCounter; i++) {
				rePosition.add(i, tempProzess.get(i));
			}
			rePosition.sort(Comparator.comparing(Prozess::getRechenZeit)); // sort
																			// by
																			// rechenZeit

			for (int i = 0; i < rePosition.size(); i++) {
	
				tempProzess.set(i, rePosition.get(i));
				bereiteProzesse.add(rePosition.get(i)); // add it to a temporary
				// container
			}
		} else { // else just add the first one to the container
			bereiteProzesse.add(tempProzess.get(0));
		}
		// start of SRTF
		int start = bereiteProzesse.get(0).getAnkunftsZeit();
		for (int i = start; i < terminierung[terminierung.length - 1]; i++) {
			if (i < pruefPunkt.get(0)) {
				// i smaller than the next check point
				if (bereiteProzesse.get(0).getRechenZeit() > 0) { // rechenZeit
																	// greater
					// than
					// 0
					bereiteProzesse.get(0).setRechenZeit(bereiteProzesse.get(0).getRechenZeit() - 1);
					bereiteProzesse.get(0).getMarks()[i] = true;
				} else if (bereiteProzesse.get(0).getRechenZeit() == 0) { // equals
																			// 0
					// but
					// still not
					// at
					// the
					// checkpoint
					// bereiteProzesse.get(0).setCheck(false);
					for (int j = 0; j < tempProzess.size(); j++) { // mark it
																	// finished
																	// and give
																	// the data
																	// to the
																	// main
																	// container
						if (tempProzess.get(j).getObjectId() == bereiteProzesse.get(0).getObjectId()) {
							tempProzess.get(j).setMarks(bereiteProzesse.get(0).getMarks());
							// tempProzess.get(j).setCheck(bereiteProzesse.get(0).isCheck());
							// --> not used
							bereiteProzesse.remove(0);

							break;
						}
					}
					// if (bereiteProzesse.size() > 1) { // if there is more
					// than 1
					// process
					// in the temporary container
					// bereiteProzesse.remove(0);
					if (bereiteProzesse.size() == 0 && pruefPunkt.size() > 1) {

						// } else if (bereiteProzesse.size() == 1 &&
						// pruefPunkt.size()
						// > 1) { // if
						// there
						// is
						// only
						// 1
						// but
						// the
						// size
						// of
						// checkpoint
						// is
						// still
						// greater
						// than
						// 1
						for (int j = 0; j < tempProzess.size(); j++) {
							if (pruefPunkt.size() > 1) {
								if (tempProzess.get(j).getAnkunftsZeit() == pruefPunkt.get(0)) { // add
									// all
									// the
									// processes
									// that
									// have
									// the
									// same
									// ankunftsZeit
									// as
									// the
									// checkpoint
									bereiteProzesse.add(tempProzess.get(j));
								}
							}
						}
						// bereiteProzesse.remove(0); // remove the finished 1
						i = pruefPunkt.get(0);
						bereiteProzesse.sort(Comparator.comparing(Prozess::getRechenZeit)); // sort
						// by
						// rechenZeit
					}
					i -= 1;
				}
			}
			// i equals check point
			else if (i == pruefPunkt.get(0)) {
				// add all the process with the same ankunftsZeit as checkpoint
				if (pruefPunkt.size() > 1) {
					for (int j = 0; j < tempProzess.size(); j++) {
						if (tempProzess.get(j).getAnkunftsZeit() == pruefPunkt.get(0)) {
							bereiteProzesse.add(tempProzess.get(j));
						}
					}
					// sort the container with the processes and remove the
					// checkpoint
					bereiteProzesse.sort(Comparator.comparing(Prozess::getRechenZeit));
					pruefPunkt.remove(0);
					i -= 1;
				}
			}
		}
		// waittime
		for (int j = 0; j < tempProzess.size(); j++) {
			int counter = 0;
			for (int j2 = 0; j2 < tempProzess.get(j).getMarks().length; j2++) {
				if (tempProzess.get(j).getMarks()[j2] == true) {
					counter++;
				}
			}
			tempProzess.get(j).setRechenZeit(counter);
			for (int k = 0; k < sizes.size(); k++) {
				if (sizes.get(k).getObjectId() == tempProzess.get(j).getObjectId()) {
					sizes.get(k).setRechenZeit(counter);
				}
			}
		}
		// count the false marks from ankunftsZeit until its finished = wait
		// time
		for (int i = 0; i < tempProzess.size(); i++) {
			int count = 0;
			int lauf = tempProzess.get(i).getRechenZeit();
			for (int j = tempProzess.get(i).getAnkunftsZeit(); j < tempProzess.get(i).getMarks().length; j++) {
				if (tempProzess.get(i).getMarks()[j] == true) {
					lauf--;
					if (lauf == 0) {
						break;
					}
				} else if (tempProzess.get(i).getMarks()[j] == false) {
					count++;
				}
			}
			tempProzess.get(i).setWarteZeit(count);
		}
		int length = terminierung[terminierung.length - 1];
		gantSRTFLRTF(tempProzess, length, 2);
	}

	// Longest Remaining Time First
	public void LRTF(ArrayList<Prozess> sizes) {
		ArrayList<Prozess> tempProzess = new ArrayList<>(sizes);
		ArrayList<Integer> ankunftsZeit = new ArrayList<>();
		ArrayList<Integer> pruefPunkt = new ArrayList<>();
		ArrayList<Prozess> bereiteProzesse = new ArrayList<>();
		int tempCounter = 1;
		// warm up session
		for (int i = 0; i < tempProzess.size(); i++) {
			ankunftsZeit.add(i, tempProzess.get(i).getAnkunftsZeit());
		}
		for (int i = 0; i < ankunftsZeit.size() - 1; i++) { // separate
															// ankunfsZeit
			if (ankunftsZeit.get(i) == ankunftsZeit.get(i + 1)) {
				ankunftsZeit.set(i, null);
			}
		}
		for (int i = 0; i < ankunftsZeit.size(); i++) { // save ankunftsZeit
			if (ankunftsZeit.get(i) != null) {
				pruefPunkt.add(ankunftsZeit.get(i));
			}
		}
		// calculation of terminierung
		int[] terminierung = new int[tempProzess.size()];
		terminierung[0] = tempProzess.get(0).getAnkunftsZeit() + tempProzess.get(0).getRechenZeit();
		for (int k = 1; k < sizes.size(); k++) {
			if (tempProzess.get(k).getAnkunftsZeit() <= terminierung[k - 1]) {
				terminierung[k] = terminierung[k - 1] + tempProzess.get(k).getRechenZeit();
			} else {
				terminierung[k] = tempProzess.get(k).getAnkunftsZeit() + tempProzess.get(k).getRechenZeit();
			}
		}
		pruefPunkt.add(terminierung[terminierung.length - 1]);
		pruefPunkt.remove(0);
		// count the amount of processes that have the same ankunftsZeit as the
		// process at 0
		while (tempProzess.get(0).getAnkunftsZeit() == tempProzess.get(tempCounter).getAnkunftsZeit()) {
			tempCounter++;
			if (tempCounter == tempProzess.size()) {
				break;
			}
		}
		// range marker: see SRTF
		for (int i = 0; i < tempProzess.size(); i++) {
			for (int j = 0; j < terminierung[terminierung.length - 1]; j++) {
				boolean[] mark = new boolean[terminierung[terminierung.length - 1]];
				mark[j] = false;
				tempProzess.get(i).setMarks(mark);
			}
		}
		// if there are processes with the same ankunftsZeit as 0 they will be
		// sorted and reversed
		if (tempCounter > 1) {
			ArrayList<Prozess> rePosition = new ArrayList<>();

			for (int i = 0; i < tempCounter; i++) {
				rePosition.add(i, tempProzess.get(i));
			}
			rePosition.sort(Comparator.comparing(Prozess::getRechenZeit)); // sort
			Collections.reverse(rePosition); // reversed
			// reverse the processes that have the same RechenZeit as that of 0
			int tempC = 1;
			if (rePosition.size() > 1) {
				while (rePosition.get(0).getRechenZeit() == rePosition.get(tempC).getRechenZeit()) {
					tempC++;
					if (tempC == rePosition.size()) {
						break;
					}
				}
				if (tempC > 1) {
					ArrayList<Prozess> reP = new ArrayList<>();
					for (int k = 0; k < tempC; k++) {
						reP.add(k, rePosition.get(k));
					}
					Collections.reverse(reP); // reverse
					for (int k = 0; k < reP.size(); k++) {
						rePosition.set(k, reP.get(k));
					}

				}
			}

			for (int i = 0; i < rePosition.size(); i++) {
				tempProzess.set(i, rePosition.get(i));
				bereiteProzesse.add(rePosition.get(i));
			}
		}
		// if none then just add the first 1 into the temporary container
		else {
			bereiteProzesse.add(tempProzess.get(0));
		}

		// LRTF
		int start = bereiteProzesse.get(0).getAnkunftsZeit();
		for (int i = start; i < terminierung[terminierung.length - 1]; i++) {
			if (i < pruefPunkt.get(0)) {
				// i smaller than the check point
				if (bereiteProzesse.get(0).getRechenZeit() > 0) { // rechenZeit
																	// greater
					// than
					// 1
					bereiteProzesse.get(0).setRechenZeit(bereiteProzesse.get(0).getRechenZeit() - 1);
					bereiteProzesse.get(0).getMarks()[i] = true;
				} else if (bereiteProzesse.get(0).getRechenZeit() == 0) { // rechenZeit
					// equals 0
					// bereiteProzesse.get(0).setCheck(false);
					for (int j = 0; j < tempProzess.size(); j++) { // marks as
																	// finished
																	// and
																	// delivered
																	// to the
																	// main
																	// container
						if (tempProzess.get(j).getObjectId() == bereiteProzesse.get(0).getObjectId()) {
							tempProzess.get(j).setMarks(bereiteProzesse.get(0).getMarks());
							// tempProzess.get(j).setCheck(bereiteProzesse.get(0).isCheck());
							break;
						}
					}
					// temporary container size is greater than 1
					if (bereiteProzesse.size() > 1) {
						bereiteProzesse.remove(0); // remove the first 1
						int tempC = 1;

						// reverse all the processes that have the same
						// rechenZeit
						// as that of 0
						if (bereiteProzesse.size() > 1) {
							bereiteProzesse.sort(Comparator.comparing(Prozess::getRechenZeit)); // sort
							Collections.reverse(bereiteProzesse); // reverse
							while (bereiteProzesse.get(0).getRechenZeit() == bereiteProzesse.get(tempC)
									.getRechenZeit()) {
								tempC++;
								if (tempC == bereiteProzesse.size()) {
									break;
								}
							}
						}
						if (tempC > 1) {
							ArrayList<Prozess> reP = new ArrayList<>();
							for (int k = 0; k < tempC; k++) {
								reP.add(k, bereiteProzesse.get(k));
							}
							reP.sort(Comparator.comparing(Prozess::getObjectId));
							reP.sort(Comparator.comparing(Prozess::getAnkunftsZeit));
							for (int k = 0; k < reP.size(); k++) {
								bereiteProzesse.set(k, reP.get(k));
							}
						}
					}
					// size of temporary container = 0 and the checkpoint size
					// is greater than 1
					else if (bereiteProzesse.size() == 1 && pruefPunkt.size() > 1) {
						for (int j = 0; j < tempProzess.size(); j++) {
							if (pruefPunkt.size() > 1) { // add all the
															// processes
								// with ankunftsZeit that
								// equals checkpoint
								if (tempProzess.get(j).getAnkunftsZeit() == pruefPunkt.get(0)) {
									bereiteProzesse.add(tempProzess.get(j));
								}
							}
						}
						bereiteProzesse.remove(0); // remove the process at 0
						i = pruefPunkt.get(0);
						bereiteProzesse.sort(Comparator.comparing(Prozess::getRechenZeit)); // sort
						// by
						// rechenZeit
						Collections.reverse(bereiteProzesse); // reverse
						// reverse all the processes that have the same
						// rechenZeit
						// as that of 0
						int tempC = 1;
						if (bereiteProzesse.size() > 1) {
							while (bereiteProzesse.get(0).getRechenZeit() == bereiteProzesse.get(tempC)
									.getRechenZeit()) {
								tempC++;
								if (tempC == bereiteProzesse.size()) {
									break;
								}
							}
						}
						if (tempC > 1) {
							ArrayList<Prozess> reP = new ArrayList<>();
							for (int k = 0; k < tempC; k++) {
								reP.add(k, bereiteProzesse.get(k));
							}
							reP.sort(Comparator.comparing(Prozess::getAnkunftsZeit));
							for (int k = 0; k < reP.size(); k++) {
								bereiteProzesse.set(k, reP.get(k));
							}
						}
					}
					i -= 1;
				}
			}
			// i equals check point
			else if (i == pruefPunkt.get(0)) {
				if (pruefPunkt.size() > 1) { // add all the processes that have
					// ankunftsZeit equals checkpoint
					Prozess tempP = bereiteProzesse.get(0);
					for (int j = 0; j < tempProzess.size(); j++) {
						if (tempProzess.get(j).getAnkunftsZeit() == pruefPunkt.get(0)) {
							bereiteProzesse.add(tempProzess.get(j));
						}
					}
					bereiteProzesse.sort(Comparator.comparing(Prozess::getRechenZeit)); // sort
					Collections.reverse(bereiteProzesse); // reverse
					// sort all by ankunftsZeit
					int tempC = 1;
					if (bereiteProzesse.size() > 1) {
						while (bereiteProzesse.get(0).getRechenZeit() == bereiteProzesse.get(tempC).getRechenZeit()) {
							tempC++;
							if (tempC == bereiteProzesse.size()) {
								break;
							}
						}
					}
					if (tempC > 1) {
						ArrayList<Prozess> reP = new ArrayList<>();
						for (int k = 0; k < tempC; k++) {
							reP.add(k, bereiteProzesse.get(k));
						}
						reP.sort(Comparator.comparing(Prozess::getAnkunftsZeit));
						for (int j = 0; j < reP.size(); j++) { // if tempP is in
																// the list
							if (tempP == reP.get(j)) {
								reP.remove(j);
								reP.add(0, tempP); // tempP will be first
								break;
							}
						}
						for (int k = 0; k < reP.size(); k++) {
							bereiteProzesse.set(k, reP.get(k));
						}
					}
					pruefPunkt.remove(0); // remove checkpoint
					i -= 1;
				}
			}
		}
		// waittime
		for (int i = 0; i < tempProzess.size(); i++) {
			int count = 0;
			int lauf = 0;
			for (int j = tempProzess.get(i).getAnkunftsZeit(); j < tempProzess.get(i).getMarks().length; j++) {
				if (tempProzess.get(i).getMarks()[j] == true) { // count the
																// true
					lauf++;
					if (lauf == tempProzess.get(i).getTempLauf()) { // if the
																	// amount of
																	// true
																	// equals
																	// rechenZeit
																	// break
						break;
					}
				} else if (tempProzess.get(i).getMarks()[j] == false) { // until
																		// then
																		// count
																		// the
																		// false
					count++;
				}
			}
			tempProzess.get(i).setWarteZeit(count); // =wait time
		}
		// Print
		int length = terminierung[terminierung.length - 1];
		gantSRTFLRTF(tempProzess, length, 3);
	}

	// Print Data
	public void gantSRTFLRTF(ArrayList<Prozess> tempProzess, int length, int t) {
		System.out.println();
		switch (t) {
		case 2:
			System.out.println("Shortest Remaining Time First");
			break;

		case 3:
			System.out.println("Longest Remaining Time First");
			break;

		default:
			break;
		}
		for (int i = 0; i < length; i++) {
			if (i < 10) {
				System.out.print("\t" + "0" + i);
			} else {

				System.out.print("\t" + i);
			}
		}
		for (int i = 0; i < tempProzess.size(); i++) {
			System.out.println("");
			System.out.print("--");
			for (int c = 0; c < length; c++) {
				System.out.print("--------");
			}
			System.out.print("\n" + tempProzess.get(i).getId() + "\t");
			boolean[] tempMark = tempProzess.get(i).getMarks();
			for (int k = 0; k < tempMark.length; k++) {
				if (tempMark[k] == false) {
					System.out.print("\t");
				} else {
					System.out.print("*\t");
				}
			}
		}
		printData(tempProzess, t);
	}

	public void printData(ArrayList<Prozess> tempProzess, int t) {
		System.out.print("\n\n");
		switch (t) {
		case 0:
			System.out.println("Shortest Job First");
			break;

		case 1:
			System.out.println("First Come First Served");
			break;

		case 2:
			System.out.println("Shortest Remaining Time First");
			break;

		case 3:
			System.out.println("Longest Remaining Time First");
			break;

		default:
			break;
		}

		for (int i = 0; i < tempProzess.size(); i++) {
			System.out.println("Prozess ID: " + tempProzess.get(i).getId() + ", Ankunftszeit: "
					+ tempProzess.get(i).getAnkunftsZeit() + ", CPU Rechenzeit: " + tempProzess.get(i).getTempLauf()
					+ ", Wartezeit: " + tempProzess.get(i).getWarteZeit() + ", Laufzeit: "
					+ (tempProzess.get(i).getTempLauf() + tempProzess.get(i).getWarteZeit()));
		}
		System.out.println("Durchschnittliche Wartezeit: " + averageWait(tempProzess));
		System.out.println("Durchschnittliche Laufzeit: " + averageLauf(tempProzess));
	}

	// calculation of average wait time
	public double averageWait(ArrayList<Prozess> prozess) {
		double avgWait = 0;
		for (int i = 0; i < prozess.size(); i++) {
			avgWait = avgWait + prozess.get(i).getWarteZeit();
		}
		avgWait /= prozess.size();
		avgWait = (int) avgWait + (Math.round(Math.pow(10, 2) * (avgWait - (int) avgWait))) / (Math.pow(10, 2)); // rounded
																													// to
																													// 2
																													// decimals
																													// behind
																													// the
																													// ,
		return avgWait;
	}

	// calculation of average Burst Time
	public double averageLauf(ArrayList<Prozess> prozess) {
		double avgLauf = 0;
		for (int i = 0; i < prozess.size(); i++) {
			avgLauf = avgLauf + prozess.get(i).getTempLauf() + prozess.get(i).getWarteZeit();
		}
		avgLauf /= prozess.size();
		avgLauf = (int) avgLauf + (Math.round(Math.pow(10, 2) * (avgLauf - (int) avgLauf))) / (Math.pow(10, 2)); // rounded
																													// to
																													// 2
																													// decimals
																													// behind
																													// the
																													// ,
		return avgLauf;
	}

	// length of tabs for SJF and FCFS
	public void tempLength(int ankunftsTime) {
		int tempLength = ankunftsTime;
		for (int k = 0; k < tempLength; k++) {
			System.out.print(" " + "\t");
		}
	}
}