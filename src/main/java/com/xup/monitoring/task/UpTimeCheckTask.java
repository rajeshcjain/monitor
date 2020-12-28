package com.xup.monitoring.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

import com.xebia.monitor.model.Task2Schedule;
import com.xebia.monitor.model.UptimeMonitoringDBObj;
import com.xebia.monitor.persistence.UptimePersistenceService;

public class UpTimeCheckTask implements Runnable {

	private final Task2Schedule task;

	private final UptimePersistenceService uptimePersistenceService;

	public UpTimeCheckTask(final Task2Schedule task, final UptimePersistenceService uptimePersistenceService) {
		this.task = task;
		this.uptimePersistenceService = uptimePersistenceService;
	}

	@Override
	public void run() {
		//runPing("ping " + task.getUrl());
		try {
			final String status1 = getStatus(task.getUrl());
			System.out.println(Thread.currentThread().getName() + " The Task1 executed at " + new Date());
			final Optional<UptimeMonitoringDBObj> optionalUptimeMonitoringDBObj = uptimePersistenceService.getEntry4Url(task.getUrl());
			if (status1.equalsIgnoreCase("On")) {
				uptimePersistenceService.updateUptime(task.getUrl(), totalUptime(optionalUptimeMonitoringDBObj.get().getUptime(), optionalUptimeMonitoringDBObj.get().getFreq()));
			} else {
				uptimePersistenceService.updateDowntime(task.getUrl(), totalDownTime(optionalUptimeMonitoringDBObj.get().getDowntime(), optionalUptimeMonitoringDBObj.get().getFreq()));

			}

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int totalUptime(final int uptime, final int freq) {
		return uptime + freq;
	}

	private int totalDownTime(final int downtime, final int freq) {
		return downtime + freq;

	}

	public static String getStatus(final String url) throws IOException {

		String result = "";
		try {
			final URL urlObj = new URL(url);
			final HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod("GET");
			// Set connection timeout
			con.setConnectTimeout(5000);
			con.connect();

			final int code = con.getResponseCode();
			if (code == 200) {
				result = "On";
			}
		} catch (final Exception e) {
			result = "Off";
		}
		return result;
	}

	public static void runPing(final String pingCommand) {

		try {
			final Process p = Runtime.getRuntime().exec(pingCommand);
			final BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String s = "";
			// reading output stream of the command
			while ((s = inputStream.readLine()) != null) {
				System.out.println(s);
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
