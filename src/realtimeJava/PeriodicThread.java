package realtimeJava;

import javax.realtime.MemoryArea;
import javax.realtime.MemoryParameters;
import javax.realtime.RealtimeThread;

public abstract class PeriodicThread extends RealtimeThread {

	public PeriodicThread(String name, int priority, MemoryParameters mp, MemoryArea ma) {
		super(null, null, mp, ma, null, null);
		this.setName(name);
		this.setPriority(priority);
	}

	@Override
	public void run() {
		boolean next = true;
		while (next) {
			execute();
			waitForNextPeriod();
		}
	}

	public abstract void execute();
}
