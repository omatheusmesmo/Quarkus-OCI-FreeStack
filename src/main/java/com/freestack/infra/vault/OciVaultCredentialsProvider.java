package com.freestack.infra.vault;

import io.quarkus.credentials.CredentialsProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
@Named("oci-vault-provider")
public class OciVaultCredentialsProvider implements CredentialsProvider {

    private static final Logger LOG = Logger.getLogger(OciVaultCredentialsProvider.class);

    @Inject
    OciVaultService vaultService;

    @Override
    public Map<String, String> getCredentials(String credentialsProviderName) {
        LOG.info("Providing credentials from OCI Vault for provider: " + credentialsProviderName);
        
        Map<String, String> credentials = new HashMap<>();
        String password = vaultService.getSecretValue();
        
        credentials.put(PASSWORD_PROPERTY_NAME, password);
        
        return credentials;
    }
}
