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

package org.apache.shardingsphere.proxy.frontend.mysql.command.query.binary.prepare;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.sql.parser.statement.core.statement.SQLStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.tcl.xa.XABeginStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.tcl.xa.XACommitStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.tcl.xa.XAEndStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.tcl.xa.XAPrepareStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.tcl.xa.XARecoveryStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.tcl.xa.XARollbackStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLAnalyzeTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLCacheIndexStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLChecksumTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLFlushStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLInstallPluginStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLKillStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLLoadIndexInfoStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLOptimizeTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLRepairTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLResetStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLSetStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLShowBinaryLogsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLShowBinlogEventsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLShowCreateEventStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLShowCreateFunctionStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLShowCreateProcedureStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLShowCreateTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLShowCreateViewStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLShowErrorsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLShowStatusStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLShowWarningsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLUninstallPluginStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dcl.MySQLAlterUserStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dcl.MySQLCreateUserStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dcl.MySQLDropUserStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dcl.MySQLGrantStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dcl.MySQLRenameUserStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dcl.MySQLRevokeStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.ddl.MySQLAlterTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.ddl.MySQLCreateDatabaseStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.ddl.MySQLCreateIndexStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.ddl.MySQLCreateTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.ddl.MySQLCreateViewStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.ddl.MySQLDropDatabaseStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.ddl.MySQLDropIndexStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.ddl.MySQLDropTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.ddl.MySQLDropViewStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.ddl.MySQLRenameTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.ddl.MySQLTruncateStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dml.MySQLCallStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dml.MySQLDeleteStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dml.MySQLDoStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dml.MySQLInsertStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dml.MySQLSelectStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dml.MySQLUpdateStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.rl.MySQLChangeMasterStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.rl.MySQLStartSlaveStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.rl.MySQLStopSlaveStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.tcl.MySQLCommitStatement;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * COM_STMT_PREPARE command statement checker for MySQL.
 *
 * @see <a href="https://dev.mysql.com/doc/refman/5.7/en/sql-prepared-statements.html">SQL Syntax Allowed in Prepared Statements</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MySQLComStmtPrepareChecker {
    
    private static final Collection<Class<?>> ALLOWED_SQL_STATEMENTS = new HashSet<>();
    
    static {
        ALLOWED_SQL_STATEMENTS.addAll(Arrays.asList(MySQLAlterTableStatement.class, MySQLAlterUserStatement.class, MySQLAnalyzeTableStatement.class,
                MySQLCacheIndexStatement.class, MySQLCallStatement.class, MySQLChangeMasterStatement.class, MySQLChecksumTableStatement.class, MySQLCommitStatement.class,
                MySQLCreateIndexStatement.class, MySQLDropIndexStatement.class, MySQLCreateDatabaseStatement.class, MySQLDropDatabaseStatement.class,
                MySQLCreateTableStatement.class, MySQLDropTableStatement.class, MySQLCreateUserStatement.class, MySQLRenameUserStatement.class, MySQLDropUserStatement.class,
                MySQLCreateViewStatement.class, MySQLDropViewStatement.class, MySQLDeleteStatement.class, MySQLDoStatement.class, MySQLFlushStatement.class,
                MySQLGrantStatement.class, MySQLInsertStatement.class, MySQLInstallPluginStatement.class, MySQLKillStatement.class, MySQLLoadIndexInfoStatement.class,
                MySQLOptimizeTableStatement.class, MySQLRenameTableStatement.class, MySQLRepairTableStatement.class, MySQLResetStatement.class,
                MySQLRevokeStatement.class, MySQLSelectStatement.class, MySQLSetStatement.class, MySQLShowWarningsStatement.class, MySQLShowErrorsStatement.class,
                MySQLShowBinlogEventsStatement.class, MySQLShowCreateProcedureStatement.class, MySQLShowCreateFunctionStatement.class, MySQLShowCreateEventStatement.class,
                MySQLShowCreateTableStatement.class, MySQLShowCreateViewStatement.class, MySQLShowBinaryLogsStatement.class, MySQLShowStatusStatement.class,
                MySQLStartSlaveStatement.class, MySQLStopSlaveStatement.class, MySQLTruncateStatement.class, MySQLUninstallPluginStatement.class, MySQLUpdateStatement.class,
                XABeginStatement.class, XAPrepareStatement.class, XACommitStatement.class, XARollbackStatement.class, XAEndStatement.class, XARecoveryStatement.class));
    }
    
    /**
     * Judge if SQL statement is allowed.
     *
     * @param sqlStatement SQL statement
     * @return SQL statement is allowed or not
     */
    public static boolean isAllowedStatement(final SQLStatement sqlStatement) {
        return ALLOWED_SQL_STATEMENTS.contains(sqlStatement.getClass());
    }
}
