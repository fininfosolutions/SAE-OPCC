package com.fininfo.saeopcc.shared;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class Utils {

  private static final String START_INSTANT = "startInstant";
  private static final String END_INSTANT = "endInstant";

  public Map<String, Instant> prepareDateRangeSpecification(LocalDate date, String operator) {
    Instant startInstant = null;
    Instant endInstant = null;

    switch (operator) {
      case "equals":
      case "notEquals":
        startInstant = date.atStartOfDay().toInstant(java.time.ZoneOffset.UTC);
        endInstant =
            date.atStartOfDay().plusDays(1).minusNanos(1).toInstant(java.time.ZoneOffset.UTC);
        break;
      case "lessThan":
        startInstant = Instant.EPOCH;
        endInstant = date.atStartOfDay().toInstant(java.time.ZoneOffset.UTC);
        break;
      case "greaterThan":
        startInstant = date.atStartOfDay().plusDays(1).toInstant(java.time.ZoneOffset.UTC);
        endInstant = Instant.parse("9999-01-01T15:23:01Z");
        break;
      default:
        throw new IllegalArgumentException("Invalid operation");
    }

    Map<String, Instant> dateRange = new HashMap<>();
    dateRange.put(START_INSTANT, startInstant);
    dateRange.put(END_INSTANT, endInstant);

    return dateRange;
  }

  @SuppressWarnings("unchecked") // For date type
  public <T, Y extends Comparable<? super Y>> Specification<T> specification(
      Specification<T> specification,
      LocalDate date,
      String operator,
      SingularAttribute<?, Y> attribute,
      BiFunction<CriteriaBuilder, Root<T>, Expression<Y>> expressionProvider) {

    Map<String, Instant> dateRange = prepareDateRangeSpecification(date, operator);
    Y startDate;
    Y endDate;

    if (attribute.getJavaType().equals(ZonedDateTime.class)) {
      startDate = (Y) ZonedDateTime.ofInstant(dateRange.get(START_INSTANT), ZoneId.systemDefault());
      endDate = (Y) ZonedDateTime.ofInstant(dateRange.get(END_INSTANT), ZoneId.systemDefault());
    } else if (attribute.getJavaType().equals(Instant.class)) {
      startDate = (Y) dateRange.get(START_INSTANT);
      endDate = (Y) dateRange.get(END_INSTANT);
    } else {
      throw new IllegalArgumentException(
          "This method is only configured for ZonedDateTime and Instant types.");
    }

    return specification.and(
        (root, query, criteriaBuilder) -> {
          Expression<Y> attributeExpression = expressionProvider.apply(criteriaBuilder, root);

          if ("notEquals".equals(operator)) {
            return criteriaBuilder.or(
                criteriaBuilder.lessThan(attributeExpression, startDate),
                criteriaBuilder.greaterThan(attributeExpression, endDate));
          } else {
            return criteriaBuilder.between(attributeExpression, startDate, endDate);
          }
        });
  }
}
