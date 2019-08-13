package adt.splaytree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

public class SplayTreeImpl<T extends Comparable<T>> extends BSTImpl<T>
		implements SplayTree<T> {

	private void splay(BSTNode<T> node) {
		if(node == null || node.getParent() == null) {
			return;
		}
		if(node.getParent().getParent() == null) {
			if(node.equals(node.getParent().getRight())) {
				leftZig((BSTNode<T>) node.getParent());
			} else {
				rightZig((BSTNode<T>) node.getParent());
			}
		} else {
			if(node.equals(node.getParent().getRight())) {
				if(node.getParent().equals(node.getParent().getParent().getRight())) {
					leftZig((BSTNode<T>) node.getParent().getParent());
					leftZig((BSTNode<T>) node.getParent());
				} else {
					rightZig((BSTNode<T>) node.getParent());
					leftZig((BSTNode<T>) node.getParent().getParent());
				}
			} else {
				if(node.getParent().equals(node.getParent().getParent().getLeft())) {
					rightZig((BSTNode<T>) node.getParent().getParent());
					rightZig((BSTNode<T>) node.getParent());
				} else {
					leftZig((BSTNode<T>) node.getParent());
					rightZig((BSTNode<T>) node.getParent().getParent());
				}
			}
		}
		splay(node);
	}
	
	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> node = super.search(element);
		if(!node.isEmpty()) {
			splay(node);
		} else {
			splay((BSTNode<T>) node.getParent());
		}
		return node;
	}
	
	@Override
	public void insert(T element) {
		super.insert(element);
		BSTNode<T> node = super.search(element);
		splay(node);
	}
	
	@Override
	public void remove(T element) {
		BSTNode<T> node = super.search(element);
		BSTNode<T> nodeParent = (BSTNode<T>) node.getParent();
		if(!node.isEmpty()) {
			super.remove(element);
			splay(nodeParent);
		} else {
			splay(nodeParent);
		}
	}
	
	protected void leftZig(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return;
		}

		BSTNode<T> nodeRotacao = Util.leftRotation(node);

		if (node == this.root) {
			this.root = nodeRotacao;
		}
	}

	protected void rightZig(BSTNode<T> node) {
		if (node == null || node.isEmpty()) {
			return;
		}

		BSTNode<T> nodeRotacao = Util.rightRotation(node);

		if (node == this.root) {
			this.root = nodeRotacao;
		}
	}
}
