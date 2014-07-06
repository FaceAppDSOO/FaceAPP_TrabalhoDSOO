package br.com.dsoo.facebook.logic.utils;

import java.util.LinkedList;
import java.util.List;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.PagableList;
import facebook4j.Paging;

public class Pager{
	
	private Facebook fb;
	
	public Pager(Facebook fb){
		this.fb = fb;
	}
	
	public <T> T getLast(PagableList<T> list) throws FacebookException{
		T obj = null;
		
		Paging<T> pag = null;
		while(list != null && list.size() > 0){
			obj = list.get(list.size() - 1);
			
			if((pag = list.getPaging()) != null){
				list = fb.fetchNext(pag);
				continue;
			}
			
			break;
		}
		
		return obj;
	}
	
	public <T> List<T> getList(PagableList<T> list) throws FacebookException{
		List<T> ret = new LinkedList<T>();
		
		Paging<T> pag = null;
		while(list != null){
			for(T obj : list){
				if(obj == null){
					break;
				}
				
				ret.add(obj);
			}
			
			if((pag = list.getPaging()) != null){
				list = fb.fetchNext(pag);
				continue;
			}
			
			break;
		}
		
		return ret;
	}
}
