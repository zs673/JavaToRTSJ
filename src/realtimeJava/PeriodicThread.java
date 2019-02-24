package realtimeJava;

import javax.realtime.MemoryArea;
import javax.realtime.MemoryParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;

public abstract class PeriodicThread extends RealtimeThread {
	boolean next = true;
	int nor = 0;
	public PeriodicThread(String name, int priority, MemoryParameters mp, MemoryArea ma) {
		super(new PriorityParameters(priority), null, mp, ma, null, null);
		this.setName(name);
	}

	@Override
	public void run() {

		while (next) {
			waitForNextPeriod();
			
			System.out.println(this.getName() + " is fired for " + nor + " release.");
			nor++;
			
			execute();
			
		}
	}

	public abstract void execute();

	public void singalToFinish() {
		next = false;
	}
}
