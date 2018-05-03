package net.formula97.springapp.skeltonapp.domains;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.formula97.springapp.skeltonapp.misc.AppConst;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = AppUser.TABLE_NAME)
@Data
@NoArgsConstructor
public class AppUser implements UserDetails, Serializable {

    /**
     * Serialized uid.
     */
    private static final long serialVersionUID = -2539850420951210610L;

    /**
     * table name.
     */
    public static final String TABLE_NAME = "APP_USER";

    public static final String COLUMN_USERNAME = "user_name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ACCOUNT_ENABLED = "account_enabled";
    public static final String COLUMN_ACCOUNT_EXPIRATION_DATE = "account_expiration_date";
    public static final String COLUMN_PASSWORD_EXPIRATION_DATE = "password_expiration_date";
    public static final String COLUMN_LOCK_REASON_CODE = "lock_reason_code";
    public static final String COLUMN_DISPLAY_NAME = "display_name";
    public static final String COLUMN_MAIL_ADDRESS = "mail_address";
    public static final String COLUMN_FORMER_PASSWORD = "former_password";
    public static final String COLUMN_AUTHORITY = "authority";

    /**
     * user name (primary key).
     */
    @Id
    @Column(name = COLUMN_USERNAME, nullable = false, length = 32)
    private String username;

    /**
     * user's password (encrypted).
     */
    @Column(name = COLUMN_PASSWORD, nullable = false, length = 128)
    private String password;

    /**
     * flag that account is enabled.
     */
    @Column(name = COLUMN_ACCOUNT_ENABLED)
    private Boolean accountEnabled;

    /**
     * Expiration date of this account.
     */
    @Column(name = COLUMN_ACCOUNT_EXPIRATION_DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date accountExpirationDate;

    /**
     * Expiration date of this user's password.
     */
    @Column(name = COLUMN_PASSWORD_EXPIRATION_DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordExpirationDate;

    /**
     * Reason code of account locked.
     */
    @Column(name = COLUMN_LOCK_REASON_CODE)
    private Integer lockReasonCode;

    /**
     * User's display name.
     */
    @Column(name = COLUMN_DISPLAY_NAME, length = 128)
    private String displayName;

    /**
     * User's mail address.
     */
    @Column(name = COLUMN_MAIL_ADDRESS, length = 128)
    private String mailAddress;

    /**
     * Password before updating.
     */
    @Column(name = COLUMN_FORMER_PASSWORD, length = 128)
    private String formerPassword;

    /**
     * User's authority.
     */
    @Column(name = COLUMN_AUTHORITY, length = 64, nullable = false)
    private String authority;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.authority));

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        Date currentDate = Calendar.getInstance().getTime();
        return currentDate.compareTo(this.accountExpirationDate) < 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return lockReasonCode != null && lockReasonCode == AppConst.LockReason.NO_LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        Date currentDate = Calendar.getInstance().getTime();
        return currentDate.compareTo(this.passwordExpirationDate) < 0;
    }

    @Override
    public boolean isEnabled() {
        return this.accountEnabled != null && this.accountEnabled;
    }
}
