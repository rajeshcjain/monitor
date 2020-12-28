package com.xup.monitoring.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xebia.monitor.model.Task2Schedule;
import com.xebia.monitor.model.UptimeMonitoringObj;
import com.xebia.monitor.persistence.UptimePersistenceService;
import com.xup.monitoring.task.UpTimeCheckTask;

@Component("scheduler")
public class Scheduler {

	@Autowired
	UptimePersistenceService uptimePersistenceService;

	Map<UptimeMonitoringObj, ScheduledFuture<?>> mapOfTasks = new HashMap<>();

	ScheduledExecutorService execService = Executors.newScheduledThreadPool(5);

	public ScheduledFuture<?> scheduleNewTask(final UptimeMonitoringObj uptimeMonitoringObj) {
		ScheduledFuture<?> future = null;
		if (uptimeMonitoringObj.getStatus().equalsIgnoreCase("active")) {
			TimeUnit units;
			//Add it in the Sorting List.
			if (uptimeMonitoringObj.getUnits().equalsIgnoreCase("minutes")) {
				units = TimeUnit.MINUTES;
			} else {
				units = TimeUnit.HOURS;
			}

			final Task2Schedule task = new Task2Schedule(uptimeMonitoringObj.getUrl(), uptimeMonitoringObj.getName(), uptimeMonitoringObj.getFreq(), units, 0);
			future = execService.scheduleAtFixedRate(new UpTimeCheckTask(task, uptimePersistenceService), task.getRunAfter(), task.getRunAfter(), units);
			mapOfTasks.put(uptimeMonitoringObj, future);
		}
		return future;
	}

	public Map<UptimeMonitoringObj, ScheduledFuture<?>> init() {
		final List<UptimeMonitoringObj> listOfObj = uptimePersistenceService.getAllActiveCheckPoints();

		for (final UptimeMonitoringObj uptimeMonitoringObj : listOfObj) {
			if (uptimeMonitoringObj.getStatus().equalsIgnoreCase("active")) {
				TimeUnit units;
				//Add it in the Sorting List.
				if (uptimeMonitoringObj.getUnits().equalsIgnoreCase("minutes")) {
					units = TimeUnit.MINUTES;
				} else {
					units = TimeUnit.HOURS;
				}

				final Task2Schedule task = new Task2Schedule(uptimeMonitoringObj.getUrl(), uptimeMonitoringObj.getName(), uptimeMonitoringObj.getFreq(), units, 0);
				final ScheduledFuture<?> future = execService.scheduleAtFixedRate(new UpTimeCheckTask(task, uptimePersistenceService), task.getRunAfter(), task.getRunAfter(), units);
				mapOfTasks.put(uptimeMonitoringObj, future);
			}

		}
		return mapOfTasks;

	}

}
