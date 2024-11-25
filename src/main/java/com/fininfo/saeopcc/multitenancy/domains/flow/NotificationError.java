package com.fininfo.saeopcc.multitenancy.domains.flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/** A NotificationError. */
@Entity
@Table(name = "notification_error")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class NotificationError implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "source_field")
  private String sourceField;

  @Column(name = "error")
  private String error;

  @Lob
  @Type(type = "org.hibernate.type.TextType")
  @Column(name = "error_description")
  private String errorDescription;

  @Column(name = "field_type")
  private String fieldType;

  @Column(name = "target_field")
  private String targetField;

  @ManyToOne
  @JsonIgnoreProperties(value = "notificationErrors", allowSetters = true)
  private Notification notification;
}
