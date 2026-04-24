package com.formacionbdi.springboot.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.formacionbdi.springboot.app.item.models.Producto;

// Para Feing:
//@FeignClient(name = "servicio-productos", url="localhost:8001")
// Para Feing con Ribbon:
@FeignClient(name = "servicio-productos")

public interface ProductoClienteRest {
	
	@GetMapping("/listar")
	public List<Producto> listar();
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id);
	
	@DeleteMapping("/eliminar/{id}")
	public void delete(@PathVariable Long id);

}
