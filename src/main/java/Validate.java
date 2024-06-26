import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@WebFilter("/*")
public class Validate extends HttpFilter implements Filter {
    private static final long serialVersionUID = 1L;
	private JwtParser jwtParser;

    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        String secretKey = context.getInitParameter("SECRET_KEY");
        if (secretKey == null) {
            throw new ServletException("Secret key not configured in context parameters");
        }
        

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        jwtParser = Jwts.parser()
                        .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                        .build();
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

    	String requestUri = request.getRequestURI();
    	if(requestUri.equals("/FlexAPI/validateUser") || requestUri.equals("/FlexAPI/addUser")) {
    		chain.doFilter(request, response);
    		return;
    	}
    	
    	String token = request.getHeader("access");	
        //String token = request.getParameter("access");
    	/*System.out.println("TOKEN: "+token);
    	
    	Enumeration<String> headers = request.getHeaderNames();
    	while(headers.hasMoreElements()) {
    		String headerName = headers.nextElement();
    		System.out.println(headerName + " : " + request.getHeader(headerName));
    	}*/
    	
        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing token");
            return;
        }

        try {
        	
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            request.setAttribute("idToken", claims.getSubject());        
            chain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
        } catch (SignatureException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token signature");
        } catch (Exception e) {
        	System.out.println("\n\n\n");
        	e.printStackTrace();
        	System.out.println("\n\n\n");
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if necessary
    }
}
