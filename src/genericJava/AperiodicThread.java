package genericJava;

public abstract class AperiodicThread extends Thread {

	private boolean isFinished = false;

	public AperiodicThread(String name, int priority) {
		super(name);
		this.setPriority(priority);
	}

	public void run() {

		while (!isFinished) {

			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}

			execute();
		}
	}

	public boolean isFinsihed() {
		return isFinished;
	}

	public void singalToFinish() {

		isFinished = true;

		synchronized (this) {
			this.notify();
		}
	}

	public abstract void execute();

}
