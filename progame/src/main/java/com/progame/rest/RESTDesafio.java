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

import com.progame.dao.DesafioDAO;
import com.progame.dto.DesafiovsDTO;

@Path("/desafio")
public class RESTDesafio {
	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	protected void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	@POST
	@Path("/novoDesafioX1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean desafioX1(DesafiovsDTO desafio) {
		DesafioDAO dao = new DesafioDAO();
		boolean isOk = false;
		try {
			isOk = dao.insertDesafioVs1(desafio);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOk; 
	}
	
	@GET
	@Path("/todosDesafios/{matricula}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList <DesafiovsDTO> desafioX1(@PathParam("matricula") String matricula) {
		DesafioDAO dao = new DesafioDAO();
		ArrayList <DesafiovsDTO> desafios = null;
		try {
			desafios = dao.getAllDesafio(matricula);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desafios; 
	}
	
	
	@POST
	@Path("/respondeDesafio")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean repondeDesafio(DesafiovsDTO desafio) {
		boolean isOk = false;
		DesafioDAO dao = new DesafioDAO();
		try {
			isOk = dao.respondeDesafio(desafio);
		}catch(Exception e) {
			e.printStackTrace();
		}				
		return isOk;
	}
	
	@GET
	@Path("/desafiosCorrecao/{matricula}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<DesafiovsDTO> desafiosCorrecao(@PathParam("matricula") String matricula) {
		DesafioDAO dao = new DesafioDAO();
		ArrayList<DesafiovsDTO> desafios = null;
		try {
			desafios = dao.getCorrigirDesafio(matricula);
		}catch(Exception e) {
			e.printStackTrace();
		}				
		return desafios;
	}
	
	
	@POST
	@Path("/salvaAvaliacao")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean salvaAvaliacao(DesafiovsDTO desafio) {
		boolean isOk = false;
		DesafioDAO dao = new DesafioDAO();
		try {
			isOk = dao.salvaAvaliacao(desafio);
		}catch(Exception e) {
			e.printStackTrace();
		}				
		return isOk;
	}
	
}
