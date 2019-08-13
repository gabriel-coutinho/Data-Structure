package adt.rbtree;

import java.util.LinkedList;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return blackHeight((RBNode<T>) root);
	}

	private int blackHeight(RBNode<T> node) {
		int result = 0;
		int esquerda = 0;
		int direita = 0;

		if (node.getColour() == Colour.BLACK) {
			result++;
		}
		if (!node.isEmpty()) {
			esquerda = blackHeight((RBNode<T>) node.getLeft());
			direita = blackHeight((RBNode<T>) node.getRight());
		}
		if (esquerda == direita) {
			return esquerda;
		}

		return -1;
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed
	 * by the type Colour.
	 */
	private boolean verifyNodesColour() {
		return verifyNodesColour((RBNode<T>)root);
	}
	private boolean verifyNodesColour(RBNode<T> node) {
		boolean aux = true;
		if (!node.isEmpty()) {
			if (node.getColour() == Colour.RED) {
				if (((RBNode<T>) node.getRight()).getColour() != Colour.BLACK
						|| ((RBNode<T>) node.getLeft()).getColour() != Colour.BLACK) {
					aux = false;
				}
			}
			boolean auxL = verifyNodesColour((RBNode<T>) node.getLeft());
			boolean auxR = verifyNodesColour((RBNode<T>) node.getRight());
			if(auxL == false || auxR == false) {
				aux = false;
			}
		}
		return aux;
	}

//	private boolean verifyChildrenOfRedNodes() {
//		return verifyChildrenOfRedNodes((RBNode<T>) root);
//	}
//
//	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
//
//		boolean aux = true;
//
//		if (node.getColour() == Colour.RED) {
//
//			if (!(((RBNode<T>) node.getLeft()).getColour() == Colour.BLACK)) {
//				aux = false;
//			}
//
//			if (!(((RBNode<T>) node.getRight()).getColour() == Colour.BLACK)) {
//				aux = false;
//			}
//
//		}
//
//		if (aux == false) {
//			return aux;
//
//		} else {
//
//			boolean auxL = true;
//
//			boolean auxR = true;
//
//			if (!(node.getLeft().isEmpty())) {
//				auxL = verifyChildrenOfRedNodes((RBNode<T>) node.getLeft());
//
//			}
//
//			if (!(node.getRight().isEmpty())) {
//				auxR = verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
//			}
//
//			if (auxL == false || auxR == false) {
//				return false;
//			} else {
//				return true;
//			}
//		}

	

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must
	 * be BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		return true;
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		boolean certa = true;
		if (blackHeight() == -1) {
			certa = false;
		}
		return certa;
	}

	@Override
	public void insert(T element) {
		if (element == null) {
			return;
		}
		insert((RBNode<T>) this.root, element);
		// print(rbPreOrder());
	}

	private void insert(RBNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setRight(new RBNode<T>());
			node.setLeft(new RBNode<T>());
			node.getRight().setParent(node);
			node.getLeft().setParent(node);
			node.setColour(Colour.RED);
			fixUpCase1(node);
		} else {

			if (element.compareTo(node.getData()) < 0) {

				insert(((RBNode<T>) node.getLeft()), element);

			} else {

				insert(((RBNode<T>) node.getRight()), element);
			}
		}
	}

	@Override
	public RBNode<T>[] rbPreOrder() {
		RBNode<T>[] result = new RBNode[size()];
		LinkedList<RBNode<T>> aux = RBOrder((RBNode<T>) this.root, new LinkedList<RBNode<T>>());

		if (aux.size() == 0) {
			return result;
		}

		for (int i = 0; i < aux.size(); i++) {
			result[i] = aux.get(i);
		}

		return result;
	}

	private LinkedList<RBNode<T>> RBOrder(RBNode<T> node, LinkedList<RBNode<T>> result) {
		if (!node.isEmpty()) {
			result.add(node);
			RBOrder((RBNode<T>) node.getLeft(), result);
			RBOrder((RBNode<T>) node.getRight(), result);
		}
		return result;

	}

	// public T[] preOrder() {
	// T[] result = (T[]) new Comparable[size()];
	// LinkedList<T> aux = preOrder(this.getRoot(), new LinkedList<T>());
	//
	// if (aux.size() == 0) {
	// return result;
	// }
	//
	// for (int i = 0; i < aux.size(); i++) {
	// result[i] = aux.get(i);
	// }
	//
	// return result;
	// }
	//
	// private LinkedList<T> preOrder(BSTNode<T> node, LinkedList<T> result) {
	// if (!node.isEmpty()) {
	// result.add(node.getData());
	// preOrder((BSTNode<T>) node.getLeft(), result);
	// preOrder((BSTNode<T>) node.getRight(), result);
	// }
	// return result;
	// }

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		if (node.equals(root)) {
			node.setColour(Colour.BLACK);
		} else {
			fixUpCase2(node);
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		if (((RBNode<T>) node.getParent()).getColour() == Colour.RED) {
			fixUpCase3(node);
		}
	}

	protected void fixUpCase3(RBNode<T> node) {
		RBNode<T> pai = (RBNode<T>) node.getParent();
		RBNode<T> avo = (RBNode<T>) pai.getParent();
		RBNode<T> tio = null;

		if (avo.getRight().equals(pai)) {
			tio = (RBNode<T>) avo.getLeft();
		} else {
			tio = (RBNode<T>) avo.getRight();
		}
		if (tio.getColour() == Colour.RED) {
			pai.setColour(Colour.BLACK);
			tio.setColour(Colour.BLACK);
			avo.setColour(Colour.RED);
			fixUpCase1(avo);
		} else {
			fixUpCase4(node);
		}
	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> proximo = node;
		RBNode<T> pai = (RBNode<T>) node.getParent();
		if (pai.getRight().equals(node) && pai.getParent().getLeft().equals(pai)) {
			leftRotation(pai);
			proximo = (RBNode<T>) node.getLeft();
		} else if (pai.getLeft().equals(node) && pai.getParent().getRight().equals(pai)) {
			rightRotation(pai);
			proximo = (RBNode<T>) node.getRight();
		}
		fixUpCase5(proximo);
	}

	protected void fixUpCase5(RBNode<T> node) {
		RBNode<T> pai = (RBNode<T>) node.getParent();
		RBNode<T> avo = (RBNode<T>) pai.getParent();
		pai.setColour(Colour.BLACK);
		avo.setColour(Colour.RED);

		if (pai.getLeft().equals(node)) {
			rightRotation(avo);
		} else {
			leftRotation(avo);
		}
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

	private void print(RBNode<T>[] x) {
		String s = "";
		for (int i = 0; i < x.length; i++) {
			s += x[i] + " ";
		}
	}
}
