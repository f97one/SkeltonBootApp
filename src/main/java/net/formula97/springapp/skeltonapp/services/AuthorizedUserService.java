package net.formula97.springapp.skeltonapp.services;

import net.formula97.springapp.skeltonapp.domains.AppUser;
import net.formula97.springapp.skeltonapp.domains.AuthorizedUser;
import net.formula97.springapp.skeltonapp.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthorizedUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null) {
            throw new IllegalArgumentException("username must not be null.");
        } else if (username.length() == 0) {
            throw new IllegalArgumentException("username must not be empty.");
        }

        AppUser appUser = appUserRepository.findById(username).orElse(null);

        if (appUser == null) {
            throw new UsernameNotFoundException(String.format(Locale.getDefault(), "Requested username ( %s ) not found.", username));
        }

        return new AuthorizedUser(appUser);
    }

}
