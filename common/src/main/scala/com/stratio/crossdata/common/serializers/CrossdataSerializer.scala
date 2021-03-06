/*
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
package com.stratio.crossdata.common.serializers


import _root_.akka.cluster.Member

import com.stratio.crossdata.common.serializers.akka.{AkkaClusterMemberSerializer, AkkaMemberStatusSerializer}
import org.apache.spark.sql.crossdata.serializers.StructTypeSerializer
import org.json4s._

trait CrossdataCommonSerializer {

  implicit val json4sJacksonFormats: Formats =
    DefaultFormats + SQLResultSerializer + UUIDSerializer +
      StructTypeSerializer + FiniteDurationSerializer + CommandSerializer + StreamedSuccessfulSQLResultSerializer +
        AkkaMemberStatusSerializer + AkkaClusterMemberSerializer + new SortedSetSerializer[Member]()

}

