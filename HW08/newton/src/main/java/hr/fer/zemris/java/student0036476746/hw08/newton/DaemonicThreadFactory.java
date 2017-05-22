package hr.fer.zemris.java.student0036476746.hw08.newton;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Class which creates daemonic threads.
 * Implements ThreadFactory.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class DaemonicThreadFactory implements ThreadFactory {
	
	@Override
	public Thread newThread(Runnable r) {
		Thread thread = Executors.defaultThreadFactory().newThread(r);
		thread.setDaemon(true);
		return thread;
	}
}
