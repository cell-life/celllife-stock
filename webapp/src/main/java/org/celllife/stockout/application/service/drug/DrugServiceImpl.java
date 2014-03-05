package org.celllife.stockout.application.service.drug;

import org.celllife.stockout.domain.drug.Drug;
import org.celllife.stockout.domain.drug.DrugDto;
import org.celllife.stockout.domain.drug.DrugRepository;
import org.celllife.stockout.domain.exception.StockOutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugServiceImpl implements DrugService {
	
	@Autowired
	DrugRepository drugRepository;
	
	@Override
	public DrugDto createDrug(DrugDto drug) {
		Drug existingDrug = drugRepository.findOneByBarcode(drug.getBarcode());
		if (existingDrug != null) {
			throw new StockOutException("Drug with barcode '"+drug.getBarcode()+"' already exists.");
		}
		Drug newDrug = convertDrug(drug);
		Drug savedDrug = drugRepository.save(newDrug);
		return new DrugDto(savedDrug);
	}

	@Override
	public DrugDto getDrug(String barcode) {
		Drug drug = drugRepository.findOneByBarcode(barcode);
		if (drug == null) {
			return null;
		}
		return new DrugDto(drug);
	}

	private Drug convertDrug(DrugDto drug) {
		Drug newDrug = new Drug(drug.getBarcode(), drug.getDescription());
		return newDrug;
	}
}
