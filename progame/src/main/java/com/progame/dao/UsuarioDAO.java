package com.progame.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.progame.dto.*;
import java.util.Random;

import com.progame.entity.Usuario;

public class UsuarioDAO {
	public UsuarioDAO() {

	}

	public boolean insert(Usuario usuario) throws Exception {

		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		StringBuilder sql = new StringBuilder();

		String senha = usuario.getSenha();
		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(senha.getBytes(),0,senha.length());
		String criptografia = new BigInteger(1,m.digest()).toString(16);

		sql.append("INSERT INTO usuario ");
		sql.append(" ( ");
		sql.append(" nomeUsuario, ");
		sql.append(" matricula, ");
		sql.append(" senha, ");		
		sql.append(" idTipoPerfil, ");
		sql.append(" idPersonagem, ");
		sql.append(" email, ");
		sql.append(" pontuacao, ");
		sql.append(" level, ");
		sql.append(" desativado ");
		sql.append(" ) ");
		sql.append(" VALUES (?,?,?,?,?,?,?,?,?);");

		try {
			pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, usuario.getNomeUsuario());
			pstmt.setString(2, usuario.getMatricula());
			pstmt.setString(3, criptografia);
			pstmt.setString(4, usuario.getIdTipoPerfil());
			pstmt.setString(5, "7");
			pstmt.setString(6, usuario.getEmail());
			pstmt.setString(7, "0");
			pstmt.setString(8, "1");
			pstmt.setString(9, "N");

			pstmt.executeUpdate();

		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}

		return true;

	}

	public Usuario getLogin(Usuario user) throws Exception {
		Usuario achou = null;
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;


		String senha = user.getSenha();
		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(senha.getBytes(),0,senha.length());
		String criptografia = new BigInteger(1,m.digest()).toString(16);


		pstmt = db.prepareStatement("select nomeUsuario, matricula, senha, idTipoPerfil, idPersonagem, email, pontuacao, level, desativado from usuario");

		try {
			result = pstmt.executeQuery();
			while (result.next()) {
				if (user.getMatricula().equals(result.getString("matricula"))
						&& criptografia.equals(result.getString("senha"))) {
					achou = new Usuario();
					achou.setNomeUsuario(result.getString("nomeUsuario"));
					achou.setMatricula(result.getString("matricula"));
					//achou.setSenha(result.getString("senha"));
					achou.setIdTipoPerfil(result.getString("idTipoPerfil"));
					achou.setIdPersonagem(result.getString("idPersonagem"));
					achou.setEmail(result.getString("email"));		
					achou.setPontuacao(result.getString("pontuacao"));				
					achou.setLevel(result.getString("level"));		
					achou.setDesativado(result.getString("desativado"));
					if(achou.getDesativado().equals("S")) {
						reativaPerfil(achou.getMatricula());						
					}
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return achou;
	}

	public boolean reativaPerfil(String matricula) throws Exception {
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;

		Boolean execute = false;
		StringBuilder sql = new StringBuilder();

		sql.append("update USUARIO set desativado='N' where matricula="+matricula);

		try {
			pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			execute = true;
		}catch(Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return execute;
	}

	public boolean desativaPerfil(String matricula) throws Exception {
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;

		Boolean execute = false;
		StringBuilder sql = new StringBuilder();

		sql.append("update USUARIO set desativado='S' where matricula="+matricula);

		try {
			pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			execute = true;
		}catch(Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return execute;
	}


	public boolean updatePontuacao(int pontos, String matricula, String levelAtual) throws Exception {
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;

		Boolean execute = false;
		StringBuilder sql = new StringBuilder();

		sql.append("update USUARIO set pontuacao="+pontos+" where matricula="+matricula);

		if (pontos < 800) {
			updateLevel(1 , matricula);
		} else if(pontos >=800 && pontos<1600) {
			updateLevel(2 , matricula);

		} else if(pontos >=1600 && pontos < 3200) {
			updateLevel(3 , matricula);

		} else if(pontos >=3200 && pontos < 6400) {
			updateLevel(4 , matricula);

		} else if(pontos >=6400 && pontos < 12800) {
			updateLevel(5 , matricula);

		} else if(pontos >=12800 && pontos < 25600) {
			updateLevel(6 , matricula);

		} else if(pontos >=25600 && pontos < 51200) {
			updateLevel(7 , matricula);

		} else if(pontos >=51200 && pontos < 102400) {
			updateLevel(8 , matricula);

		} else if(pontos >=102400 && pontos < 204800) {
			updateLevel(9 , matricula);
		} else if(pontos >=204800 && pontos < 409600) {
			updateLevel(10 , matricula);

		} else if(pontos >=409600 && pontos < 819200) {
			updateLevel(11 , matricula);

		} else if(pontos >=819200 && pontos < 819200) {
			updateLevel(12 , matricula);
		} 


		try {
			pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			execute = true;
		}catch(Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return execute;
	}


	public boolean updateLevel(int level, String matricula) throws Exception {
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;

		Boolean execute = false;
		StringBuilder sql = new StringBuilder();

		sql.append("update USUARIO set level="+level+" where matricula="+matricula);

		try {
			pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			execute = true;
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return execute;
	}


	public Usuario getUsuario(String matricula) throws Exception {
		Usuario achou = null;
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;

		pstmt = db.prepareStatement("select nomeUsuario, matricula, senha, idTipoPerfil, idPersonagem, email, desativado, "
				+ "pontuacao, level from usuario where matricula="+matricula);

		try {
			result = pstmt.executeQuery();
			while (result.next()) {
				if (matricula.equals(result.getString("matricula"))) {
					achou = new Usuario();
					achou.setNomeUsuario(result.getString("nomeUsuario"));
					achou.setMatricula(result.getString("matricula"));
					//achou.setSenha(result.getString("senha"));
					achou.setIdTipoPerfil(result.getString("idTipoPerfil"));
					achou.setIdPersonagem(result.getString("idPersonagem"));
					achou.setEmail(result.getString("email"));		
					achou.setPontuacao(result.getString("pontuacao"));				
					achou.setLevel(result.getString("level"));	
					achou.setDesativado(result.getString("desativado"));				
				}
			}

		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return achou;
	}

	public ArrayList<Usuario> getAllUsuario() throws Exception {
		Usuario achou = null;
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;
		ArrayList <Usuario> usuario = new ArrayList<Usuario>();

		pstmt = db.prepareStatement("select * from usuario where idTipoPerfil=3 and desativado !='S' order by pontuacao desc;");

		try {
			result = pstmt.executeQuery();
			while (result.next()) {
				if(!result.getString("desativado").equals("S")) {
					achou = new Usuario();
					achou.setNomeUsuario(result.getString("nomeUsuario"));
					achou.setMatricula(result.getString("matricula"));
					//achou.setSenha(result.getString("senha"));
					achou.setIdTipoPerfil(result.getString("idTipoPerfil"));
					achou.setIdPersonagem(result.getString("idPersonagem"));
					achou.setEmail(result.getString("email"));		
					achou.setPontuacao(result.getString("pontuacao"));				
					achou.setLevel(result.getString("level"));	
					achou.setDesativado(result.getString("desativado"));				
					usuario.add(achou);
				}

			}

		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return usuario;
	}

	public boolean tiraPonto(int penalidade, Usuario user ) throws Exception {
		boolean isOk = false;
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;

		ArrayList <Usuario> all = null; 
		all = getAllUsuario();				
		try {
			int novo = 0;
			for(int i = 0; i < all.size();i++) {
				novo = Integer.parseInt(all.get(i).getPontuacao()) - penalidade;
				if((!all.get(i).getMatricula().equals(user.getMatricula())) && Integer.parseInt(all.get(i).getPontuacao()) >= penalidade  ) {
					StringBuilder sql = new StringBuilder();	
					sql.append("update USUARIO set pontuacao='"+novo+"' where matricula='"+all.get(i).getMatricula()+"';");
					pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
					pstmt.executeUpdate();
					updatePontuacao(novo, all.get(i).getMatricula(), all.get(i).getLevel());
				}
			}
			isOk = true;
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return isOk;
	}

	public boolean pagaItem(int preco, Usuario user) throws Exception {
		boolean isOk = false;
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;

		StringBuilder sql = new StringBuilder();	

		try {
			int novo = Integer.parseInt(user.getPontuacao()) - preco;
			sql.append("update USUARIO set pontuacao='"+novo+"' where matricula='"+user.getMatricula()+"';");
			pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			updatePontuacao(novo, user.getMatricula(), user.getLevel());
			isOk = true;
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return isOk;	
	}

	// Tira pontos de um determinado usuario
	public boolean tiraPonto(int penalidade, int pontuacaoAtual, String matricula) throws Exception {
		boolean isOk = false;
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;

		StringBuilder sql = new StringBuilder();	
		int novo = pontuacaoAtual - penalidade;

		try {
			if (novo < 0) { 
				novo = 0;
			}

			sql.append("update USUARIO set pontuacao='"+novo+"' where matricula='"+matricula+"';");
			pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			updatePontuacao(novo, matricula, "");
			isOk = true;
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return isOk;
	}

	public boolean atualizaDados(Usuario usuario) throws Exception {
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;

		Boolean execute = false;
		StringBuilder sql = new StringBuilder();

		sql.append("update USUARIO set email='"+usuario.getEmail()+"', desativado='"+usuario.getDesativado()+"', nomeUsuario='"+usuario.getNomeUsuario()+"' where matricula="+usuario.getMatricula());

		try {
			pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			execute = true;
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return execute;
	}

	public String geraSenha() {
		int[] v = new int[10]; // vetor de 10 posições
		Random gerador = new Random(); // nosso gerador de números
		boolean b = false; // um controlador

		for (int i = 0; i < v.length;) {
			if (i == 0) {
				v[i] = gerador.nextInt(20) + 1;
				i++;
			} else {
				v[i] = gerador.nextInt(20) + 1;
				b = false;
				for (int j = 0; j < i; j++) {
					if (v[i] == v[j]) {
						b = false;
						break;
					} else {
						b = true;
					}
				}
				if (b) {
					i++;
				}
			}
		}
		System.out.print("======> senhaaa: "+ v.toString()+"\n");

		return v.toString();

	}

	public String novaSenha(String email) throws Exception {
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;

		String senha = geraSenha(); 
		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(senha.getBytes(),0,senha.length());
		String criptografia = new BigInteger(1,m.digest()).toString(16);

		StringBuilder sql = new StringBuilder();

		sql.append("update USUARIO set senha='"+criptografia+"' where email='"+email+"';");

		try {
			pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return senha;
	}
	
	
	
	public boolean comparaSenha(String senhaAntiga, String matricula) throws Exception {
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;

		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(senhaAntiga.getBytes(),0,senhaAntiga.length());
		String criptografia = new BigInteger(1,m.digest()).toString(16);

		boolean exists = false;

		pstmt = db.prepareStatement("select * from usuario");

		try {
			result = pstmt.executeQuery();
			while (result.next()) {
				if (criptografia.equals(result.getString("senha")) &&
						matricula.equals(result.getString("matricula"))) {
					exists = true;
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return exists;
	}

	public String defineNovaSenha(SenhaUsuarioDTO user) throws Exception {
		Connection db = ConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;

		ResultSet result = null;
		
		if(!comparaSenha(user.getSenhaAntiga(), user.getMatricula())) {
			return "Senha atual incorreta!";
		}
		
		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(user.getNovaSenha().getBytes(),0,user.getNovaSenha().length());
		String criptografia = new BigInteger(1,m.digest()).toString(16);

		StringBuilder sql = new StringBuilder();

		sql.append("update USUARIO set senha='"+criptografia+"' where matricula='"+user.getMatricula()+"';");

		try {
			pstmt = db.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		} finally {
			if (pstmt != null)
				pstmt.close();
			db.close();
		}
		return "Nova senha definida com sucesso!";
	}

}
