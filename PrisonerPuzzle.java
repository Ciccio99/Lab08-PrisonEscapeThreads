/**
 * File:
 * $Id: PrisonerPuzzle.java,v 1.1 2014/05/06 19:30:05 afs2842 Exp $
 * 
 * Log:
 * $Log: PrisonerPuzzle.java,v $
 * Revision 1.1  2014/05/06 19:30:05  afs2842
 * *** empty log message ***
 *
 */

/**
 * @author Alberto Scicali
 */
import java.util.*;
public class PrisonerPuzzle {
private static int prisonerNum;
	/**
	 * Main thread that controls the entirety of PrisonPuzzle. If the number of prisoners participating is not passed,
	 * then it is set to 100
	 * @param args
	 */
	public static void main(String[] args) {
		// Will run if ran in eclipse or even through terminal, either accepting a given int form the terminal argument
		// or simply setting it to 100 otherwise.
		if(args.length > 0){
			if(Integer.parseInt(args[0]) >= 2)
				prisonerNum = Integer.parseInt(args[0]);
			else
				prisonerNum = 100;
		}
		else
			prisonerNum = 100;
		
		ArrayList<Thread> prisonerThreads = new ArrayList<Thread>();
		Date time = new Date();
		long timer = time.getTime();
		
		Room theRoom = new Room(prisonerNum);
		// Create leader
		Thread leader = new Thread(new Prisoner(1, prisonerNum, theRoom));
		prisonerThreads.add(leader);
		// Create prisoners
		for(int i = 2; i <= prisonerNum; i++){
			Thread prisoner = new Thread(new Prisoner(i, theRoom));
			prisonerThreads.add(prisoner);
		}
		// Start all prisoners
		for(int i = 0; i < prisonerThreads.size(); i++){
			prisonerThreads.get(i).start();
		}
		// Join all prisoners
		for(int i = 0; i < prisonerThreads.size(); i++){
			try{
				prisonerThreads.get(i).join();
			}
			catch(InterruptedException e){}
		}
		
		int expectedNum = Room.calculateExpected(prisonerNum);
		System.out.println(String.format("Leader writes freedom note!\nTotal visits: %d\nExpected visitors: %d", Room.dayCount, expectedNum));
		Date newTime = new Date();
		System.out.println("Timer: " + (newTime.getTime() - timer));
	}
}
