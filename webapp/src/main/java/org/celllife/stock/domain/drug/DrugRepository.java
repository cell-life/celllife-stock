package org.celllife.stock.domain.drug;

import javax.persistence.QueryHint;

import org.celllife.stock.framework.logging.LogLevel;
import org.celllife.stock.framework.logging.Loggable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

@Loggable(LogLevel.DEBUG)
@RestResource(path = "drugs")
public interface DrugRepository extends PagingAndSortingRepository<Drug, Long> {

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Drug findOneByBarcode(@Param("barcode") String barcode);
}
