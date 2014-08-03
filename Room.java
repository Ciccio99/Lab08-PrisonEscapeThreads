import java.util.Random;

/**
 * File:
 * $Id: Room.java,v 1.1 2014/05/06 19:30:06 afs2842 Exp $
 * 
 * Log:
 * $Log: Room.java,v $
 * Revision 1.1  2014/05/06 19:30:06  afs2842
 * *** empty log message ***
 *
 */

/**
 * @author Alberto Scicali
 *
 */
public class Room {
	private static int switchState;
	public static int dayCount;
	private static int randomId;
	private int totalPrisoners;
	/**
	 * Constructs a room object and instantiates all the variables
	 * @param numPrisoners
	 */
	public Room(int numPrisoners){
		switchState = 0;
		dayCount = 0;
		Random r = new Random();
		totalPrisoners = numPrisoners;
		randomId = r.nextInt(numPrisoners) + 1;
	}
	/**
	 * Retrieves the number of days it takes for the prisoners to be freed
	 * @return number of day
	 */
	public static int getDayCount(){
		return dayCount;
	}
	/**
	 * Calculates the expected number of day that it will take for the prisoners to finish counting
	 * @param n
	 * @return
	 */
	public static int calculateExpected(int n){
		double result = 0.0;
		for(double i = 1; i <= n-1; i++){
			result += n/(n-i);
		}
		result += n*(n-1);
		return ((int) Math.ceil(result));
	}
	/**
	 * Controls what the threads do when sent to the room.
	 * If the thread's number is not the same as the random number, then it is set to wait.
	 * Once the rightly numbered person enters the room, all threads are notified to try to get in again.
	 * This is done for time efficiency reasons.
	 * @param prisoner
	 * @return true if the leader is done counting, false otherwise
	 */
	public synchronized boolean visitRoom(Prisoner prisoner){
		if(prisoner.getNumber() == randomId){
			dayCount++;
			if(switchState == 0 && !prisoner.getTurnedOnBefore()){
				switchState = 1;
				prisoner.setTurnedOnBefore();
			}
			else if(switchState == 1 && prisoner.getNumber() == 1){
				switchState = 0;
				prisoner.incrementUniqueVisitorCounter();
				System.out.println(String.format("On day %d, Leader updates the unique" +
						" unique visitor count to: %d", getDayCount(), prisoner.getUniqueVisitorCounter()));
			}
			Random r = new Random();
			randomId = r.nextInt(totalPrisoners) + 1;
			notifyAll();
		}
		else{
			try{
				wait();
			} catch (InterruptedException e) {
			}
		}
		if(prisoner.isCountingDone())
			return true;
		return false;
	}

}
