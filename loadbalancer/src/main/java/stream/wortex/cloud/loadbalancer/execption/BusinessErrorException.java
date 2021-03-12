package stream.wortex.cloud.loadbalancer.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Well well well... Something went wrong")
public class BusinessErrorException extends RuntimeException {

}
