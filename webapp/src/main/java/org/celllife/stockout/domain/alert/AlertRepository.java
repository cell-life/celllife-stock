package org.celllife.stockout.domain.alert;

import javax.persistence.QueryHint;

import org.celllife.stockout.domain.drug.Drug;
import org.celllife.stockout.domain.user.User;
import org.celllife.stockout.framework.logging.LogLevel;
import org.celllife.stockout.framework.logging.Loggable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

@Loggable(LogLevel.DEBUG)
@RestResource(path = "alerts")
public interface AlertRepository extends PagingAndSortingRepository<Alert, Long> {

	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Alert findLatestByUserAndDrug(@Param("user") User user, @Param("drug") Drug drug);
}
