package net.formula97.springapp.skeltonapp.services;

import net.formula97.springapp.skeltonapp.BaseTestCase;
import net.formula97.springapp.skeltonapp.domains.AppUser;
import net.formula97.springapp.skeltonapp.domains.AuthorizedUser;
import net.formula97.springapp.skeltonapp.repositories.AppUserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"server.port:0", "spring.profiles.active:test"})
public class AuthorizedUserServiceTest extends BaseTestCase {

    @Autowired
    private AuthorizedUserService authorizedUserService;
    @Autowired
    private AppUserRepository appUserRepository;

    @Before
    public void setUp() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2199);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        // 一般ユーザー1を追加
        AppUser user1 = new AppUser();
        user1.setUsername("ippan1");
        user1.setPassword(BCrypt.hashpw("ippan1", BCrypt.gensalt()));
        user1.setAccountEnabled(true);
        user1.setAccountExpirationDate(cal.getTime());
        user1.setPasswordExpirationDate(cal.getTime());
        user1.setFormerPassword("");
        user1.setAuthority("USER");
        user1.setDisplayName("一般ユーザー");
        user1.setMailAddress("ippan@example.com");
        user1.setLockReasonCode(0);
        appUserRepository.saveAndFlush(user1);

        // 管理者ユーザー1を追加
        AppUser user2 = new AppUser();
        user2.setUsername("kanrisha1");
        user2.setPassword(BCrypt.hashpw("kanrisha1", BCrypt.gensalt()));
        user2.setAccountEnabled(true);
        user2.setAccountExpirationDate(cal.getTime());
        user2.setPasswordExpirationDate(cal.getTime());
        user2.setFormerPassword("");
        user2.setAuthority("ADMIN");
        user2.setDisplayName("管理者ユーザー");
        user2.setMailAddress("kanrisha@example.com");
        user1.setLockReasonCode(0);
        appUserRepository.saveAndFlush(user2);

    }

    @After
    public void tearDown() throws Exception {
        // 追加したユーザーを削除
        appUserRepository.deleteById("ippan1");
        appUserRepository.deleteById("kanrisha1");
    }

    @Test
    public void usernameをnullにするとIllegalArgumentExceptionを投げる() throws Exception {
        try {
            UserDetails userDetails = authorizedUserService.loadUserByUsername(null);

            fail("例外は投げられなかった");
        } catch (UsernameNotFoundException e) {
            fail("IllegalArgumentExceptionは投げられなかった");
        } catch (IllegalArgumentException e) {
            assertThat("IllegalArgumentExceptionが投げられている", e, is(instanceOf(IllegalArgumentException.class)));
            assertThat(e.getMessage(), is("username must not be null."));
        }
    }

    @Test
    public void usenameを空文字にするとIllegalArgumentExceptionを投げる() throws Exception {
        try {
            UserDetails userDetails = authorizedUserService.loadUserByUsername("");

            fail("例外は投げられなかった");
        } catch (UsernameNotFoundException e) {
            fail("IllegalArgumentExceptionは投げられなかった");
        } catch (IllegalArgumentException e) {
            assertThat("IllegalArgumentExceptionが投げられている", e, is(instanceOf(IllegalArgumentException.class)));
            assertThat(e.getMessage(), is("username must not be empty."));
        }
    }

    @Test
    public void 存在するユーザーを指定するとユーザーが取得できる() throws Exception {
        UserDetails userDetails = authorizedUserService.loadUserByUsername("ippan1");
        assertThat(userDetails, is(notNullValue()));
        assertThat(userDetails, is(instanceOf(AuthorizedUser.class)));

        assertThat(BCrypt.checkpw("ippan1", userDetails.getPassword()), is(true));

        AppUser appUser = ((AuthorizedUser) userDetails).getAppUser();
        assertThat(appUser.getLockReasonCode(), is(0));
        assertThat(appUser.getAccountEnabled(), is(true));
        assertThat(appUser.getAuthority(), is("USER"));
        assertThat(appUser.getDisplayName(), is("一般ユーザー"));
    }

    @Test
    public void 存在しないユーザーを指定するとUsernameNotFoundExceptionが投げられる() throws Exception {
        try {
            UserDetails userDetails = authorizedUserService.loadUserByUsername("kanrisha2");

            fail("例外は投げられなかった");
        } catch (UsernameNotFoundException e) {
            assertThat("UsernameNotFoundExceptionが投げられている", e, is(instanceOf(UsernameNotFoundException.class)));
        } catch (IllegalArgumentException e) {
            fail("UsernameNotFoundExceptionは投げられなかった");
        }

    }
}