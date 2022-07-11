package co.kr.promptech.freeboard.service;

import co.kr.promptech.freeboard.dto.AccountDTO;
import co.kr.promptech.freeboard.model.Account;
import co.kr.promptech.freeboard.repository.AccountRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> memberEntityWrapper = accountRepository.findAccountByUsername(username);
        Account user = memberEntityWrapper.orElse(null);

        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new User(user.getUsername(), user.getPassword(), authorities);
        }
        else throw new UsernameNotFoundException("User not found");
    }

    @Transactional
    public Long save(AccountDTO accountDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Account user = Account.builder()
                .username(accountDTO.getUsername())
                .email(accountDTO.getEmail())
                .password(passwordEncoder.encode(accountDTO.getPassword()))
                .profileImage("/profiles/basic.jpeg")
                .build();

         return accountRepository.save(user).getId();
    }

    @Transactional
    public boolean updateProfileImage(Account account, MultipartFile profileImage){

        String resourcePath = "src/main/resources/static/";
        String dirPath = "profiles/";

        File dir = new File(resourcePath + dirPath);
        if(!dir.exists()) {
            dir.mkdir();
            logger.info("profile dir created! " + resourcePath);
        }
        try {
            String extension = FilenameUtils.getExtension(profileImage.getOriginalFilename());
            String fileName = account.getUsername() + "." + extension;
            String filePath = resourcePath + dirPath + fileName ;

            Path uploadPath = Paths.get(filePath);
            logger.info("profile image path : " + uploadPath);
            Files.write(uploadPath, profileImage.getBytes());

            account.setProfileImage(dirPath + fileName);
            accountRepository.save(account);
        } catch (Exception e){
            logger.info("image file handling error");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Account findAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username).orElse(null);
    }
}