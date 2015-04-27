package org.keycloak.migration;

import org.jboss.logging.Logger;
import org.keycloak.migration.migrators.MigrationTo1_2_0_RC1;
import org.keycloak.models.KeycloakSession;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class MigrationModelManager {
    private static Logger logger = Logger.getLogger(MigrationModelManager.class);

    public static void migrate(KeycloakSession session) {
        MigrationModel model = session.realms().getMigrationModel();
        String storedVersion = model.getStoredVersion();
        if (MigrationModel.LATEST_VERSION.equals(storedVersion)) return;
        ModelVersion stored = null;
        if (storedVersion == null) stored = new ModelVersion(0, 0, 0);
        else stored = new ModelVersion(storedVersion);

        if (stored.lessThan(MigrationTo1_2_0_RC1.VERSION)) {
            logger.info("Migrating older model to 1.2.0.RC1 updates");
            new MigrationTo1_2_0_RC1().migrate(session);
        }

        model.setStoredVersion(MigrationModel.LATEST_VERSION);


    }
}
