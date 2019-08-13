package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		if (!node.isEmpty()) {
			return height((BSTNode<T>) node.getLeft()) - height((BSTNode<T>) node.getRight());
		}
		return 0;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		if (node == null) {
			return;
		}
		int balance = calculateBalance(node);
		if (balance > 1) {
			BSTNode<T> aux = (BSTNode<T>) node.getLeft();
			int balanceFilho = calculateBalance(aux);
			if (balanceFilho < 0) {
				leftRotation(aux);
				rightRotation(node);
			} else {
				rightRotation(node);
			}
		} else if (balance < -1) {
			BSTNode<T> aux = (BSTNode<T>) node.getRight();
			int balanceFilho = calculateBalance(aux);
			if (balanceFilho > 0) {
				rightRotation(aux);
				leftRotation(node);
			} else {
				leftRotation(node);
			}
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		if (node == null) {
			return;
		}
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while (parent != null) {
			rebalance(parent);
			parent = (BSTNode<T>) parent.getParent();
		}
	}

	@Override
	protected void insert(BSTNode<T> node, T element) {
		super.insert(node, element);
		rebalance(node);
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = super.search(element);
		super.remove(element);
		rebalanceUp(node);
	}
	
	protected void leftRotation(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return;
		}

		BSTNode<T> nodeRotation = Util.leftRotation(node);

		if (node == this.root) {
			this.root = nodeRotation;
		}
	}

	protected void rightRotation(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return;
		}

		BSTNode<T> nodeRotation = Util.rightRotation(node);

		if (node == this.root) {
			this.root = nodeRotation;
		}
	}
}
