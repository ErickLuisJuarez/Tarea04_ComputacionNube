package com.formacionbdi.springboot.app.item.controllers;

import java.util.List;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.formacionbdi.springboot.app.item.models.Producto;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.service.ItemService;

@RestController
public class ItemController {
	
	@Autowired
	//@Qualifier("serviceRestTemplate")
	@Qualifier("serviceFeign")
	private ItemService itemService;
	
	@HystrixCommand(fallbackMethod = "fallbackListar")
	@GetMapping("/listar")
	public List<Item> listar(){
		return itemService.findAll();
	}

	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad){
		
        try {
			// Latencia artificial de 1.5s
			Thread.sleep(1500L); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		return itemService.findById(id, cantidad);
	}

	public Item metodoAlternativo(Long id, Integer cantidad) {
		Item item = new Item();
		Producto producto = new Producto();
		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setNombre("Carro de repuesto");
		producto.setPrecio(0.0);
		item.setProducto(producto);
		return item;
	}
	
	public List<Item> fallbackListar() {
	    Producto producto = new Producto();
	    producto.setId(0L);
	    producto.setNombre("Producto no disponible");
	    producto.setPrecio(0.0);

	    Item item = new Item();
	    item.setCantidad(0);
	    item.setProducto(producto);

	    return Arrays.asList(item);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		itemService.delete(id);
	}

}
