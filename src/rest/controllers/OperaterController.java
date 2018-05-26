package rest.controllers;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import rest.KeyGenerator;
import rest.SimpleKeyGenerator;
import rest.dao.OperaterDAOImpl;
import rest.entities.Operater;
import rest.services.IOperaterService;
import rest.services.OperaterService;

@Stateless
@LocalBean
@Path("/operater")
@Transactional
public class OperaterController {

	private IOperaterService service;

	public OperaterController() {
		this.service = new OperaterService(new OperaterDAOImpl());
	}

	private KeyGenerator keyGenerator = new SimpleKeyGenerator();

	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("text/json")
	public Response login(Operater opt) {

		Operater operater = this.service.getOperater(opt.getUsername(), opt.getPassword());

		if (operater != null)
			return Response.ok().header(AUTHORIZATION, "Bearer " + issueToken(operater.getUsername())).build();
		else
			return Response.status(UNAUTHORIZED).build();
	}

	private String issueToken(String username) {
		Key key = keyGenerator.generateKey();
		String jwtToken = Jwts.builder().setSubject(username).setIssuer(getClass().getSimpleName())
				.setIssuedAt(new Date()).setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
				.signWith(SignatureAlgorithm.HS512, key).compact();
		return jwtToken;
	}

	@POST
	@Path("/register")
	@Consumes("application/json")
	@Produces("text/json")
	public Response add(Operater operater) {
		Operater opt = operater;
		service.add(opt);

		return Response.ok().build();
	}

	// ======================================
	// = Private methods =
	// ======================================

	private Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}
