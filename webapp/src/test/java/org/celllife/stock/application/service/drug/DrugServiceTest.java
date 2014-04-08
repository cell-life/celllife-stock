package org.celllife.stock.application.service.drug;

import junit.framework.Assert;

import org.celllife.stock.application.service.drug.DrugService;
import org.celllife.stock.domain.drug.DrugDto;
import org.celllife.stock.domain.drug.DrugRepository;
import org.celllife.stock.test.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class DrugServiceTest {

	@Autowired
	DrugService drugService;
	
	@Autowired
	DrugRepository drugRepository;
	
	@Test
	public void testCreateDrug() throws Exception {
		Long drugId = null;
		try {
			DrugDto drug = new DrugDto("123232342234", "Test Drugs");
			DrugDto savedDrug = drugService.createDrug(drug);
			drugId = savedDrug.getId();
			Assert.assertNotNull(savedDrug);
			Assert.assertNotNull(savedDrug.getId());
			Assert.assertEquals(drug.getBarcode(), savedDrug.getBarcode());
		} finally {
			if (drugId != null) drugRepository.delete(drugId);
		}
	}
}
