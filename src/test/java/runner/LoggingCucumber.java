package runner;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import cucumber.api.junit.Cucumber;
import cucumber.runtime.junit.FeatureRunner;

public class LoggingCucumber extends Cucumber {
	
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	
	final int featureCount;
	final AtomicInteger atomicRunCount = new AtomicInteger(0);
	volatile boolean hasFailed = false;

	public LoggingCucumber(Class<?> clazz) throws InitializationError, IOException {
		super(clazz);
		
		featureCount = getChildren().size();
		System.out.println("Running " + featureCount + " feature(s)");
	}
	
	@Override
	public void run(RunNotifier notifier) {
		notifier.addListener(new RunListener() {
			
			@Override
			public void testAssumptionFailure(Failure failure) {
				printInColour(ANSI_RED, "Test assumption failure: " + failure);
				hasFailed = true;
			}
			
			@Override
			public void testFailure(Failure failure) throws Exception {
				printInColour(ANSI_RED, "Test failure: " + failure);
				hasFailed = true;
			}
			
		});
		super.run(notifier);
	}
	
	@Override
	protected void runChild(FeatureRunner child, RunNotifier notifier) {
		final String name = child.getName();
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("Running: " + name);
		System.out.println("--------------------------------------------------------------------------------------");
		
		super.runChild(child, notifier);
		
		int runCount = atomicRunCount.incrementAndGet();
		String colour = hasFailed ? ANSI_RED : ANSI_GREEN;
		printInColour(colour, "--------------------------------------------------------------------------------------");
		printInColour(colour, "Finished feature " + runCount + " of " + featureCount + ": "  + name);
		if (hasFailed) {
			printInColour(colour, "There are failures!");
		}
		printInColour(colour, "--------------------------------------------------------------------------------------");
	}
	
	private void printInColour(String colour, String message) {
		System.out.println(colour + message + ANSI_BLACK);
	}
	
}
