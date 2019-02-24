package realtimeJava;

import java.util.BitSet;

import javax.realtime.Affinity;
import javax.realtime.AsyncEventHandler;
import javax.realtime.Clock;
import javax.realtime.MemoryArea;
import javax.realtime.MemoryParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.ProcessorAffinityException;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;

import util.Util;

public class SimpleRealtimeSystem {

	public static void main(String args[]) {
		// Get current time
		Clock c = Clock.getRealtimeClock();
		
		BitSet affinitySet = new BitSet();
		affinitySet.set(0);

		// Create one periodic thread
		PTInstance periodicT1 = new PTInstance("periodic T1", 5, null, null);
		try {
			Affinity.set(Affinity.generate(affinitySet), periodicT1);
		} catch (ProcessorAffinityException e) {
			e.printStackTrace();
		}
		PeriodicParameters pp1 = new PeriodicParameters(c.getTime(), new RelativeTime(1000, 0), new RelativeTime(500, 0), new RelativeTime(1000, 0),
				new budgetOverRunHandler(periodicT1), new deadlineMissHandler(periodicT1));
		periodicT1.setReleaseParameters(pp1);
		periodicT1.start();

		
		
		// Create one periodic thread
		PTInstance periodicT2 = new PTInstance("periodic T2", 10, null, null);
		try {
			Affinity.set(Affinity.generate(affinitySet), periodicT2);
		} catch (ProcessorAffinityException e) {
			e.printStackTrace();
		}
		PeriodicParameters pp2 = new PeriodicParameters(c.getTime(), new RelativeTime(1000, 0), new RelativeTime(300, 0), new RelativeTime(1000, 0),
				new budgetOverRunHandler(periodicT2), new deadlineMissHandler(periodicT2));
		periodicT2.setReleaseParameters(pp2);
		periodicT2.start();
		
		// Create one periodic thread
		PTInstance periodicT3 = new PTInstance("periodic T3", 15, null, null);
		try {
			Affinity.set(Affinity.generate(affinitySet), periodicT3);
		} catch (ProcessorAffinityException e) {
			e.printStackTrace();
		}
		PeriodicParameters pp3 = new PeriodicParameters(c.getTime(), new RelativeTime(1000, 0), new RelativeTime(100, 0), new RelativeTime(1000, 0),
				new budgetOverRunHandler(periodicT3), new deadlineMissHandler(periodicT3));
		periodicT3.setReleaseParameters(pp3);
		periodicT3.start();

	}

}

class PTInstance extends PeriodicThread {
	int nor = 0;

	public PTInstance(String name, int priority, MemoryParameters mp, MemoryArea ma) {
		super(name,priority, mp, ma);
	}

	@Override
	public void execute() {
		System.out.println(this.getName() + " is fired for " + nor + " release");
		nor++;
		System.out.println("execute for " + this.getReleaseParameters().getCost().getMilliseconds() + " milliseconds.");
		Util.timeConsumer(this.getReleaseParameters().getCost().getMilliseconds());
	}

}

// Class overRunHandler
class deadlineMissHandler extends AsyncEventHandler {
	private RealtimeThread myRtt;

	// Constructor
	public deadlineMissHandler(RealtimeThread rt) {
		super();
		this.myRtt = rt;
	}

	// Handle AsyncEvent (deadline over run)
	public void handleAsyncEvent() {
		System.out.println("Fatal Error: " + myRtt.getName() + " Deadline miss handled! ");
		myRtt.deschedulePeriodic();
	}

}

// Class overRunHandler
class budgetOverRunHandler extends AsyncEventHandler {
	private RealtimeThread myRtt;

	// Constructor
	public budgetOverRunHandler(RealtimeThread rt) {
		super();
		this.myRtt = rt;
	}

	// Handle AsyncEvent (deadline over run)
	public void handleAsyncEvent() {
		System.out.println("Fatal Error: " + myRtt.getName() + " computation time overrun handled! ");
		myRtt.deschedulePeriodic();
	}

}
