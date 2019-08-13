package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionOpenAddress;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		int probe = 0;

		while (probe < table.length) {

			int indice = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);

			if (table[indice] == null || table[indice] instanceof DELETED) {
				table[indice] = element;
				elements++;
				return;
			} else {
				probe++;
				COLLISIONS++;
			}
		}
		throw new HashtableOverflowException();
	}

	@Override
	public void remove(T element) {
		int probe = 0;
		boolean removido = false;
		int indice = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);

		while (probe < table.length && !removido && table[indice] != null) {

			if (table[indice].equals(element)) {
				table[indice] = new DELETED();
				elements--;
				removido = true;
			} else {
				probe++;
				indice = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);
			}
		}
	}

	@Override
	public T search(T element) {
		int probe = 0;
		int indice = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);

		while (probe < table.length && table[indice] != null) {

			if (table[indice].equals(element)) {
				return (T) table[indice];
			} else {
				probe++;
				indice = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);
			}
		}
		return null;
	}

	@Override
	public int indexOf(T element) {
		int indiceElemento = -1;
		int probe = 0;
		boolean encontrado = false;
		
		while (probe < table.length && !encontrado) {

			int indice = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);

			if (table[indice] == null) {
				encontrado = true;
			}

			else if (table[indice].equals(element)) {
				indiceElemento = indice;
				encontrado = true;
			} else {
				probe++;
			}
		}
		return indiceElemento;
	}
}
