package genericJava;

public abstract class PeriodicThread extends Thread {

	private long period;
	private boolean isFinished = false;

	public PeriodicThread(String name, long period) {
		super(name);
		this.period = period;
	}

	public void start() {
		System.out.println("Task " + this.getName() + " is started");

		while (!isFinished) {
			long timeStamp = System.currentTimeMillis();

			execute();

			try {
				Thread.sleep(period - (System.currentTimeMillis() - timeStamp));
			} catch (InterruptedException e) {
				System.out.println("Task " + this.getName() + "is interrupted.");
			} catch (IllegalArgumentException e) {
				System.out.println("Task " + this.getName() + " has missed its deadline. Fire immediately");
			}
		}

	}
	
	public long getPeriod() {
		return period;
	}

	public boolean isFinsihed() {
		return isFinished;
	}
	
	public void singalToFinish() {
		isFinished = true;
	}
	
	public abstract void execute();

}
