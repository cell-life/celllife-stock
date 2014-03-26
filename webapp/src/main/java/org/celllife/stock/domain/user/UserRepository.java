package org.celllife.stock.domain.user;

import java.util.List;

import javax.persistence.QueryHint;

import org.celllife.stock.framework.logging.LogLevel;
import org.celllife.stock.framework.logging.Loggable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

@Loggable(LogLevel.DEBUG)
@RestResource(path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    User findOneByMsisdn(@Param("msisdn") String msisdn);

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    List<User> findByClinicCode(@Param("clinicCode") String clinicCode);
}
