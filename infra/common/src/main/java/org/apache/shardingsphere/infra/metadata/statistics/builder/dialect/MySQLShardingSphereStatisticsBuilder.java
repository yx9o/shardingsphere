/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.infra.metadata.statistics.builder.dialect;

import org.apache.shardingsphere.infra.autogen.version.ShardingSphereVersion;
import org.apache.shardingsphere.infra.metadata.database.ShardingSphereDatabase;
import org.apache.shardingsphere.infra.metadata.statistics.ShardingSphereDatabaseData;
import org.apache.shardingsphere.infra.metadata.statistics.ShardingSphereRowData;
import org.apache.shardingsphere.infra.metadata.statistics.ShardingSphereSchemaData;
import org.apache.shardingsphere.infra.metadata.statistics.ShardingSphereTableData;
import org.apache.shardingsphere.infra.metadata.statistics.builder.ShardingSphereStatisticsBuilder;

import java.util.Collections;

/**
 * ShardingSphere statistics builder for MySQL.
 */

public final class MySQLShardingSphereStatisticsBuilder implements ShardingSphereStatisticsBuilder {
    
    private static final String SHARDINGSPHERE = "shardingsphere";
    
    private static final String CLUSTER_INFORMATION = "cluster_information";
    
    private static final String SHARDING_TABLE_STATISTICS = "sharding_table_statistics";
    
    @Override
    public ShardingSphereDatabaseData build(final ShardingSphereDatabase database) {
        ShardingSphereDatabaseData result = new ShardingSphereDatabaseData();
        if (database.containsSchema(SHARDINGSPHERE)) {
            ShardingSphereSchemaData schemaData = new ShardingSphereSchemaData();
            initClusterInformationTable(schemaData);
            initShardingTableStatisticsTable(schemaData);
            result.putSchema(SHARDINGSPHERE, schemaData);
        }
        return result;
    }
    
    private void initClusterInformationTable(final ShardingSphereSchemaData schemaData) {
        ShardingSphereTableData tableData = new ShardingSphereTableData(CLUSTER_INFORMATION);
        tableData.getRows().add(new ShardingSphereRowData(Collections.singletonList(ShardingSphereVersion.VERSION)));
        schemaData.putTable(CLUSTER_INFORMATION, tableData);
    }
    
    private void initShardingTableStatisticsTable(final ShardingSphereSchemaData schemaData) {
        schemaData.putTable(SHARDING_TABLE_STATISTICS, new ShardingSphereTableData(SHARDING_TABLE_STATISTICS));
    }
    
    @Override
    public String getDatabaseType() {
        return "MySQL";
    }
}
