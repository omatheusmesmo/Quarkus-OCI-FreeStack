package com.freestack.infra.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                PreparedStatement statement = connection
                        .prepareStatement("SELECT BANNER FROM V$VERSION WHERE ROWNUM = 1");
                ResultSet resultSet = statement.executeQuery()) {

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

        try (Connection connection = dataSource.getConnection()) {

            // Get database version
            try (PreparedStatement psVersion = connection
                    .prepareStatement("SELECT BANNER FROM V$VERSION WHERE ROWNUM = 1");
                    ResultSet rsVersion = psVersion.executeQuery()) {
                if (rsVersion.next()) {
                    response.put("version", rsVersion.getString(1));
                }
            }

            // Get instance name
            try (PreparedStatement psInstance = connection
                    .prepareStatement("SELECT INSTANCE_NAME FROM V$INSTANCE");
                    ResultSet rsInstance = psInstance.executeQuery()) {
                if (rsInstance.next()) {
                    response.put("instanceName", rsInstance.getString(1));
                }
            }

            // Get database name
            try (PreparedStatement psDbName = connection.prepareStatement("SELECT NAME FROM V$DATABASE");
                    ResultSet rsDbName = psDbName.executeQuery()) {
                if (rsDbName.next()) {
                    response.put("databaseName", rsDbName.getString(1));
                }
            }

            response.put("status", "connected");
            response.put("message", "Oracle Autonomous Database - Always Free Tier");

        } catch (Exception e) {
            response.put("status", "error");
            response.put("error", e.getMessage());
        }

        return response;
    }

    @GET
    @Path("/project/changes")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getProjectChanges() {
        Map<String, Object> response = new HashMap<>();
        response.put("step", 2);
        response.put("title", "O Poder do Banco Convergente");

        Map<String, String> changes = new HashMap<>();
        changes.put("architecture",
                "Refactored to Layered Package-by-Feature (domain, dto, repository, service, resource)");
        changes.put("persistence", "Migrated to PanacheEntity for automated ID management with Oracle sequences");
        changes.put("json_support",
                "Native Oracle JSON support with PASSING clause for robust SQL-path parameter binding");
        changes.put("migrations",
                "Integrated Flyway for professional database versioning (V1.0.1__Create_Article_Table.sql)");
        changes.put("validation", "Added Bean Validation with quarkus-hibernate-validator on DTOs");
        changes.put("architecture",
                "Package-by-Feature (domain, repository, service, resource) for high maintainability");

        response.put("implemented_features", changes);
        response.put("status", "COMPLETED");
        return response;
    }
}
