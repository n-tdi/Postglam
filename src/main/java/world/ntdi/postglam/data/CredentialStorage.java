package world.ntdi.postglam.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredentialStorage {
    String HOST; int PORT; String USERNAME; String PASSWORD;
}
