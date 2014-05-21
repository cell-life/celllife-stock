package org.celllife.stock.domain.alert;

import java.util.List;

import javax.persistence.QueryHint;

import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.user.User;
import org.celllife.stock.framework.logging.LogLevel;
import org.celllife.stock.framework.logging.Loggable;
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

    @Query("select new org.celllife.stock.domain.alert.AlertSummaryDto("
    		+ "u.msisdn, u.clinicCode, u.clinicName, u.coordinates, "
    		+ "(select count(*) from Alert a1 where a1.user = u.id and a1.status in ('NEW','SENT') and a1.level = 1), " 
    		+ "(select count(*) from Alert a2 where a2.user = u.id and a2.status in ('NEW','SENT') and a2.level = 2), "
    		+ "(select count(*) from Alert a3 where a3.user = u.id and a3.status in ('NEW','SENT') and a3.level = 3)"
    		+ ") "
            + "from User u "
            + "group by u.msisdn, u.clinicCode, u.clinicName")
	List<AlertSummaryDto> calculateAlertSummary();
}
