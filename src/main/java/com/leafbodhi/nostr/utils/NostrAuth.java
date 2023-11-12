//package com.leafbodhi.nostr.utils;
//
///**
// * @author Jond
// */
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwsHeader;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.net.URI;
//import java.util.regex.Pattern;
//
//public class NostrAuth {
//
//    public static final String NOSTR_AUTH_SCHEME = "Nostr";
//
//    public static class NostrAuthOptions {
//        // Currently empty
//    }
//
//    public static class NostrAuthToken extends AbstractAuthenticationToken {
//        private static final long serialVersionUID = 1L;
//        public static final String CLAIM_PUBLIC_KEY = "publicKey";
//        private final String publicKey;
//        private final UserDetails userDetails;
//
//        public NostrAuthToken(String publicKey) {
//            super(AuthorityUtils.NO_AUTHORITIES);
//            this.publicKey = publicKey;
//            this.userDetails = new User(publicKey, "",
//                    AuthorityUtils.createAuthorityList("ROLE_PUBLIC_USER", "ROLE_ANONYMOUS"));
//            setAuthenticated(false);
//        }
//
//        public NostrAuthToken(Claims claims, UserDetails userDetails) {
//            super(AuthorityUtils.NO_AUTHORITIES);
//            this.publicKey = (String) claims.get(CLAIM_PUBLIC_KEY);
//            this.userDetails = userDetails;
//            setAuthenticated(true);
//        }
//
//        @Override
//        public Object getCredentials() {
//            return null;
//        }
//
//        @Override
//        public Object getPrincipal() {
//            return userDetails;
//        }
//
//        public String getPublicKey() {
//            return publicKey;
//        }
//
//        @Override
//        public String toString() {
//            return "[NostrAuthToken] publicKey : " + publicKey + " ; isAuthenticated : " + isAuthenticated()
//                    + " ; userDetails : " + userDetails;
//        }
//    }
//    @Slf4j
//    public static class NostrAuthHandler implements AuthenticationProvider {
//        private static final Pattern AUTH_NONCE_PATTERN = Pattern.compile("[0-9a-f]{8}");
//        private final UserDetailsService userDetailsService;
//        private final PasswordEncoder passwordEncoder;
//
//        public NostrAuthHandler(UserDetailsService userDetailsService,
//                                PasswordEncoder passwordEncoder) {
//            this.userDetailsService = userDetailsService;
//            this.passwordEncoder = passwordEncoder;
//        }
//
//        @Override
//        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//            NostrAuthToken nostrAuthToken = (NostrAuthToken) authentication;
//            if (nostrAuthToken.isAuthenticated())
//                return nostrAuthToken;
//
//            Claims claims;
//            try {
//                // Looks like this will not work for java 8
//                // .require("nonce", AUTH_NONCE_PATTERN.pattern())
//                claims = Jwts.parserBuilder()
//                        .build().parseClaimsJws(nostrAuthToken.getPublicKey()).getBody();
//            } catch (Exception e) {
//                log.warn("[{}] Exception on token verification.", nostrAuthToken, e);
//                return null;
//            }
//            String username = (String) claims.get(NostrAuthToken.CLAIM_PUBLIC_KEY);
//            if (StringUtils.isBlank(username)) {
//                return null;
//            }
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            if (userDetails == null || StringUtils.isBlank(userDetails.getPassword())
//                    || !passwordEncoder.matches("", userDetails.getPassword())) {
//                if (log.isDebugEnabled()) {
//                    log.debug("User details not found for token. username : [{}]", username);
//                }
//                return null;
//            }
//
//            NostrAuthToken authToken = new NostrAuthToken(claims, userDetails);
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//            return authToken;
//        }
//
//        @Override
//        public boolean supports(Class<?> clazz) {
//            return NostrAuthToken.class.isAssignableFrom(clazz);
//        }
//    }
//
//    public static String generateNostrToken(String publicKey, String privateKey, URI uri, String httpMethod)
//            throws JsonProcessingException {
//        // We are creating JWT token, which internally has an array of tags, where we are
//        // adding uri and
//        // method tags in the token.
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode uriTag = mapper.createObjectNode();
//        uriTag.put("TagIdentifier", "uri");
//        ArrayNode uriData = uriTag.putArray("Data");
//        uriData.add(uri.getRawPath());
//        ObjectNode methodTag = mapper.createObjectNode();
//        methodTag.put("TagIdentifier", "method");
//        ArrayNode methodData = methodTag.putArray("Data");
//        methodData.add(httpMethod);
//        ArrayNode tags = mapper.createArrayNode();
//        tags.add(uriTag);
//        tags.add(methodTag);
//        // Create payload of the token
//        ObjectNode payload = mapper.createObjectNode();
//        payload.put(NostrAuthToken.CLAIM_PUBLIC_KEY, publicKey);
//        payload.put("tags", tags);
//        // Build the token with payload, generate signature using private key
//        String token = Jwts.builder().setHeaderParam(JwsHeader.KEY_ID, publicKey)
//                .signWith(SignatureAlgorithm.RS256, privateKey).setPayload(payload.toString()).compact();
//        return token;
//    }
//
//    /*
//     * NOTE: The following method is not exact Java equivalent, because as per the provided C# code, the IOptionsMonitor and ILoggerFactory dependency injections are being used, but their corresponding Java classes and implementation is not available.
//     * Therefore, below code is just an illustration of how the NostrAuthHandler can be wired with Spring security authentication manager.
//     * For actual implementation, more context and information is needed about IOptionsMonitor and ILoggerFactory classes and their implementation,
//     * which is outside the scope of this conversion.
//     */
//
//    public static AuthenticationManager getNostrAuthManager(UserDetailsService userDetailsService,
//                                                            PasswordEncoder passwordEncoder) {
//        AuthenticationManager authenticationManager = new AuthenticationManager() {
//
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                return null;
//            }
//        };
//
//        NostrAuthHandler nostrHandler = new NostrAuthHandler(userDetailsService, passwordEncoder);
//        //TODO
////        authenticationManager.authenticate(nostrHandler);
////                .setAuthenticationProvider(nostrHandler);
//
//        return authenticationManager;
//    }
//
//}