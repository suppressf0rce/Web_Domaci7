package rest.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import rest.JWTTokenNeeded;
import rest.dao.ReceptDAOImpl;
import rest.entities.Recept;
import rest.services.IReceptService;
import rest.services.ReceptService;

@Stateless
@LocalBean
@Path("/recept")
public class ReceptController {
	
	private IReceptService service;
	
	public ReceptController() {
		this.service = new ReceptService(new ReceptDAOImpl());
	}
	


    @GET
    @Produces("text/json")
	public Response getAll(){
		return Response.ok(this.service.getAll()).build();
	}
    
    @GET @Path("{id}")
    @Produces("text/json")
    public Response getByID(@PathParam("id") int id) {
    	return Response.ok(this.service.getById(id)).build();
    }
   
    @POST
    @Consumes("application/json")
    @Produces("text/json")
    @JWTTokenNeeded
    public Response add(Recept recept) {
    	return Response.ok(this.service.add(recept)).build();
    }
    
    @GET @Path("/search/{name}")
    @Produces("text/json")
    public Response searchByName(@PathParam("name") String name){
    	return Response.ok(this.service.searchByName(name)).build();
    }
    
    @GET @Path("/range/{range}")
    @Produces("text/json")
    public Response range(@PathParam("range") int range){
    	return Response.ok(this.service.range100(range)).build();
    }

}
