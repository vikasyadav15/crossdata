/*
 * Licensed to STRATIO (C) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  The STRATIO (C) licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.stratio.meta2.metadata;

import java.util.Set;

public class ConnectorMetadata implements IMetadata {
  private final Set<DataStoreName> dataStoreRefs;

  private final ConnectorName name;
  private final String version;
  private final Set<String> requiredProperties;
  private final Set<String> othersProperties;

  public ConnectorMetadata(ConnectorName name, String version, Set<DataStoreName> dataStoreRefs,
      Set<String> requiredProperties, Set<String> othersProperties) {
    this.dataStoreRefs = dataStoreRefs;
    this.name = name;
    this.version = version;
    this.requiredProperties = requiredProperties;
    this.othersProperties = othersProperties;
 }

  public ConnectorName getName() {
    return name;
  }

  public Set<DataStoreName> getDataStoreRefs() {
  return dataStoreRefs;
  }

  public String getVersion() {
    return version;
  }

  public Set<String> getRequiredProperties() {
    return requiredProperties;
  }

  public Set<String> getOthersProperties() {
    return othersProperties;
  }
}