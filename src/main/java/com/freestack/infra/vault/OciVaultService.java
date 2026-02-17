package com.freestack.infra.vault;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.BasicAuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.auth.InstancePrincipalsAuthenticationDetailsProvider;
import com.oracle.bmc.secrets.SecretsClient;
import com.oracle.bmc.secrets.model.Base64SecretBundleContentDetails;
import com.oracle.bmc.secrets.requests.GetSecretBundleRequest;
import com.oracle.bmc.secrets.responses.GetSecretBundleResponse;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.util.Base64;
import java.util.Optional;

@ApplicationScoped
public class OciVaultService {

    private static final Logger LOG = Logger.getLogger(OciVaultService.class);

    @ConfigProperty(name = "oci.secret.ocid")
    Optional<String> secretOcid;

    @ConfigProperty(name = "oci.auth.instance-principal", defaultValue = "false")
    boolean useInstancePrincipal;

    public String getSecretValue() {
        if (secretOcid.isEmpty() || secretOcid.get().startsWith("ocid1.vaultsecret.oc1.xxx")) {
            throw new RuntimeException("CRITICAL ERROR: OCI Vault configuration is missing. This application requires a valid OCI Secret OCID to access the database.");
        }

        LOG.info("Retrieving secret from OCI Vault: " + secretOcid.get());

        try (SecretsClient secretsClient = createSecretsClient()) {
            GetSecretBundleRequest getSecretBundleRequest = GetSecretBundleRequest.builder()
                    .secretId(secretOcid.get())
                    .build();

            GetSecretBundleResponse getSecretBundleResponse = secretsClient.getSecretBundle(getSecretBundleRequest);
            Base64SecretBundleContentDetails contentDetails = (Base64SecretBundleContentDetails) getSecretBundleResponse
                    .getSecretBundle()
                    .getSecretBundleContent();

            byte[] decodedBytes = Base64.getDecoder().decode(contentDetails.getContent());
            return new String(decodedBytes);
        } catch (Exception e) {
            LOG.error("Error retrieving secret from OCI Vault", e);
            throw new RuntimeException("Could not retrieve secret from OCI Vault. Connection refused for security reasons.", e);
        }
    }

    private SecretsClient createSecretsClient() throws Exception {
        BasicAuthenticationDetailsProvider provider;

        if (useInstancePrincipal) {
            LOG.info("Authentication: Instance Principal (Production)");
            provider = InstancePrincipalsAuthenticationDetailsProvider.builder().build();
        } else {
            LOG.info("Authentication: Local Config File (Development)");
            ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
            provider = new ConfigFileAuthenticationDetailsProvider(configFile);
        }

        return SecretsClient.builder().build(provider);
    }
}
