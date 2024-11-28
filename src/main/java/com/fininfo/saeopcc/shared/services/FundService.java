package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.Asset;
import com.fininfo.saeopcc.shared.domains.Fund;
import com.fininfo.saeopcc.shared.domains.enumeration.AssetType;
import com.fininfo.saeopcc.shared.repositories.AssetRepository;
import com.fininfo.saeopcc.shared.repositories.FundRepository;
import com.fininfo.saeopcc.shared.services.dto.FundDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FundService {
  @Autowired private FundRepository fundRepository;
  @Autowired ModelMapper modelMapper;
  @Autowired private AssetRepository assetRepository;
  @Autowired private AssetService assetService;

  public Fund syncFund(Fund fund, Boolean isRef) {
    Fund synced = null;
    if (fund.getId() != null) {
      if (!isRef && (fund.getIsin() == null || fund.getIsin().isBlank())) {
        return synced;
      }
      fund.setAssetType(AssetType.FUND);
      synced = fundRepository.save(fund);
    } else log.error("Synchronize Fund Process Cannot Execute without ID !!!");
    return synced;
  }

  public Fund findByIsin(String isin) {

    List<Fund> fundsByIsin = fundRepository.findByIsin(isin);

    return !fundsByIsin.isEmpty() ? fundsByIsin.get(0) : null;
  }

  public List<FundDTO> findAll() {
    return fundRepository.findAll().stream()
        .map(x -> modelMapper.map(x, FundDTO.class))
        .collect(Collectors.toList());
  }

  public Optional<FundDTO> findOne(Long id) {
    log.debug("Request to get Fund : {}", id);
    return fundRepository.findById(id).map(fund -> modelMapper.map(fund, FundDTO.class));
  }

  public void updateIsin(Long fundId, String newIsin) {
    log.debug("Request to update ISIN for Fund ID: {} with ISIN: {}", fundId, newIsin);

    if (newIsin == null || newIsin.isEmpty() || !assetService.iSINtest(newIsin)) {
      throw new IllegalArgumentException("ISIN " + newIsin + " est invalide.");
    }

    Optional<Asset> assetOpt = assetRepository.findById(fundId);
    if (assetOpt.isEmpty()) {
      throw new IllegalArgumentException("Asset introuvable pour Fund ID: " + fundId);
    }

    Asset asset = assetOpt.get();
    log.debug(
        "Updating ISIN for Asset ID: {} from {} to {}", asset.getId(), asset.getIsin(), newIsin);
    asset.setIsin(newIsin);
    assetRepository.save(asset);
  }
}
