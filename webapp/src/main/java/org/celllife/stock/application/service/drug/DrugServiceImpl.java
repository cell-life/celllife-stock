package org.celllife.stock.application.service.drug;

import org.celllife.stock.domain.drug.Drug;
import org.celllife.stock.domain.drug.DrugDto;
import org.celllife.stock.domain.drug.DrugRepository;
import org.celllife.stock.domain.exception.StockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DrugServiceImpl implements DrugService {
	
	@Autowired
	DrugRepository drugRepository;
	
	@Override
	@Transactional
	public DrugDto createDrug(DrugDto drug) {
		Drug existingDrug = drugRepository.findOneByBarcode(drug.getBarcode());
		if (existingDrug != null) {
			throw new StockException("Drug with barcode '"+drug.getBarcode()+"' already exists.");
		}
		Drug newDrug = convertDrug(drug);
		Drug savedDrug = drugRepository.save(newDrug);
		return new DrugDto(savedDrug);
	}

	@Override
	@Transactional(readOnly = true)
	public DrugDto getDrug(String barcode) {
		Drug drug = drugRepository.findOneByBarcode(barcode);
		if (drug == null) {
			return null;
		}
		return new DrugDto(drug);
	}

	private Drug convertDrug(DrugDto drug) {
		Drug newDrug = new Drug(drug.getBarcode(), drug.getName(), drug.getDescription());
		return newDrug;
	}
}
