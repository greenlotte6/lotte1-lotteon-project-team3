/* UserEntity 생성 후에 사용가능
package kr.co.lotteOn.security;

import kr.co.lotteOn.entity.user.User;
import kr.co.lotteOn.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userrepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("username : " + username);

        //사용자 조회 - 사용자가 입력한 아이디, 비밀번호는 이전단계인 AuthenticationProvider 쪽에서 먼저 수행됨
        Optional<User> optUser = userrepository.findById(username);

        if (optUser.isPresent()) {
            MyUserDetails myUserDetails = MyUserDetails.builder()
                    .user(optUser.get())
                    .build();
            //리턴되는 myUserDetails는 Security ContextHolder에 저장
            return myUserDetails;
        }


        return null;
    }
}

 */
