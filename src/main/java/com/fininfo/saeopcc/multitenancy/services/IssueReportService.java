package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount;
import com.fininfo.saeopcc.multitenancy.domains.IssueReport;
import com.fininfo.saeopcc.multitenancy.repositories.CompartementRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueAccountRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueReportRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueDTO;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueReportDTO;
import com.fininfo.saeopcc.multitenancy.services.dto.SummaryDTO;
import com.fininfo.saeopcc.shared.domains.Asset;
import com.fininfo.saeopcc.shared.domains.FiscalNature;
import com.fininfo.saeopcc.shared.domains.Fund;
import com.fininfo.saeopcc.shared.domains.Issuer;
import com.fininfo.saeopcc.shared.domains.SDG;
import com.fininfo.saeopcc.shared.domains.enumeration.AssetType;
import com.fininfo.saeopcc.shared.domains.enumeration.FundCategory;
import com.fininfo.saeopcc.shared.domains.enumeration.FundClassification;
import com.fininfo.saeopcc.shared.domains.enumeration.FundForm;
import com.fininfo.saeopcc.shared.domains.enumeration.FundGuarantee;
import com.fininfo.saeopcc.shared.domains.enumeration.FundRate;
import com.fininfo.saeopcc.shared.domains.enumeration.FundStatus;
import com.fininfo.saeopcc.shared.domains.enumeration.FundType;
import com.fininfo.saeopcc.shared.domains.enumeration.Guarantee;
import com.fininfo.saeopcc.shared.domains.enumeration.Holding;
import com.fininfo.saeopcc.shared.domains.enumeration.PaiementStatusBond;
import com.fininfo.saeopcc.shared.domains.enumeration.PaiementStatusEquity;
import com.fininfo.saeopcc.shared.domains.enumeration.RateType;
import com.fininfo.saeopcc.shared.domains.enumeration.SecurityForm;
import com.fininfo.saeopcc.shared.domains.enumeration.VotingRight;
import com.fininfo.saeopcc.shared.repositories.FiscalNatureRepository;
import com.fininfo.saeopcc.shared.repositories.FundRepository;
import com.fininfo.saeopcc.shared.repositories.IssuerRepository;
import com.fininfo.saeopcc.shared.repositories.SDGRepository;
import com.fininfo.saeopcc.shared.services.dto.AssetDTO;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IssueReportService {

  private static final String FORME_LABEL = "Forme";

  @Autowired private IssueAccountRepository issueAccountDTORepository;

  @Autowired private IssueReportRepository admissionLetterRepository;
  @Autowired private IssueAccountRepository issueAccountRepository;

  @Autowired private IssuerRepository issuerRepository;

  @Autowired private FiscalNatureRepository fiscalNatureRepository;

  @Autowired private SDGRepository sdgRepository;

  @Autowired private ModelMapper modelMapper;

  @Autowired private FundRepository fundRepository;
  @Autowired private IssueRepository issueRepository;

  @Autowired private CompartementRepository compartementRepository;

  public IssueReportDTO generateIssueReport(Long issueAccountDTOId) {
    Optional<IssueAccount> optionalAccount = issueAccountDTORepository.findById(issueAccountDTOId);
    if (!optionalAccount.isPresent()) {
      throw new IllegalArgumentException("Admission Account not found");
    }

    IssueAccount issueAccountDTO = optionalAccount.get();

    IssueReport admissionLetter = new IssueReport();
    admissionLetter.setIssueAccount(issueAccountDTO);

    IssueReport savedLetter = admissionLetterRepository.save(admissionLetter);

    return modelMapper.map(savedLetter, IssueReportDTO.class);
  }

  public byte[] generateIssueReportPdf(AssetDTO assetDTO, Long issueId) {
    Optional<Issue> optionalIssue = issueRepository.findById(issueId);
    String quantity =
        optionalIssue.isPresent() ? optionalIssue.get().getQuantity().toString() : "N/A";

    Asset asset = modelMapper.map(assetDTO, Asset.class);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));
    // Document document = new Document(pdf);
    Document document = new Document(pdf, PageSize.A4);
    document.setMargins(50, 20, 50, 20);
    document.add(new Paragraph("\n\n"));
    PdfFont font = null;
    try {
      font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    } catch (IOException e) {
      System.err.println("Erreur lors du chargement de la police : " + e.getMessage());
      return null; // ou gérer l'exception différemment
    }
    document.setFont(font).setFontSize(9); // Réglage de la police à taille 10

    Paragraph titleParagraph = new Paragraph();
    if (!asset.getAssetType().equals(AssetType.FUND)) {

      titleParagraph.add(new Text("ANNEXE5:").setBold().setUnderline(1, -2));
    }
    if (asset.getAssetType().equals(AssetType.FUND)) {
      titleParagraph.add(" Lettre comptable d’admission d’OPCC");
    } else {
      titleParagraph.add(" Lettre comptable d'admission");
    }

    titleParagraph.setTextAlignment(TextAlignment.CENTER);

    Div titleDiv =
        new Div()
            .add(titleParagraph)
            .setBorder(new SolidBorder(1f))
            .setWidth(UnitValue.createPercentValue(90))
            .setHorizontalAlignment(HorizontalAlignment.CENTER);

    document.add(titleDiv);

    Color lightBlue = new DeviceRgb(214, 230, 244);
    Color c1Color = new DeviceRgb(172, 185, 202);
    if (!asset.getAssetType().equals(AssetType.FUND)) {
      document.add(
          new Paragraph(
              "Nous vous prions de bien vouloir admettre aux opérations de MAROCLEAR la valeur et les quantités mentionnées ci-après :"));
    }

    Table table = new Table(2);
    table.setWidth(UnitValue.createPercentValue(100));
    Cell header1 = new Cell();
    header1.add(new Paragraph("Information"));
    header1.setTextAlignment(TextAlignment.CENTER);
    header1.setWidth(UnitValue.createPercentValue(50));

    Cell header2 = new Cell();
    header2.add(new Paragraph("Détail"));
    header2.setTextAlignment(TextAlignment.CENTER);
    header2.setWidth(UnitValue.createPercentValue(50));

    table.addCell(header1);
    table.addCell(header2);
    if (asset.getAssetType().equals(AssetType.BOND)) {
      Cell categoryCell =
          new Cell().add(new Paragraph("Catégorie de la valeur")).setBackgroundColor(lightBlue);
      Optional<FiscalNature> fnatopt =
          fiscalNatureRepository.findById(asset.getFiscalNature().getId());
      if (fnatopt.isPresent()) {

        Cell assetTypeCell =
            new Cell()
                .add(new Paragraph(fnatopt.get().getDescription()))
                .setBackgroundColor(lightBlue);
        table.addCell(categoryCell);
        table.addCell(assetTypeCell);
      }
    }
    if (asset.getAssetType().equals(AssetType.FUND)) {
      Optional<Fund> fundOpt = fundRepository.findById(asset.getId());
      Fund fund = (fundOpt.isPresent() ? fundOpt.get() : null);
      if (fund != null) {
        Cell categoryCell =
            new Cell().add(new Paragraph("Catégorie de la valeur")).setBackgroundColor(lightBlue);
        table.addCell(categoryCell);
        table.addCell(
            createEnumCheckBoxList(FundType.values(), fund != null ? fund.getFundType() : null));
      }
    }
    if (!asset.getAssetType().equals(AssetType.FUND)) {

      Optional<Issuer> issuerOpt = issuerRepository.findById(assetDTO.getIssuerId());
      Issuer issuer = (issuerOpt.isPresent() ? issuerOpt.get() : null);
      if (issuer != null) {

        table.addCell("Emetteur");
        table.addCell(issuer.getDescription());
      }
    }
    if (asset.getAssetType().equals(AssetType.FUND)) {

      Optional<SDG> sdgOpt = sdgRepository.findById(assetDTO.getSdgId());
      SDG sdg = (sdgOpt.isPresent() ? sdgOpt.get() : null);
      if (sdg != null) {

        table.addCell("SDG");
        table.addCell(sdg.getDescription());
      }
    }
    Cell libelleCell =
        new Cell().add(new Paragraph("Libellé de la valeur")).setBackgroundColor(lightBlue);
    Cell libCell =
        new Cell().add(new Paragraph(asset.getDescription())).setBackgroundColor(lightBlue);
    table.addCell(libelleCell);
    table.addCell(libCell);

    table.addCell("Code de la valeur");
    table.addCell(asset.getCode());
    Cell secquantityCell =
        new Cell().add(new Paragraph("Quantité de titres")).setBackgroundColor(lightBlue);
    Cell secquantity2Cell = new Cell().add(new Paragraph(quantity)).setBackgroundColor(lightBlue);
    table.addCell(secquantityCell);
    table.addCell(secquantity2Cell);
    if (asset.getAssetType().equals(AssetType.FUND)) {
      Optional<Fund> fundOpt = fundRepository.findById(asset.getId());
      Fund fund = (fundOpt.isPresent() ? fundOpt.get() : null);
      if (fund != null) {
        Cell categoryCell = new Cell().add(new Paragraph("Catégorie du fonds"));
        table.addCell(categoryCell);
        table.addCell(
            createEnumCheckBoxList(
                FundCategory.values(), fund != null ? fund.getFundCategory() : null));
        // Cell categoryCell2 =
        //     new Cell().add(new Paragraph("Composition")).setBackgroundColor(lightBlue);
        // table.addCell(categoryCell2);
        // table
        //     .addCell(
        //         createEnumCheckBoxList(
        //             FundClassification.values(),
        //             fund != null ? fund.getFundClassification() : null))
        //     .setBackgroundColor(lightBlue);
      }
    }

    document.add(table);
    document.add(new Paragraph(" "));

    if (asset.getAssetType().equals(AssetType.FUND)) {
      Optional<Fund> fundOpt = fundRepository.findById(asset.getId());
      Fund fund = (fundOpt.isPresent() ? fundOpt.get() : null);
      if (fund != null) {
        Table mainTable = new Table(4);
        mainTable.setWidth(UnitValue.createPercentValue(100));
        mainTable.setBorder(Border.NO_BORDER);

        Table rateTypeTable =
            createEnumTable(FundRate.values(), fund != null ? fund.getFundRate() : null, "Taux");
        Table guaranteeTable =
            createEnumTable(
                FundGuarantee.values(), fund != null ? fund.getFundGuarantee() : null, "Garantie");
        Table paymentStatusTable =
            createEnumTable(FundStatus.values(), fund.getFundStatus(), "Statut");

        Table securityFormTable =
            createEnumTable(FundForm.values(), fund.getFundForm(), FORME_LABEL);

        mainTable.addCell(new Cell().add(rateTypeTable).setBorder(Border.NO_BORDER));
        mainTable.addCell(new Cell().add(guaranteeTable).setBorder(Border.NO_BORDER));
        mainTable.addCell(new Cell().add(paymentStatusTable).setBorder(Border.NO_BORDER));
        mainTable.addCell(new Cell().add(securityFormTable).setBorder(Border.NO_BORDER));

        document.add(mainTable);
      }
    }

    if (asset.getAssetType().equals(AssetType.FUND)) {
      document.add(
          new Paragraph(
              "Nous vous prions de bien vouloir admettre aux opérations de MAROCLEAR la valeur et les quantités mentionnées ci-après :"));
    }

    document.add(new Paragraph(""));
    document.add(new Paragraph("Ecritures à comptabiliser:"));
    document.add(new Paragraph(""));

    Table table2 = new Table(4);
    table2.setWidth(UnitValue.createPercentValue(100));

    table2.addCell(new Cell().setBorder(Border.NO_BORDER));
    table2.addCell("N° de compte");
    table2.addCell("GESTIONNAIRE DU COMPTE");
    table2.addCell("Libelle du compte");
    Cell header3 = new Cell();
    header3.add(new Paragraph("A DEBITER").setBackgroundColor(lightBlue));
    Cell header6 = new Cell();
    header6.setBackgroundColor(lightBlue);
    Cell header4 = new Cell();
    header4.add(new Paragraph("MAROCLEAR").setBackgroundColor(lightBlue));
    Cell header5 = new Cell();
    header5.add(new Paragraph("EMISSION").setBackgroundColor(lightBlue));

    table2.addCell(header3);
    table2.addCell(header6);
    table2.addCell(header4);
    table2.addCell(header5);
    table2.addCell("A CREDITER");
    table2.addCell("");
    if (asset.getAssetType().equals(AssetType.FUND)) {
      table2.addCell("CDG CAPITAL");
    } else {

      table2.addCell("");
    }
    table2.addCell("TITRES A REPARTIR");

    document.add(table2);
    if (!asset.getAssetType().equals(AssetType.FUND)) {
      document.add(
          new Paragraph(
              "Nous attestons avoir été mandaté par l’émetteur pour vous faire parvenir ses instructions \r\n"
                  + //
                  "A…………………………, le………………………..\r\n"
                  + //
                  ""));
    }
    if (asset.getAssetType().equals(AssetType.FUND)) {
      document.add(
          new Paragraph(
              "Nous attestons avoir été mandaté par l’émetteur pour vous faire parvenir ses instructions \r\n"));
    }

    Table table3 = new Table(2);
    table3.setWidth(UnitValue.createPercentValue(100));

    Cell cell1 =
        new Cell()
            .add(
                new Paragraph("CACHET ET SIGNATURE DE L’AFFILIE OU DE SON MANDATAIRE")
                    .setBackgroundColor(c1Color))
            .setWidth(UnitValue.createPercentValue(50)); // 50% de la largeur totale

    Cell cell2 =
        new Cell()
            .add(new Paragraph("ACCUSE DE RECEPTION MAROCLEAR").setBackgroundColor(c1Color))
            .setWidth(UnitValue.createPercentValue(50)); // 50% de la largeur totale
    Cell emptyCell1 = new Cell();
    emptyCell1.setHeight(60);
    Cell emptyCell2 = new Cell();
    emptyCell2.setHeight(60);

    table3.addCell(cell1);
    table3.addCell(cell2);
    table3.addCell(emptyCell1);
    table3.addCell(emptyCell2);

    document.add(table3);
    if (asset.getAssetType().equals(AssetType.FUND)) {
      document.add(
          new Paragraph(
              "\t\r\n"
                  + //
                  "A………RABAT …………………, le……02/06/2023…………………..\r\n"
                  + //
                  ""));
    }

    document.close();

    Optional<Compartement> compartement = compartementRepository.findByFund_Id(asset.getId());
    if (compartement.isPresent()) {

      Optional<IssueAccount> issueAccount =
          issueAccountRepository.findByCompartement_Id(compartement.get().getId());

      if (issueAccount.isPresent()) {
        generateIssueReport(issueAccount.get().getId());
      }
    }
    return outputStream.toByteArray();
  }

  public Table createEnumTable(Enum<?>[] enumValues, Enum<?> selectedValue, String header) {
    Color lightGray = new DeviceRgb(213, 220, 228); // RVB pour le bleu ciel

    Table enumTable = new Table(1);

    String checkedBoxHtml = "<input type=\"checkbox\" checked />";
    String uncheckedBoxHtml = "<input type=\"checkbox\"/>";

    // Add the header
    Paragraph headerParagraph = new Paragraph(header);
    headerParagraph.setTextAlignment(TextAlignment.CENTER);
    headerParagraph.setBackgroundColor(lightGray);
    enumTable.addCell(headerParagraph);

    // Iterate through the enumeration values
    for (Enum enumValue : enumValues) {
      log.debug("Current Enum : {}", enumValue);
      Div cellElement = new Div();

      // Get the custom label for the enum value
      String enumLabel = getEnumLabel(enumValue);

      // Create a cell with a checkbox and the custom label
      if (enumValue.equals(selectedValue)) {
        String checkboxHtml = checkedBoxHtml + enumLabel + "\n";
        IBlockElement checkbox =
            (IBlockElement) HtmlConverter.convertToElements(checkboxHtml).get(0);
        cellElement.add(checkbox);
      } else {
        String checkboxHtml = uncheckedBoxHtml + enumLabel + "\n";
        IBlockElement checkbox =
            (IBlockElement) HtmlConverter.convertToElements(checkboxHtml).get(0);
        cellElement.add(checkbox);
      }

      enumTable.addCell(cellElement);
    }

    return enumTable;
  }

  public Cell createEnumCheckBoxList(Enum[] enumValues, Enum selectedValue) {

    Cell enumCell = new Cell();

    String checkedBoxHtml = "<input type=\"checkbox\" checked />";
    String uncheckedBoxHtml = "<input type=\"checkbox\"/>";

    // Iterate through the enumeration values
    for (Enum enumValue : enumValues) {
      log.debug("Current Enum : {}", enumValue);
      Div cellElement = new Div();

      // Get the custom label for the enum value
      String enumLabel = getEnumLabel(enumValue);

      // Create a cell with a checkbox and the custom label
      if (enumValue.equals(selectedValue)) {
        String checkboxHtml = checkedBoxHtml + enumLabel;
        IBlockElement checkbox =
            (IBlockElement) HtmlConverter.convertToElements(checkboxHtml).get(0);
        enumCell.add(checkbox);
      } else {
        String checkboxHtml = uncheckedBoxHtml + enumLabel;
        IBlockElement checkbox =
            (IBlockElement) HtmlConverter.convertToElements(checkboxHtml).get(0);
        enumCell.add(checkbox);
      }

      enumCell.add(cellElement);
    }

    return enumCell;
  }

  private String getEnumLabel(Enum enumValue) {
    if (enumValue instanceof RateType) {
      return ((RateType) enumValue).getLabel();
    }
    if (enumValue instanceof PaiementStatusEquity) {
      return ((PaiementStatusEquity) enumValue).getLabel();
    }
    if (enumValue instanceof PaiementStatusBond) {
      return ((PaiementStatusBond) enumValue).getLabel();
    }

    if (enumValue instanceof SecurityForm) {
      return ((SecurityForm) enumValue).getLabel();
    }
    if (enumValue instanceof Guarantee) {
      return ((Guarantee) enumValue).getLabel();
    }
    if (enumValue instanceof VotingRight) {
      return ((VotingRight) enumValue).getLabel();
    }
    if (enumValue instanceof Holding) {
      return ((Holding) enumValue).getLabel();
    }

    if (enumValue instanceof FundStatus) {
      return ((FundStatus) enumValue).getLabel();
    }
    if (enumValue instanceof FundForm) {
      return ((FundForm) enumValue).getLabel();
    }
    if (enumValue instanceof FundGuarantee) {
      return ((FundGuarantee) enumValue).getLabel();
    }
    if (enumValue instanceof FundType) {
      return ((FundType) enumValue).getLabel();
    }
    if (enumValue instanceof FundCategory) {
      return ((FundCategory) enumValue).getLabel();
    }
    if (enumValue instanceof FundClassification) {
      return ((FundClassification) enumValue).getLabel();
    }
    if (enumValue instanceof FundRate) {
      return ((FundRate) enumValue).getLabel();
    }

    // Handle other enum types as needed
    return enumValue.toString();
  }

  public byte[] generatePdfReport(IssueDTO issueDTO, SummaryDTO summaryDTO) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));
    Document document = new Document(pdf);

    DeviceRgb headerBackground = new DeviceRgb(233, 237, 246); // Couleur de l'entête
    DeviceRgb labelColor = new DeviceRgb(113, 146, 69); // Couleur des labels
    DeviceRgb valueColor = new DeviceRgb(50, 50, 50); // Couleur des valeurs

    // Ajouter le titre "ÉMISSION"
    Paragraph emissionTitle =
        new Paragraph("ÉMISSION")
            .setFontSize(12)
            .setBold()
            .setTextAlignment(TextAlignment.LEFT)
            .setMarginBottom(5)
            .setMarginTop(20)
            .setFontColor(valueColor);
    document.add(emissionTitle);

    // Table : Section "ÉMISSION"
    Table emissionTable =
        new Table(UnitValue.createPercentArray(new float[] {5, 15, 15, 10, 10, 10, 10, 10, 10, 5}))
            .useAllAvailableWidth();

    // Ajout des entêtes
    emissionTable.addHeaderCell(createHeaderCell("ID", headerBackground));
    emissionTable.addHeaderCell(createHeaderCell("Description", headerBackground));
    emissionTable.addHeaderCell(createHeaderCell("Fonds", headerBackground));
    emissionTable.addHeaderCell(createHeaderCell("Compartiment", headerBackground));
    emissionTable.addHeaderCell(createHeaderCell("Prix de souscription", headerBackground));
    emissionTable.addHeaderCell(createHeaderCell("Quantité souscrite", headerBackground));
    emissionTable.addHeaderCell(createHeaderCell("Taille du fonds", headerBackground));
    emissionTable.addHeaderCell(createHeaderCell("Limite Max", headerBackground));
    emissionTable.addHeaderCell(createHeaderCell("Date d'ouverture", headerBackground));
    emissionTable.addHeaderCell(createHeaderCell("Date de clôture", headerBackground));

    // Ajout des valeurs avec des valeurs par défaut pour les champs nuls
    emissionTable.addCell(
        createValueCell(
            issueDTO != null && issueDTO.getId() != null ? String.valueOf(issueDTO.getId()) : ""));
    emissionTable.addCell(
        createValueCell(
            issueDTO != null && issueDTO.getDescription() != null
                ? issueDTO.getDescription()
                : ""));
    emissionTable.addCell(
        createValueCell(
            issueDTO != null && issueDTO.getIssueAccountIssueCompartementFundDescription() != null
                ? issueDTO.getIssueAccountIssueCompartementFundDescription()
                : ""));
    emissionTable.addCell(
        createValueCell(
            issueDTO != null && issueDTO.getIssueAccountIssueCompartementId() != null
                ? issueDTO.getIssueAccountIssueCompartementId()
                : ""));
    emissionTable.addCell(
        createValueCell(issueDTO != null ? formatAmountWithSpaces(issueDTO.getPrice(), 0) : ""));
    emissionTable.addCell(
        createValueCell(issueDTO != null ? formatAmountWithSpaces(issueDTO.getQuantity(), 1) : ""));
    emissionTable.addCell(
        createValueCell(
            issueDTO != null ? formatAmountWithSpaces(issueDTO.getInitialClosingAmount(), 0) : ""));
    emissionTable.addCell(
        createValueCell(
            issueDTO != null ? formatAmountWithSpaces(issueDTO.getMaximumLimitAmount(), 0) : ""));
    emissionTable.addCell(
        createValueCell(
            issueDTO != null && issueDTO.getOpeningDate() != null
                ? issueDTO.getOpeningDate().toString()
                : ""));
    emissionTable.addCell(
        createValueCell(
            issueDTO != null && issueDTO.getClosingDate() != null
                ? issueDTO.getClosingDate().toString()
                : ""));

    document.add(emissionTable);

    // Ajouter un espace entre les sections
    document.add(new Paragraph("\n"));

    // Ajouter le titre "VUE 360°"
    Paragraph summaryTitle =
        new Paragraph("VUE 360°")
            .setFontSize(12)
            .setBold()
            .setTextAlignment(TextAlignment.LEFT)
            .setMarginBottom(5)
            .setMarginTop(20)
            .setFontColor(valueColor);
    document.add(summaryTitle);

    // Table : Section "VUE 360°"
    Table summaryTable = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();

    /*
      @param type
        0: Montant
        1: Quantité
        2: Percentage
    */
    addSummaryField(
        summaryTable,
        "Montant Appelé:",
        summaryDTO != null ? summaryDTO.getCalledAmount() : "",
        labelColor,
        valueColor,
        0);
    addSummaryField(
        summaryTable,
        "Quantité Appelée:",
        summaryDTO != null ? summaryDTO.getCalledQuantity() : "",
        labelColor,
        valueColor,
        1);
    addSummaryField(
        summaryTable,
        "Capital Restant:",
        summaryDTO != null ? summaryDTO.getRemainingCapital() : "",
        labelColor,
        valueColor,
        0);
    addSummaryField(
        summaryTable,
        "Montant Non Appelé:",
        summaryDTO != null ? summaryDTO.getNotCalledAmount() : "",
        labelColor,
        valueColor,
        0);
    addSummaryField(
        summaryTable,
        "Quantité Non Appelée:",
        summaryDTO != null ? summaryDTO.getNotCalledQuantity() : "",
        labelColor,
        valueColor,
        1);
    addSummaryField(
        summaryTable,
        "Quantité Restante:",
        summaryDTO != null ? summaryDTO.getRemainingQuantity() : "",
        labelColor,
        valueColor,
        1);
    addSummaryField(
        summaryTable,
        "Total Souscrit:",
        summaryDTO != null ? summaryDTO.getTotalSubscribed() : "",
        labelColor,
        valueColor,
        0);
    addSummaryField(
        summaryTable,
        "Quantité Souscrite:",
        summaryDTO != null ? summaryDTO.getQuantitySubscribed() : "",
        labelColor,
        valueColor,
        1);
    addSummaryField(
        summaryTable,
        "Total Non Souscrit:",
        summaryDTO != null ? summaryDTO.getTotalUnsubscribed() : "",
        labelColor,
        valueColor,
        0);
    addSummaryField(
        summaryTable,
        "Quantité Non Souscrite:",
        summaryDTO != null ? summaryDTO.getQuantityUnsubscribed() : "",
        labelColor,
        valueColor,
        1);
    addSummaryField(
        summaryTable,
        "Montant Libéré:",
        summaryDTO != null ? summaryDTO.getReleasedAmount() : "",
        labelColor,
        valueColor,
        0);
    addSummaryField(
        summaryTable,
        "Quantité Libérée:",
        summaryDTO != null ? summaryDTO.getReleasedQuantity() : "",
        labelColor,
        valueColor,
        1);
    addSummaryField(
        summaryTable,
        "Montant Non Libéré:",
        summaryDTO != null ? summaryDTO.getNotReleasedAmount() : "",
        labelColor,
        valueColor,
        0);
    addSummaryField(
        summaryTable,
        "Quantité Non Libérée:",
        summaryDTO != null ? summaryDTO.getNotReleasedQuantity() : "",
        labelColor,
        valueColor,
        1);
    addSummaryField(
        summaryTable,
        "% Non Appelé:",
        summaryDTO != null ? summaryDTO.getNotCalledPercentage() : "",
        labelColor,
        valueColor,
        2);

    document.add(summaryTable);

    document.close();

    return outputStream.toByteArray();
  }

  private Cell createHeaderCell(String text, DeviceRgb backgroundColor) {
    return new Cell()
        .add(new Paragraph(text).setFontSize(10))
        .setTextAlignment(TextAlignment.CENTER)
        .setBackgroundColor(backgroundColor);
  }

  private Cell createValueCell(String text) {
    return new Cell()
        .add(new Paragraph(text).setFontSize(10))
        .setTextAlignment(TextAlignment.CENTER);
  }

  private void addSummaryField(
      Table table,
      String label,
      Object value,
      DeviceRgb labelColor,
      DeviceRgb valueColor,
      int type) {
    Table innerTable =
        new Table(2)
            .useAllAvailableWidth()
            .addCell(
                new Cell()
                    .add(new Paragraph(label).setFontSize(8).setFontColor(labelColor))
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.LEFT))
            .addCell(
                new Cell()
                    .add(
                        new Paragraph(formatAmountWithSpaces(value, type))
                            .setFontSize(8)
                            .setFontColor(valueColor))
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT));
    table.addCell(new Cell().add(innerTable));
  }

  private String formatAmountWithSpaces(Object value, int type) {

    NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
    numberFormat.setMaximumFractionDigits(2);
    numberFormat.setMinimumFractionDigits(2);

    if (value == null) {
      return "N/A";
    }
    if (value instanceof BigDecimal number) {
      String formattedValue = numberFormat.format(number);

      switch (type) {
        case 0 -> {
          return formattedValue + " MAD";
        }
        case 1 -> {
          return formattedValue;
        }
        case 2 -> {
          return value + " %";
        }
      }
    }
    return value.toString();
  }
}
