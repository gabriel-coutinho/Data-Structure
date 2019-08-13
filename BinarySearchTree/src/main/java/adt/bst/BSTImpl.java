package adt.bst;

import java.util.LinkedList;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.getRoot());
	}

	private int height(BSTNode<T> node) {
		int result = -1;
		if (!node.isEmpty()) {
			int esquerda = height((BSTNode<T>) node.getLeft());
			int direita = height((BSTNode<T>) node.getRight());
			result = 1 + Math.max(esquerda, direita);
		}
		return result;
	}

	@Override
	public BSTNode<T> search(T element) {
		if (isNull(element)) {
			return new BSTNode<T>();
		}
		return search(this.getRoot(), element);
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {

		if (node.isEmpty() || node.getData().equals(element)) {
			return node;
		} else {

			if (element.compareTo(node.getData()) > 0) {

				return search(((BSTNode<T>) node.getRight()), element);

			} else {

				return search(((BSTNode<T>) node.getLeft()), element);
			}

		}
	}

	@Override
	public void insert(T element) {
		if (isNull(element)) {
			return;
		}
		insert(this.getRoot(), element);
	}

	private void insert(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setRight(new BSTNode<T>());
			node.setLeft(new BSTNode<T>());
			node.getRight().setParent(node);
			node.getLeft().setParent(node);

		} else {

			if (element.compareTo(node.getData()) < 0) {

				insert(((BSTNode<T>) node.getLeft()), element);

			} else {

				insert(((BSTNode<T>) node.getRight()), element);
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return maximum(this.getRoot());
	}

	private BSTNode<T> maximum(BSTNode<T> node) {
		BSTNode<T> result = node;
		if (!node.isEmpty()) {
			if (result.getRight().isEmpty()) {
				return result;
			}
			result = maximum((BSTNode<T>) node.getRight());
		} else {
			result = null;
		}
		return result;
	}

	@Override
	public BSTNode<T> minimum() {
		return minimum(this.getRoot());
	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		BSTNode<T> result = node;
		if (!node.isEmpty()) {
			if (result.getLeft().isEmpty()) {
				return result;
			}
			result = minimum((BSTNode<T>) node.getLeft());
		} else {
			result = null;
		}
		return result;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		if (isNull(element)) {
			return null;
		}
		BSTNode<T> result = null;
		BSTNode<T> temp = search(element);
		if (temp.isEmpty()) {
			return null;
		}
		if (!temp.getRight().isEmpty()) {
			result = minimum((BSTNode<T>) temp.getRight());
		} else {
			result = (BSTNode<T>) temp.getParent();
			while (result != null && temp.equals((BSTNode<T>) result.getRight())) {
				temp = result;
				result = (BSTNode<T>) result.getParent();
			}
		}
		return result;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		if (isNull(element)) {
			return null;
		}
		BSTNode<T> result = null;
		BSTNode<T> temp = search(element);
		if (temp.isEmpty()) {
			return null;
		}
		if (!temp.getLeft().isEmpty()) {
			result = maximum((BSTNode<T>) temp.getLeft());
		} else {
			result = (BSTNode<T>) temp.getParent();
			while (result != null && temp.equals((BSTNode<T>) result.getLeft())) {
				temp = result;
				result = (BSTNode<T>) result.getParent();
			}
		}
		return result;
	}

	@Override
	public void remove(T element) {
		if (isNull(element)) {
			return;
		}
		BSTNode<T> node = search(element);
		if (node.isEmpty()) {
			return;
		}
		if (getGrau(node) == 0) {
			removeFolha(node);
		} else if (getGrau(node) == 1) {
			removeGrauUm(node);
		} else {
			removeGrauDois(node);
		}
	}

	private void removeGrauDois(BSTNode<T> node) {
		BSTNode<T> sucessor = sucessor(node.getData());
		
		if (sucessor == null) {
			return;
		}
		
		int grauSucessor = getGrau(sucessor);
		node.setData(sucessor.getData());
		
		if (grauSucessor == 0) {
			removeFolha(sucessor);
		}else if (grauSucessor == 1) {
			removeGrauUm(sucessor);
		} else {
			removeGrauDois(sucessor);
		}
	}

	private void removeGrauUm(BSTNode<T> node) {
		if (node.getParent() == null) {
			if (!node.getRight().isEmpty()) {
				node.getRight().setParent(null);
				this.root = (BSTNode<T>) node.getRight();
			} else {
				node.getLeft().setParent(null);
				this.root = (BSTNode<T>) node.getLeft();
			}
		} else {
			BSTNode<T> temp = null;
			
			if (!node.getRight().isEmpty()) {
				temp = (BSTNode<T>) node.getRight();
			} else {
				temp = (BSTNode<T>) node.getLeft();
			}
			
			temp.setParent(node.getParent());
			
			if (node.getParent().getRight().equals(node)) {
				node.getParent().setRight(temp);
			} else {
				node.getParent().setLeft(temp);
			}
		}
	}

	private void removeFolha(BSTNode<T> node) {
		node.setData(null);
	}

	private int getGrau(BSTNode<T> node) {
		int grau = 0;
		if (!node.getRight().isEmpty()) {
			grau++;
		}
		if (!node.getLeft().isEmpty()) {
			grau++;
		}
		return grau;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] preOrder() {
		T[] result = (T[]) new Comparable[size()];
		LinkedList<T> aux = preOrder(this.getRoot(), new LinkedList<T>());

		if (aux.size() == 0) {
			return result;
		}

		for (int i = 0; i < aux.size(); i++) {
			result[i] = aux.get(i);
		}

		return result;
	}

	private LinkedList<T> preOrder(BSTNode<T> node, LinkedList<T> result) {
		if (!node.isEmpty()) {
			result.add(node.getData());
			preOrder((BSTNode<T>) node.getLeft(), result);
			preOrder((BSTNode<T>) node.getRight(), result);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] order() {
		T[] result = (T[]) new Comparable[size()];
		LinkedList<T> aux = order(this.getRoot(), new LinkedList<T>());

		if (aux.size() == 0) {
			return result;
		}

		for (int i = 0; i < aux.size(); i++) {
			result[i] = aux.get(i);
		}

		return result;

	}

	private LinkedList<T> order(BSTNode<T> node, LinkedList<T> result) {
		if (!node.isEmpty()) {
			order((BSTNode<T>) node.getLeft(), result);
			result.add(node.getData());
			order((BSTNode<T>) node.getRight(), result);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] postOrder() {
		T[] result = (T[]) new Comparable[size()];
		LinkedList<T> aux = postOrder(this.getRoot(), new LinkedList<T>());
		if (aux.size() == 0) {
			return result;
		}
		for (int i = 0; i < aux.size(); i++) {
			result[i] = aux.get(i);
		}

		return result;
	}

	private LinkedList<T> postOrder(BSTNode<T> node, LinkedList<T> result) {
		if (!node.isEmpty()) {
			postOrder((BSTNode<T>) node.getLeft(), result);
			postOrder((BSTNode<T>) node.getRight(), result);
			result.add(node.getData());
		}
		return result;
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

	private boolean isNull(T element) {
		if (element == null) {
			return true;
		}
		return false;
	}

}
