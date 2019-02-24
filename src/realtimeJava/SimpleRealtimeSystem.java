package realtimeJava;

import java.util.BitSet;

import javax.realtime.Affinity;
import javax.realtime.AperiodicParameters;
import javax.realtime.AsyncEventHandler;
import javax.realtime.Clock;
import javax.realtime.MemoryArea;
import javax.realtime.MemoryParameters;
import javax.realtime.PeriodicParameters;
import javax.realtime.ProcessorAffinityException;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;

import util.SystemSpec;
import util.Util;

public class SimpleRealtimeSystem {

	public static void main(String args[]) {

		Thread.currentThread().setPriority(38);
		
		// Get current time
		Clock c = Clock.getRealtimeClock();
		
		BitSet affinitySet = new BitSet();
		affinitySet.set(SystemSpec.Core);

		// create one aperiodic thread
		ATInstance aperiodicT1 = new ATInstance("aperiodic T1", 26, null, null);
		try {
			Affinity.set(Affinity.generate(affinitySet), aperiodicT1);
		} catch (ProcessorAffinityException e) {
			e.printStackTrace();
		}
		AperiodicParameters ap1 = new AperiodicParameters(new RelativeTime(50, 0), new RelativeTime(60, 0),
				null /* new budgetOverRunHandler(aperiodicT1) */, new deadlineMissHandler(aperiodicT1));
		aperiodicT1.setReleaseParameters(ap1);

		// Create one periodic thread
		PTInstance periodicT1 = new PTInstance("periodic T1", SystemSpec.PT1_Priority, null, null);
		try {
			Affinity.set(Affinity.generate(affinitySet), periodicT1);
		} catch (ProcessorAffinityException e) {
			e.printStackTrace();
		}
		PeriodicParameters pp1 = new PeriodicParameters(c.getTime(), new RelativeTime(SystemSpec.PT1_Period, 0),
				new RelativeTime(SystemSpec.PT1_Cost, 0), new RelativeTime(SystemSpec.PT1_Deadline, 0),
				null/* new budgetOverRunHandler(periodicT1) */, new deadlineMissHandler(periodicT1));
		periodicT1.setReleaseParameters(pp1);

		// Create one periodic thread
		PTInstance periodicT2 = new PTInstance("periodic T2", SystemSpec.PT2_Priority, null, null);
		try {
			Affinity.set(Affinity.generate(affinitySet), periodicT2);
		} catch (ProcessorAffinityException e) {
			e.printStackTrace();
		}
		PeriodicParameters pp2 = new PeriodicParameters(c.getTime(), new RelativeTime(SystemSpec.PT2_Period, 0),
				new RelativeTime(SystemSpec.PT2_Cost, 0), new RelativeTime(SystemSpec.PT2_Deadline, 0),
				null/* new budgetOverRunHandler(periodicT2) */, new deadlineMissHandler(periodicT2));
		periodicT2.setReleaseParameters(pp2);

		// Create one periodic thread
		PTInstance periodicT3 = new PTInstance("periodic T3", SystemSpec.PT3_Priority, null, null);
		try {
			Affinity.set(Affinity.generate(affinitySet), periodicT3);
		} catch (ProcessorAffinityException e) {
			e.printStackTrace();
		}
		PeriodicParameters pp3 = new PeriodicParameters(c.getTime(), new RelativeTime(SystemSpec.PT3_Period, 0),
				new RelativeTime(SystemSpec.PT3_Cost, 0), new RelativeTime(SystemSpec.PT3_Deadline, 0),
				null/* new budgetOverRunHandler(periodicT3) */, new deadlineMissHandler(periodicT3));
		periodicT3.setReleaseParameters(pp3);

		periodicT1.start();
		periodicT2.start();
		periodicT3.start();

		try {
			periodicT1.join();
			periodicT2.join();
			periodicT3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class ATInstance extends AperiodicThread {
	public ATInstance(String name, int priority, MemoryParameters mp, MemoryArea ma) {
		super(name, priority, mp, ma);
	}

	@Override
	public void execute() {
		System.out.println(this.getName() + " is fired for one release");
		Util.timeConsumer(this.getReleaseParameters().getCost().getMilliseconds());
	}
}

class PTInstance extends PeriodicThread {

	public PTInstance(String name, int priority, MemoryParameters mp, MemoryArea ma) {
		super(name, priority, mp, ma);
	}


	@Override
	public void execute() {

		if (nor % SystemSpec.Number_Of_Period == 0)
			this.next = false;

		if (this.getName().equals("periodic T3") && nor % 3 == 0){
			
			BitSet affinitySet = new BitSet();
			affinitySet.set(0);

			// create one aperiodic thread
			ATInstance aperiodicT1 = new ATInstance("aperiodic T1", 26, null, null);
			try {
				Affinity.set(Affinity.generate(affinitySet), aperiodicT1);
			} catch (ProcessorAffinityException e) {
				e.printStackTrace();
			}
			AperiodicParameters ap1 = new AperiodicParameters(new RelativeTime(50, 0), new RelativeTime(60, 0),
					null /* new budgetOverRunHandler(aperiodicT1) */, new deadlineMissHandler(aperiodicT1));
			aperiodicT1.setReleaseParameters(ap1);
			aperiodicT1.start();
		}
		// apt.schedulePeriodic();

		Util.timeConsumer(this.getReleaseParameters().getCost().getMilliseconds());

		System.out.println(this.getName() + " executed for " + this.getReleaseParameters().getCost().getMilliseconds() + " milliseconds.");
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
		System.out.println("FATAL ERROR: " + myRtt.getName() + " Deadline miss handled! ");
		myRtt.deschedulePeriodic();
	}

}

//// Class overRunHandler
// class budgetOverRunHandler extends AsyncEventHandler {
// private RealtimeThread myRtt;
//
// // Constructor
// public budgetOverRunHandler(RealtimeThread rt) {
// super();
// this.myRtt = rt;
// }
//
// // Handle AsyncEvent (deadline over run)
// public void handleAsyncEvent() {
// System.out.println("FATAL ERROR: " + myRtt.getName() + " computation time
//// overrun handled! ");
// myRtt.deschedulePeriodic();
// }
//
// }
