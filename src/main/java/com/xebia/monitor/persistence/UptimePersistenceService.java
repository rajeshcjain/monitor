package com.xebia.monitor.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.xebia.monitor.model.UptimeMonitoringDBObj;
import com.xebia.monitor.model.UptimeMonitoringObj;

@Service("uptimePersistenceService")
public class UptimePersistenceService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static String create = "insert into xup.uptime(name,url,freq,units,status,uptime,downtime) values(?,?,?,?,?,?,?)";
	private static String update = "update xup.uptime set name = ?,url = ?,freq = ?,units = ?,status = ? where id = ? ";
	private static String updateUptime = "update xup.uptime set uptime = ? where url = ?";
	private static String updateDowntime = "update xup.uptime set downtime = ? where url = ?";
	private static String getEntry4Url = "select * from xup.uptime where url=?";

	private static String getAllUptimeCheckPoints = "select * from xup.uptime";
	private static String getCheckPointsBasedOnFreq = "select * from xup.uptime where freq=?";
	private static String getCheckPointsBasedOnName = "select * from xup.uptime where name like ?";
	private static String getAllActiveCheckPoints = "select * from xup.uptime where status=?";
	private static String getCheckPoint = "select * from xup.uptime where name=? and url=? and freq=? and units=?";
	private static String updateStatus = "update xup.uptime set status=? where id=?";

	public void updateStatus(final String status, final UptimeMonitoringObj uptimeMonitoringObj) {
		final Optional<UptimeMonitoringDBObj> optionalExistingObj = getEntry4Url(uptimeMonitoringObj.getUrl());
		if (optionalExistingObj.isPresent()) {
			final UptimeMonitoringDBObj existingObj = optionalExistingObj.get();
			if (existingObj.getFreq() == uptimeMonitoringObj.getFreq() && existingObj.getName().equalsIgnoreCase(uptimeMonitoringObj.getName()) && existingObj.getUrl().equalsIgnoreCase(uptimeMonitoringObj.getUrl())
					&& existingObj.getUnits().equalsIgnoreCase(uptimeMonitoringObj.getUnits()) && existingObj.getStatus().equalsIgnoreCase(uptimeMonitoringObj.getStatus())) {
				return;
			}

			if (existingObj != null) {
				jdbcTemplate.update(updateStatus, status, existingObj.getId());
				return;
			}
		}
		jdbcTemplate.update(create, uptimeMonitoringObj.getName(), uptimeMonitoringObj.getUrl(), uptimeMonitoringObj.getFreq(), uptimeMonitoringObj.getUnits(), uptimeMonitoringObj.getStatus(), 0, 0);
		return;

	}

	public void save(final UptimeMonitoringObj uptimeMonitoringObj) {
		final Optional<UptimeMonitoringDBObj> optionalExistingObj = getEntry4Url(uptimeMonitoringObj.getUrl());
		if (optionalExistingObj.isPresent()) {
			final UptimeMonitoringDBObj existingObj = optionalExistingObj.get();
			if (existingObj.getFreq() == uptimeMonitoringObj.getFreq() && existingObj.getName().equalsIgnoreCase(uptimeMonitoringObj.getName()) && existingObj.getStatus().equalsIgnoreCase(uptimeMonitoringObj.getStatus())
					&& existingObj.getUnits().equalsIgnoreCase(uptimeMonitoringObj.getUnits()) && existingObj.getStatus().equalsIgnoreCase(uptimeMonitoringObj.getStatus())) {
				return;
			}

			if (existingObj != null) {
				jdbcTemplate.update(update, uptimeMonitoringObj.getName(), uptimeMonitoringObj.getUrl(), uptimeMonitoringObj.getFreq(), uptimeMonitoringObj.getUnits(), uptimeMonitoringObj.getStatus(), existingObj.getId());
				return;
			}
		}
		jdbcTemplate.update(create, uptimeMonitoringObj.getName(), uptimeMonitoringObj.getUrl(), uptimeMonitoringObj.getFreq(), uptimeMonitoringObj.getUnits(), uptimeMonitoringObj.getStatus());
	}

	public Optional<UptimeMonitoringDBObj> getEntry4Url(final String url) {
		final List<UptimeMonitoringDBObj> listOfObj = jdbcTemplate.query(getEntry4Url, new UptimeDBObjRowMapper(), url);
		if (listOfObj == null || listOfObj.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(listOfObj.get(0));
	}

	public void updateUptime(final String url, final int uptime) {
		jdbcTemplate.update(updateUptime, uptime, url);
	}

	public void updateDowntime(final String url, final int downtime) {
		jdbcTemplate.update(updateDowntime, downtime, url);
	}

	public List<UptimeMonitoringObj> findCheckPoints() {
		return jdbcTemplate.query(getAllUptimeCheckPoints, new UptimeRowMapper());
	}

	public List<UptimeMonitoringObj> findCheckPointsForFreq(final int freq) {
		return jdbcTemplate.query(getCheckPointsBasedOnFreq, new UptimeRowMapper(), new Object[] { freq });
	}

	public Optional<UptimeMonitoringDBObj> getCheckPoint(final UptimeMonitoringObj uptimeMonitoringObj) {
		final List<UptimeMonitoringDBObj> listOfObj = jdbcTemplate.query(getCheckPoint, new UptimeDBObjRowMapper(), uptimeMonitoringObj.getName(), uptimeMonitoringObj.getUrl(), uptimeMonitoringObj.getFreq(), uptimeMonitoringObj.getUnits());
		if (listOfObj == null || listOfObj.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(listOfObj.get(0));
	}

	public List<UptimeMonitoringObj> findCheckPointsForName(final String name) {
		final String finalName = "%" + name + "%";
		return jdbcTemplate.query(getCheckPointsBasedOnName, new UptimeRowMapper(), new Object[] { finalName });
	}

	public List<UptimeMonitoringObj> getAllActiveCheckPoints() {
		return jdbcTemplate.query(getAllActiveCheckPoints, new UptimeRowMapper(), new Object[] { "active" });
	}

	public List<UptimeMonitoringObj> getAllInActiveCheckPoints() {
		return jdbcTemplate.query(getAllActiveCheckPoints, new UptimeRowMapper(), new Object[] { "inactive" });
	}

}

class UptimeRowMapper implements RowMapper<UptimeMonitoringObj> {
	@Override
	public UptimeMonitoringObj mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final UptimeMonitoringObj obj = new UptimeMonitoringObj(rs.getString("name"), rs.getString("url"), rs.getInt("freq"), rs.getString("units"), rs.getString("status"));
		return obj;
	}
}

class UptimeDBObjRowMapper implements RowMapper<UptimeMonitoringDBObj> {
	@Override
	public UptimeMonitoringDBObj mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final UptimeMonitoringDBObj obj = new UptimeMonitoringDBObj(rs.getInt("id"), rs.getString("name"), rs.getString("url"), rs.getInt("freq"), rs.getString("units"), rs.getString("status"), rs.getInt("uptime"), rs.getInt("downtime"));
		return obj;
	}
}