package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.HeaderUtil;
import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;
import com.fininfo.saeopcc.shared.domains.Asset;
import com.fininfo.saeopcc.shared.services.AssetQueryService;
import com.fininfo.saeopcc.shared.services.AssetService;
import com.fininfo.saeopcc.shared.services.dto.AssetCriteria;
import com.fininfo.saeopcc.shared.services.dto.AssetDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST controller for managing {@link com.backupswift.referentiel.domain.Asset}. */
@RestController
@RequestMapping("/api/v1")
public class AssetResource {

  private final Logger log = LoggerFactory.getLogger(AssetResource.class);

  private static final String ENTITY_NAME = "primaryarkerAsset";

  @Value("${spring.application.name}")
  private String applicationName;

  private final AssetService assetService;

  private final AssetQueryService assetQueryService;

  private final ModelMapper modelMapper;

  public AssetResource(
      AssetService assetService, AssetQueryService assetQueryService, ModelMapper modelMapper) {
    this.assetService = assetService;
    this.assetQueryService = assetQueryService;
    this.modelMapper = modelMapper;
  }

  /**
   * {@code POST /assets} : Create a new asset.
   *
   * @param assetDTO the assetDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *     assetDTO, or with status {@code 400 (Bad Request)} if the asset has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/assets")
  public ResponseEntity<AssetDTO> createAsset(@RequestBody AssetDTO assetDTO)
      throws URISyntaxException {
    log.debug("REST request to save Asset : {}", assetDTO);
    if (assetDTO.getId() != null) {
      throw new BadRequestAlertException(
          "A new asset cannot already have an ID", ENTITY_NAME, "idexists");
    }

    AssetDTO result = assetService.save(assetDTO);

    return ResponseEntity.created(new URI("/api/assets/" + result.getId()))
        .headers(
            HeaderUtil.createEntityCreationAlert(
                applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT /assets} : Updates an existing asset.
   *
   * @param assetDTO the assetDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *     assetDTO, or with status {@code 400 (Bad Request)} if the assetDTO is not valid, or with
   *     status {@code 500 (Internal Server Error)} if the assetDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/assets")
  public ResponseEntity<AssetDTO> updateAsset(@RequestBody AssetDTO assetDTO)
      throws URISyntaxException {
    log.debug("REST request to update Asset : {}", assetDTO);
    if (assetDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    AssetDTO result = assetService.save(assetDTO);
    return ResponseEntity.ok()
        .headers(
            HeaderUtil.createEntityUpdateAlert(
                applicationName, true, ENTITY_NAME, assetDTO.getId().toString()))
        .body(result);
  }

  @GetMapping("/assets/all")
  public ResponseEntity<List<AssetDTO>> getAllAssets(AssetCriteria criteria) {
    log.debug("REST request to get Assets by criteria: {}", criteria);
    List<AssetDTO> assetDTOs = assetQueryService.findByCriteria(criteria);
    return ResponseEntity.ok().body(assetDTOs);
  }

  /**
   * {@code GET /assets} : get all the assets.
   *
   * @param pageable the pagination information.
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assets in body.
   */
  @GetMapping("/assets")
  public ResponseEntity<List<AssetDTO>> getAssets(AssetCriteria criteria, Pageable pageable) {
    log.debug("REST request to get Assets by criteria: {}", criteria);
    Page<AssetDTO> page = assetQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET /assets/count} : count all the assets.
   *
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
   */
  @GetMapping("/assets/count")
  public ResponseEntity<Long> countAssets(AssetCriteria criteria) {
    log.debug("REST request to count Assets by criteria: {}", criteria);
    return ResponseEntity.ok().body(assetQueryService.countByCriteria(criteria));
  }

  /**
   * {@code GET /assets/:id} : get the "id" asset.
   *
   * @param id the id of the assetDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetDTO, or
   *     with status {@code 404 (Not Found)}.
   */
  @GetMapping("/assets/{id}")
  public ResponseEntity<AssetDTO> getAsset(@PathVariable Long id) {
    log.debug("REST request to get Asset : {}", id);
    Optional<AssetDTO> assetDTO = assetService.findOne(id);
    return ResponseUtil.wrapOrNotFound(assetDTO);
  }

  /**
   * {@code DELETE /assets/:id} : delete the "id" asset.
   *
   * @param id the id of the assetDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/assets/{id}")
  public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
    log.debug("REST request to delete Asset : {}", id);
    assetService.delete(id);
    return ResponseEntity.noContent()
        .headers(
            HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
        .build();
  }

  @PutMapping("/assets/{id}/toggleactive")
  public ResponseEntity<AssetDTO> toggleActive(@PathVariable Long id) {
    Asset asset = assetService.toggleActive(id);

    if (asset != null) {
      AssetDTO assetDTO = modelMapper.map(asset, AssetDTO.class);
      return ResponseEntity.ok().body(assetDTO);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
