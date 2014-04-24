package org.celllife.stock.domain.notification;

import java.util.List;

import org.celllife.stock.domain.alert.Alert;
import org.celllife.stock.framework.logging.LogLevel;
import org.celllife.stock.framework.logging.Loggable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

@Loggable(LogLevel.DEBUG)
@RestResource(path = "notifications")
public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

	@Query("SELECT n from Notification n where n.alert = :alert")
	List<Notification> findByAlert(@Param("alert") Alert alert);
	
}
