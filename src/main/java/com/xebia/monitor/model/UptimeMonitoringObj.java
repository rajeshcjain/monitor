package com.xebia.monitor.model;

public class UptimeMonitoringObj {

	private String name;
	private String url;
	private Integer freq;
	private String units;
	private String status;

	public UptimeMonitoringObj() {

	}

	public UptimeMonitoringObj(final String name, final String url, final Integer freq, final String units, final String status) {
		super();
		this.name = name;
		this.url = url;
		this.freq = freq;
		this.units = units;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public Integer getFreq() {
		return freq;
	}

	public String getUnits() {
		return units;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (freq == null ? 0 : freq.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (status == null ? 0 : status.hashCode());
		result = prime * result + (units == null ? 0 : units.hashCode());
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
		final UptimeMonitoringObj other = (UptimeMonitoringObj) obj;
		if (freq == null) {
			if (other.freq != null) {
				return false;
			}
		} else if (!freq.equals(other.freq)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		if (units == null) {
			if (other.units != null) {
				return false;
			}
		} else if (!units.equals(other.units)) {
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
		return "UptimeMonitoringObj [name=" + name + ", url=" + url + ", freq=" + freq + ", units=" + units + ", status=" + status + "]";
	}

}
