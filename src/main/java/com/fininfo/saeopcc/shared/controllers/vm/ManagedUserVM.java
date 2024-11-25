package com.fininfo.saeopcc.shared.controllers.vm;

import com.fininfo.saeopcc.shared.services.dto.UserDTO;

/** View Model extending the UserDTO, which is meant to be used in the user management UI. */
public class ManagedUserVM extends UserDTO {

  public ManagedUserVM() {
    // Empty constructor needed for Jackson.
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "ManagedUserVM{" + super.toString() + "} ";
  }
}
