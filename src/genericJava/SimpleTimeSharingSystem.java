package genericJava;

import util.SystemSpec;
import util.Util;

public class SimpleTimeSharingSystem {

	public static void main(String args[]) {

		// Create one periodic thread
		PTInstance periodicT1 = new PTInstance("periodic T1", SystemSpec.PT1_Normal_Priority, SystemSpec.PT1_Period, SystemSpec.PT1_Cost);

		// Create one periodic thread
		PTInstance periodicT2 = new PTInstance("periodic T2", SystemSpec.PT2_Normal_Priority, SystemSpec.PT2_Period, SystemSpec.PT2_Cost);

		// Create one periodic thread
		PTInstance periodicT3 = new PTInstance("periodic T3", SystemSpec.PT3_Normal_Priority, SystemSpec.PT3_Period, SystemSpec.PT3_Cost);

		periodicT1.start();
		periodicT2.start();
		periodicT3.start();

		try {
			periodicT1.join();
			periodicT2.join();
			periodicT3.join();
		} catch (InterruptedException e) {
		}

	}

}

class ATInstance extends AperiodicThread {
	long cost;

	public ATInstance(String name, int priority, long cost) {
		super(name, priority);
		this.cost = cost;
	}

	@Override
	public void execute() {
		System.out.println(this.getName() + " is fired for one release");
		Util.timeConsumer(cost);
	}
}

class PTInstance extends PeriodicThread {

	int nor = 0;
	long cost;

	public PTInstance(String name, int priority, int period, long cost) {
		super(name, priority, period);
		this.cost = cost;
	}

	@Override
	public void execute() {
		System.out.println(this.getName() + " is fired for " + nor + " release");
		nor++;

		if (nor % SystemSpec.Number_Of_Period == 0) {
			this.singalToFinish();

		}

		if (this.getName().equals("periodic T3") && nor % 3 == 0) {
			ATInstance aperiodicT1 = new ATInstance("aperiodic T1", SystemSpec.AT1_Normal_Priority, SystemSpec.AT1_Cost);
			aperiodicT1.start();
		}

		Util.timeConsumer(cost);

		System.out.println(this.getName() + " execute for " + cost + " milliseconds.");
	}

}
