package com.freestack.infra.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Oracle Database Health Check
 * 
 * This health check validates connectivity to Oracle Autonomous Database
 * by executing a query on V$VERSION view to retrieve database information.
 * 
 * Marked as @Readiness, which means Kubernetes/OpenShift will not route
 * traffic to the pod until this check returns UP status.
 */
@Readiness
@ApplicationScoped
public class OracleDatabaseHealthCheck implements HealthCheck {

    @Inject
    DataSource dataSource;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Oracle Database Connection");

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT BANNER FROM V$VERSION WHERE ROWNUM = 1");
                ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                String oracleVersion = resultSet.getString(1);

                return responseBuilder
                        .up()
                        .withData("version", oracleVersion)
                        .withData("connection", "active")
                        .withData("database", "Oracle Autonomous Database")
                        .build();
            }

        } catch (Exception e) {
            return responseBuilder
                    .down()
                    .withData("error", e.getMessage())
                    .withData("connection", "failed")
                    .build();
        }

        return responseBuilder
                .down()
                .withData("connection", "no data returned")
                .build();
    }
}
