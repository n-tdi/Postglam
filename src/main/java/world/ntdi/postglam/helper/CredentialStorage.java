package world.ntdi.postglam.helper;

/**
 * A record for storing Database credentials.
 * @param HOST URL of the Database
 * @param PORT Port that the database is running on
 * @param USERNAME Username for root access to the database
 * @param PASSWORD Password for root access to the database
 */
public record CredentialStorage(String HOST, int PORT, String USERNAME, String PASSWORD) {
}
