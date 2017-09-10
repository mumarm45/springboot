package com.posts.Service;

import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by mumarm45 on 09/08/2017.
 */
public class TokenServiceTest {

    @Mock
    private TimeService timeService;

    @InjectMocks
    private TokenService tokenService;

    public static String USERNAME = "testuser";

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(this.tokenService, "secret", "secret");
    }


    private String createToken() {

        return tokenService.createToken(new UserDetailDummy(USERNAME));
    }


    @Test
    public void getUsernameFromTokenTest() throws Exception {
        when(timeService.now()).thenReturn(DateUtil.now());

        String token = createToken();

        assertThat(tokenService.getUsernameFromToken(token)).isEqualToIgnoringCase(USERNAME);
    }

    @Test
    public void getExpireDateFromTokenTest() throws Exception {
        final Date now = new Date();
        when(timeService.now()).thenReturn(DateUtil.now());
        String token = createToken();
        Date expirationDate = tokenService.getExpireDateFromToken(token);
        assertThat(expirationDate.after(now));
    }

    @Test
    public void getCreateDateFromTokenTest() throws Exception {
        final Date now = new Date();
        when(timeService.now()).thenReturn(now);
        String token = createToken();
        assertThat(tokenService.getCreateDateFromToken(token)).hasSameTimeAs(now);
    }

    @Test
    public void isTokenExpireTest() {
        when(timeService.now()).thenReturn(DateUtil.now());
        String token = createToken();

        assertThat(tokenService.isTokenExpire(token)).isEqualTo(false);
    }

    @Test
    public void getClaimFromToke(){
        final Date now = new Date();
        when(timeService.now()).thenReturn(DateUtil.now());

        String token  = createToken();

       // Map<String, Object> claims = createClaims(now.toString());

        assertThat(tokenService.getClaimFromToken(token).getSubject()).isEqualToIgnoringCase(USERNAME);

    }

    private Map<String, Object> createClaims(String creationDate) {
        Map<String, Object> claims = new HashMap();
        claims.put(TokenService.CLAIM_SUBJECT, USERNAME);
        claims.put("date", DateUtil.parseDatetime(creationDate));
        return claims;
    }

}
