package com.progame.rest;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.progame.dao.ItemDAO;
import com.progame.dao.UsuarioDAO;
import com.progame.dto.ItemEspadaDTO;
import com.progame.dto.ItemEspadaDTO;
import com.progame.entity.Usuario;


@Path("/item")
public class RESTItem {
	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	protected void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@POST
	@Path("/compraEspada")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response insert(ItemEspadaDTO item) {
		ItemDAO dao = new ItemDAO();
		
		try {
			dao.insert_uso_espada(item);
			return Response.ok().entity("Item comprado!").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity("Erro ao cadastrar!").build();
		}

	}
	
	@GET
	@Path("/getEspadas")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ItemEspadaDTO> getEspadas() {
		ItemDAO dao = new ItemDAO();
		ArrayList <ItemEspadaDTO> espadas = null; 
		
		try {
			espadas = dao.getEspadas();
		}catch(Exception e) {
			e.printStackTrace();
		}				
		return espadas;
	}
	
	@GET
	@Path("/getMinhasCompras/{matricula}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ItemEspadaDTO> getMinhasCompras(@PathParam("matricula") String matricula) {
		ItemDAO dao = new ItemDAO();
		ArrayList <ItemEspadaDTO> all = null; 
		
		try {
			all = dao.minhasCompras(matricula);
		}catch(Exception e) {
			e.printStackTrace();
		}				
		return all;
	}
	
	@GET
	@Path("/getOneEspada/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList <ItemEspadaDTO> getOneEspada(@PathParam("id") int id) {
		ItemDAO dao = new ItemDAO();
		ArrayList <ItemEspadaDTO> espada = null; 
		
		try {
			espada = dao.getOneEspada(id);
		}catch(Exception e) {
			e.printStackTrace();
		}				
		return espada;
	}
	
	@POST
	@Path("/atualizaMinhasCompras")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean atualizaMinhasCompras(ItemEspadaDTO item) {
		ItemDAO dao = new ItemDAO();
		ItemEspadaDTO espada = null; 
		boolean isOk = false;
		try {
			isOk = dao.atualizaMinhasCompras(item);
		}catch(Exception e) {
			e.printStackTrace();
		}				
		return isOk;
	}
}

