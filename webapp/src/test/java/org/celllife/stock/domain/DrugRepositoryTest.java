package org.celllife.stock.domain;

import junit.framework.Assert;

import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.drug.DrugRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:/META-INF/spring/spring-cache.xml",
        "classpath:/META-INF/spring/spring-config.xml",
        "classpath:/META-INF/spring/spring-domain.xml",
        "classpath:/META-INF/spring/spring-jdbc.xml",
        "classpath:/META-INF/spring/spring-orm.xml",
        "classpath:/META-INF/spring/spring-tx.xml",
        "classpath:/META-INF/spring-data/spring-data-jpa.xml"
})
public class DrugRepositoryTest {

    @Autowired
    private DrugRepository drugRepository;

    @Test
    public void testCreateOne() throws Exception {
    	String barcode = "0762837491";
    	Drug drug = null;
    	try {
    		drug = new Drug(barcode, "Disprin");
    		drugRepository.save(drug);    	
	    	Drug savedDrug = drugRepository.findOneByBarcode(barcode);
	    	Assert.assertNotNull(savedDrug);
    	} finally {
    		if (drug != null) drugRepository.delete(drug);
    	}
    }
}
