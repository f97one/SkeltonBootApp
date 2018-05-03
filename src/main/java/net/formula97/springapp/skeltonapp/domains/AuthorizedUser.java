package net.formula97.springapp.skeltonapp.domains;

import org.springframework.security.core.userdetails.User;

/**
 * Glue User object with Default implementation of UserDetail.
 */
public class AuthorizedUser extends User {

    /**
     * serialized uid.
     */
    private static final long serialVersionUID = -7263310661934678306L;

    /**
     * system UserDetail object.
     */
    private AppUser mAppUser;

    /**
     * Glue Constructor.
     *
     * @param appUser System UserDetails.
     */
    public AuthorizedUser(AppUser appUser) {
        super(appUser.getUsername(), appUser.getPassword(), appUser.getAuthorities());

        this.mAppUser = appUser;
    }

    /**
     * Returns system UserDetails.
     *
     * @return System UserDetails.
     */
    public  AppUser getAppUser() {
        return mAppUser;
    }
}
