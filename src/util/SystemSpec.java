package util;

public class SystemSpec {
	
	public static int Number_Of_Period = 100;
	
	
	private static int scale = 1;
	private static int period = 2500;
	
	/* Periodic Thread 1
	 */
	public static int PT1_Period = period *scale;
	public static int PT1_Cost = 200*scale;
	public static int PT1_Deadline = 2000*scale;
	public static int PT1_Priority = 23;
	
	/* Periodic Thread 2
	 */
	public static int PT2_Period = period*scale;
	public static int PT2_Cost = 600*scale;
	public static int PT2_Deadline = 1700*scale;
	public static int PT2_Priority = 25;
	
	/* Periodic Thread 3
	 */
	public static int PT3_Period = period*scale;
	public static int PT3_Cost = 1000*scale;
	public static int PT3_Deadline = 1100*scale;
	public static int PT3_Priority = 27;
	
	/* Aperiodic Thread 1
	 */
	public static int AT1_Cost = 100*scale;
	public static int AT1_Deadline = 50*scale;
	public static int AT1_Priority = 30;

}
