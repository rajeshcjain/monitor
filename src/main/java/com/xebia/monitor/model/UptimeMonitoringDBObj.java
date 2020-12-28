package com.xebia.monitor.model;

public class UptimeMonitoringDBObj {

	private Integer id;
	private String name;
	private String url;
	private Integer freq;
	private String units;
	private String status;
	private Integer uptime;
	private Integer downtime;

	public UptimeMonitoringDBObj() {

	}

	public UptimeMonitoringDBObj(final Integer id, final String name, final String url, final Integer freq, final String units, final String status, final Integer uptime, final Integer downtime) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.freq = freq;
		this.units = units;
		this.status = status;
		this.uptime = uptime;
		this.downtime = downtime;
	}

	public Integer getDowntime() {
		return downtime;
	}

	public Integer getUptime() {
		return uptime;
	}

	public Integer getId() {
		return id;
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
		result = prime * result + (downtime == null ? 0 : downtime.hashCode());
		result = prime * result + (freq == null ? 0 : freq.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (status == null ? 0 : status.hashCode());
		result = prime * result + (units == null ? 0 : units.hashCode());
		result = prime * result + (uptime == null ? 0 : uptime.hashCode());
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
		final UptimeMonitoringDBObj other = (UptimeMonitoringDBObj) obj;
		if (downtime == null) {
			if (other.downtime != null) {
				return false;
			}
		} else if (!downtime.equals(other.downtime)) {
			return false;
		}
		if (freq == null) {
			if (other.freq != null) {
				return false;
			}
		} else if (!freq.equals(other.freq)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
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
		if (uptime == null) {
			if (other.uptime != null) {
				return false;
			}
		} else if (!uptime.equals(other.uptime)) {
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
		return "UptimeMonitoringDBObj [id=" + id + ", name=" + name + ", url=" + url + ", freq=" + freq + ", units=" + units + ", status=" + status + ", uptime=" + uptime + ", downtime=" + downtime + "]";
	}

}
