package loadbalancer.service.loadbalancer;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import user.service.userservice.Account;

@FeignClient(name = "user-account-service")
@LoadBalancerClient(name = "user-account-service", configuration = LoadBalancerConfiguration.class)
@EntityScan(basePackages = "user.service.userservice")
public interface UserAccountServiceClient {

    @PostMapping("/accounts")
    ResponseEntity<String> createAccount(@RequestBody Account account);
}