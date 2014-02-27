package org.celllife.stockout.domain.stock;

import org.celllife.stockout.framework.logging.LogLevel;
import org.celllife.stockout.framework.logging.Loggable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

@Loggable(LogLevel.DEBUG)
@RestResource(path = "stocks")
public interface StockRepository extends PagingAndSortingRepository<Stock, Long> {

}
