package bgu.spl.mics.application.passiveObjects;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Passive data-object representing a information about an agent in MI6. You
 * must not alter any of the given public methods of this class.
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {
	private static class singletonSquad {
		private static Squad instance = new Squad();
	}

	private Map<String, Agent> agents = new HashMap<String, Agent>();
	private Object lock = new Object();

	/**
	 * Retrieves the single instance of this class.
	 */

	public boolean isEmpty() {
		if (agents.isEmpty()) {
			return true;
		}
		return false;
	}

	public static Squad getInstance() {
		// TODO: Implement this
		return singletonSquad.instance;
	}

	/**
	 * Initializes the squad. This method adds all the agents to the squad.
	 * <p>
	 * 
	 * @param agents Data structure containing all data necessary for initialization
	 *               of the squad.
	 */
	public void load(Agent[] agents) {
		// TODO Implement this
		for (int i = 0; i < agents.length; i++) {
			this.agents.put(agents[i].getSerialNumber(), agents[i]);
		}
	}

	/**
	 * Releases agents.
	 */
	public void releaseAgents(List<String> serials) {
		// TODO Implement this
	synchronized (lock) {
		for (String s : serials) {
			agents.get(s).release();
			lock.notifyAll();
		}
	}
	}

	/**
	 * simulates executing a mission by calling sleep.
	 * 
	 * @param time milliseconds to sleep
	 */
	public void sendAgents(List<String> serials, int time) {
		// TODO Implement this
			try {
				Thread.sleep(time*100);//each time-tick is 100 milliseconds
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}

		releaseAgents(serials);
	}

	/**
	 * acquires an agent, i.e. holds the agent until the caller is done with it
	 * 
	 * @param serials the serial numbers of the agents
	 * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’
	 *         otherwise
	 */
	public boolean getAgents(List<String> serials) { 
		// TODO Implement this
		for (String e : serials)
			if (!agents.containsKey(e))
				return false;
		synchronized (lock) {
			for (String e : serials) {

				while (!agents.get(e).isAvailable()) {
					// wait till the current agent is available for the mission
					try {
						lock.wait();
					} catch (InterruptedException e1) {
					}
				}

				agents.get(e).acquire();
			}
		}

		return true;
	}

	/**
	 * gets the agents names
	 * 
	 * @param serials the serial numbers of the agents
	 * @return a list of the names of the agents with the specified serials.
	 */
	public List<String> getAgentsNames(List<String> serials) {
		// TODO Implement this
		List<String> agentNames = new LinkedList<>();
		for (String e : serials) {
			agentNames.add(agents.get(e).getName());
		}
		return agentNames;
	}

}