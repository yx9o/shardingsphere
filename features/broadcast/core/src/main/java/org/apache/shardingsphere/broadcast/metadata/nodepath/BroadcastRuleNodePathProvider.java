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

package org.apache.shardingsphere.broadcast.metadata.nodepath;

import org.apache.shardingsphere.broadcast.config.BroadcastRuleConfiguration;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.mode.node.path.config.database.DatabaseRuleNodePath;
import org.apache.shardingsphere.mode.node.spi.DatabaseRuleNodePathProvider;

import java.util.Collections;

/**
 * Broadcast rule node path provider.
 */
public final class BroadcastRuleNodePathProvider implements DatabaseRuleNodePathProvider {
    
    public static final String RULE_TYPE = "broadcast";
    
    public static final String TABLES = "tables";
    
    private static final DatabaseRuleNodePath INSTANCE = new DatabaseRuleNodePath(RULE_TYPE, Collections.emptyList(), Collections.singleton(TABLES));
    
    @Override
    public DatabaseRuleNodePath getDatabaseRuleNodePath() {
        return INSTANCE;
    }
    
    @Override
    public Class<? extends RuleConfiguration> getType() {
        return BroadcastRuleConfiguration.class;
    }
}
