package co.kr.promptech.freeboard.repository;

import co.kr.promptech.freeboard.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findAccountByUsername(String username);
}
