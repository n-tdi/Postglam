package world.ntdi.postglam.helper;

import lombok.Getter;

public record CredentialStorage(String HOST, int PORT, String USERNAME, String PASSWORD) {
}
