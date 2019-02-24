package simulator;

public class TemperatureSimulator {

	private double currentTemperature = 0;
	private boolean isHeating = true;

	private double requiredTemperautre;

	public static double heatingPerMil = 0.001;
	public static double coolingPerMil = 0.003;


	public TemperatureSimulator(double requiredTemperautre) {
		this.requiredTemperautre = requiredTemperautre;
		
	}

	public void start() {
		System.out.println("Simulator Start. Initial Temperature: " + currentTemperature + "°„C, required temperature: " + requiredTemperautre + " °„C.");

		Monitor monitor = new Monitor(1, 1000, this);
		monitor.start();

		try {
			monitor.join();
		} catch (InterruptedException e) {
		}
	}

	public double getCurrentT() {
		return currentTemperature;
	}

	public double increaseT(double time) {
		currentTemperature += time * heatingPerMil;
		return currentTemperature;
	}

	public double decreaseT(double time) {
		currentTemperature -= time * coolingPerMil;
		return currentTemperature;
	}

	public double getRequiredT() {
		return requiredTemperautre;
	}

	public void heating() {
		isHeating = true;
	}

	public void cooling() {
		isHeating = false;
	}

	public Boolean isHeating() {
		return isHeating;
	}

	public static void main(String args[]) {

		/*
		 * The Simulator starts with Temperature 0 °„C.
		 */

		TemperatureSimulator simulator = new TemperatureSimulator(50);

		simulator.start();

	}

}
