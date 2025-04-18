package com.fininfo.saeopcc.multitenancy.domains.flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fininfo.saeopcc.shared.domains.AbstractAuditingEntity;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A Document. */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
public class Document extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "mime_type")
  private String mimeType;

  @Column(name = "size")
  private Long size;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "reference_notif", unique = true)
  private String referenceNotif;

  @Lob
  @Column(name = "content")
  private byte[] content;

  @Column(name = "mur")
  private Long mur;

  @ManyToOne
  @JsonIgnoreProperties(value = "documents", allowSetters = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Flow flow;
}
