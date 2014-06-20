package org.celllife.stock.domain.stock;

import java.util.Date;
import java.util.List;

import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.user.User;
import org.celllife.stock.framework.logging.LogLevel;
import org.celllife.stock.framework.logging.Loggable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

@Loggable(LogLevel.DEBUG)
@RestResource(path = "stocks")
public interface StockRepository extends PagingAndSortingRepository<Stock, Long> {
	
	@Query("SELECT s FROM Stock s WHERE s.date >= :startDate and s.date <= :endDate AND s.user = :user AND s.type = :type")
	List<Stock> findByDateBetweenByUserAndType(
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate, 
			@Param("user") User user, 
			@Param("type") StockType type);

	@Query("SELECT s FROM Stock s WHERE s.date >= :startDate and s.date <= :endDate AND s.type = :type")
	List<Stock> findByDateBetweenAndType(
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate, 
			@Param("type") StockType type);

	@Query("SELECT s from Stock s where s.type = :type and s.status = 'NEW'")
	List<Stock> findNewByType(@Param("type") StockType type);

	@Query("SELECT s from Stock s where s.type = :type and s.status = 'FAILED'")
	List<Stock> findFailedByType(@Param("type") StockType type);
	
    @Query("SELECT s from Stock s where s.type = :type and s.status = 'SENT' and s.user = :user and s.date >= :startDate and s.date <= :endDate")
    List<Stock> findSentByTypeAndUserAndDateBetween(
            @Param("type") StockType type,
            @Param("user") User user,
            @Param("startDate") Date startDate, 
            @Param("endDate") Date endDate);
	
	@Query("SELECT s from Stock s where s.drug = :drug")
	List<Stock> findByDrug(@Param("drug") Drug drug);
}
