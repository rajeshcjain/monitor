package com.xebia.monitor.model;

import java.util.concurrent.TimeUnit;

public class Task2Schedule {

	private final String url;
	private final String name;
	private final int runAfter;
	private final TimeUnit unit;
	private final int downTimeInMinutes;

	public Task2Schedule(final String url, final String name, final int runAfter, final TimeUnit unit, final int downTimeInMinutes) {
		this.url = url;
		this.name = name;
		this.runAfter = runAfter;
		this.unit = unit;
		this.downTimeInMinutes = downTimeInMinutes;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	public int getRunAfter() {
		return runAfter;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public int getDownTimeInMinutes() {
		return downTimeInMinutes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + downTimeInMinutes;
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + runAfter;
		result = prime * result + (unit == null ? 0 : unit.hashCode());
		result = prime * result + (url == null ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Task2Schedule other = (Task2Schedule) obj;
		if (downTimeInMinutes != other.downTimeInMinutes) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (runAfter != other.runAfter) {
			return false;
		}
		if (unit != other.unit) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Task2Schedule [url=" + url + ", name=" + name + ", runAfter=" + runAfter + ", unit=" + unit + ", downTimeInMinutes=" + downTimeInMinutes + "]";
	}

}
