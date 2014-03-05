package org.celllife.stockout.domain.alert;

import java.util.List;

import javax.persistence.QueryHint;

import org.celllife.stockout.domain.drug.Drug;
import org.celllife.stockout.domain.user.User;
import org.celllife.stockout.framework.logging.LogLevel;
import org.celllife.stockout.framework.logging.Loggable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

@Loggable(LogLevel.DEBUG)
@RestResource(path = "alerts")
public interface AlertRepository extends PagingAndSortingRepository<Alert, Long> {

	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query("SELECT a FROM Alert a WHERE a.user = :user AND drug = :drug AND (status = 'NEW' OR status = 'SENT' OR status = 'RESOLVED')")
	Alert findOneLatestByUserAndDrug(@Param("user") User user, @Param("drug") Drug drug);
	
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query("SELECT a FROM Alert a WHERE a.user = :user AND (status = 'NEW' OR status = 'SENT')")
	List<Alert> findOpenByUser(@Param("user") User user);
	
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	@Query("SELECT a FROM Alert a WHERE a.user = :user AND status = 'NEW'")
	List<Alert> findNewByUser(@Param("user") User user);
}
