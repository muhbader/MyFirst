package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.Message;

public class TickBroadcast implements Broadcast {
	int tick;
	public TickBroadcast(int i) {
		tick=i;
	}
	public int gettick() {
		return tick;
	}
	
}