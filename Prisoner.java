/**
 * File:
 * $Id: Prisoner.java,v 1.1 2014/05/06 19:30:05 afs2842 Exp $
 * 
 * Log:
 * $Log: Prisoner.java,v $
 * Revision 1.1  2014/05/06 19:30:05  afs2842
 * *** empty log message ***
 *
 */

/**
 * @author Alberto Scicali
 *
 */
public class Prisoner implements Runnable {
	private static int totalPrisoners;
	private int prisonerNum;
	private int switchCount;
	public static boolean completed;
	private boolean visitedRoom;
	private static Room theRoom;
	
	/**
	 * Constructor for prisoner object, instantiates the necessary variables
	 * @param prisonerNumber
	 * @param room
	 */
	public Prisoner(int prisonerNumber, Room room){
		prisonerNum = prisonerNumber;
		visitedRoom = false;
		theRoom = room;
	}
	/**
	 * Constructs the prisoner object, and instantiates all the variables
	 * @param prisonerNumber
	 * @param total
	 * @param room
	 */
	public Prisoner(int prisonerNumber,int total, Room room){
		this.prisonerNum = prisonerNumber;
		totalPrisoners = total;
		switchCount = 1;
		visitedRoom = true;
		completed = false;
		theRoom = room;
	}
	/**
	 * Get the prisonerID
	 * @return prisoner ID number
	 */
	public int getNumber(){
		return this.prisonerNum;
	}
	/**
	 * Informs whether the prisoner has turn the switch on before or not
	 * @return true if visited, false otherwise
	 */
	public boolean getTurnedOnBefore(){
		return this.visitedRoom;
	}
	/**
	 * Retrieves how many prisoners have flipped the switch
	 * @return the count
	 */
	public int getUniqueVisitorCounter(){
		return switchCount;
	}
	/**
	 * Increments the value that holds how many prisoners have flipped
	 * the switch
	 */
	public void incrementUniqueVisitorCounter(){
		switchCount++;
		if(switchCount == totalPrisoners)
			completed = true;
	}
	/**
	 * Retrieves if the prisoners have succeeded or not
	 * @return true if done counting, false otherwise
	 */
	public boolean isCountingDone(){
		return completed;
	}
	/**
	 * Sets the prisoner's value of whether they have flipped the switch already or not
	 */
	public void setTurnedOnBefore(){
		if(visitedRoom == false){
			visitedRoom = true;
			System.out.println(String.format("On day %d, Prisoner " +
					"%d turns on the light!", Room.getDayCount(), getNumber()));
		}
	}

	/**
	 * @Overidden
	 * Runs until all the prisoners have flipped the switch
	 */
	public void run() {
		while(!theRoom.visitRoom(this)){}
	}
}
