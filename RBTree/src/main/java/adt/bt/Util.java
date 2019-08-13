package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> aux = (BSTNode<T>) node.getRight();
		BSTNode<T> esquerdaAux = (BSTNode<T>) aux.getLeft();
		aux.setLeft(node);
		node.setRight(esquerdaAux);
		esquerdaAux.setParent(node);
		aux.setParent(node.getParent());
		if(node.getParent() != null) {
			if(node.equals(node.getParent().getRight())) {
				node.getParent().setRight(aux);
			} else {
				node.getParent().setLeft(aux);
			}
		}
		node.setParent(aux);
		return aux;
	}


	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> aux = (BSTNode<T>) node.getLeft();
		BSTNode<T> direitaAux = (BSTNode<T>) aux.getRight();
		aux.setRight(node);
		node.setLeft(direitaAux);
		direitaAux.setParent(node);
		aux.setParent(node.getParent());
		if(node.getParent() != null) {
			if(node.equals(node.getParent().getRight())) {
				node.getParent().setRight(aux);
			} else {
				node.getParent().setLeft(aux);
			}
		}
		node.setParent(aux);
		return aux;
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
