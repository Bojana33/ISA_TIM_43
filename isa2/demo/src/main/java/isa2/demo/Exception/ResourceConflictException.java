package isa2.demo.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceConflictException extends RuntimeException {
    private static final long serialVersionUID = 1791564636123821405L;

    private Integer resourceId;

    public ResourceConflictException(Integer resourceId, String message) {
        super(message);
        this.setResourceId(resourceId);
    }
}
