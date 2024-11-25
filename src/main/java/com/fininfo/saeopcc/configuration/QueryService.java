package com.fininfo.saeopcc.configuration;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public abstract class QueryService<T> {

  /**
   * Helper function to return a specification for filtering on a single field, where equality, and
   * null/non-null conditions are supported.
   *
   * @param filter the individual attribute filter coming from the frontend.
   * @param field the JPA static metamodel representing the field.
   * @param <X> The type of the attribute which is filtered.
   * @return a Specification
   */
  protected <X> Specification<T> buildSpecification(
      Filter<X> filter, SingularAttribute<? super T, X> field) {
    return buildSpecification(filter, root -> root.get(field));
  }

  /**
   * Helper function to return a specification for filtering on a single field, where equality, and
   * null/non-null conditions are supported.
   *
   * @param filter the individual attribute filter coming from the frontend.
   * @param metaclassFunction the function, which navigates from the current T to a column, for
   *     which the filter applies.
   * @param <X> The type of the attribute which is filtered.
   * @return a Specification
   */
  protected <X> Specification<T> buildSpecification(
      Filter<X> filter, Function<Root<T>, Expression<X>> metaclassFunction) {
    if (filter.getEquals() != null) {
      return equalsSpecification(metaclassFunction, filter.getEquals());
    } else if (filter.getIn() != null) {
      return valueIn(metaclassFunction, filter.getIn());
    } else if (filter.getNotIn() != null) {
      return valueNotIn(metaclassFunction, filter.getNotIn());
    } else if (filter.getNotEquals() != null) {
      return notEqualsSpecification(metaclassFunction, filter.getNotEquals());
    } else if (filter.getSpecified() != null) {
      return byFieldSpecified(metaclassFunction, filter.getSpecified());
    }
    return null;
  }

  /**
   * Helper function to return a specification for filtering on a {@link java.lang.String} field,
   * where equality, containment, and null/non-null conditions are supported.
   *
   * @param filter the individual attribute filter coming from the frontend.
   * @param field the JPA static metamodel representing the field.
   * @return a Specification
   */
  protected Specification<T> buildStringSpecification(
      StringFilter filter, SingularAttribute<? super T, String> field) {
    return buildSpecification(filter, root -> root.get(field));
  }

  protected Specification<T> buildLocalDateSpecification(
      LocalDateFilter filter, SingularAttribute<? super T, LocalDate> field) {
    if (filter == null) {
      return null;
    }

    Specification<T> specification = Specification.where(null);

    if (filter.getEquals() != null) {
      specification =
          specification.and(
              (root, query, builder) ->
                  builder.equal(
                      root.get((SingularAttribute<? super T, LocalDate>) field),
                      filter.getEquals()));
    }

    if (filter.getGreaterThan() != null) {
      specification =
          specification.and(
              (root, query, builder) ->
                  builder.greaterThan(
                      root.get((SingularAttribute<? super T, LocalDate>) field),
                      filter.getGreaterThan()));
    }

    if (filter.getLessThan() != null) {
      specification =
          specification.and(
              (root, query, builder) ->
                  builder.lessThan(
                      root.get((SingularAttribute<? super T, LocalDate>) field),
                      filter.getLessThan()));
    }

    return specification;
  }

  /**
   * Helper function to return a specification for filtering on a {@link java.lang.String} field,
   * where equality, containment, and null/non-null conditions are supported.
   *
   * @param filter the individual attribute filter coming from the frontend.
   * @param metaclassFunction lambda, which based on a Root&lt;T&gt; returns Expression - basicaly
   *     picks a column
   * @return a Specification
   */
  protected Specification<T> buildSpecification(
      StringFilter filter, Function<Root<T>, Expression<String>> metaclassFunction) {
    if (filter.getEquals() != null) {
      return equalsSpecification(metaclassFunction, filter.getEquals());
    } else if (filter.getIn() != null) {
      return valueIn(metaclassFunction, filter.getIn());
    } else if (filter.getNotIn() != null) {
      return valueNotIn(metaclassFunction, filter.getNotIn());
    } else if (filter.getContains() != null) {
      return likeUpperSpecification(metaclassFunction, filter.getContains());
    } else if (filter.getDoesNotContain() != null) {
      return doesNotContainSpecification(metaclassFunction, filter.getDoesNotContain());
    } else if (filter.getNotEquals() != null) {
      return notEqualsSpecification(metaclassFunction, filter.getNotEquals());
    } else if (filter.getSpecified() != null) {
      return byFieldSpecified(metaclassFunction, filter.getSpecified());
    }
    return null;
  }

  /**
   * Helper function to return a specification for filtering on a single {@link
   * java.lang.Comparable}, where equality, less than, greater than and less-than-or-equal-to and
   * greater-than-or-equal-to and null/non-null conditions are supported.
   *
   * @param <X> The type of the attribute which is filtered.
   * @param filter the individual attribute filter coming from the frontend.
   * @param field the JPA static metamodel representing the field.
   * @return a Specification
   */
  protected <X extends Comparable<? super X>> Specification<T> buildRangeSpecification(
      RangeFilter<X> filter, SingularAttribute<? super T, X> field) {
    return buildSpecification(filter, root -> root.get(field));
  }

  /**
   * Helper function to return a specification for filtering on a single {@link
   * java.lang.Comparable}, where equality, less than, greater than and less-than-or-equal-to and
   * greater-than-or-equal-to and null/non-null conditions are supported.
   *
   * @param <X> The type of the attribute which is filtered.
   * @param filter the individual attribute filter coming from the frontend.
   * @param metaclassFunction lambda, which based on a Root&lt;T&gt; returns Expression - basicaly
   *     picks a column
   * @return a Specification
   */
  protected <X extends Comparable<? super X>> Specification<T> buildSpecification(
      RangeFilter<X> filter, Function<Root<T>, Expression<X>> metaclassFunction) {
    if (filter.getEquals() != null) {
      return equalsSpecification(metaclassFunction, filter.getEquals());
    } else if (filter.getIn() != null) {
      return valueIn(metaclassFunction, filter.getIn());
    }

    Specification<T> result = Specification.where(null);
    if (filter.getSpecified() != null) {
      result = result.and(byFieldSpecified(metaclassFunction, filter.getSpecified()));
    }
    if (filter.getNotEquals() != null) {
      result = result.and(notEqualsSpecification(metaclassFunction, filter.getNotEquals()));
    }
    if (filter.getNotIn() != null) {
      result = result.and(valueNotIn(metaclassFunction, filter.getNotIn()));
    }
    if (filter.getGreaterThan() != null) {
      result = result.and(greaterThan(metaclassFunction, filter.getGreaterThan()));
    }
    if (filter.getGreaterThanOrEqual() != null) {
      result = result.and(greaterThanOrEqualTo(metaclassFunction, filter.getGreaterThanOrEqual()));
    }
    if (filter.getLessThan() != null) {
      result = result.and(lessThan(metaclassFunction, filter.getLessThan()));
    }
    if (filter.getLessThanOrEqual() != null) {
      result = result.and(lessThanOrEqualTo(metaclassFunction, filter.getLessThanOrEqual()));
    }
    return result;
  }

  /**
   * Helper function to return a specification for filtering on one-to-one or many-to-one reference.
   * Usage:
   *
   * <pre>
   * Specification&lt;Employee&gt; specByProjectId = buildReferringTSpecification(criteria.getProjectId(),
   *     Employee_.project, Project_.id);
   * Specification&lt;Employee&gt; specByProjectName = buildReferringTSpecification(criteria.getProjectName(),
   *     Employee_.project, Project_.name);
   * </pre>
   *
   * @param filter the filter object which contains a value, which needs to match or a flag if
   *     nullness is checked.
   * @param reference the attribute of the static metamodel for the referring T.
   * @param valueField the attribute of the static metamodel of the referred T, where the equality
   *     should be checked.
   * @param <O> The type of the referenced T.
   * @param <X> The type of the attribute which is filtered.
   * @return a Specification
   */
  protected <O, X> Specification<T> buildReferringTSpecification(
      Filter<X> filter,
      SingularAttribute<? super T, O> reference,
      SingularAttribute<? super O, X> valueField) {
    return buildSpecification(filter, root -> root.get(reference).get(valueField));
  }

  protected <O, X> Specification<T> buildReferringTSpecification(
      Filter<X> filter, SetAttribute<T, O> reference, SingularAttribute<O, X> valueField) {
    return buildReferringTSpecification(
        filter, root -> root.join(reference), t -> t.get(valueField));
  }

  protected <O, M, X> Specification<T> buildReferringTSpecification(
      Filter<X> filter,
      Function<Root<T>, SetJoin<M, O>> functionToT,
      Function<SetJoin<M, O>, Expression<X>> tToColumn) {
    if (filter.getEquals() != null) {
      return equalsSpecification(functionToT.andThen(tToColumn), filter.getEquals());
    } else if (filter.getSpecified() != null) {
      // Interestingly, 'functionToT' doesn't work, we need the longer lambda formula
      return byFieldSpecified(root -> functionToT.apply(root), filter.getSpecified());
    }
    return null;
  }

  protected <O, X extends Comparable<? super X>> Specification<T> buildReferringTSpecification(
      final RangeFilter<X> filter,
      final SetAttribute<T, O> reference,
      final SingularAttribute<O, X> valueField) {
    return buildReferringTSpecification(
        filter, root -> root.join(reference), t -> t.get(valueField));
  }

  protected <O, M, X extends Comparable<? super X>> Specification<T> buildReferringTSpecification(
      final RangeFilter<X> filter,
      Function<Root<T>, SetJoin<M, O>> functionToT,
      Function<SetJoin<M, O>, Expression<X>> tToColumn) {

    Function<Root<T>, Expression<X>> fused = functionToT.andThen(tToColumn);
    if (filter.getEquals() != null) {
      return equalsSpecification(fused, filter.getEquals());
    } else if (filter.getIn() != null) {
      return valueIn(fused, filter.getIn());
    }
    Specification<T> result = Specification.where(null);
    if (filter.getSpecified() != null) {
      // Interestingly, 'functionToT' doesn't work, we need the longer lambda formula
      result = result.and(byFieldSpecified(root -> functionToT.apply(root), filter.getSpecified()));
    }
    if (filter.getNotEquals() != null) {
      result = result.and(notEqualsSpecification(fused, filter.getNotEquals()));
    }
    if (filter.getNotIn() != null) {
      result = result.and(valueNotIn(fused, filter.getNotIn()));
    }
    if (filter.getGreaterThan() != null) {
      result = result.and(greaterThan(fused, filter.getGreaterThan()));
    }
    if (filter.getGreaterThanOrEqual() != null) {
      result = result.and(greaterThanOrEqualTo(fused, filter.getGreaterThanOrEqual()));
    }
    if (filter.getLessThan() != null) {
      result = result.and(lessThan(fused, filter.getLessThan()));
    }
    if (filter.getLessThanOrEqual() != null) {
      result = result.and(lessThanOrEqualTo(fused, filter.getLessThanOrEqual()));
    }
    return result;
  }

  protected <X> Specification<T> equalsSpecification(
      Function<Root<T>, Expression<X>> metaclassFunction, final X value) {
    return (root, query, builder) -> builder.equal(metaclassFunction.apply(root), value);
  }

  protected <X> Specification<T> notEqualsSpecification(
      Function<Root<T>, Expression<X>> metaclassFunction, final X value) {
    return (root, query, builder) ->
        builder.not(builder.equal(metaclassFunction.apply(root), value));
  }

  protected Specification<T> likeUpperSpecification(
      Function<Root<T>, Expression<String>> metaclassFunction, final String value) {
    return (root, query, builder) ->
        builder.like(builder.upper(metaclassFunction.apply(root)), wrapLikeQuery(value));
  }

  protected Specification<T> doesNotContainSpecification(
      Function<Root<T>, Expression<String>> metaclassFunction, final String value) {
    return (root, query, builder) ->
        builder.not(
            builder.like(builder.upper(metaclassFunction.apply(root)), wrapLikeQuery(value)));
  }

  protected <X> Specification<T> byFieldSpecified(
      Function<Root<T>, Expression<X>> metaclassFunction, final boolean specified) {
    return specified
        ? (root, query, builder) -> builder.isNotNull(metaclassFunction.apply(root))
        : (root, query, builder) -> builder.isNull(metaclassFunction.apply(root));
  }

  protected <X> Specification<T> byFieldEmptiness(
      Function<Root<T>, Expression<Set<X>>> metaclassFunction, final boolean specified) {
    return specified
        ? (root, query, builder) -> builder.isNotEmpty(metaclassFunction.apply(root))
        : (root, query, builder) -> builder.isEmpty(metaclassFunction.apply(root));
  }

  protected <X> Specification<T> valueIn(
      Function<Root<T>, Expression<X>> metaclassFunction, final Collection<X> values) {
    return (root, query, builder) -> {
      In<X> in = builder.in(metaclassFunction.apply(root));
      for (X value : values) {
        in = in.value(value);
      }
      return in;
    };
  }

  protected <X> Specification<T> valueNotIn(
      Function<Root<T>, Expression<X>> metaclassFunction, final Collection<X> values) {
    return (root, query, builder) -> {
      In<X> in = builder.in(metaclassFunction.apply(root));
      for (X value : values) {
        in = in.value(value);
      }
      return builder.not(in);
    };
  }

  protected <X extends Comparable<? super X>> Specification<T> greaterThanOrEqualTo(
      Function<Root<T>, Expression<X>> metaclassFunction, final X value) {
    return (root, query, builder) ->
        builder.greaterThanOrEqualTo(metaclassFunction.apply(root), value);
  }

  protected <X extends Comparable<? super X>> Specification<T> greaterThan(
      Function<Root<T>, Expression<X>> metaclassFunction, final X value) {
    return (root, query, builder) -> builder.greaterThan(metaclassFunction.apply(root), value);
  }

  protected <X extends Comparable<? super X>> Specification<T> lessThanOrEqualTo(
      Function<Root<T>, Expression<X>> metaclassFunction, final X value) {
    return (root, query, builder) ->
        builder.lessThanOrEqualTo(metaclassFunction.apply(root), value);
  }

  protected <X extends Comparable<? super X>> Specification<T> lessThan(
      Function<Root<T>, Expression<X>> metaclassFunction, final X value) {
    return (root, query, builder) -> builder.lessThan(metaclassFunction.apply(root), value);
  }

  protected String wrapLikeQuery(String txt) {
    return "%" + txt.toUpperCase() + '%';
  }
}
