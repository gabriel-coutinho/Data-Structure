package adt.bt;

import adt.bst.BSTNode;

public class Util {

	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * 
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> right = (BSTNode<T>) node.getRight();
		BSTNode<T> parent = (BSTNode<T>) node.getParent();

		node.setRight(right.getLeft());
		right.getLeft().setParent(node);
		right.setLeft(node);

		right.setParent(parent);

		if(!(node.getParent() == null)) {
			if (node.equals(node.getParent().getLeft())) {
				parent.setLeft(right);
			} else if (node.equals(node.getParent().getRight())) {
				parent.setRight(right);
			}
		}

		right.setParent(parent);
		node.setParent(right);

		return right;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * 
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> left = (BSTNode<T>) node.getLeft();
		BSTNode<T> parent = (BSTNode<T>) node.getParent();

		node.setLeft(left.getRight());
		left.getRight().setParent(node);
		left.setRight(node);

		left.setParent(parent);
		
		if(!(node.getParent() == null)) {
			if (node.equals(node.getParent().getLeft())) {
				parent.setLeft(left);
			} else if (node.equals(node.getParent().getRight())) {
				parent.setRight(left);
			}
		}

		left.setParent(parent);
		node.setParent(left);
		
		return left;

	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
