package br.com.dsoo.facebook.logic.constants;

public enum Family{
	GRANDPARENT("grand(fa|mo)ther"),
	PARENTS("(fa|mo)ther"),
	UNCLES("(uncle|aunt)"),
	BROTHERS_AND_SISTERS("(broth|sist)er"),
	COUSINS("cousin"),
	SONS("(son|daughter)"),
	GRANDSONS("grand(son|daughter)"),
	UNDEFINED("undefined");

	public final String value;
	Family(String type){
		this.value = type;
	}
}
