package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> grafo;
	FoodDao dao = new FoodDao();
	
	public String creaGrafo(double calories) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//creo vertici
		Graphs.addAllVertices(this.grafo, dao.portionName(calories));
		
		List<Adiacenza> adiacenza = new ArrayList<>();
		adiacenza = dao.adiacenze(calories);
		//creo archi
		for(Adiacenza a: adiacenza) {
			Graphs.addEdgeWithVertices(this.grafo, a.getName1(), a.getName2(), a.getPeso());
		}
		
		return "Grafo creato con " + this.grafo.vertexSet().size() + " vertici e " + this.grafo.edgeSet().size() + "archi";
	}
	
	public Set<String> vertici(){
		return grafo.vertexSet();
	}
	
	public List<Vicini> correlate(String s){
		List<String> vicini = Graphs.neighborListOf(this.grafo, s);
		List<Vicini> lista = new ArrayList<>();
		int peso;
		for(String t: vicini) {
			peso = (int) (Math.round(this.grafo.getEdgeWeight(this.grafo.getEdge(s, t))*100)/100);
			lista.add(new Vicini(t, peso));
		}
		return lista;
	}
}
