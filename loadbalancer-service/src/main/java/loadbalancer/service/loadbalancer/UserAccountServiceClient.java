// package loadbalancer.service.loadbalancer;

// import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
// import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// @FeignClient(name = "user-account-service")
// @LoadBalancerClient(name = "user-account-service", configuration =
// LoadBalancerConfiguration.class)
// public interface UserAccountServiceClient {

// @PostMapping("/accounts")
// ResponseEntity<String> createAccount(@RequestBody Account account);
// }