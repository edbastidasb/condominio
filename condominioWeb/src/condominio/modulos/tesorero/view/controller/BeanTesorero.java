package condominio.modulos.tesorero.view.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import condominio.core.model.entities.Gasto;
import condominio.core.model.entities.TipoGasto;
import condominio.core.model.entities.Usuario;
import condominio.modulos.tesorero.model.ManagerTesorero;
import condominio.modulos.usuario.model.ManagerUsuario;
import condominio.modulos.util.view.controller.JSFUtil;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class BeanTesorero implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Gasto> listaGastos;
	private Gasto gasto = new Gasto();
	private Gasto editarGasto = new Gasto();
	private long idtgastofk;

	@EJB
	ManagerTesorero managerTesorero;
	ManagerUsuario managerUsuario;

	@PostConstruct
	public void init() {
		try {
			listaGastos = managerTesorero.findAllGastos();
		} catch (Exception e) {
			e.getStackTrace();
			JSFUtil.crearMensajeError(e.getMessage());
		}

	}

	public void actionListenerCargarGasto(Gasto g) {
		editarGasto = g;
	}

	public void actionListenerIngresarGasto() {
		try {
			TipoGasto tipoGasto=managerTesorero.findTipoGastoById(idtgastofk);
			Date fechatransaccion=new Date();
			Usuario usuario= managerUsuario.findUsuarioById(2);
			gasto.setTipoGasto(tipoGasto);
			gasto.setFechatransaccion(fechatransaccion);
			gasto.setUsuario(usuario);
			managerTesorero.ingresarGasto(gasto);
			listaGastos = managerTesorero.findAllGastos();
			gasto = new Gasto();
			JSFUtil.crearMensajeInfo("Gasto creado correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			listaGastos = managerTesorero.findAllGastos();
			JSFUtil.crearMensajeError(e.getMessage());
		}
	}

	public void actionListenerEditarGasto() {
		try {
			managerTesorero.editarGasto(editarGasto);
			listaGastos = managerTesorero.findAllGastos();
			JSFUtil.crearMensajeInfo("Rol editado correctamente.!");
		} catch (Exception e) {
			e.printStackTrace();
			listaGastos = managerTesorero.findAllGastos();
			JSFUtil.crearMensajeError(e.getMessage());
		}
	}

	public void actionListenerEliminarGasto(long idGasto) {
		try {
			managerTesorero.eliminarGasto(idGasto);
			listaGastos = managerTesorero.findAllGastos();
			JSFUtil.crearMensajeInfo("Gasto eliminado correctamente");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
	}

	public List<Gasto> getListaGastos() {
		return listaGastos;
	}

	public void setListaGastos(List<Gasto> listaGastos) {
		this.listaGastos = listaGastos;
	}

	public Gasto getGasto() {
		return gasto;
	}

	public void setGasto(Gasto gasto) {
		this.gasto = gasto;
	}

	public Gasto getEditarGasto() {
		return editarGasto;
	}

	public void setEditarGasto(Gasto editarGasto) {
		this.editarGasto = editarGasto;
	}

	public long getIdtgastofk() {
		return idtgastofk;
	}

	public void setIdtgastofk(long idtgastofk) {
		this.idtgastofk = idtgastofk;
	}

}
