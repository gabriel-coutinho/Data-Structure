package adt.queue;

import adt.stack.Stack;
import adt.stack.StackImpl;
import adt.stack.StackOverflowException;
import adt.stack.StackUnderflowException;

public class QueueUsingStack<T> implements Queue<T> {

	private Stack<T> stack1;
	private Stack<T> stack2;

	// Apenas para saber se esta cheio.
	private int numElemenetos;
	int tamanho;

	public QueueUsingStack(int size) {
		stack1 = new StackImpl<T>(size);
		stack2 = new StackImpl<T>(size);
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		// Adiciona todos os elementos na pilha 1.
		if (!isFull()) {
			if (element != null) {
				// tratando os erros de pilhas.
				try {
					stack1.push(element);
					numElemenetos++;
				} catch (StackOverflowException e) {
				}
			}
		} else {
			throw new QueueOverflowException();
		}
	}

	@Override
	  	public T dequeue() throws QueueUnderflowException {
	  		// Chama o head pra remover o elemento que eu desejo.
	  		T resultado = head();
	  
	  		// Ao sair do head o elemento do pop da pilha 2 eh o elemento que foi o
	  		// primeiro a entrar na fila, portando o que deveria ser removido.
	  		if (!isEmpty()) {
	  			try {
	  				resultado = stack2.pop();
	  				numElemenetos--;
	  			} catch (StackUnderflowException e) {
	  			}
	  			return resultado;
	  
	  		} else {
	  			throw new QueueUnderflowException();
	  		}
	  	}

	@Override
	  	public T head() {
	  		// O ultimo elemento da pilha que eu insiro eh o primeiro elemento da fila.
	  		T resultado = null;
	  		if (stack2.isEmpty()) {
	  			while (!stack1.isEmpty() && !stack2.isFull()) {
	  				//Tratando erros de pilhas.
	  				try {
	  					stack2.push(stack1.pop());
	  				} catch (StackOverflowException e) {
	  				} catch (StackUnderflowException e) {
	  				}
	  			}
	  		}
	  		if (!stack2.isEmpty()) {
	  			resultado = stack2.top();
	  
	  		}
	  		return resultado;
	  	}

	@Override
	public boolean isEmpty() {
		if (stack1.isEmpty() && stack2.isEmpty()) {
			  			return true;
			  		}
			  		return false;
	}

	@Override
	public boolean isFull() {
		if (numElemenetos == tamanho) {
			  			return true;
			  		}
			  		return false;
	}
}

