package com.freestack;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Oracle Info Resource
 * 
 * REST endpoint that returns information about Oracle Database connection.
 * This is the "Hello Oracle" that demonstrates OCI integration.
 */
@Path("/oracle")
public class OracleInfoResource {

    @Inject
    DataSource dataSource;

    @GET
    @Path("/version")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getDatabaseVersion() {
        Map<String, String> response = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT BANNER FROM V$VERSION WHERE ROWNUM = 1")) {

            if (resultSet.next()) {
                response.put("status", "connected");
                response.put("version", resultSet.getString(1));
                response.put("message", "Successfully connected to Oracle Autonomous Database!");
            }

        } catch (Exception e) {
            response.put("status", "error");
            response.put("error", e.getMessage());
        }

        return response;
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDatabaseInfo() {
        Map<String, Object> response = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {

            // Get database version
            ResultSet versionRs = statement.executeQuery("SELECT BANNER FROM V$VERSION WHERE ROWNUM = 1");
            if (versionRs.next()) {
                response.put("version", versionRs.getString(1));
            }
            versionRs.close();

            // Get instance name
            ResultSet instanceRs = statement.executeQuery("SELECT INSTANCE_NAME FROM V$INSTANCE");
            if (instanceRs.next()) {
                response.put("instanceName", instanceRs.getString(1));
            }
            instanceRs.close();

            // Get database name
            ResultSet dbNameRs = statement.executeQuery("SELECT NAME FROM V$DATABASE");
            if (dbNameRs.next()) {
                response.put("databaseName", dbNameRs.getString(1));
            }
            dbNameRs.close();

            response.put("status", "connected");
            response.put("message", "Oracle Autonomous Database - Always Free Tier");

        } catch (Exception e) {
            response.put("status", "error");
            response.put("error", e.getMessage());
        }

        return response;
    }
}
