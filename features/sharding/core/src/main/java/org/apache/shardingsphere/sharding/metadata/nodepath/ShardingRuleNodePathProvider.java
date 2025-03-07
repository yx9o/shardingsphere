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

package org.apache.shardingsphere.sharding.metadata.nodepath;

import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.mode.node.path.config.database.DatabaseRuleNodePath;
import org.apache.shardingsphere.mode.node.spi.DatabaseRuleNodePathProvider;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;

import java.util.Arrays;

/**
 * Sharding rule node path provider.
 */
public final class ShardingRuleNodePathProvider implements DatabaseRuleNodePathProvider {
    
    public static final String RULE_TYPE = "sharding";
    
    public static final String TABLES = "tables";
    
    public static final String AUTO_TABLES = "auto_tables";
    
    public static final String BINDING_TABLES = "binding_tables";
    
    public static final String SHARDING_ALGORITHMS = "sharding_algorithms";
    
    public static final String KEY_GENERATORS = "key_generators";
    
    public static final String AUDITORS = "auditors";
    
    public static final String DEFAULT_DATABASE_STRATEGY = "default_database_strategy";
    
    public static final String DEFAULT_TABLE_STRATEGY = "default_table_strategy";
    
    public static final String DEFAULT_KEY_GENERATE_STRATEGY = "default_key_generate_strategy";
    
    public static final String DEFAULT_AUDIT_STRATEGY = "default_audit_strategy";
    
    public static final String DEFAULT_SHARDING_COLUMN = "default_sharding_column";
    
    public static final String SHARDING_CACHE = "sharding_cache";
    
    private static final DatabaseRuleNodePath INSTANCE = new DatabaseRuleNodePath(RULE_TYPE,
            Arrays.asList(TABLES, AUTO_TABLES, BINDING_TABLES, SHARDING_ALGORITHMS, KEY_GENERATORS, AUDITORS),
            Arrays.asList(DEFAULT_DATABASE_STRATEGY, DEFAULT_TABLE_STRATEGY, DEFAULT_KEY_GENERATE_STRATEGY, DEFAULT_AUDIT_STRATEGY, DEFAULT_SHARDING_COLUMN, SHARDING_CACHE));
    
    @Override
    public DatabaseRuleNodePath getDatabaseRuleNodePath() {
        return INSTANCE;
    }
    
    @Override
    public Class<? extends RuleConfiguration> getType() {
        return ShardingRuleConfiguration.class;
    }
}
