package org.embulk.input.aster;

import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.sql.Connection;
import java.sql.SQLException;
import org.embulk.config.Config;
import org.embulk.config.ConfigDefault;
import org.embulk.input.jdbc.AbstractJdbcInputPlugin;

import org.embulk.spi.Exec;
import org.slf4j.Logger;

public class AsterInputPlugin
        extends AbstractJdbcInputPlugin
{
    private final Logger logger = Exec.getLogger(AsterInputPlugin.class);

    public interface AsterPluginTask
            extends PluginTask
    {
        @Config("host")
        public String getHost();

        @Config("port")
        @ConfigDefault("2406")
        public int getPort();

        @Config("user")
        public String getUser();

        @Config("password")
        @ConfigDefault("\"\"")
        public String getPassword();

        @Config("database")
        public String getDatabase();

    }

    @Override
    protected Class<? extends PluginTask> getTaskClass()
    {
        return AsterPluginTask.class;
    }

    @Override
    protected AsterInputConnection newConnection(PluginTask task) throws SQLException
    {
        AsterPluginTask t = (AsterPluginTask) task;

        String url = String.format("jdbc:ncluster://%s",t.getHost());

        Properties props = new Properties();
	    props.setProperty("database", t.getDatabase());
        props.setProperty("user", t.getUser());
        props.setProperty("password", t.getPassword());

        props.putAll(t.getOptions());

        try
        {
            Class.forName("com.asterdata.ncluster.Driver");
        } catch (ClassNotFoundException e)
        {
            logger.error("Class not found: " + e.getMessage());
        }

        Connection con = DriverManager.getConnection(url, props);

        try {
            Statement stmt = con.createStatement();
            String sql = String.format("select * from nc_system.nc_user_tables;");
            logger.info("SQL: " + sql);
            stmt.execute(sql);

            AsterInputConnection c = new AsterInputConnection(con);
            con = null;
            return c;
        }
        finally {
            if (con != null) {
                con.close();
            }
        }
    }
}
