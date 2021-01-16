package pl.solventive.LocalFarmer.LocalFarmerApi.util;

import com.google.common.collect.Iterables;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;
import pl.solventive.LocalFarmer.LocalFarmerApi.security.services.LFUserPrincipal;

import java.util.Optional;

public class RequestHandler {

    public static <T> T getSingle(Optional<T> object, String entityName) {
        if (object.isPresent()) {
            return object.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, entityName + " not found."
            );
        }
    }

    public static <T> Iterable<T> getList(Iterable<T> objects) {
        if (Iterables.size(objects) == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "The response is empty."
            );
        } else {
            return objects;
        }
    }

    public static int getUserId() {
        try {
            LFUserPrincipal principal = (LFUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return principal.getUserId();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Could not retrieve userId.\n" + e.getMessage()
            );
        }
    }

    public static String validateResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> message.append(error.getDefaultMessage()).append(", "));
            return message.toString();
        } else {
            return null;
        }
    }
}
