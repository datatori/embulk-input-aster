package org.embulk.input.aster;

import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.embulk.spi.Exec;
import org.embulk.input.jdbc.JdbcInputConnection;

public class AsterInputConnection
        extends JdbcInputConnection
{
    private final Logger logger = Exec.getLogger(AsterInputConnection.class);

    public AsterInputConnection(Connection connection)
            throws SQLException
    {
        super(connection, "public");
    }
}
