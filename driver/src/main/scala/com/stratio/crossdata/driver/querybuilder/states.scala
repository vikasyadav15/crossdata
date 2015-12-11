/**
 * Copyright (C) 2015 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.crossdata.driver.querybuilder

import com.stratio.crossdata.driver.querybuilder.dslentities.{CombinationInfo, SortCriteria, XDQLStatement}


class SimpleRunnableQuery private(projections: Seq[Expression],
                                  relation: Relation,
                                  context: String => String,
                                  filters: Option[Predicate] = None)
  extends RunnableQuery(projections, relation, context, filters)
  with Sortable
  with Limitable
  with Groupable {

  def this(projections: Seq[Expression], relation: Relation, context: String => String) =
    this(projections, relation, context, None)

  // It has to be abstract (simple runnable query has transitions) and concrete
  override def where(condition: Predicate): this.type =
    //Not knew alternatives to `asInstanceOf`: http://stackoverflow.com/a/791157/1893995
    new SimpleRunnableQuery(projections, relation, context, Some(combinePredicates(condition))).asInstanceOf[this.type]

}

class GroupedQuery(projections: Seq[Expression],
                   relation: Relation,
                   context: String => String,
                   filters: Option[Predicate] = None,
                   groupingExpressions: Seq[Expression])
  extends RunnableQuery(projections, relation, context, filters, groupingExpressions)
  with Sortable
  with Limitable {

  def having(expression: String): HavingQuery = having(XDQLStatement(expression))

  def having(expression: Predicate): HavingQuery =
    new HavingQuery(projections, relation, context, filters, groupingExpressions, expression)


  override def where(condition: Predicate): this.type =
    //Not knew alternatices to `asInstanceOf`: http://stackoverflow.com/a/791157/1893995
    new GroupedQuery(
      projections,
      relation,
      context,
      Some(combinePredicates(condition)),
      groupingExpressions
    ).asInstanceOf[this.type]


}

class HavingQuery(projections: Seq[Expression],
                  relation: Relation,
                  context: String => String,
                  filters: Option[Predicate] = None,
                  groupingExpressions: Seq[Expression],
                  havingExpressions: Predicate)
  extends RunnableQuery(
    projections, relation, context,
    filters, groupingExpressions, Some(havingExpressions))
  with Sortable
  with Limitable {

  override def where(condition: Predicate): this.type =
    new HavingQuery(
      projections,
      relation,
      context,
      Some(combinePredicates(condition)),
      groupingExpressions,
      havingExpressions
    ).asInstanceOf[this.type]

}

class SortedQuery(projections: Seq[Expression],
                  relation: Relation,
                  context: String => String,
                  filters: Option[Predicate] = None,
                  groupingExpressions: Seq[Expression] = Seq.empty,
                  havingExpressions: Option[Predicate] = None,
                  ordering: SortCriteria)
  extends RunnableQuery(
    projections, relation, context,
    filters, groupingExpressions, havingExpressions, Some(ordering)
  ) with Limitable {

  override def where(condition: Predicate): this.type =
    new SortedQuery(
      projections,
      relation,
      context,
      Some(combinePredicates(condition)),
      groupingExpressions,
      havingExpressions,
      ordering
    ).asInstanceOf[this.type]

}

class LimitedQuery(projections: Seq[Expression],
                   relation: Relation,
                   context: String => String,
                   filters: Option[Predicate] = None,
                   groupingExpressions: Seq[Expression] = Seq.empty,
                   havingExpressions: Option[Predicate] = None,
                   ordering: Option[SortCriteria],
                   limit: Int)
  extends RunnableQuery(
    projections, relation, context,
    filters, groupingExpressions, havingExpressions, ordering, Some(limit)) {

  override def where(condition: Predicate): this.type =
    new LimitedQuery(
      projections,
      relation,
      context,
      Some(combinePredicates(condition)),
      groupingExpressions,
      havingExpressions,
      ordering,
      limit
    ).asInstanceOf[this.type]

}

class CombinedQuery(projections: Seq[Expression],
                    relation: Relation,
                    context: String => String,
                    filters: Option[Predicate] = None,
                    groupingExpressions: Seq[Expression] = Seq.empty,
                    havingExpressions: Option[Predicate] = None,
                    ordering: Option[SortCriteria],
                    limit: Option[Int],
                    combinationInfo: CombinationInfo)
  extends RunnableQuery(
    projections, relation, context,
    filters, groupingExpressions, havingExpressions, ordering, limit, Some(combinationInfo)
  ) with Combinable {

  def where(condition: Predicate): this.type = throw new Error("Predicates cannot by applied to combined queries")

}