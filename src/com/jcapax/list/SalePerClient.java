package com.jcapax.list;

public class SalePerClient {
	private String nombreProducto;
	private String cantidad;
	private String fecha;
	
	
	public SalePerClient(String nombreProducto, String cantidad, String fecha) {
		super();
		this.nombreProducto = nombreProducto;
		this.cantidad = cantidad;
		this.fecha = fecha;
	}


	@Override
	public String toString() {
		return nombreProducto +" || "+ cantidad +" || "+ fecha;
	}


	public String getNombreProducto() {
		return nombreProducto;
	}


	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}


	public String getCantidad() {
		return cantidad;
	}


	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	

}
