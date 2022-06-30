package co.kr.promptech.freeboard.service;

import co.kr.promptech.freeboard.dto.AccountDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.repository.AccountRepository;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> memberEntityWrapper = accountRepository.findAccountByUsername(username);
        Account user = memberEntityWrapper.orElse(null);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    @Transactional
    public Long save(AccountDTO accountDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Account user = Account.builder()
                .username(accountDTO.getUsername())
                .email(accountDTO.getEmail())
                .password(passwordEncoder.encode(accountDTO.getPassword()))
                .build();

         return accountRepository.save(user).getId();
    }

    public Account findById(Long writerId) {
        return accountRepository.findById(writerId).get();
    }

    public Account findAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username).get();
    }
}
