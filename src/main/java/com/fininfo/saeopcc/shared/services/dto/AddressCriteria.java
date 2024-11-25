package com.fininfo.saeopcc.shared.services.dto;

import com.fininfo.saeopcc.configuration.Criteria;
import com.fininfo.saeopcc.configuration.Filter;
import com.fininfo.saeopcc.configuration.LongFilter;
import com.fininfo.saeopcc.configuration.StringFilter;
import com.fininfo.saeopcc.shared.domains.enumeration.AddressTypes;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressCriteria implements Serializable, Criteria {

  public static class AddressTypesFilter extends Filter<AddressTypes> {

    public AddressTypesFilter() {}

    public AddressTypesFilter(AddressTypesFilter filter) {
      super(filter);
    }

    @Override
    public AddressTypesFilter copy() {
      return new AddressTypesFilter(this);
    }
  }

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private StringFilter name;

  private AddressTypesFilter type;

  private LongFilter roleId;

  public AddressCriteria(AddressCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.name = other.name == null ? null : other.name.copy();
    this.type = other.type == null ? null : other.type.copy();
    this.roleId = other.roleId == null ? null : other.roleId.copy();
  }

  @Override
  public AddressCriteria copy() {
    return new AddressCriteria(this);
  }
}
