package com.xebia.monitor.model;

public class UptimeReq {

	private String name;
	private String url;
	private Integer freq;
	private String time_units;

	public UptimeReq() {

	}

	public UptimeReq(final String name, final String url, final Integer freq, final String time_units) {
		super();
		this.freq = freq;
		this.name = name;
		this.url = url;
		this.time_units = time_units;
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

	public String getTime_units() {
		return time_units;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (freq == null ? 0 : freq.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (time_units == null ? 0 : time_units.hashCode());
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
		final UptimeReq other = (UptimeReq) obj;
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
		if (time_units == null) {
			if (other.time_units != null) {
				return false;
			}
		} else if (!time_units.equals(other.time_units)) {
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
		return "UptimeReq [name=" + name + ", url=" + url + ", freq=" + freq + ", time_units=" + time_units + "]";
	}

}
