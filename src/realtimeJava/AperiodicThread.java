package realtimeJava;

import javax.realtime.MemoryArea;
import javax.realtime.MemoryParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;

public abstract class AperiodicThread extends RealtimeThread {

	public AperiodicThread(String name, int priority, MemoryParameters mp, MemoryArea ma) {
		super(new PriorityParameters(priority), null, mp, ma, null, null);
		this.setName(name);
	}

	@Override
	public void run() {
		execute();
	}

	public abstract void execute();
}
