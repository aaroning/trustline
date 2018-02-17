package com.trustline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * An integration test which starts the servers and checks the standard output
 * 
 * @author aingber
 *
 */
public class TrustlineIT {

	private static Runtime rt = Runtime.getRuntime();

	private static ProcessBuilder aliceServer;
	private static ProcessBuilder bobServer;

	File aliceOutputFile;
	File bobOutputFile;

	private void startServers() throws IOException {

		aliceServer = new ProcessBuilder("./bin/start-server.sh", "8080");
		bobServer = new ProcessBuilder("./bin/start-server.sh", "8081");
		aliceOutputFile = new File("AliceOutput");
		bobOutputFile = new File("BobOutput");
		aliceServer.redirectOutput(aliceOutputFile);
		bobServer.redirectOutput(bobOutputFile);
		aliceServer.start();
		bobServer.start();
	}

	@Test
	public void send_from_alice_to_bob() throws IOException, InterruptedException {
		startServers();
		TimeUnit.SECONDS.sleep(2); // wait for servers to start
		try {
			ProcessBuilder clientProcess = new ProcessBuilder("./bin/client.sh", "Alice", "Bob", "10");
			clientProcess.start();
			TimeUnit.SECONDS.sleep(2); //give time for client operation to complete
			List lines = FileUtils.readLines(aliceOutputFile, "UTF-8");
			assertEquals("Welcome to Trustline", lines.get(0));
			assertEquals("Trustline balance is: 0",lines.get(1));
			assertEquals("Paying 10 to Bob...", lines.get(2));
			assertEquals("Sent", lines.get(3));
			assertEquals("Trustline balance is: -10", lines.get(4));
			lines = FileUtils.readLines(bobOutputFile, "UTF-8");
			assertEquals("Welcome to Trustline", lines.get(0));
			assertEquals("Trustline balance is: 0",lines.get(1));
			assertEquals("You were paid 10!", lines.get(2));
			assertEquals("Trustline balance is: 10", lines.get(3));
		}
		finally {
			stopServers();
			removeFiles();
		}

	}

	private void stopServers() throws IOException, InterruptedException {
		aliceServer.command("./bin/stop-server.sh", "8080").start();
		bobServer.command("./bin/stop-server.sh", "8081").start();
	}

	private void removeFiles() throws IOException {
		aliceOutputFile.delete();
		bobOutputFile.delete();
		FileUtils.deleteQuietly(aliceOutputFile);
		FileUtils.deleteQuietly(bobOutputFile);
		FileUtils.deleteQuietly(new File("trustline-process-8080.pid"));
		FileUtils.deleteQuietly(new File("trustline-process-8081.pid"));
		FileUtils.deleteDirectory(new File("logs"));
	}
}
