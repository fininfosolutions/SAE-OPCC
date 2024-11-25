package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.IntegerFilter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.backupswift.referentiel.domain.Devise} entity. This class is
 * used in {@link com.fininfo.saeopcc.shared.controllers.DeviseResource} to receive all the possible
 * filtering options from the Http GET request parameters. For example the following could be a
 * valid request: {@code /devises?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used,
 * we need to use fix type specific filters.
 */
public class DeviseCriteria implements Serializable, Criteria {

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter nom;

  private StringFilter codeAlpha;

  private IntegerFilter codeNum;

  private IntegerFilter decimale;

  private StringFilter symbole;

  private StringFilter uniteFraction;

  private IntegerFilter nombreBase;

  public DeviseCriteria() {}

  public DeviseCriteria(DeviseCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.nom = other.nom == null ? null : other.nom.copy();
    this.codeAlpha = other.codeAlpha == null ? null : other.codeAlpha.copy();
    this.codeNum = other.codeNum == null ? null : other.codeNum.copy();
    this.decimale = other.decimale == null ? null : other.decimale.copy();
    this.symbole = other.symbole == null ? null : other.symbole.copy();
    this.uniteFraction = other.uniteFraction == null ? null : other.uniteFraction.copy();
    this.nombreBase = other.nombreBase == null ? null : other.nombreBase.copy();
  }

  @Override
  public DeviseCriteria copy() {
    return new DeviseCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public StringFilter getNom() {
    return nom;
  }

  public void setNom(StringFilter nom) {
    this.nom = nom;
  }

  public StringFilter getCodeAlpha() {
    return codeAlpha;
  }

  public void setCodeAlpha(StringFilter codeAlpha) {
    this.codeAlpha = codeAlpha;
  }

  public IntegerFilter getCodeNum() {
    return codeNum;
  }

  public void setCodeNum(IntegerFilter codeNum) {
    this.codeNum = codeNum;
  }

  public IntegerFilter getDecimale() {
    return decimale;
  }

  public void setDecimale(IntegerFilter decimale) {
    this.decimale = decimale;
  }

  public StringFilter getSymbole() {
    return symbole;
  }

  public void setSymbole(StringFilter symbole) {
    this.symbole = symbole;
  }

  public StringFilter getUniteFraction() {
    return uniteFraction;
  }

  public void setUniteFraction(StringFilter uniteFraction) {
    this.uniteFraction = uniteFraction;
  }

  public IntegerFilter getNombreBase() {
    return nombreBase;
  }

  public void setNombreBase(IntegerFilter nombreBase) {
    this.nombreBase = nombreBase;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final DeviseCriteria that = (DeviseCriteria) o;
    return Objects.equals(id, that.id)
        && Objects.equals(nom, that.nom)
        && Objects.equals(codeAlpha, that.codeAlpha)
        && Objects.equals(codeNum, that.codeNum)
        && Objects.equals(decimale, that.decimale)
        && Objects.equals(symbole, that.symbole)
        && Objects.equals(uniteFraction, that.uniteFraction)
        && Objects.equals(nombreBase, that.nombreBase);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nom, codeAlpha, codeNum, decimale, symbole, uniteFraction, nombreBase);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "DeviseCriteria{"
        + (id != null ? "id=" + id + ", " : "")
        + (nom != null ? "nom=" + nom + ", " : "")
        + (codeAlpha != null ? "codeAlpha=" + codeAlpha + ", " : "")
        + (codeNum != null ? "codeNum=" + codeNum + ", " : "")
        + (decimale != null ? "decimale=" + decimale + ", " : "")
        + (symbole != null ? "symbole=" + symbole + ", " : "")
        + (uniteFraction != null ? "uniteFraction=" + uniteFraction + ", " : "")
        + (nombreBase != null ? "nombreBase=" + nombreBase + ", " : "")
        + "}";
  }
}
