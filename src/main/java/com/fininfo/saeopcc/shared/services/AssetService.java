package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.Asset;
import com.fininfo.saeopcc.shared.repositories.AssetRepository;
import com.fininfo.saeopcc.shared.repositories.FundRepository;
import com.fininfo.saeopcc.shared.services.dto.AssetDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing {@link Asset}. */
@Service
@Transactional
public class AssetService {

  private static final Logger log = LoggerFactory.getLogger(AssetService.class);
  @Autowired private EntityManager em;

  // @Autowired(required = false)
  @Autowired private AssetRepository assetRepository;

  @Autowired private FundRepository fundRepository;

  @Autowired private ModelMapper modelMapper;

  /**
   * Asset service constructor
   *
   * @param assetMapper
   * @param em
   * @param assetRepository
   */
  // public AssetService(AssetMapper assetMapper, EntityManager em,
  // AssetRepository assetRepository)
  // {

  // this.assetMapper = assetMapper;
  // this.assetRepository = assetRepository;
  // this.em = em;
  // }

  /**
   * Save a asset.
   *
   * @param assetDTO the entity to save.
   * @return the persisted entity.
   */
  public AssetDTO save(AssetDTO assetDTO) {
    log.debug("Request to save Asset : {}", assetDTO);
    Asset asset = modelMapper.map(assetDTO, Asset.class);
    asset = assetRepository.save(asset);
    return modelMapper.map(asset, AssetDTO.class);
  }

  /**
   * Get all the assets.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Page<AssetDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Assets");
    return assetRepository.findAll(pageable).map(x -> modelMapper.map(x, AssetDTO.class));
  }

  /**
   * Get one asset by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<AssetDTO> findOne(Long id) {
    log.debug("Request to get Asset : {}", id);
    return assetRepository.findById(id).map(x -> modelMapper.map(x, AssetDTO.class));
  }

  public Page<AssetDTO> findByIsinIsNotNull(Pageable pageable) {
    Page<Asset> page = assetRepository.findByIsinIsNotNull(pageable);
    return page.map(asset -> modelMapper.map(asset, AssetDTO.class));
  }

  /**
   * Delete the asset by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete Asset : {}", id);
    assetRepository.deleteById(id);
  }

  public List<AssetDTO> getAllAssetDtos() {

    List<Asset> assets = assetRepository.findAll();

    return assets.stream()
        .map(x -> modelMapper.map(x, AssetDTO.class))
        .collect(Collectors.toList());
  }

  public Asset toggleActive(Long entityId) {
    Asset asset =
        assetRepository
            .findById(entityId)
            .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

    asset.setActive(!asset.getActive());

    return assetRepository.save(asset);
  }

  public Asset findByIsin(String isin) {

    List<Asset> assetsByIsin = assetRepository.findByIsin(isin);

    return !assetsByIsin.isEmpty() ? assetsByIsin.get(0) : null;
  }

  public boolean iSINtest(String isin) {
    isin = isin.trim().toUpperCase();

    if (!isin.matches("^[A-Z]{2}[A-Z0-9]{9}\\d$")) return false;

    StringBuilder sb = new StringBuilder();
    for (char c : isin.substring(0, 12).toCharArray()) sb.append(Character.digit(c, 36));

    return luhnTest(sb.toString());
  }

  public boolean luhnTest(String number) {
    int s1 = 0;
    int s2 = 0;
    String reverse = new StringBuffer(number).reverse().toString();
    for (int i = 0; i < reverse.length(); i++) {
      int digit = Character.digit(reverse.charAt(i), 10);
      if (i % 2 == 0) {
        s1 += digit;
      } else {
        s2 += 2 * digit;
        if (digit >= 5) {
          s2 -= 9;
        }
      }
    }
    return (s1 + s2) % 10 == 0;
  }
}
