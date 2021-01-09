package pl.solventive.LocalFarmer.LocalFarmerApi.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class RequestHandler {

    public static <T> T handleSingle(T object, String entityName) {
        if (object != null) {
            return object;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, entityName + " not found."
            );
        }
    }

    public static <T> List<T> handleList(List<T> objects) {
        if (objects.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "The response is empty."
            );
        } else {
            return objects;
        }
    }
}
