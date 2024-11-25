package com.fininfo.saeopcc.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDoc {

  private String filename;
  private byte[] content;
}
