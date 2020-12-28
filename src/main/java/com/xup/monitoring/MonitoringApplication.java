package com.xup.monitoring;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.xebia.monitor.model.UptimeMonitoringObj;
import com.xebia.monitor.persistence.UptimePersistenceService;
import com.xup.monitoring.schedule.Scheduler;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.xebia.monitor.persistence", "com.xup.monitoring.schedule" })
public class MonitoringApplication implements CommandLineRunner {

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private UptimePersistenceService uptimePersistenceService;

	public static void main(final String[] args) {
		SpringApplication.run(MonitoringApplication.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {
		final Map<UptimeMonitoringObj, ScheduledFuture<?>> map = scheduler.init();
		while (true) {
			final List<UptimeMonitoringObj> listOfInActiveObj = uptimePersistenceService.getAllInActiveCheckPoints();
			convertStatusNRemove4mMap(listOfInActiveObj, "active", map);

			final List<UptimeMonitoringObj> listOfActiveObj = uptimePersistenceService.getAllActiveCheckPoints();
			AddRecentlyAddedTasks2Map(listOfActiveObj, map);
			//Running it after every 30 seconds..
			Thread.sleep(1000 * 30);
		}

	}

	void convertStatusNRemove4mMap(final List<UptimeMonitoringObj> listOfObj, final String toStatus, final Map<UptimeMonitoringObj, ScheduledFuture<?>> map) {
		for (final UptimeMonitoringObj obj : listOfObj) {
			final UptimeMonitoringObj newObj = new UptimeMonitoringObj(obj.getName(), obj.getUrl(), obj.getFreq(), obj.getUnits(), toStatus);
			if (map.containsKey(newObj) && !map.get(newObj).isDone()) {
				final boolean isCancelled = map.get(newObj).cancel(true);
				System.out.println("The task " + newObj + " is cancelled: " + isCancelled);
				map.remove(newObj);
			}
		}
	}

	void AddRecentlyAddedTasks2Map(final List<UptimeMonitoringObj> listOfObj, final Map<UptimeMonitoringObj, ScheduledFuture<?>> map) {
		for (final UptimeMonitoringObj obj : listOfObj) {
			if (!map.containsKey(obj)) {
				map.put(obj, scheduler.scheduleNewTask(obj));
			}
		}
	}

}
