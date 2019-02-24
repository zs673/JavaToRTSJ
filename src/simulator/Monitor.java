package simulator;

import genericJava.PeriodicThread;

public class Monitor extends PeriodicThread {

	TemperatureSimulator sim;

	public Monitor(int id, long period, TemperatureSimulator sim) {
		super("Monitor" + id, period);
		this.sim = sim;
	}

	@Override
	public void execute() {
		System.out.println("current temperature: " + sim.getCurrentT());

		if (sim.isHeating()) {
			sim.increaseT(this.getPeriod());
		} else {
			sim.decreaseT(this.getPeriod());
		}
		
		if(sim.getCurrentT() < sim.getRequiredT())
			sim.heating();
		else
			sim.cooling();
	}

}
